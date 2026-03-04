package com.loadot.dto.response;

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

    private final LocalDateTime updatedAt;

    // Entity -> Response DTO 변환 (정적 팩토리 메서드)
    public static CharacterInfoResponse from(Character character, CharacterInfoDto dto) {
        return new CharacterInfoResponse(character, dto);
    }

    private CharacterInfoResponse(Character character, CharacterInfoDto dto) {
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
        this.stats              = dto.getStats();
        this.tendencies         = dto.getTendencies();
        this.decorations        = dto.getDecorations();
    }
}