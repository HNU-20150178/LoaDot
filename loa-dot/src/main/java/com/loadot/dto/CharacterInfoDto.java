package com.loadot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
public class CharacterInfoDto {
    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("CharacterClassName")
    private String characterClassName;

    @JsonProperty("ItemAvgLevel")
    private String itemAvgLevel;

    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("GuildName")
    private String guildName;

    @JsonProperty("ExpeditionLevel")
    private String expeditionLevel;

    @JsonProperty("CharacterImage")
    private String characterImage;

}
