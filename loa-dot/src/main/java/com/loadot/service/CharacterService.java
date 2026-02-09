package com.loadot.service;

import com.loadot.dto.CharacterInfoDto;
import com.loadot.entity.Character;
import com.loadot.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor // 생성자 주입 자동화 (WebClient, Repository 가져오기)
@Transactional
public class CharacterService {

    private final WebClient lostarkWebClient; // Config에서 만든 빈(Bean) 주입
    private final CharacterRepository characterRepository;

    @Transactional
    public Character getAndSaveCharacter(String characterName) {
        // 1. 로스트아크 API 호출 (캐릭터 정보 가져오기)
        CharacterInfoDto dto = lostarkWebClient.get()
                .uri("/armories/characters/" + characterName + "/profiles")
                .retrieve()
                .bodyToMono(CharacterInfoDto.class) // JSON을 DTO로 변환
                .block(); // 비동기를 동기 방식으로 기다림

        if (dto == null) {
            throw new RuntimeException("캐릭터 정보를 찾을 수 없습니다.");
        }

        // 2. DTO 데이터를 엔티티(Entity)로 변환
        Character character = new Character(
                dto.getCharacterName(),
                "서버정보미구현", // API 응답에 따라 수정 필요
                dto.getCharacterClassName(),
                Double.parseDouble(dto.getItemAvgLevel().replace(",", "")) // "1,620.00" -> 1620.0
        );

        // 3. DB에 저장 (이미 있으면 업데이트, 없으면 삽입)
        // 실제로는 findByName으로 체크 후 수정하는 로직을 추가하면 더 좋습니다!
        return characterRepository.save(character);
    }
}