<script setup>
import { computed } from 'vue'
const props = defineProps({ gems: { type: Array, default: () => [] } })
const gems = computed(() => Array.isArray(props.gems) ? props.gems : [])
const gemTypeClass = (type) => ({
  damage: ['멸화', '겁화'].includes(type),
  cooldown: ['홍염', '작열'].includes(type),
  luminous: type === '광휘'
})
</script>

<template>
  <section class="detail-section-card">
    <h3 class="detail-section-title">보석</h3>
    <div v-if="gems.length" class="gem-grid">
      <article v-for="gem in gems" :key="gem.slot" class="gem-card">
        <div class="gem-icon-wrap">
          <img v-if="gem.icon" :src="gem.icon" :alt="`${gem.level ?? ''}레벨 ${gem.type ?? '보석'}`" class="gem-icon">
          <span v-else class="gem-icon-placeholder">보석</span>
          <span v-if="gem.level != null" class="gem-level">Lv.{{ gem.level }}</span>
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
.gem-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(min(100%, 190px), 1fr)); gap: 8px; }
.gem-card { display: flex; align-items: center; gap: 10px; min-width: 0; padding: 8px; border: 1px solid var(--detail-border); border-radius: 8px; background: var(--detail-surface); }
.gem-icon-wrap { position: relative; flex: 0 0 48px; width: 48px; height: 48px; border: 1px solid rgba(255,210,0,.35); border-radius: 7px; background: #171717; }
.gem-icon, .gem-icon-placeholder { display: grid; width: 100%; height: 100%; place-items: center; object-fit: cover; border-radius: inherit; color: #888; font-size: 10px; }
.gem-level { position: absolute; right: -5px; bottom: -5px; padding: 1px 4px; border: 1px solid rgba(255,210,0,.3); border-radius: 4px; background: #1e1e1e; color: #ffd200; font-size: 10px; font-weight: 700; }
.gem-info { min-width: 0; }
.gem-type { display: inline-block; margin-bottom: 5px; color: #d1d5db; font-size: 12px; font-weight: 700; }
.gem-type.damage { color: #fb923c; } .gem-type.cooldown { color: #60a5fa; } .gem-type.luminous { color: #c4b5fd; }
.gem-skill { display: flex; align-items: center; gap: 5px; min-width: 0; color: #ddd; font-size: 11px; }
.gem-skill span { overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.gem-skill-icon { width: 20px; height: 20px; flex: 0 0 20px; border-radius: 4px; object-fit: cover; }
.gem-skill-unmatched { color: #777; font-size: 10px; }
.no-data { text-align: center; color: #666; font-size: 12px; padding: 20px 0; }
</style>
