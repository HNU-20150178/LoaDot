<script setup>
import { defineProps, defineEmits } from 'vue'

// Props 정의
const props = defineProps({
  searchName: {
    type: String,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  },
  errorMsg: {
    type: String,
    default: ''
  }
})

// Emits 정의
const emit = defineEmits(['update:searchName', 'search'])

// 검색 실행
const handleSearch = () => {
  emit('search')
}

// 입력값 업데이트
const updateSearchName = (event) => {
  emit('update:searchName', event.target.value)
}
</script>

<template>
  <div class="search-box">
    <input 
      :value="searchName"
      @input="updateSearchName"
      @keyup.enter="handleSearch"
      type="text" 
      placeholder="캐릭터 닉네임을 입력하세요" 
    />
    <button @click="handleSearch" :disabled="loading">
      {{ loading ? '검색 중...' : '검색' }}
    </button>
    <p v-if="errorMsg" class="error">{{ errorMsg }}</p>
  </div>
</template>

<style scoped>
.search-box { 
  display: flex; 
  flex-direction: column; 
  gap: 10px; 
  width: 300px; 
}

input {
  padding: 15px; 
  border-radius: 8px; 
  border: 1px solid #333;
  background: #1e1e1e; 
  color: white; 
  outline: none; 
  text-align: center;
}

input:focus { 
  border-color: #00d2d3; 
}

button {
  padding: 15px; 
  border-radius: 8px; 
  border: none;
  background: #00d2d3; 
  color: #121212; 
  font-weight: bold; 
  cursor: pointer;
  transition: 0.3s;
}

button:hover { 
  background: #00b5b6; 
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.error { 
  color: #ff6b6b; 
  font-size: 0.9rem; 
  margin-top: 10px; 
}
</style>