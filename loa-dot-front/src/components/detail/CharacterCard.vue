<script setup>
import { getGradeColor } from '@/utils/gameData'
defineProps({ cards: { type: Array, default: () => [] }, setEffects: { type: Array, default: () => [] }, setName: { type: String, default: '' } })
</script>

<template>
  <section class="detail-section-card">
    <h3 class="detail-section-title">카드</h3>
    <div v-if="cards?.length">
      <div class="card-wrap" tabindex="0">
        <div class="card">
          <div v-for="card in cards" :key="card.name" class="card-item" :style="{ borderColor: getGradeColor(card.grade) }">
            <img :src="card.image" :alt="card.name" class="card-img">
            <div class="star-bar"><span v-for="i in 5" :key="i" class="star" :class="{ active: i <= card.awakeCount }" :style="i <= card.awakeCount ? { color: getGradeColor(card.grade) } : {}">★</span></div>
          </div>
        </div>
        <div class="card-tooltip" role="tooltip">
          <div class="tt-title">카드 정보</div>
          <div class="tt-cards"><div v-for="card in cards" :key="card.name" class="tt-card"><span class="tt-name">{{ card.name }}</span><span class="tt-awake">+{{ card.awakeCount }}</span></div></div>
          <div class="tt-effects"><div class="tt-effects-title">카드 효과</div><ul><li v-for="effect in setEffects" :key="effect">{{ effect }}</li></ul></div>
        </div>
      </div>
      <div class="card-set-effect"><span class="set-name">{{ setName }}</span></div>
    </div>
    <div v-else class="no-data">활성화된 카드가 없습니다.</div>
  </section>
</template>

<style scoped>
.card { display: grid; grid-template-columns: repeat(6,minmax(0,1fr)); gap: 4px; width: 100%; box-sizing: border-box; }
.card-item { position: relative; width: 100%; aspect-ratio: 1/1.4; border-radius: 6px; border: 2px solid transparent; background: #1c1e22; overflow: hidden; box-sizing: border-box; }
.card-img { width: 100%; height: 100%; display: block; object-fit: cover; }
.star-bar { position: absolute; bottom: 0; left: 0; right: 0; display: flex; justify-content: center; gap: 1px; background: linear-gradient(to top,rgba(0,0,0,.9),rgba(0,0,0,.5) 60%,transparent); padding: 15px 0 5px; }
.star { font-size: 10px; color: rgba(255,255,255,.15); text-shadow: 1px 1px 1px rgba(0,0,0,.9); }
.star.active { text-shadow: 0 0 4px currentColor,1px 1px 1px rgba(0,0,0,.8); }
.card-wrap { position: relative; width: 100%; outline: none; }
.card-tooltip { position: absolute; bottom: calc(100% + 10px); left: 50%; transform: translateX(-50%) translateY(4px); z-index: 50; width: max-content; min-width: min(260px,calc(100vw - 48px)); max-width: min(360px,calc(100vw - 48px)); padding: 10px 12px; background: #1e1e1e; border: 1px solid #333; border-radius: 6px; color: #e5e5e5; font-size: 12px; box-shadow: 0 6px 20px rgba(0,0,0,.5); opacity: 0; visibility: hidden; pointer-events: none; transition: opacity .15s,transform .15s,visibility .15s; }
.card-tooltip::after { content: ''; position: absolute; top: 100%; left: 50%; width: 10px; height: 10px; background: #1e1e1e; border-right: 1px solid #333; border-bottom: 1px solid #333; transform: translate(-50%,-5px) rotate(45deg); }
.card-wrap:hover .card-tooltip,.card-wrap:focus-within .card-tooltip,.card-wrap:focus .card-tooltip { opacity: 1; visibility: visible; transform: translateX(-50%) translateY(0); }
.tt-title { text-align: center; font-weight: 600; font-size: 14px; margin-bottom: 8px; }
.tt-cards { display: grid; grid-template-columns: 1fr 1fr; gap: 4px 12px; margin-bottom: 10px; }
.tt-card { display: flex; justify-content: space-between; gap: 8px; }
.tt-name { max-width: 120px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.tt-awake { color: #facc15; }
.tt-effects { border-top: 1px solid #374151; padding-top: 8px; }
.tt-effects-title { color: #93c5fd; font-weight: 500; margin-bottom: 4px; }
.tt-effects ul { list-style: none; margin: 0; padding: 0; }
.tt-effects li { font-size: 11px; line-height: 1.5; color: #facc15; margin-bottom: 2px; }
.set-name { color: var(--detail-accent); }
.no-data { text-align: center; color: #666; font-size: 12px; padding: 20px 0; }
</style>
