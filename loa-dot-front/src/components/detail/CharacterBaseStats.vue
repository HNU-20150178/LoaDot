<script setup>
const props = defineProps({ baseStats: { type: Object, default: () => ({ attackPower: '0', maxHp: '0' }) }, combatStats: { type: Array, default: () => [] } })
const formatNum = (value) => Number(value).toLocaleString()
</script>

<template>
  <section class="detail-section-card hero-stats-card">
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
.hero-stats-card { display: flex; flex-direction: column; }
.hero-stats-card .stats-grid:first-of-type .stat-row { flex-direction: column; gap: 2px; padding: 12px; margin: 0; border: 1px solid var(--loa-border); border-radius: 9px; background: var(--loa-slot); }
.hero-stats-card .stats-grid:first-of-type .stat-row > span:first-child { color: var(--loa-muted); font-size: 11px; }
.hero-stats-card .stats-grid:first-of-type .value { color: var(--loa-gold-soft); font-size: 18px; font-weight: 750; }
.hero-stats-card .stats-grid:last-of-type { margin-top: 5px; }
.hero-stats-card .stats-grid:last-of-type .stat-row { color: var(--loa-muted); font-size: 11px; }
.hero-stats-card .stats-grid:last-of-type .value { color: var(--loa-text); font-weight: 650; }
@media (max-width: 360px) { .stats-grid { grid-template-columns: 1fr; } }
</style>
