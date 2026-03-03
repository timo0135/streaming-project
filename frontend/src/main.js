import {createApp} from 'vue'
import {createPinia} from 'pinia'
import piniaPersistedstate from 'pinia-plugin-persistedstate'
import api from './plugin/api.js';

import App from './App.vue'
import router from './router'
import './index.css';

const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPersistedstate)
app.use(api, {
  baseUrl: import.meta.env.VITE_API_BASE_URL,
});
app.use(pinia)
app.use(router)

app.mount('#app')
