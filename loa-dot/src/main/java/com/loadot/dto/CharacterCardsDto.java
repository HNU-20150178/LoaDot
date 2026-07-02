package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CharacterCardsDto {

    @JsonProperty("Cards")
    private List<Cards> cardsDTO;

    @JsonProperty("Effects")
    private List<EffectsDto> effects;

    @Getter @Setter
    public static class Cards {
        @JsonProperty("Slot")
        private int slot;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Icon")
        private String icon;

        @JsonProperty("AwakeCount")
        private int awakeCount;

        @JsonProperty("AwakeTotal")
        private int awakeTotal;

        @JsonProperty("Grade")
        private String grade;

        @JsonProperty("Tooltip")
        private String tooltip;
    }

    @Getter @Setter
    public static class EffectsDto {
        @JsonProperty("Index")
        private int index;

        @JsonProperty("CardSlots")
        private List<Integer> cardSlots;

        @JsonProperty("Items")
        private List<EffectItemDto> items;
    }

    @Getter @Setter
    public static class EffectItemDto {
        @JsonProperty("Name")
        private String name;

        @JsonProperty("Description")
        private String description;
    }
}