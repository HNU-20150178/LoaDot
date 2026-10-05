<template>
  <div class="search-result-container">

    <div class="status-msg" v-if="loading">로딩 중...</div>
    <div class="status-msg" v-else-if="errorMsg">{{ errorMsg }}</div>
    
    <div class="content-layout" v-else-if="characterData">
      <aside class="profile-sidebar">
        <CharacterCard 
          :characterData="characterData"
          @reset="goHome"
        />
      </aside>
      
      <main class="detail-main">
        <CharacterDetail 
          :characterData="characterData"
        />
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
  padding: 20px;
  box-sizing: border-box;
}

.content-layout {
  display: flex;
  gap: 24px;
  align-items: flex-start;
  width: 100%;
}

.profile-sidebar {
  flex-shrink: 0;
  width: 320px;
}

.detail-main {
  flex: 1;
  min-width: 0;
}

.status-msg {
  margin: 20px 0;
  font-size: 1.2rem;
}

@media (max-width: 1024px) {
  .content-layout {
    flex-direction: column;
  }
  .profile-sidebar {
    width: 100%;
  }
}
</style>