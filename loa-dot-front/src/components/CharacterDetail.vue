<script setup>
import Gem from './detail/CharacterGem.vue'
import ArkPassive from './detail/CharacterArkPassive.vue'
import BaseStats from './detail/CharacterBaseStats.vue'
import ArkGrid from './detail/CharacterArkGrid.vue'
import Engraving from './detail/CharacterEngraving.vue'
import Card from './detail/CharacterCard.vue'

defineProps({ characterData: { type: Object, required: true } })
</script>

<template>
  <div class="character-detail-container">
    <div class="detail-grid">
      <div class="detail-column"><Gem :gems="characterData.gems" /></div>
      <div class="detail-column">
        <ArkPassive
          :title="characterData.arkPassiveTitle"
          :points="characterData.arkPassivePoints"
          :effects="characterData.arkPassiveEffects"
        />
        <BaseStats :base-stats="characterData.baseStats" :combat-stats="characterData.combatStats" />
        <ArkGrid :slots="characterData.arkGridSlots" />
      </div>
      <div class="detail-column"><Engraving :engravings="characterData.engravingsArkPassiveEffects" /></div>
      <div class="detail-column">
        <Card :cards="characterData.cards" :set-effects="characterData.cardSetEffects" :set-name="characterData.cardSetName" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.character-detail-container { padding: 20px; color: white; min-width: 0; }
.detail-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(min(100%, 300px), 1fr)); gap: 20px; }
.detail-column { min-width: 0; display: flex; flex-direction: column; gap: 16px; }
@media (max-width: 480px) { .character-detail-container { padding: 12px; } }
</style>
