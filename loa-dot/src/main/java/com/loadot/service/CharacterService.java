package com.loadot.service;

import com.loadot.dto.CharacterInfoDto;
import com.loadot.entity.Character;
import com.loadot.entity.CharacterHistory;
import com.loadot.repository.CharacterHistoryRepository;
import com.loadot.repository.CharacterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 주입 자동화 (WebClient, Repository 가져오기)
public class CharacterService {

    private final WebClient lostarkWebClient; // Config에서 만든 빈(Bean) 주입
    private final CharacterRepository characterRepository;
    private final CharacterHistoryRepository characterHistoryRepository;

    @Transactional
    public Character getAndSaveCharacter(String characterName) {
        // 로스트아크 API 호출 (캐릭터 정보 가져오기)
        CharacterInfoDto dto = lostarkWebClient.get()
                .uri("/armories/characters/" + characterName + "/profiles")
                .retrieve()
                .bodyToMono(CharacterInfoDto.class) // JSON을 DTO로 변환
                .block(); // 비동기를 동기 방식으로 기다림

        if (dto == null) {
            throw new RuntimeException("캐릭터 정보를 찾을 수 없습니다.");
        }

        // DB에서 기존 캐릭터 확인 (없으면 신규 생성, 있으면 업데이트)
        Character character = characterRepository.findByCharacterName(characterName)
                .map(existing -> existing.update(dto)) // 업데이트 로직
                .orElseGet(() -> characterRepository.save(new Character(dto))); // 신규 생성

        // 캐릭터 히스토리 테이블에 저장
        this.recordCharacterHistory(character);

        return character;
    }

    /**
     * 캐릭터 히스토리 저장
     * @param character 검색된 현재 캐릭터 엔티티
     */
    private void recordCharacterHistory(Character character) {
        // 1. 가장 최근의 히스토리
        Optional<CharacterHistory> latestHistory = characterHistoryRepository.findTop1ByCharacterOrderByRecordedAtDesc(character);

        // 2. 전체 히스토리 중 최고 전투력 조회 (전투력 비교용)
        Long maxCombatPowerResult = characterHistoryRepository.findMaxCombatPowerByCharacter(character);

        // 결과가 null이면 0L을 사용하도록 안전하게 처리
        long maxCombatPower = (maxCombatPowerResult != null) ? maxCombatPowerResult : 0L;

        boolean isLevelChanged = latestHistory.isEmpty() || !latestHistory.get().getItemAvgLevel().equals(character.getItemAvgLevelDouble());
        boolean isCombatPowerNewRecord = character.getCombatPowerLong() > maxCombatPower;

        // 아이템 레벨이 변했거나, 전투력이 경신되었다면 저장
        if (isLevelChanged || isCombatPowerNewRecord) {
            Long combatPowerToRecord = Math.max(character.getCombatPowerLong(), maxCombatPower);

            characterHistoryRepository.save(new CharacterHistory(character, character.getItemAvgLevelDouble(), combatPowerToRecord));
        }
    }
}