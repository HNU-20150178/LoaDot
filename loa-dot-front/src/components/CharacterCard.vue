<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  characterData: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['reset'])

const handleReset = () => {
  emit('reset')
}
</script>

<template>
  <div class="caracterCardWrap">
    <!-- 배경 글로우 효과 -->
    <div class="card-glow" />

    <!-- 상단 배지 -->
    <div class="card-badges">
      <span class="badge server-badge">{{ characterData.serverName }}</span>
      <span class="badge class-badge">{{ characterData.characterClassName }}</span>
    </div>

    <!-- 캐릭터 이미지 영역 -->
    <div class="image-section">
      <div class="image-wrapper">
        <img
          :src="characterData.characterImage"
          :alt="characterData.characterName"
          class="character-image"
        />

        <!-- Symbol: 이미지 우하단 구석 -->
        <div class="symbol-corner" v-if="characterData.symbol">
          <img
            :src="characterData.symbol"
            alt="ester-emblem"
            class="symbol-img"
          />
        </div>

        <!-- Emblems: 이미지 좌하단 구석 -->
        <div class="emblems-corner" v-if="characterData.decorationEmblems">
          <img
            v-for="url in characterData.decorationEmblems?.split(',')"
            :key="url"
            :src="url"
            :alt="url"
            class="emblem-img"
          />
        </div>

        <!-- 아이템 레벨 오버레이 (이미지 하단) -->
        <div class="level-overlay">
          <span class="level-label">LV.</span>
          <span class="level-value">{{ Number(characterData.itemAvgLevelDouble || 0).toFixed(2) }}</span>
        </div>
      </div>
    </div>

    <!-- 캐릭터 이름/타이틀 -->
    <div class="character-title">
      <span class="char-title-text" v-if="characterData.title">{{ characterData.title }}</span>
      <h2 class="char-name">{{ characterData.characterName }}</h2>
    </div>

    <!-- 스탯 그리드 -->
    <div class="stats-grid">
      <div class="stat-item">
        <span class="stat-label">원정대</span>
        <span class="stat-value">Lv.{{ characterData.expeditionLevel }}</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">전투력</span>
        <span class="stat-value combat">{{ Number(characterData.combatPowerDouble || 0).toFixed(2) }}</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">영지</span>
        <span class="stat-value">Lv.{{ characterData.townLevel }} {{ characterData.townName }}</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">명예</span>
        <span class="stat-value">{{ characterData.honorPoint }}</span>
      </div>
    </div>

    <!-- 하단 길드+임시버튼 -->
    <div class="card-footer">
      <span class="guild-name">
        <span class="guild-icon">길드:</span>
        {{ characterData.guildName }}
      </span>
      <button class="reset-btn" @click="handleReset" title="다시 검색">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
          <path d="M3 12a9 9 0 1 0 9-9 9.75 9.75 0 0 0-6.74 2.74L3 8"/>
          <path d="M3 3v5h5"/>
        </svg>
      </button>
    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Cinzel:wght@400;600;700&family=Noto+Sans+KR:wght@300;400;500;700&display=swap');

.caracterCardWrap {
  position: relative;
  width: 320px;
  background: linear-gradient(160deg, #141820 0%, #0d1117 60%, #111620 100%);
  border: 1px solid rgba(180, 140, 60, 0.3);
  border-radius: 16px;
  overflow: hidden;
  font-family: 'Noto Sans KR', sans-serif;
  color: #e8dcc8;
  box-shadow:
    0 0 0 1px rgba(180,140,60,0.1),
    0 20px 60px rgba(0, 0, 0, 0.7),
    inset 0 1px 0 rgba(255,255,255,0.05);
}

/* 배경 글로우 */
.card-glow {
  position: absolute;
  top: -60px;
  left: 50%;
  transform: translateX(-50%);
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(180,140,60,0.12) 0%, transparent 70%);
  pointer-events: none;
  z-index: 0;
}

/* 배지 */
.card-badges {
  position: relative;
  display: flex;
  gap: 8px;
  padding: 14px 16px 10px;
  z-index: 1;
}

.badge {
  font-size: 0.7rem;
  font-weight: 500;
  padding: 3px 10px;
  border-radius: 20px;
  letter-spacing: 0.05em;
}

.server-badge {
  background: rgba(100, 120, 180, 0.2);
  border: 1px solid rgba(100, 120, 180, 0.4);
  color: #8aabdf;
}

.class-badge {
  background: rgba(180, 140, 60, 0.15);
  border: 1px solid rgba(180, 140, 60, 0.35);
  color: #c9a84c;
}

/* 이미지 섹션 */
.image-section {
  position: relative;
  padding: 0 16px;
  z-index: 1;
}

.image-wrapper {
  position: relative;
  border-radius: 10px;
  overflow: hidden;
  background: linear-gradient(180deg, #1c2030 0%, #0e1018 100%);
  border: 1px solid rgba(180, 140, 60, 0.2);
}

.character-image {
  width: 100%;
  height: 260px;
  object-fit: cover;
  object-position: top center;
  display: block;
}

/* Symbol - 우하단 */
.symbol-corner {
  position: absolute;
  bottom: 38px;
  right: 8px;
  z-index: 3;
  filter: drop-shadow(0 0 6px rgba(180,140,60,0.6));
}

.symbol-img {
  width: 30px;
  height: 30px;
  object-fit: contain;
}

/* Emblems - 좌하단 */
.emblems-corner {
  position: absolute;
  bottom: 38px;
  left: 8px;
  z-index: 3;
  display: flex;
  gap: 3px;
  flex-wrap: wrap;
  max-width: 100px;
}

.emblem-img {
  width: 24px;
  height: 24px;
  object-fit: contain;
  filter: drop-shadow(0 1px 3px rgba(0,0,0,0.8));
}

/* 레벨 오버레이 */
.level-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(transparent, rgba(0,0,0,0.85));
  padding: 20px 12px 8px;
  display: flex;
  align-items: baseline;
  gap: 6px;
}

.level-label {
  font-family: 'Cinzel', serif;
  font-size: 0.6rem;
  color: rgba(200, 170, 80, 0.7);
  letter-spacing: 0.15em;
}

.level-value {
  font-family: 'Cinzel', serif;
  font-size: 1.05rem;
  font-weight: 700;
  color: #f0c040;
  text-shadow: 0 0 12px rgba(240, 192, 64, 0.6);
}

/* 이름 영역 */
.character-title {
  padding: 14px 18px 6px;
  z-index: 1;
  position: relative;
}

.char-title-text {
  display: block;
  font-size: 0.7rem;
  color: rgba(180, 140, 60, 0.75);
  letter-spacing: 0.1em;
  margin-bottom: 2px;
}

.char-name {
  margin: 0;
  font-family: 'Cinzel', serif;
  font-size: 1.5rem;
  font-weight: 700;
  color: #f5ecd5;
  letter-spacing: 0.02em;
  line-height: 1.2;
  text-shadow: 0 2px 8px rgba(0,0,0,0.6);
}

/* 스탯 그리드 */
.stats-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1px;
  margin: 12px 16px;
  background: rgba(180, 140, 60, 0.15);
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid rgba(180, 140, 60, 0.2);
  position: relative;
  z-index: 1;
}

.stat-item {
  background: rgba(255,255,255,0.02);
  padding: 10px 12px;
  display: flex;
  flex-direction: column;
  gap: 2px;
  transition: background 0.2s;
}

.stat-item:hover {
  background: rgba(180, 140, 60, 0.07);
}

.stat-label {
  font-size: 0.65rem;
  color: #7a7a8a;
  letter-spacing: 0.05em;
  font-weight: 500;
}

.stat-value {
  font-size: 0.85rem;
  color: #c8bca8;
  font-weight: 500;
}

.stat-value.combat {
  color: #e08050;
  font-weight: 700;
}

/* 푸터 */
.card-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 16px 16px;
  position: relative;
  z-index: 1;
}

.guild-name {
  font-size: 0.8rem;
  color: #8a9080;
  display: flex;
  align-items: center;
  gap: 6px;
}

.guild-icon {
  color: rgba(180, 140, 60, 0.6);
  font-size: 0.75rem;
}

.reset-btn {
  background: rgba(255,255,255,0.04);
  border: 1px solid rgba(255,255,255,0.1);
  color: #666;
  width: 34px;
  height: 34px;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.reset-btn:hover {
  background: rgba(180, 140, 60, 0.15);
  border-color: rgba(180, 140, 60, 0.4);
  color: #c9a84c;
  transform: rotate(180deg);
}
</style>