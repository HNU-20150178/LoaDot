<script setup>
import { getGradeColor } from '@/utils/gameData'
defineProps({ engravings: { type: Array, default: () => [] } })
</script>

<template>
  <section class="detail-section-card">
    <h3 class="detail-section-title">각인</h3>
    <div v-if="engravings?.length">
      <div v-for="(engrave, index) in engravings" :key="index" class="engrave-passive-item" :style="{ borderLeft: `3px solid ${getGradeColor(engrave.Grade)}` }">
        <div class="engrave-info">
          <span class="engrave-name" :style="{ color: getGradeColor(engrave.Grade) }">{{ engrave.Name }}</span>
          <span class="engrave-grade-badge">{{ engrave.Grade }} Lv.{{ engrave.Level }}</span>
          <span v-if="engrave.AbilityStoneLevel" class="stone-level">스톤 Lv.{{ engrave.AbilityStoneLevel }}</span>
        </div>
        <p class="engrave-passive-desc">{{ engrave.Description }}</p>
      </div>
    </div>
    <div v-else class="no-data">활성화된 각인이 없습니다.</div>
  </section>
</template>

<style scoped>
.engrave-passive-item { background: var(--detail-surface); border: 1px solid var(--detail-border); border-radius: 8px; padding: 10px; margin-bottom: 8px; }
.engrave-info { display: flex; align-items: center; gap: 6px; margin-bottom: 6px; min-width: 0; flex-wrap: wrap; }
.engrave-name { font-size: 13px; font-weight: 700; }
.engrave-grade-badge { font-size: 9px; padding: 2px 6px; border: 1px solid var(--loa-border); border-radius: 99px; background: rgba(255,255,255,.04); color: var(--loa-muted); }
.stone-level { font-size: 10px; color: #00ccff; margin-left: auto; }
.engrave-passive-desc { font-size: 11px; line-height: 1.4; color: #b0b8c4; margin: 0; white-space: pre-wrap; }
.no-data { text-align: center; color: #666; font-size: 12px; padding: 20px 0; }
</style>
