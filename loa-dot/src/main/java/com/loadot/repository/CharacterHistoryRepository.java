package com.loadot.repository;

import com.loadot.entity.Character;
import com.loadot.entity.CharacterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterHistoryRepository extends JpaRepository<CharacterHistory, Long> {

    Optional<CharacterHistory> findTop1ByCharacterOrderByRecordedAtDesc(Character character);

    Long findMaxCombatPowerByCharacter(Character character);
}
