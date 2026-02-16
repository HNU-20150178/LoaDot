package com.loadot.service;

import com.loadot.dto.CharacterInfoDto;
import com.loadot.entity.Character;
import com.loadot.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor // 생성자 주입 자동화 (WebClient, Repository 가져오기)
public class CharacterService {

    private final WebClient lostarkWebClient; // Config에서 만든 빈(Bean) 주입
    private final CharacterRepository characterRepository;

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

        // DTO 데이터를 엔티티(Entity)로 변환
        return new Character(
                dto.getCharacterName(), dto.getServerName(),
                dto.getCharacterClassName(),
                Double.parseDouble(dto.getItemAvgLevel().replace(",", "")), // ex) "1,620.00" -> 1620.0
                dto.getGuildName(),
                dto.getExpeditionLevel(),
                dto.getCharacterImage()
        );
    }
}