<template>
  <div class="search-result-container">

    <div class="status-msg" v-if="loading">로딩 중...</div>
    <div class="status-msg" v-else-if="errorMsg">{{ errorMsg }}</div>
    
    <div class="dashboard-layout" v-else-if="characterData">
      <aside class="dashboard-sidebar">
        <CharacterCard :characterData="characterData" @reset="goHome" />
        <CharacterBaseStats :base-stats="characterData.baseStats" :combat-stats="characterData.combatStats" />
        <CharacterEngravings :engravings="characterData.engravingsArkPassiveEffects" />
      </aside>

      <main class="dashboard-main">
        <CharacterEquipment :characterData="characterData" />
        <CharacterDetail :characterData="characterData" />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { characterAPI } from '../services/api'
import CharacterCard from './CharacterCard.vue'
import CharacterDetail from './CharacterDetail.vue'
import CharacterEquipment from './CharacterEquipment.vue'
import CharacterBaseStats from './detail/CharacterBaseStats.vue'
import CharacterEngravings from './detail/CharacterEngraving.vue'

const props = defineProps({
  characterName: { type: String, required: true }
})

const router = useRouter()
const localSearchName = ref(props.characterName)
const characterData = ref(null)
const loading = ref(false)
const errorMsg = ref('')

const fetchCharacter = async (name) => {
  if (!name.trim()) return
  
  loading.value = true
  errorMsg.value = ''
  characterData.value = null

  try {
    const result = await characterAPI.getCharacterByName(name)
    if (result.success) {
      characterData.value = result.data
    } else {
      errorMsg.value = result.error
    }
  } catch (error) {
    console.error('Unexpected error:', error)
    errorMsg.value = '예상치 못한 오류가 발생했습니다.'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchCharacter(props.characterName)
})

watch(() => props.characterName, (newName) => {
  localSearchName.value = newName
  fetchCharacter(newName)
})

const handleNewSearch = () => {
  if (!localSearchName.value.trim()) return
  router.push({
    name: 'search-result',
    params: { characterName: localSearchName.value.trim() }
  })
}

const goHome = () => {
  router.push({ name: 'home' })
}
</script>

<style scoped>
.search-result-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding-left: 20px;
  padding-right: 20px;
  box-sizing: border-box;
}

.dashboard-layout { display: grid; grid-template-columns: minmax(280px, 360px) minmax(0, 1fr); gap: 16px; width: 100%; max-width: 1700px; margin: 0 auto; align-items: start; }
.dashboard-sidebar, .dashboard-main { display: grid; gap: 12px; min-width: 0; }
.dashboard-main { gap: 14px; }

.status-msg {
  margin: 20px 0;
  font-size: 1.2rem;
}

@media (max-width: 1023px) {
  .dashboard-layout { grid-template-columns: minmax(0, 1fr); }
  .dashboard-sidebar { grid-template-columns: minmax(0, 1fr); }
}
@media (max-width: 600px) {
  .search-result-container { padding-inline: 12px; }
  .dashboard-layout, .dashboard-sidebar, .dashboard-main { gap: 12px; }
}
</style>
