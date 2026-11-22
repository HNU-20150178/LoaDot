<script setup>
import { getGradeColor } from '@/utils/gameData'
defineProps({ slots: { type: Array, default: () => [] } })
</script>

<template>
  <section class="detail-section-card">
    <h3 class="detail-section-title">아크 그리드</h3>
    <div v-for="slot in slots" :key="slot.name" class="ark-item" :style="{ borderLeft: `3px solid ${getGradeColor(slot.grade)}` }">
      <div class="ark-header">
        <img :src="slot.icon" width="52" height="52" :alt="slot.name">
        <span class="ark-name">{{ slot.name }}</span>
        <span class="ark-grade" :style="{ color: getGradeColor(slot.grade) }">{{ slot.grade }}</span>
        <span class="ark-point" :style="{ color: getGradeColor(slot.grade) }">({{ slot.point }})</span>
      </div>
      <div class="ark-tooltip-box"><p v-for="(line, idx) in slot.parsedTooltip" :key="idx" class="tooltip-line">{{ line }}</p></div>
    </div>
  </section>
</template>

<style scoped>
.ark-item { margin-bottom: 10px; position: relative; }
.ark-header { display: flex; align-items: center; gap: 6px; margin-bottom: 4px; min-width: 0; }
.ark-name { font-size: 11px; font-weight: 600; color: #ddd; }
.ark-grade { font-size: 11px; margin-left: auto; }
.ark-point { font-size: 11px; }
.ark-tooltip-box { display: none; position: absolute; top: 100%; left: 0; z-index: 100; pointer-events: none; width: max-content; max-width: min(350px, calc(100vw - 48px)); padding: 10px 12px; background: rgba(20,20,20,.95); border: 1px solid rgba(255,255,255,.15); border-radius: 6px; box-shadow: 0 4px 12px rgba(0,0,0,.5); }
.ark-header:hover + .ark-tooltip-box, .ark-tooltip-box:hover { display: block; }
.tooltip-line { font-size: 11px; color: #cbd5e1; line-height: 1.6; margin: 0; padding: 2px 0; }
</style>
