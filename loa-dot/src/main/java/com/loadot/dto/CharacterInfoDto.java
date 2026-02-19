package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CharacterInfoDto {

    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("CharacterClassName")
    private String characterClassName;

    @JsonProperty("CharacterLevel")
    private Integer characterLevel;

    @JsonProperty("ItemAvgLevel")
    private String itemAvgLevel;

    @JsonProperty("CombatPower")
    private String combatPower;

    @JsonProperty("ExpeditionLevel")
    private Integer expeditionLevel;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("GuildName")
    private String guildName;

    @JsonProperty("GuildMemberGrade")
    private String guildMemberGrade;

    @JsonProperty("HonorPoint")
    private Integer honorPoint;

    @JsonProperty("CharacterImage")
    private String characterImage;

    @JsonProperty("UsingSkillPoint")
    private Integer usingSkillPoint;

    @JsonProperty("TotalSkillPoint")
    private Integer totalSkillPoint;

    @JsonProperty("TownLevel")
    private Integer townLevel;

    @JsonProperty("TownName")
    private String townName;

    // 중첩된 객체 구조들
    @JsonProperty("Stats")
    private List<StatDto> stats;

    @JsonProperty("Tendencies")
    private List<TendencyDto> tendencies;

    @JsonProperty("Decorations")
    private DecorationDto decorations;

    // --- 내부 DTO 클래스들 ---

    @Getter @Setter
    public static class StatDto {
        @JsonProperty("Type")
        private String type;
        @JsonProperty("Value")
        private String value;
        @JsonProperty("Tooltip")
        private List<String> tooltip;
    }

    @Getter @Setter
    public static class TendencyDto {
        @JsonProperty("Type")
        private String type;
        @JsonProperty("Point")
        private Integer point;
        @JsonProperty("MaxPoint")
        private Integer maxPoint;
    }

    @Getter @Setter
    public static class DecorationDto {
        @JsonProperty("Symbol")
        private String symbol;
        @JsonProperty("Emblems")
        private List<String> emblems;
    }
}