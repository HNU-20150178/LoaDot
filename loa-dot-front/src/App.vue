<script setup>
import { ref } from 'vue'
import { characterAPI } from './services/api'
import AppHeader from './components/AppHeader.vue'
import SearchBox from './components/SearchBox.vue'
import CharacterCard from './components/CharacterCard.vue'

// --- 상태(State) 관리 ---
const searchName = ref('')        // 입력한 닉네임
const characterData = ref(null)   // 받아온 캐릭터 데이터
const loading = ref(false)        // 로딩 상태
const errorMsg = ref('')          // 에러 메시지

// --- API 호출 함수 ---
const searchCharacter = async () => {
  if (!searchName.value.trim()) {
    errorMsg.value = '캐릭터 닉네임을 입력해주세요.';
    return;
  }
  
  loading.value = true;
  errorMsg.value = '';
  characterData.value = null;

  try {
    // API 호출
    const result = await characterAPI.getCharacterByName(searchName.value);
    
    if (result.success) {
      characterData.value = result.data;
    } else {
      errorMsg.value = result.error;
    }
  } catch (error) {
    // 예상치 못한 에러 처리
    console.error('Unexpected error:', error);
    errorMsg.value = '예상치 못한 오류가 발생했습니다.';
  } finally {
    loading.value = false;
  }
}

// --- 다시 검색하기 ---
const resetSearch = () => {
  characterData.value = null;
  searchName.value = '';
  errorMsg.value = '';
}
</script>

<template>
  <div class="app-container">
    
    <AppHeader />

    <SearchBox 
      v-if="!characterData"
      v-model:searchName="searchName"
      :loading="loading"
      :errorMsg="errorMsg"
      @search="searchCharacter"
    />

    <CharacterCard 
      v-else
      :characterData="characterData"
      @reset="resetSearch"
    />

  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #121212;
  color: #e0e0e0;
  font-family: 'Apple SD Gothic Neo', sans-serif;
}
</style>