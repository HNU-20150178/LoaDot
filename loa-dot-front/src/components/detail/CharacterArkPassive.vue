<script setup>
const props = defineProps({ title: String, points: { type: Array, default: () => [] }, effects: { type: Array, default: () => [] } })
const getCategoryClass = (name) => {
  if (name?.includes('진화')) return 'evolution'
  if (name?.includes('깨달음')) return 'enlighten'
  if (name?.includes('도약')) return 'leap'
  return ''
}
const filterEffects = (categoryName) => {
  if (!props.effects) return []
  const pureCategory = categoryName.replace(' 패시브', '').trim()
  return props.effects.filter(effect => {
    const nameMatch = effect.name && effect.name.includes(pureCategory)
    const detailMatch = effect.details && effect.details.some(d =>
      (d.title && d.title.includes(pureCategory)) || (d.description && d.description.includes(pureCategory))
    )
    return nameMatch || detailMatch
  })
}
</script>

<template>
  <section class="detail-section-card">
    <h3 class="detail-section-title">아크 패시브</h3>
    <p class="passive-title">{{ title }}</p>
    <div v-for="point in points" :key="point.Name" class="ark-group">
      <div class="group-header" :class="getCategoryClass(point.Name)">
        <div class="header-left"><span class="category-dot" :class="getCategoryClass(point.Name)"></span><span class="group-name">{{ point.Name }}</span></div>
        <div class="header-right"><span class="group-value">{{ point.Value }} P</span><span v-if="point.Rank" class="group-rank">{{ point.Rank }}</span></div>
      </div>
      <div class="effect-list">
        <div v-for="(effect, index) in filterEffects(point.Name)" :key="`${point.Name}-${effect.name}-${index}`" class="effect-row">
          <div v-if="effect.icon" class="effect-icon-wrap"><img :src="effect.icon" :alt="effect.name" class="effect-icon"></div>
          <div v-else class="effect-icon-wrap placeholder"></div>
          <div class="effect-body"><div class="effect-name-row"><span class="effect-title" :title="effect.description">{{ effect.title }}</span><span v-if="effect.level" class="effect-level">{{ effect.level }}</span></div></div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.passive-title { text-align: left; font-size: 11px; color: var(--loa-muted); margin: -4px 0 12px; }
.ark-group { margin-bottom: 12px; } .ark-group:last-child { margin-bottom: 0; }
.group-header { display: flex; align-items: center; justify-content: space-between; padding: 7px 9px; border-radius: 7px; margin-bottom: 6px; background: var(--loa-slot); border: 1px solid var(--loa-border); }
.group-header.evolution { border-color: rgba(234,179,8,.2); }
.group-header.enlighten { border-color: rgba(96,165,250,.2); }
.group-header.leap { border-color: rgba(74,251,230,.18); }
.header-left { display: flex; align-items: center; gap: 8px; }
.category-dot { width: 7px; height: 7px; border-radius: 50%; flex-shrink: 0; }
.category-dot.evolution { background: var(--loa-gold-soft); box-shadow: 0 0 5px rgba(234,179,8,.35); }
.category-dot.enlighten { background: #8caef0; box-shadow: 0 0 5px rgba(96,165,250,.3); }
.category-dot.leap { background: #72d9c8; box-shadow: 0 0 5px rgba(74,251,230,.25); }
.group-name { font-size: 12px; font-weight: 700; } .evolution .group-name { color: var(--loa-gold-soft); } .enlighten .group-name { color: #9bbbf5; } .leap .group-name { color: #83d9ca; }
.header-right { display: flex; flex-direction: column; align-items: flex-end; gap: 1px; }
.group-value { padding: 2px 7px; border: 1px solid var(--loa-border-strong); border-radius: 99px; font-size: 10px; font-weight: 750; color: var(--loa-text); background: rgba(255,255,255,.04); } .group-rank { font-size: 9px; color: var(--loa-muted); }
.effect-list { display: flex; flex-direction: column; gap: 2px; padding: 0 4px; }
.effect-row { display: flex; align-items: flex-start; gap: 8px; padding: 5px 8px; border-radius: 6px; transition: background .15s; }
.effect-row:hover { background: rgba(255,255,255,.04); }
.effect-icon-wrap { flex-shrink: 0; width: 28px; height: 28px; border-radius: 6px; overflow: hidden; background: linear-gradient(135deg,#1a1a2e,#2a2a3e); border: 1px solid rgba(255,255,255,.1); }
.effect-icon-wrap.placeholder { background: rgba(255,255,255,.03); border-color: rgba(255,255,255,.05); }
.effect-icon { width: 100%; height: 100%; object-fit: cover; }
.effect-body { flex: 1; min-width: 0; }
.effect-name-row { display: flex; align-items: center; justify-content: space-between; gap: 6px; margin-bottom: 2px; }
.effect-title { font-size: 12px; font-weight: 600; color: #ddd; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.effect-level { flex-shrink: 0; font-size: 11px; font-weight: 700; color: #ffd200; background: rgba(255,210,0,.1); border: 1px solid rgba(255,210,0,.2); border-radius: 4px; padding: 0 5px; line-height: 1.6; }
</style>
