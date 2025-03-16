import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/index'
import ElementPlus from 'element-plus' // 导入Element Plus
import 'element-plus/dist/index.css' // 导入Element Plus样式
const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
