package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
/**
 * CharacterArkPassiveDto
 * 아크패시브Dto
 */
public class CharacterArkPassiveDto {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("IsArkPassive")
    private Boolean isArkPassive;

    // 중첩된 객체 구조들
    @JsonProperty("Points")
    private List<PointsDto> points;

    @JsonProperty("Effects")
    private List<EffectsDto> effects;

    // --- 내부 DTO 클래스들 ---
    @Getter @Setter
    public static class PointsDto {
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Value")
        private String value;
        @JsonProperty("Tooltip")
        private String tooltip;
        @JsonProperty("Description")
        private String description;
    }

    @Getter @Setter
    public static class EffectsDto {
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Description")
        private String description;
        @JsonProperty("Icon")
        private String icon;
        @JsonProperty("ToolTip")
        private String tooltip;
    }
}