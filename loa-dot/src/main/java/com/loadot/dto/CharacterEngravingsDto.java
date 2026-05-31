package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
/**
 * CharacterEngravingsDto
 * 각인Dto
 */
public class CharacterEngravingsDto {

    @JsonProperty("Engravings")
    private List<Object> engravings;

    @JsonProperty("Effects")
    private List<Object> effects;

    @JsonProperty("ArkPassiveEffects")
    private List<ArkPassiveEffect> arkPassiveEffects;

    @Getter
    @Setter
    @ToString
    public static class ArkPassiveEffect {

        @JsonProperty("AbilityStoneLevel")
        private Integer abilityStoneLevel;

        @JsonProperty("Grade")
        private String grade;

        @JsonProperty("Level")
        private int level;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Description")
        private String description;
    }
}