<script setup>
// 헤더
// vuerouter 추가 예정 
</script>

<template>
  <header>
    <h1>
      <router-link to="/" class="logo-link">
      LoaDot
      <span class="dot">.</span>
      </router-link>
    </h1>
  </header>
</template>

<style scoped>
header {
  margin-bottom: 2rem;
}

h1 { 
  font-size: 3rem; 
  margin: 0;
  color: white; 
}

.dot { 
  color: #00d2d3; 
}
</style>