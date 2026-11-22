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
        <div class="header-right"><span class="group-value">{{ point.Value }}</span><span v-if="point.Rank" class="group-rank">{{ point.Rank }}</span></div>
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
.passive-title { text-align: center; font-size: 13px; color: #888; margin-bottom: 16px; }
.ark-group { margin-bottom: 18px; } .ark-group:last-child { margin-bottom: 0; }
.group-header { display: flex; align-items: center; justify-content: space-between; padding: 6px 10px; border-radius: 8px; margin-bottom: 8px; }
.group-header.evolution { background: rgba(234,179,8,.08); border: 1px solid rgba(234,179,8,.2); }
.group-header.enlighten { background: rgba(59,130,246,.08); border: 1px solid rgba(59,130,246,.2); }
.group-header.leap { background: rgba(34,197,94,.08); border: 1px solid rgba(34,197,94,.2); }
.header-left { display: flex; align-items: center; gap: 8px; }
.category-dot { width: 7px; height: 7px; border-radius: 50%; flex-shrink: 0; }
.category-dot.evolution { background: #eab308; box-shadow: 0 0 6px rgba(234,179,8,.6); }
.category-dot.enlighten { background: #3b82f6; box-shadow: 0 0 6px rgba(59,130,246,.6); }
.category-dot.leap { background: #22c55e; box-shadow: 0 0 6px rgba(34,197,94,.6); }
.group-name { font-size: 13px; font-weight: 700; } .evolution .group-name { color: #eab308; } .enlighten .group-name { color: #3b82f6; } .leap .group-name { color: #22c55e; }
.header-right { display: flex; flex-direction: column; align-items: flex-end; gap: 1px; }
.group-value { font-size: 13px; font-weight: 700; color: #f0f0f0; } .group-rank { font-size: 10px; color: #888; }
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
