<template>
  <div class="equipment-container glass" v-if="characterData.equipments">
    <h3 class="section-title">장비 및 악세서리</h3>

    <div class="equipment-layout">
      <!-- 1. 좌측: 무기, 방어구, 완갑 (7부위) -->
      <div class="equipment-column left">
        <h4 class="equipment-group-title">무기 · 방어구</h4>
        <div 
          v-for="item in armorList" 
          :key="item.type" 
          class="equip-slot"
        >
          <!-- 아이콘 박스 -->
          <div 
            class="icon-box"
            :class="{ 'ancient-border': item.petBorder === 6 && item.type !== '완갑' }"
            :style="item.type !== '완갑'
              ? (item.petBorder === 6 
                  ? { backgroundImage: `url('https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/bg_equipment_petBorder.png')` } 
                  : { borderColor: getGradeColor(item.grade) })
              : { border: 'none' }"
          >
            <img v-if="item.icon" :src="item.icon" :alt="item.name" class="equip-icon" />
          </div>

          <!-- 텍스트 정보 -->
          <div class="equip-info">
            <div class="item-header">
              <span class="item-name" :style="{ color: getGradeColor(item.grade) }">
                {{ item.name || '미장착' }}
              </span>
              <span v-if="item.quality !== null && item.quality !== undefined && item.quality >= 0" class="quality-text" :style="{ color: getQualityColor(item.quality) }">
                {{ item.quality }}
              </span>
            </div>

            <!-- 품질 게이지 바 -->
            <div v-if="item.quality !== null && item.quality !== undefined && item.quality >= 0" class="quality-bar-bg">
              <div 
                class="quality-bar-fill" 
                :style="{ 
                  width: `${item.quality}%`, 
                  backgroundColor: getQualityColor(item.quality) 
                }"
              ></div>
            </div>
          </div>
        </div>
      </div>

      <!-- 2. 우측: 장신구 & 어빌리티 스톤 & 팔찌 (8부위) -->
      <div class="equipment-column right">
        <h4 class="equipment-group-title">장신구 · 팔찌 · 어빌리티 스톤</h4>
        <div 
          v-for="item in accessoryList" 
          :key="item.type" 
          class="equip-slot"
        >
          <!-- 아이콘 박스 -->
          <div 
            class="icon-box" 
            :style="{ borderColor: getGradeColor(item.grade) }"
          >
            <img v-if="item.icon" :src="item.icon" :alt="item.name" class="equip-icon" />
          </div>

          <!-- 텍스트 정보 -->
          <div class="equip-info">
            <div class="item-header">
              <span class="item-name" :style="{ color: getGradeColor(item.grade) }">
                {{ item.name || '미장착' }}
              </span>
              <span v-if="item.quality !== null && item.quality !== undefined && item.quality >= 0" class="quality-text" :style="{ color: getQualityColor(item.quality) }">
                {{ item.quality }}
              </span>
            </div>

            <!-- 품질 게이지 바 -->
            <div v-if="item.quality !== null && item.quality !== undefined && item.quality >= 0" class="quality-bar-bg">
              <div 
                class="quality-bar-fill" 
                :style="{ 
                  width: `${item.quality}%`, 
                  backgroundColor: getQualityColor(item.quality) 
                }"
              ></div>
            </div>

            <!-- 연마/특성 옵션 세부 정보 -->
            <div class="item-sub-info">
              <span v-if="item.optionsText" class="options-text">{{ item.optionsText }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="no-data glass">장비 정보를 불러올 수 없습니다.</div>
</template>

<script setup>
import { computed } from 'vue'
import { getGradeColor } from '@/utils/gameData'
import { getQualityColor } from '@/utils/gameData'

const props = defineProps({
  characterData: {
    type: Object,
    required: true
  }
})

const ARMOR_TYPES = ['무기', '투구', '상의', '하의', '장갑', '어깨', '완갑']
const ACCESSORY_TYPES = ['목걸이', '귀걸이', '귀걸이', '반지', '반지', '어빌리티 스톤', '팔찌', '보주']

// 방어구/무기 리스트 필터링
const armorList = computed(() => {
  if (!props.characterData.equipments) return []
  return props.characterData.equipments.filter(item => ARMOR_TYPES.includes(item.type))
})

// 악세서리/스톤 리스트 필터링
const accessoryList = computed(() => {
  if (!props.characterData.equipments) return []
  return props.characterData.equipments.filter(item => ACCESSORY_TYPES.includes(item.type))
})

</script>

<style scoped>
.equipment-container {
  min-width: 0;
}

.section-title {
  font-size: 16px;
}

/* 2열 좌우 레이아웃 */
.equipment-layout {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.equipment-column {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.equipment-group-title { margin: 0 0 1px; color: var(--loa-muted); font-size: 11px; font-weight: 700; letter-spacing: .04em; }

/* 개별 아이템 슬롯 */
.equip-slot {
  display: flex;
  align-items: center;
  gap: 12px;
  background: var(--detail-surface);
  padding: 8px 12px;
  border-radius: 8px;
  border: 1px solid var(--detail-border);
}

/* 아이콘 박스 */
.icon-box {
  position: relative;
  width: 44px;
  height: 44px;
  flex-shrink: 0;
  border-radius: 6px;
  border: 2px solid #334155;
  background: var(--loa-bg-raised);
  overflow: hidden;
  box-sizing: border-box;
}

/* 고대 등급 전용 아이콘 박스*/
.icon-box.ancient-border {
  border: none;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  padding: 2px;
}

.equip-icon {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

/* 아이템 정보 컨테이너 */
.equip-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
  min-width: 0;
}

.item-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.item-name {
  font-size: 13px;
  font-weight: 600;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.quality-text {
  font-size: 11px;
  font-weight: 700;
}

/* 품질 바 디자인 */
.quality-bar-bg {
  width: 100%;
  height: 4px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 2px;
  overflow: hidden;
}

.quality-bar-fill {
  height: 100%;
  transition: width 0.3s ease;
}

/* 세부 옵션/세트 정보 */
.item-sub-info {
  display: flex;
  gap: 6px;
  font-size: 11px;
  color: #94a3b8;
}

.set-badge {
  color: #f59e0b;
  font-weight: 600;
}

.elixir-text, .options-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.no-data {
  text-align: center;
  color: #64748b;
  padding: 24px;
}

@media (max-width: 768px) {
  .equipment-layout {
    grid-template-columns: 1fr;
  }
}
@media (min-width: 769px) and (max-width: 1200px) {
  .equipment-layout { grid-template-columns: 1fr; }
}
</style>
