package com.loadot.rest;

import com.loadot.dto.CharacterGemsDto;
import com.loadot.dto.response.CharacterInfoResponse;
import com.loadot.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class LoaDotApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mapsGemToMatchingSkillByGemSlot() {
		CharacterGemsDto gemsDto = new CharacterGemsDto();
		CharacterGemsDto.GemDto gem = new CharacterGemsDto.GemDto();
		gem.setSlot(2);
		gem.setIcon("gem-icon");
		gem.setLevel(10);
		gem.setTooltip("{\"Element_000\":{\"value\":\"멸화의 보석\"}}");
		gemsDto.setGems(List.of(gem));

		CharacterGemsDto.EffectsDto effects = new CharacterGemsDto.EffectsDto();
		CharacterGemsDto.SkillEffectDto skill = new CharacterGemsDto.SkillEffectDto();
		skill.setGemSlot(2);
		skill.setName("테스트 스킬");
		skill.setIcon("skill-icon");
		effects.setSkills(List.of(skill));
		gemsDto.setEffects(effects);

		List<CharacterInfoResponse.GemResponse> result = DataUtil.parseGems(gemsDto);

		assertEquals(1, result.size());
		assertEquals("멸화", result.getFirst().getType());
		assertEquals("테스트 스킬", result.getFirst().getSkillName());
		assertEquals("skill-icon", result.getFirst().getSkillIcon());
	}

	@Test
	void safelyHandlesMissingGemsAndUnmatchedSkills() {
		assertEquals(List.of(), DataUtil.parseGems(null));

		CharacterGemsDto gemsDto = new CharacterGemsDto();
		CharacterGemsDto.GemDto gem = new CharacterGemsDto.GemDto();
		gem.setSlot(1);
		gemsDto.setGems(List.of(gem));

		List<CharacterInfoResponse.GemResponse> result = DataUtil.parseGems(gemsDto);
		assertEquals(1, result.size());
		assertNull(result.getFirst().getSkillName());
		assertNull(result.getFirst().getType());
	}

}
