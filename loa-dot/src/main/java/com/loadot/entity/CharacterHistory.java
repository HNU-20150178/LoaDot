package com.loadot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class CharacterHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private com.loadot.entity.Character character;

    private Double itemAvgLevel;
    private Long combatPower;
    private LocalDateTime recordedAt;

    public CharacterHistory(Character character, Double itemAvgLevel, Long combatPowerLong) {
        this.character = character;
        this.itemAvgLevel = itemAvgLevel;
        this.combatPower = combatPowerLong;
        this.recordedAt = LocalDateTime.now();
    }
}