<template>
  <div class="character-detail-container">
    <div class="detail-grid">

      <div class="detail-column">
        <section class="qul-box glass">
          <h3 class="section-title">아크 패시브</h3>
          <p class="passive-title">{{ characterData.arkPassiveTitle }}</p>

          <div
            v-for="point in characterData.arkPassivePoints"
            :key="point.Name"
            class="ark-group"
          >
            <div class="group-header" :class="getCategoryClass(point.Name)">
              <div class="header-left">
                <span class="category-dot" :class="getCategoryClass(point.Name)"></span>
                <span class="group-name">{{ point.Name }}</span>
              </div>
              <div class="header-right">
                <span class="group-value">{{ point.Value }}</span>
                <span class="group-rank" v-if="point.Rank">{{ point.Rank }}</span>
              </div>
            </div>

            <div class="effect-list">
              <div
                v-for="(effect, index) in filterEffects(point.Name)"
                :key="`${point.Name}-${effect.name}-${index}`"
                class="effect-row"
              >
                <div class="effect-icon-wrap" v-if="effect.icon">
                  <img :src="effect.icon" :alt="effect.name" class="effect-icon" />
                </div>
                <div class="effect-icon-wrap placeholder" v-else></div>

                <div class="effect-body">
                  <div class="effect-name-row">
                    <span class="effect-title" :title="effect.description">{{ effect.title }}</span>
                    <span class="effect-level" v-if="effect.level">{{ effect.level }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>

        <!-- 기본 정보 -->
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

        <!-- 아크 그리드 -->
        <section class="qul-box glass">
          <h3 class="section-title">아크 그리드</h3>
          <div v-for="arkgrid in characterData.arkGridSlots" :key="arkgrid.name" class="ark-item">
            <div class="ark-header">
              <img :src="arkgrid.icon" width="18">
              <span class="ark-name">{{ arkgrid.name }}</span>
              <span class="ark-grade">{{ arkgrid.grade }}</span>
            </div>
            <div class="ark-tooltip-box">
              <p v-for="(line, idx) in arkgrid.parsedTooltip" :key="idx" class="tooltip-line">
                {{ line }}
              </p>
            </div>
          </div>
        </section>
      </div>

      <div class="detail-column">
        <section class="qul-box glass">
          <h3 class="section-title">각인</h3>
          
          <div v-if="characterData?.engravingsArkPassiveEffects && characterData.engravingsArkPassiveEffects.length > 0">
            <div 
              v-for="(engrave, index) in characterData.engravingsArkPassiveEffects" 
              :key="index" 
              :class="['engrave-passive-item', getGradeClass(engrave.Grade)]"
            >
              <div class="engrave-info">
                <span class="engrave-name">{{ engrave.Name }}</span>
                <span class="engrave-grade-badge">{{ engrave.Grade }} Lv.{{ engrave.Level }}</span>
                <span class="stone-level" v-if="engrave.AbilityStoneLevel">스톤 Lv.{{ engrave.AbilityStoneLevel }}</span>
              </div>
              <p class="engrave-passive-desc">{{ engrave.Description }}</p>
            </div>
          </div>
          <div v-else class="no-data">활성화된 아크 패시브 각인이 없습니다.</div>
        </section>
      </div>

      <div class="detail-column">
        <section class="qul-box glass">
          <h3 class="section-title">카드</h3>
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
import { computed } from 'vue'

const props = defineProps({
  characterData: {
    type: Object,
    required: true
  }
})

const combatStats = computed(() => props.characterData.combatStats || [])
const baseStats = computed(() => props.characterData.baseStats || { attackPower: '0', maxHp: '0' })

const formatNum = (val) => Number(val).toLocaleString()

const getCategoryClass = (name) => {
  if (name?.includes('진화')) return 'evolution'
  if (name?.includes('깨달음')) return 'enlighten'
  if (name?.includes('도약')) return 'leap'
  return ''
}

const getGradeClass = (grade) => {
  if (!grade) return 'normal';
  if (grade === '유물') return 'relic';
  if (grade === '고대') return 'ancient';
  return 'normal';
};

const filterEffects = (categoryName) => {
  const effects = props.characterData?.arkPassiveEffects
  if (!effects) return []

  const pureCategory = categoryName.replace(' 포인트', '').trim()

  return effects.filter(effect => {
    const nameMatch = effect.name && effect.name.includes(pureCategory)
    const detailMatch = effect.details && effect.details.some(d =>
      (d.title && d.title.includes(pureCategory)) ||
      (d.description && d.description.includes(pureCategory))
    )
    return nameMatch || detailMatch
  })
}
</script>

<style scoped>
.base-stats-grid,
.combat-stats-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  column-gap: 20px;
  row-gap: 8px;
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
  margin-bottom: 6px;
  font-weight: bold;
  color: #fff;
}

.passive-title {
  text-align: center;
  font-size: 11px;
  color: #888;
  margin-bottom: 16px;
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

/* ── 아크패시브 카테고리 그룹 ── */
.ark-group {
  margin-bottom: 18px;
}
.ark-group:last-child {
  margin-bottom: 0;
}

.group-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 6px 10px;
  border-radius: 8px;
  margin-bottom: 8px;
}

.group-header.evolution { background: rgba(234,179,8,0.08);  border: 1px solid rgba(234,179,8,0.2); }
.group-header.enlighten { background: rgba(59,130,246,0.08); border: 1px solid rgba(59,130,246,0.2); }
.group-header.leap      { background: rgba(34,197,94,0.08);  border: 1px solid rgba(34,197,94,0.2); }

.header-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.category-dot {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  flex-shrink: 0;
}
.category-dot.evolution { background: #eab308; box-shadow: 0 0 6px rgba(234,179,8,0.6); }
.category-dot.enlighten { background: #3b82f6; box-shadow: 0 0 6px rgba(59,130,246,0.6); }
.category-dot.leap      { background: #22c55e; box-shadow: 0 0 6px rgba(34,197,94,0.6); }

.group-name {
  font-size: 13px;
  font-weight: 700;
}
.evolution .group-name { color: #eab308; }
.enlighten .group-name { color: #3b82f6; }
.leap      .group-name { color: #22c55e; }

.header-right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 1px;
}

.group-value {
  font-size: 13px;
  font-weight: 700;
  color: #f0f0f0;
}

.group-rank {
  font-size: 10px;
  color: #888;
}

/* ── 이펙트 리스트 ── */
.effect-list {
  display: flex;
  flex-direction: column;
  gap: 2px;
  padding: 0 4px;
}

.effect-row {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  padding: 5px 8px;
  border-radius: 6px;
  transition: background 0.15s;
}
.effect-row:hover {
  background: rgba(255,255,255,0.04);
}

.effect-icon-wrap {
  flex-shrink: 0;
  width: 28px;
  height: 28px;
  border-radius: 6px;
  overflow: hidden;
  background: linear-gradient(135deg, #1a1a2e, #2a2a3e);
  border: 1px solid rgba(255,255,255,0.1);
}
.effect-icon-wrap.placeholder {
  background: rgba(255,255,255,0.03);
  border-color: rgba(255,255,255,0.05);
}
.effect-icon {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.effect-body {
  flex: 1;
  min-width: 0;
}

.effect-name-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 6px;
  margin-bottom: 2px;
}

.effect-title {
  font-size: 12px;
  font-weight: 600;
  color: #ddd;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.effect-level {
  flex-shrink: 0;
  font-size: 11px;
  font-weight: 700;
  color: #ffd200;
  background: rgba(255,210,0,0.1);
  border: 1px solid rgba(255,210,0,0.2);
  border-radius: 4px;
  padding: 0 5px;
  line-height: 1.6;
}

.effect-detail {
  display: flex;
  gap: 4px;
  font-size: 10px;
  color: #777;
  line-height: 1.4;
  overflow: hidden;
}

.detail-title {
  flex-shrink: 0;
  color: #83E9FF;
  font-weight: 600;
}

.detail-desc {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* ── 아크 그리드 ── */
.ark-item {
  margin-bottom: 10px;
}

.ark-header {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-bottom: 4px;
}

.ark-name {
  font-size: 13px;
  font-weight: 600;
  color: #ddd;
}

.ark-grade {
  font-size: 11px;
  color: #888;
  margin-left: auto;
}

.ark-tooltip-box {
  padding: 6px 8px;
  background: rgba(255,255,255,0.03);
  border-radius: 6px;
  border: 1px solid rgba(255,255,255,0.06);
}

.tooltip-line {
  font-size: 11px;
  color: #999;
  line-height: 1.5;
  margin: 0;
}

.engrave-passive-item {
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 6px;
  padding: 10px;
  margin-bottom: 10px;
}
.engrave-passive-item.relic { border-left: 3px solid #ea6000; }
.engrave-passive-item.ancient { border-left: 3px solid #ccaa00; }

.engrave-info {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-bottom: 6px;
}
.engrave-name {
  font-size: 13px;
  font-weight: bold;
  color: #fff;
}
.engrave-grade-badge {
  font-size: 10px;
  padding: 1px 4px;
  border-radius: 3px;
  background: rgba(255,255,255,0.1);
  color: #aaa;
}
.relic .engrave-grade-badge { color: #ff9933; background: rgba(234, 96, 0, 0.15); }
.ancient .engrave-grade-badge { color: #ffcc00; background: rgba(204, 170, 0, 0.15); }

.stone-level {
  font-size: 10px;
  color: #00ccff;
  margin-left: auto;
}
.engrave-passive-desc {
  font-size: 11px;
  line-height: 1.4;
  color: #b0b8c4;
  margin: 0;
  white-space: pre-wrap;
}
.no-data { text-align: center; color: #666; font-size: 12px; padding: 20px 0; }
</style>