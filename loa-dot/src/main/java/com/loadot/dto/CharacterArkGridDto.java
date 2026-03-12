package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
/**
 * CharacterArkGridDto
 * 아크그리드Dto
 */
public class CharacterArkGridDto {

    @JsonProperty("Slots")
    private List<SlotsDto> slots;

    @JsonProperty("Effects")
    private List<EffectsDto> effects;

    // --- 내부 DTO 클래스들 ---
    @Getter @Setter
    public static class SlotsDto {
        @JsonProperty("Index")
        private int index;
        @JsonProperty("Icon")
        private String icon;
        @JsonProperty("Point")
        private String point;
        @JsonProperty("Grade")
        private String grade;
        @JsonProperty("Tooltip")
        private String tooltip;
        @JsonProperty("Gems")
        private String gems;

        public static class GemsDto {
            @JsonProperty("Index")
            private int index;
            @JsonProperty("Icon")
            private String icon;
            @JsonProperty("IsActive")
            private Boolean isActive;
            @JsonProperty("Grade")
            private String grade;
            @JsonProperty("Tooltip")
            private String tooltip;
        }
    }

    @Getter @Setter
    public static class EffectsDto {
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Level")
        private String level;
        @JsonProperty("ToolTip")
        private String tooltip;
    }
}