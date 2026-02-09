package com.loadot.controller;

import com.loadot.dto.CharacterInfoDto;
import com.loadot.entity.Character;
import com.loadot.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class LoaDotAppController {

	private final CharacterService characterService;

	// 2. HTTP GET 요청을 처리하고, 경로에 있는 이름을 변수로 받음
	@GetMapping("/{characterName}")
	public ResponseEntity<Character> getCharacterInfo(@PathVariable String characterName) {
		// 3. 서비스에서 저장 및 반환된 Character 엔티티를 클라이언트에게 전달
		Character character = characterService.getAndSaveCharacter(characterName);
		return ResponseEntity.ok(character);
	}
}
