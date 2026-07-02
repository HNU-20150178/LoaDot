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
}
