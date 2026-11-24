<script setup>
import AppHeader from './components/AppHeader.vue'
</script>

<template>
  <div class="app-container">
    
    <AppHeader />

    <RouterView />

  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
  min-height: 100vh;
  background-color: var(--loa-bg);
  color: var(--loa-text);
  font-family: 'Apple SD Gothic Neo', sans-serif;
}
</style>
