<script setup>
const props = defineProps({ baseStats: { type: Object, default: () => ({ attackPower: '0', maxHp: '0' }) }, combatStats: { type: Array, default: () => [] } })
const formatNum = (value) => Number(value).toLocaleString()
</script>

<template>
  <section class="detail-section-card">
    <h3 class="detail-section-title">기본 정보</h3>
    <div class="stats-grid">
      <div class="stat-row"><span>공격력</span><span class="value">{{ formatNum(baseStats?.attackPower ?? '0') }}</span></div>
      <div class="stat-row"><span>최대 생명력</span><span class="value">{{ formatNum(baseStats?.maxHp ?? '0') }}</span></div>
    </div>
    <hr class="divider">
    <div class="stats-grid"><div v-for="stat in combatStats" :key="stat.type" class="stat-row small"><span class="label">{{ stat.type }}</span><span class="value">{{ stat.value }}</span></div></div>
  </section>
</template>

<style scoped>
.stats-grid { display: grid; grid-template-columns: 1fr 1fr; column-gap: 20px; row-gap: 8px; }
.stat-row { display: flex; justify-content: space-between; margin-bottom: 8px; font-size: 13px; }
.divider { border: 0; border-top: 1px solid rgba(255,255,255,.1); margin: 3px 0; }
@media (max-width: 360px) { .stats-grid { grid-template-columns: 1fr; } }
</style>
