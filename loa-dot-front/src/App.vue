<script setup>
import { ref } from 'vue'
import axios from 'axios'

// --- 상태(State) 관리 ---
const searchName = ref('')        // 입력한 닉네임
const characterData = ref(null)   // 받아온 캐릭터 데이터
const loading = ref(false)        // 로딩 상태
const errorMsg = ref('')          // 에러 메시지

// --- API 호출 함수 ---
const searchCharacter = async () => {
  if (!searchName.value) return;
  
  loading.value = true;
  errorMsg.value = '';
  characterData.value = null;

  try {
    // Spring Boot API 호출 (포트 8081)
    const response = await axios.get(`http://localhost:8081/api/characters/${searchName.value}`);
    characterData.value = response.data; // 성공 시 데이터 저장
  } catch (error) {
    console.error(error);
    errorMsg.value = "캐릭터를 찾을 수 없거나 서버 오류입니다.";
  } finally {
    loading.value = false;
  }
}

// --- 다시 검색하기 ---
const resetSearch = () => {
  characterData.value = null;
  searchName.value = '';
}
</script>

<template>
  <div class="app-container">
    
    <header>
      <h1>LoaDot<span class="dot">.</span></h1>
    </header>

    <div v-if="!characterData" class="search-box">
      <input 
        v-model="searchName" 
        @keyup.enter="searchCharacter"
        type="text" 
        placeholder="캐릭터 닉네임을 입력하세요" 
      />
      <button @click="searchCharacter" :disabled="loading">
        {{ loading ? '검색 중...' : '검색' }}
      </button>
      <p v-if="errorMsg" class="error">{{ errorMsg }}</p>
    </div>

    <div v-else class="result-card">
      <div class="card-header">
        <span class="server">{{ characterData.serverName }}</span>
        <span class="class-badge">{{ characterData.characterClass }}</span>
      </div>
      <h2>{{ characterData.name }}</h2>
      <div class="level-info">
        <span class="label">아이템 레벨</span>
        <span class="value">{{ characterData.itemLevel }}</span>
      </div>
      
      <button class="back-btn" @click="resetSearch">다시 검색</button>
    </div>

  </div>
</template>

<style scoped>
/* 로스트아크 느낌의 다크 테마 스타일 */
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

h1 { font-size: 3rem; margin-bottom: 2rem; color: white; }
.dot { color: #00d2d3; }

/* 검색창 스타일 */
.search-box { display: flex; flex-direction: column; gap: 10px; width: 300px; }
input {
  padding: 15px; border-radius: 8px; border: 1px solid #333;
  background: #1e1e1e; color: white; outline: none; text-align: center;
}
input:focus { border-color: #00d2d3; }
button {
  padding: 15px; border-radius: 8px; border: none;
  background: #00d2d3; color: #121212; font-weight: bold; cursor: pointer;
  transition: 0.3s;
}
button:hover { background: #00b5b6; }

/* 결과 카드 스타일 */
.result-card {
  background: #1e1e1e; padding: 40px; border-radius: 16px;
  width: 350px; text-align: center;
  box-shadow: 0 10px 30px rgba(0,0,0,0.5); border: 1px solid #333;
}
.card-header { display: flex; justify-content: center; gap: 10px; margin-bottom: 10px; }
.server { color: #888; font-size: 0.9rem; }
.class-badge { background: #333; padding: 2px 8px; border-radius: 4px; font-size: 0.8rem; }
h2 { font-size: 2.5rem; margin: 10px 0; color: white; }
.level-info { background: #2a2a2a; padding: 15px; border-radius: 8px; margin: 20px 0; }
.value { color: #ff9f43; font-weight: bold; font-size: 1.2rem; margin-left: 10px; }
.back-btn { background: transparent; border: 1px solid #555; color: #aaa; width: 100%; }
.back-btn:hover { background: #333; color: white; }
.error { color: #ff6b6b; font-size: 0.9rem; margin-top: 10px; }
</style>