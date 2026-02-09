package com.loadot.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
public class CharacterInfoDto {
    private String characterName;
    private String characterClassName;
    private String itemMaxLevel;

}
