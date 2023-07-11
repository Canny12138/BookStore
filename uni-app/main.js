import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

App.mpType = 'app'

// 引入全局uView
import uView from 'uview-ui'
Vue.use(uView);

import store from '@/store';
let vuexStore = require("@/store/$u.mixin.js");
Vue.mixin(vuexStore);
const app = new Vue({
    ...App,
	store
})


// http拦截器，将此部分放在new Vue()和app.$mount()之间，才能App.vue中正常使用
import httpInterceptor from '@/common/http.interceptor.js'
Vue.use(httpInterceptor, app)

// http接口API集中管理引入部分
import httpApi from '@/common/http.api.js'
Vue.use(httpApi, app)

// 引入工具包
import utils from '@/common/utils.js'
Vue.use(utils, app)


app.$mount()
