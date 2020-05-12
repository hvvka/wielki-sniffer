import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import primevue from './plugins/primevue'
import animatecss from './plugins/animatecss'
import vueAxios from './plugins/axios'

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  primevue,
  animatecss,
  vueAxios,
  render: h => h(App)
}).$mount('#app');
