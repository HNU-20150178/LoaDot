package com.loadot.service;

import com.loadot.dto.CharacterInfoDto;
import com.loadot.dto.response.CharacterInfoResponse;
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
@RequiredArgsConstructor
public class CharacterService {

    private final WebClient lostarkWebClient;
    private final CharacterRepository characterRepository;
    private final CharacterHistoryRepository characterHistoryRepository;

    @Transactional
    public CharacterInfoResponse getAndSaveCharacter(String characterName) {
        // 로스트아크 API 호출 (캐릭터 정보 가져오기)
        CharacterInfoDto dto = lostarkWebClient.get()
                .uri("/armories/characters/" + characterName + "/profiles")
                .retrieve()
                .bodyToMono(CharacterInfoDto.class)
                .block();

        if (dto == null) {
            throw new RuntimeException("캐릭터 정보를 찾을 수 없습니다.");
        }

        // DB에서 기존 캐릭터 확인 (없으면 신규 생성, 있으면 업데이트)
        Character character = characterRepository.findByCharacterName(characterName)
                .map(existing -> existing.update(dto))
                .orElseGet(() -> characterRepository.save(new Character(dto)));

        // 캐릭터 히스토리 테이블에 저장
        this.recordCharacterHistory(character);

        // Entity -> Response DTO 변환 후 반환
        return CharacterInfoResponse.from(character, dto);
    }

    /**
     * 캐릭터 히스토리 저장
     * @param character 검색된 현재 캐릭터 엔티티
     */
    private void recordCharacterHistory(Character character) {
        Optional<CharacterHistory> latestHistory = characterHistoryRepository.findTop1ByCharacterOrderByRecordedAtDesc(character);
        Double maxCombatPowerResult = characterHistoryRepository.findMaxCombatPowerByCharacter(character);
        Double maxCombatPower = (maxCombatPowerResult != null) ? maxCombatPowerResult : 0.0;

        boolean isLevelChanged = latestHistory.isEmpty() || !latestHistory.get().getItemAvgLevelDouble().equals(character.getItemAvgLevelDouble());
        boolean isCombatPowerNewRecord = character.getCombatPowerDouble() > maxCombatPower;

        if (isLevelChanged || isCombatPowerNewRecord) {
            Double combatPowerToRecord = Math.max(character.getCombatPowerDouble(), maxCombatPower);
            characterHistoryRepository.save(new CharacterHistory(character, character.getItemAvgLevelDouble(), combatPowerToRecord));
        }
    }
}