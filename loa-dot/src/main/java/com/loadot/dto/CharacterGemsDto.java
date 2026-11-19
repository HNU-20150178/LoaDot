package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CharacterGemsDto {

    @JsonProperty("Gems")
    private List<GemDto> gems;

    @JsonProperty("Effects")
    private EffectsDto effects;

    @Getter
    @Setter
    public static class GemDto {
        @JsonProperty("Slot")
        private Integer slot;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Icon")
        private String icon;

        @JsonProperty("Level")
        private Integer level;

        @JsonProperty("Grade")
        private String grade;

        @JsonProperty("Tooltip")
        private String tooltip;
    }

    @Getter
    @Setter
    public static class EffectsDto {
        @JsonProperty("Skills")
        private List<SkillEffectDto> skills;
    }

    @Getter
    @Setter
    public static class SkillEffectDto {
        @JsonProperty("GemSlot")
        private Integer gemSlot;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Icon")
        private String icon;

        @JsonProperty("Tooltip")
        private String tooltip;
    }
}
