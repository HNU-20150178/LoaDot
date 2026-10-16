<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import SearchBox from './SearchBox.vue'

const route = useRoute()
const router = useRouter()

const isHome = computed(() => route.name === 'home')

const handleHeaderSearch = (searchName) => {
  if (!searchName.trim()) return
  router.push({
    name: 'search-result',
    params: { characterName: searchName.trim() }
  })
}
</script>

<template>
  <header :class="{ 'header-home': isHome, 'header-result': !isHome }">
    <!-- 로고 영역 -->
    <h1>
      <router-link to="/" class="logo-link">
        LoaDot<span class="dot">.</span>
      </router-link>
    </h1>

    <!-- 검색 영역 -->
    <div v-if="!isHome" class="search-bar-area">
      <SearchBox 
        v-model:searchName="route.params.characterName"
        :loading="false"
        errorMsg=""
        @search="handleHeaderSearch(route.params.characterName)"
      />
    </div>
  </header>
</template>

<style scoped>
.logo-link, 
.logo-link:visited, 
.logo-link:active, 
.logo-link:focus {
  color: #ffffff;
  text-decoration: none;
  outline: none;
}

header.header-home {
  margin-bottom: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 0;
}

header.header-home h1 {
  font-size: 3rem;
  margin: 0;
  color: white;
}

header.header-result {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 15px 30px;
  margin-bottom: 1rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  box-sizing: border-box;
}

header.header-result h1 {
  font-size: 1.8rem;
  margin: 0;
  color: white;
}

.dot { 
  color: #00d2d3; 
}

.search-bar-area :deep(.search-box) {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 380px;
  gap: 8px;
}

.search-bar-area :deep(input) {
  flex: 1;
  padding: 10px 14px;
  text-align: left;
  height: 40px;
  box-sizing: border-box;
}

.search-bar-area :deep(button) {
  padding: 0 16px;
  height: 40px;
  box-sizing: border-box;
  white-space: nowrap;
}

.search-bar-area :deep(.error) {
  position: absolute;
  top: 60px;
  font-size: 0.8rem;
}
</style>