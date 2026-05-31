<template>
  <SearchBox 
    v-model:searchName="searchName"
    :loading="false"
    errorMsg=""
    @search="goToResult"
  />
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import SearchBox from './SearchBox.vue'

const searchName = ref('')
const router = useRouter()

const goToResult = () => {
  if (!searchName.value.trim()) return
  router.push({
    name: 'search-result',
    params: { characterName: searchName.value.trim() }
  })
}
</script>