<script setup>
import { computed } from 'vue'
import { getGradeColor } from '@/utils/gameData'
const props = defineProps({ gems: { type: Array, default: () => [] } })
const gems = computed(() => Array.isArray(props.gems) ? props.gems : [])
const averageLevel = computed(() => gems.value.length
  ? (gems.value.reduce((total, gem) => total + (Number(gem.level) || 0), 0) / gems.value.length).toFixed(1)
  : '0.0')
const gemTypeClass = (type) => ({
  damage: ['멸화', '겁화'].includes(type),
  cooldown: ['홍염', '작열'].includes(type),
  luminous: type === '광휘'
})
</script>

<template>
  <section class="detail-section-card">
    <h3 class="detail-section-title">
      <span>보석</span>
      <span class="gem-summary"><strong>{{ gems.length }}</strong>개 <i></i> 평균 Lv.{{ averageLevel }}</span>
    </h3>
    <div v-if="gems.length" class="gem-grid">
      <article v-for="gem in gems" :key="gem.slot" class="gem-card">
        <div class="gem-icon-wrap">
          <img v-if="gem.icon" :src="gem.icon" :alt="`${gem.level ?? ''}레벨 ${gem.type ?? '보석'}`" class="gem-icon">
          <span v-else class="gem-icon-placeholder">보석</span>
          <span v-if="gem.level != null" class="gem-level" :style="{ borderColor: getGradeColor(gem.grade), color: getGradeColor(gem.grade) }">{{ gem.level }}Lv</span>
        </div>
        <div class="gem-info">
          <span class="gem-type" :class="gemTypeClass(gem.type)">{{ gem.type || '종류 미상' }}</span>
          <div v-if="gem.skillName || gem.skillIcon" class="gem-skill">
            <img v-if="gem.skillIcon" :src="gem.skillIcon" :alt="gem.skillName || '적용 스킬'" class="gem-skill-icon">
            <span>{{ gem.skillName || '연결된 스킬' }}</span>
          </div>
          <span v-else class="gem-skill-unmatched">연결된 스킬 정보 없음</span>
        </div>
      </article>
    </div>
    <div v-else class="no-data">장착된 보석 정보가 없습니다.</div>
  </section>
</template>

<style scoped>
.gem-summary { display: inline-flex; align-items: center; gap: 5px; margin-left: auto; color: var(--loa-muted); font-size: 10px; font-weight: 500; }
.gem-summary strong { color: var(--loa-gold); font-size: 11px; }
.gem-summary i { width: 3px; height: 3px; border-radius: 50%; background: var(--loa-muted); }
.gem-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(min(100%, 220px), 1fr)); gap: 7px; }
.gem-card { display: flex; align-items: center; gap: 9px; min-width: 0; padding: 7px 9px; border: 1px solid var(--detail-border); border-radius: 8px; background: var(--detail-surface); }
.gem-icon-wrap { position: relative; flex: 0 0 38px; width: 38px; height: 38px; border: 1px solid var(--loa-border-strong); border-radius: 7px; background: var(--loa-bg-raised); }
.gem-icon, .gem-icon-placeholder { display: grid; width: 100%; height: 100%; place-items: center; object-fit: cover; border-radius: inherit; color: #888; font-size: 10px; }
.gem-level { position: absolute; right: -5px; bottom: -5px; padding: 1px 4px; border: 1px solid; border-radius: 4px; background: var(--loa-bg-raised); font-size: 9px; font-weight: 700; line-height: 1.35; }
.gem-info { display: flex; align-items: center; gap: 8px; min-width: 0; flex: 1; }
.gem-type { flex: 0 0 auto; display: inline-block; padding: 3px 6px; border: 1px solid var(--loa-border); border-radius: 5px; background: rgba(255,255,255,.035); color: #d1d5db; font-size: 10px; font-weight: 700; }
.gem-type.damage { color: #f6bd72; border-color: rgba(245,158,11,.25); background: rgba(245,158,11,.08); } .gem-type.cooldown { color: #91bdf5; border-color: rgba(96,165,250,.24); background: rgba(96,165,250,.08); } .gem-type.luminous { color: #c4b5fd; }
.gem-skill { display: flex; align-items: center; gap: 5px; min-width: 0; color: #ddd; font-size: 11px; }
.gem-skill span { overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.gem-skill-icon { width: 20px; height: 20px; flex: 0 0 20px; border-radius: 4px; object-fit: cover; }
.gem-skill-unmatched { color: var(--loa-muted); font-size: 10px; white-space: nowrap; }
.no-data { text-align: center; color: #666; font-size: 12px; padding: 20px 0; }
</style>
