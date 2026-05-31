import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // 방금 만든 라우터 주입

const app = createApp(App)
app.use(router)
app.mount('#app')