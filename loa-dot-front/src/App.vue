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
  justify-content: center;
  min-height: 100vh;
  background-color: #121212;
  color: #e0e0e0;
  font-family: 'Apple SD Gothic Neo', sans-serif;
}
</style>