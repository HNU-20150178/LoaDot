<template>
  <div class="character-detail-container">
    <div class="detail-grid">
      
      <div class="detail-column">

        <section class="qul-box glass">
          <h3 class="section-title">아크 패시브</h3>
          <p class="passive-title">{{ characterData.arkPassiveTitle }}</p>

          <div v-for="point in characterData.arkPassivePoints" :key="point.Name" class="ark-group">
            <div class="group-header">
              <span class="group-name">{{ point.Name }}</span>
              <span class="group-value">{{ point.Value }}</span>
            </div>
            <div v-for="effect in filterEffects(point.Name)" :key="effect.Name" class="effect-item">
              <div class="effect-left">
                <img :src="effect.Icon" width="20" v-if="effect.Icon" class="effect-icon">
                
                <span v-if="effect.Tier" class="tier-badge">{{ effect.Tier }}T</span>
                
                <span class="effect-name">{{ effect.PureName }}</span>
              </div>

              <div class="effect-right">
                <span class="effect-level" v-if="effect.Level">Lv.{{ effect.Level }}</span>
              </div>
            </div>
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
          <div v-for="arkgrid in characterData.arkGridSlots" :key="arkgrid.Name" class="ark-item">
            <img :src="arkgrid.Icon" width="18">
           <span>{{ arkgrid.Name }}</span>
            <span class="point">{{ arkgrid.Grade }}</span>
            <span class="point">{{ arkgrid.Tooltip }}</span>
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
              <img :src="card.image">
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

const filterEffects = (categoryName) => {
  const effects = props.characterData?.arkPassiveEffects;
  if (!effects) return [];

  return effects
    .filter(effect => effect.Description && effect.Description.includes(categoryName))
    .map(effect => {
      const desc = effect.Description;

      // HTML 태그 제거 및 텍스트만 추출
      const cleanText = desc.replace(/<[^>]*>?/gm, '').trim();

      // 티어 정보 추출 (숫자 + 티어)
      const tierMatch = cleanText.match(/(\d+)티어/);
      const tier = tierMatch ? tierMatch[1] : '';

      // 레벨 정보 추출 (Lv. + 숫자)
      const levelMatch = cleanText.match(/Lv\.(\d+)/);
      const level = levelMatch ? levelMatch[1] : '';

      // 스킬/패시브 이름 정밀 추출
      let pureName = cleanText
        .replace(categoryName, '')
        .replace(`${tier}티어`, '')
        .replace(`Lv.${level}`, '')
        .trim();

      return {
        ...effect,
        Tier: tier,
        Level: level,
        PureName: pureName || effect.Name, // 이름 추출 실패 시 기본 Name 사용
        FullText: cleanText
      };
    });
};
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

.ark-group {
  margin-bottom: 20px;
  text-align: left;
}

.group-header {
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  margin-bottom: 8px;
  padding-bottom: 4px;
}

.group-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #83E9FF;
  margin-right: 10px;
}

.effect-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 2px 0;
  font-size: 0.9rem;
}

.effect-level {
  color: #ffd200;
}




.effect-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.tier-badge {
  background: rgba(255, 255, 255, 0.15);
  color: #aaa;
  font-size: 10px;
  padding: 1px 4px;
  border-radius: 4px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.effect-name {
  font-size: 13px;
  color: #efefef;
}

.effect-icon {
  border-radius: 4px;
  background: rgba(0, 0, 0, 0.3);
}
</style>