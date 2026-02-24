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

    private Double itemAvgLevelDouble;
    private Double combatPowerDouble;
    private LocalDateTime recordedAt;

    public CharacterHistory(Character character, Double itemAvgLevelDouble, Double combatPowerDouble) {
        this.character = character;
        this.itemAvgLevelDouble = itemAvgLevelDouble;
        this.combatPowerDouble = combatPowerDouble;
        this.recordedAt = LocalDateTime.now();
    }
}