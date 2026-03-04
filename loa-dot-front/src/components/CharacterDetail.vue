<template>
  <div class="character-detail-container">
    <div class="detail-grid">
      
      <div class="detail-column">

        <section class="qul-box glass">
          <h3 class="section-title"> 아크 패시브 </h3>
          <div v-for="item in characterData.arkGrid" :key="item.name" class="ark-item">
            <img :src="item.icon" width="18">
            <span :class="'text-grade' + item.grade">{{ item.name }}</span>
            <span class="point">{{ item.point }}</span>
          </div>
        </section>

        <section class="qul-box glass">
          <h3 class="section-title">기본 정보</h3>

          <div class="base-stats-grid">
            <div class="stat-row">
              <span>공격력</span>
              <span class="value">{{ formatNum(baseStats.attackPower) }}</span>
            </div>
            <div class="stat-row">
              <span>최대 생명력</span>
              <span class="value">{{ formatNum(baseStats.maxHp) }}</span>
            </div>
          </div>
          <hr class="divider">

          <div class="combat-stats-grid">
            <div v-for="stat in combatStats" :key="stat.type" class="stat-row small">
              <span class="label">{{ stat.Type }}</span>
              <span class="value">{{ stat.Value }}</span>
            </div>
          </div>
        </section>

        <section class="qul-box glass">
          <h3 class="section-title"> 아크 그리드 </h3>
          <div v-for="item in characterData.arkGrid" :key="item.name" class="ark-item">
            <img :src="item.icon" width="18">
            <span :class="'text-grade' + item.grade">{{ item.name }}</span>
            <span class="point">{{ item.point }}</span>
          </div>
        </section>
      </div>

      <div class="detail-column">
        <section class="qul-box glass">
          <h3 class="section-title "> 각인 </h3>
          <div v-for="engrave in characterData.engravings" :key="engrave.name" class="engrave-item">
            <div class="engrave-info">
              <span class="elics-tag">4</span>
              <span>{{ engrave.name }}</span>
            </div>
            <span class="engrave-lv" v-if="engrave.level">Lv.{{ engrave.level }}</span>
          </div>
        </section>
      </div>

      <div class="detail-column">
        <section class="qul-box glass">
          <h3 class="section-title"> 카드 </h3>
          <div class="card-grid">
            <div v-for="card in characterData.cards" :key="card.name" class="card-item">
              <img :src="card.image" :class="'grade-' + card.grade">
            </div>
          </div>
          <div class="card-set-effect">
            <span class="set-name">{{ characterData.cardSetName }}</span>
            <ul class="set-details">
              <li v-for="effect in characterData.cardSetEffects" :key="effect">{{ effect }}</li>
            </ul>
          </div>
        </section>
      </div>

    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  characterData: {
    type: Object,
    required: true
  }
})

// 1. 전투 특성만 필터링 (치명, 특화, 제압, 신속, 인내, 숙련)
const combatStats = computed(() => {
  const targets = ['치명', '특화', '제압', '신속', '인내', '숙련'];
  return props.characterData?.stats?.filter(s => targets.includes(s.Type)) || [];
});

// 2. 기본 정보만 추출 (공격력, 최대 생명력)
const baseStats = computed(() => {
  const stats = props.characterData?.stats || [];
  return {
    attackPower: stats.find(s => s.Type === '공격력')?.Value || '0',
    maxHp: stats.find(s => s.Type === '최대 생명력')?.Value || '0'
  };
});

// 숫자 포맷팅 함수
const formatNum = (val) => Number(val).toLocaleString();

</script>

<style scoped>
.base-stats-grid, 
.combat-stats-grid {
  display: grid;
  grid-template-columns: 1fr 1fr; /* 한 줄에 두 항목 */
  column-gap: 20px; /* 좌우 항목 사이 간격 */
  row-gap: 8px;    /* 위아래 항목 사이 간격 */
}

.character-detail-container {
  padding: 20px;
  color: white;
}

.detail-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.glass {
  background: rgba(0, 0, 0, 0.6);
  backdrop-filter: blur(12px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 15px;
}

.section-title {
  font-size: 14px;
  text-align: center;
  margin-bottom: 15px;
  font-weight: bold;
}

.stat-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  font-size: 13px;
}

.divider {
  border: 0;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  margin: 10px 0;
}
</style>