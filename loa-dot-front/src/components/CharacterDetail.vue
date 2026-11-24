<script setup>
import Gem from './detail/CharacterGem.vue'
import ArkPassive from './detail/CharacterArkPassive.vue'
import ArkGrid from './detail/CharacterArkGrid.vue'
import Engraving from './detail/CharacterEngraving.vue'
import Card from './detail/CharacterCard.vue'

defineProps({ characterData: { type: Object, required: true } })
</script>

<template>
  <div class="character-detail-container">
    <div class="character-detail-column">
      <Gem :gems="characterData.gems" />
      <ArkGrid :slots="characterData.arkGridSlots" />
    </div>
    <div class="character-detail-column">
      <ArkPassive :title="characterData.arkPassiveTitle" :points="characterData.arkPassivePoints" :effects="characterData.arkPassiveEffects" />
      <Card :cards="characterData.cards" :set-effects="characterData.cardSetEffects" :set-name="characterData.cardSetName" />
    </div>
  </div>
</template>

<style scoped>
.character-detail-container { display: grid; gap: 12px; min-width: 0; color: var(--loa-text); }
.character-detail-column { display: flex; flex-direction: column; gap: 12px; min-width: 0; }
@media (min-width: 1440px) {
  .character-detail-container { grid-template-columns: repeat(2, minmax(0, 1fr)); }
  .character-detail-container > :nth-child(3) { grid-column: 1 / -1; }
}
</style>
