import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import axios from 'axios'

axios.defaults.headers.post["Content-Type"] = "application/json"
Vue.prototype.$http = axios
axios.defaults.baseURL = 'http://39.105.42.250:8080/'
Vue.config.productionTip = false

Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
