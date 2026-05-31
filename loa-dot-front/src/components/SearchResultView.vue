<template>
  <SearchBox 
    v-model:searchName="localSearchName"
    :loading="loading"
    :errorMsg="errorMsg"
    @search="handleNewSearch"
  />

  <div class="status-msg" v-if="loading">로딩 중...</div>
  <div class="status-msg" v-else-if="errorMsg">{{ errorMsg }}</div>
  
  <template v-else-if="characterData">
    <CharacterCard 
      :characterData="characterData"
      @reset="goHome"
    />
    <CharacterDetail 
      :characterData="characterData"
    />
  </template>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { characterAPI } from '../services/api' // 상대 경로 맞춤
import SearchBox from './SearchBox.vue'
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
.status-msg { margin: 20px; font-size: 1.2rem; }
</style>