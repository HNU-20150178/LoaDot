<script setup>
import { defineProps, defineEmits } from 'vue'

// Props 정의
const props = defineProps({
  characterData: {
    type: Object,
    required: true
  }
})

// Emits 정의
const emit = defineEmits(['reset'])

// 다시 검색하기
const handleReset = () => {
  emit('reset')
}
</script>

<template>
  <div class="result-card">
    <div class="card-header">
      <span class="server-badge">{{ characterData.serverName }}</span>
      <span class="class-badge">{{ characterData.characterClassName }}</span>
    </div>
    
    <div class="character-info">
      <span class="server">길드 </span>{{ characterData.guildName }}
    </div>
    <div class="character-info">
      <span class="server">원정대 </span>Lv.{{ characterData.expeditionLevel }}
    </div>
    <div class="character-info">
      <span class="server">전투력 </span>{{ Number(characterData.combatPowerDouble || 0).toFixed(2) }}
    </div>
    
    <h2>{{ characterData.name }}</h2>
    
    <div class="level-info">
      <span class="label">아이템 레벨</span>
      <span class="value">{{ Number(characterData.itemAvgLevelDouble || 0).toFixed(2) }}</span>
    </div>
    
    <img 
      :src="characterData.characterImage" 
      :alt="characterData.name"
      class="character-image"
    />
    
    <button class="back-btn" @click="handleReset">⟳</button>
  </div>
</template>

<style scoped>
.result-card {
  background: #1e1e1e; 
  padding: 40px; 
  border-radius: 16px;
  width: 350px; 
  text-align: center;
  box-shadow: 0 10px 30px rgba(0,0,0,0.5); 
  border: 1px solid #333;
}

.card-header { 
  display: flex; 
  justify-content: center; 
  gap: 10px; 
  margin-bottom: 10px; 
}

.server-badge, .class-badge { 
  background: #333; 
  padding: 2px 8px; 
  border-radius: 4px; 
  font-size: 0.8rem; 
}

.character-info {
  margin: 5px 0;
}

.server { 
  color: #888; 
  font-size: 0.9rem; 
}

h2 { 
  font-size: 2.5rem; 
  margin: 10px 0; 
  color: white; 
}

.level-info { 
  background: #2a2a2a; 
  padding: 15px; 
  border-radius: 8px; 
  margin: 20px 0; 
}

.label {
  color: #888;
  margin-right: 10px;
}

.value { 
  color: #ff9f43; 
  font-weight: bold; 
  font-size: 1.2rem; 
}

.character-image {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin: 20px 0;
}

.back-btn { 
  background: transparent; 
  border: 1px solid #555; 
  color: #aaa; 
  width: 100%; 
  padding: 15px; 
  border-radius: 8px;
  cursor: pointer;
  transition: 0.3s;
}

.back-btn:hover { 
  background: #333; 
  color: white; 
}
</style>