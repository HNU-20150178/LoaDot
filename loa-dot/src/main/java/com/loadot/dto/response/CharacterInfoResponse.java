package com.loadot.dto.response;

import com.loadot.dto.CharacterArkGridDto;
import com.loadot.dto.CharacterArkPassiveDto;
import com.loadot.dto.CharacterInfoDto;
import com.loadot.entity.Character;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CharacterInfoResponse {

    private final Long id;
    private final String characterName;
    private final String serverName;
    private final String characterClassName;
    private final Integer characterLevel;
    private final Integer expeditionLevel;

    private final String itemAvgLevel;
    private final Double itemAvgLevelDouble;

    private final String combatPower;
    private final Double combatPowerDouble;

    private final String title;
    private final String guildName;
    private final String guildMemberGrade;

    private final Integer townLevel;
    private final String townName;

    private final Integer usingSkillPoint;
    private final Integer totalSkillPoint;
    private final Integer honorPoint;

    private final String characterImage;
    private final String symbol;
    private final String decorationEmblems;

    private final List<CharacterInfoDto.StatDto> stats;
    private final List<CharacterInfoDto.TendencyDto> tendencies;
    private final CharacterInfoDto.DecorationDto decorations;

    // CharacterArkPassiveDto
    private final String arkPassiveTitle;

    private final boolean isArkPassive;

    private final List<CharacterArkPassiveDto.PointsDto> arkPassivePoints;
    private final List<CharacterArkPassiveDto.EffectsDto> arkPassiveEffects;

    // CharacterArkGridDto
    private final List<CharacterArkGridDto.SlotsDto> arkGridSlots;

    private final List<CharacterArkGridDto.EffectsDto> arkGridEffects;

    private final LocalDateTime updatedAt;

    // Entity,Dto -> Response DTO 변환 (정적 팩토리 메서드)
    public static CharacterInfoResponse from(Character character,
                                             CharacterInfoDto characterInfoDto,
                                             CharacterArkPassiveDto characterArkPassiveDto,
                                             CharacterArkGridDto characterArkGridDto) {
        return new CharacterInfoResponse(character, characterInfoDto, characterArkPassiveDto, characterArkGridDto);
    }

    private CharacterInfoResponse(Character character,
                                  CharacterInfoDto characterInfoDto,
                                  CharacterArkPassiveDto characterArkPassiveDto,
                                  CharacterArkGridDto characterArkGridDto) {
        this.id                 = character.getId();
        this.characterName      = character.getCharacterName();
        this.serverName         = character.getServerName();
        this.characterClassName = character.getCharacterClassName();
        this.characterLevel     = character.getCharacterLevel();
        this.expeditionLevel    = character.getExpeditionLevel();
        this.itemAvgLevel       = character.getItemAvgLevel();
        this.itemAvgLevelDouble = character.getItemAvgLevelDouble();
        this.combatPower        = character.getCombatPower();
        this.combatPowerDouble  = character.getCombatPowerDouble();
        this.title              = character.getTitle();
        this.guildName          = character.getGuildName();
        this.guildMemberGrade   = character.getGuildMemberGrade();
        this.townLevel          = character.getTownLevel();
        this.townName           = character.getTownName();
        this.usingSkillPoint    = character.getUsingSkillPoint();
        this.totalSkillPoint    = character.getTotalSkillPoint();
        this.honorPoint         = character.getHonorPoint();
        this.characterImage     = character.getCharacterImage();
        this.symbol             = character.getSymbol();
        this.decorationEmblems  = character.getDecorationEmblems();
        this.updatedAt          = character.getUpdatedAt();
        this.stats              = characterInfoDto.getStats();
        this.tendencies         = characterInfoDto.getTendencies();
        this.decorations        = characterInfoDto.getDecorations();
        this.arkPassiveTitle    = characterArkPassiveDto.getTitle();
        this.isArkPassive       = characterArkPassiveDto.getIsArkPassive();
        this.arkPassivePoints   = characterArkPassiveDto.getPoints();
        this.arkPassiveEffects  = characterArkPassiveDto.getEffects();
        this.arkGridSlots       = characterArkGridDto.getSlots();
        this.arkGridEffects     = characterArkGridDto.getEffects();

    }
}