package com.loadot.entity;

import com.loadot.dto.CharacterInfoDto;
import com.loadot.util.DataUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "characters")
public class Character {

    @Id
    @Column(name = "character_name") // 캐릭터 이름은 유니크하므로 PK로 사용 가능 (또는 Long id 권장)
    private String characterName;

    private String serverName;
    private String characterClassName;
    private Integer characterLevel;
    private Integer expeditionLevel;

    private String itemAvgLevel;
    private Double itemAvgLevelDouble;

    private String combatPower;       // 전투력
    private Long combatPowerLong;       // 전투력

    private String title;           // 칭호
    private String guildName;       // 길드명
    private String guildMemberGrade; // 길드 직위

    private Integer townLevel;      // 영지 레벨
    private String townName;        // 영지 이름

    private Integer usingSkillPoint; // 사용 스킬 포인트
    private Integer totalSkillPoint; // 전체 스킬 포인트

    private Integer honorPoint;      // 명예 포인트 (승급 단계 등)

    @Column(length = 1000)
    private String characterImage;   // 캐릭터 이미지 URL

    private LocalDateTime updatedAt; // 마지막 업데이트 시간

    // 비즈니스 로직을 위한 생성자
    public Character(CharacterInfoDto dto) {
        this.characterName = dto.getCharacterName();
        this.serverName = dto.getServerName();
        this.characterClassName = dto.getCharacterClassName();
        this.characterLevel = dto.getCharacterLevel();
        this.expeditionLevel = dto.getExpeditionLevel();
        this.itemAvgLevel = dto.getItemAvgLevel();
        this.itemAvgLevelDouble = DataUtil.parseLevelToDouble(dto.getItemAvgLevel());
        this.combatPower = dto.getCombatPower();
        this.combatPowerLong = DataUtil.stringToLong(dto.getCombatPower());
        this.title = dto.getTitle();
        this.guildName = dto.getGuildName();
        this.guildMemberGrade = dto.getGuildMemberGrade();
        this.townLevel = dto.getTownLevel();
        this.townName = dto.getTownName();
        this.usingSkillPoint = dto.getUsingSkillPoint();
        this.totalSkillPoint = dto.getTotalSkillPoint();
        this.honorPoint = dto.getHonorPoint();
        this.characterImage = dto.getCharacterImage();
        this.updatedAt = LocalDateTime.now();
    }

    public Character update(CharacterInfoDto dto) {
        this.serverName = dto.getServerName();
        this.characterClassName = dto.getCharacterClassName();
        this.characterLevel = dto.getCharacterLevel();
        this.expeditionLevel = dto.getExpeditionLevel();
        this.itemAvgLevel = dto.getItemAvgLevel();
        this.combatPowerLong = DataUtil.stringToLong(dto.getCombatPower());
        this.title = dto.getTitle();
        this.guildName = dto.getGuildName();
        this.guildMemberGrade = dto.getGuildMemberGrade();
        this.characterImage = dto.getCharacterImage();
        this.updatedAt = LocalDateTime.now();
        return this;
    }
}