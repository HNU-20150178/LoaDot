package com.loadot.controller;

import com.loadot.dto.response.CharacterInfoResponse;
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

	@GetMapping("/{characterName}")
	public ResponseEntity<CharacterInfoResponse> getCharacterInfo(@PathVariable String characterName) {
		CharacterInfoResponse response = characterService.getAndSaveCharacter(characterName);
		return ResponseEntity.ok(response);
	}
}
