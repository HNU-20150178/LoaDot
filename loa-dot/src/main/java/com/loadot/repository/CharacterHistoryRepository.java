package com.loadot.repository;

import com.loadot.entity.Character;
import com.loadot.entity.CharacterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterHistoryRepository extends JpaRepository<CharacterHistory, Long> {

    Optional<CharacterHistory> findTop1ByCharacterOrderByRecordedAtDesc(Character character);

    @Query("SELECT MAX(ch.combatPowerDouble) FROM CharacterHistory ch WHERE ch.character = :character")
    Double findMaxCombatPowerByCharacter(@org.springframework.data.repository.query.Param("character") Character character);
}
