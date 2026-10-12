package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

/**
 * CharacterEquipmentDto
 * 캐릭터장비Dto
 */
public class CharacterEquipmentDto {

    // 중첩된 객체 구조들
    private List<CharacterEquipmentDto.EquipmentDto> Equipments;

    // --- 내부 DTO 클래스들 ---
    @Getter @Setter
    public static class EquipmentDto {
        @JsonProperty("Type")
        private String type;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Icon")
        private String icon;
        @JsonProperty("Grade")
        private String grade;
        @JsonProperty("ToolTip")
        private String tooltip;
    }
}