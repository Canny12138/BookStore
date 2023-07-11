# 关于图书商城
	uni-app图书商城项目, 主要用了uView作为ui框架。目前实现了：
	- 注册登陆 
	- 首页展示
	- 商品分类
	- 购物车
	- 订单功能 
	- 个人中心
	其中，购物车，和订单部分功能用了原生uni组件和mix-mall 电商项目模版速成
## uView版本
	目前刚更新uView2.0，不过很多内置样式和组件都不完，建议使用1.x版本
直接在官网下载已经配置uView空项目即可。

## Http请求
```js
// http.api.js
let url = {
	index: '/api/index'
}

// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作，更多内容详见uView对拦截器的介绍部分：
// https://uviewui.com/js/http.html#%E4%BD%95%E8%B0%93%E8%AF%B7%E6%B1%82%E6%8B%A6%E6%88%AA%EF%BC%9F
const install = (Vue, vm) => {
	// 此处没有使用传入的params参数
	let getIndex = (params = {}) => vm.$u.get(url.index, params)
	
	// 将各个定义的接口名称，统一放进对象挂载到vm.$u.api(因为vm就是this，也即this.$u.api)下
	vm.$u.api = {
		getIndex
	}
}

export default {
	install
}
```

```js
// http.interceptor.js
/**
 * uView已经为用户考虑好了所有的情况，并详细指导您如何在外部JS中引用vuex变量以及Vue的this实例等。
   建议在根目录下新建/common/http.interceptor.js文件，也即创建common目录(如果没有的话)，再创建http.interceptor.js文件，将拦截器相关代码写在里面。
 */
const install = (Vue, vm) => {
	// 此为自定义配置参数，具体参数见上方说明
	Vue.prototype.$u.http.setConfig({
		baseUrl: 'https://api.shop.eduwork.cn', // 请求的本域名
		// 设置为json，返回后会对数据进行一次JSON.parse()
		dataType: 'json',
		showLoading: true, // 是否显示请求中的loading
		loadingText: '请求中...', // 请求loading中的文字提示
		loadingTime: 800, // 在此时间内，请求还没回来的话，就显示加载中动画，单位ms
		originalData: true, // 是否在拦截器中返回服务端的原始数据
		loadingMask: true, // 展示loading的时候，是否给一个透明的蒙层，防止触摸穿透
		// 配置请求头信息
		header: {
			'content-type': 'application/json;charset=UTF-8'
		},
	});
	
	// 请求拦截部分，如配置，每次请求前都会执行，见上方说明
	Vue.prototype.$u.http.interceptor.request = (config) => {
		// config.header.token = token;
		config.header.Token = 'Bearer '+ 'xxxxxx';
		
		// 可以对某个url进行特别处理，此url参数为this.$u.get(url)中的url值
		// if(config.url == '/user/login') config.header.noToken = true;
		// 最后需要将config进行return
				
		return config;
	}
	
	// 响应拦截，如配置，每次请求结束都会执行本方法
	Vue.prototype.$u.http.interceptor.response = ({ statusCode, data }) => {
		if(statusCode< 400) { // 200 201 204 301 302  省事
			// statusCode为服务端返回值，可能有code，result等字段
			// 这里对res.result进行返回，将会在this.$u.post(url).then(res => {})的then回调中的res的到
			// 如果配置了originalData为true，请留意这里的返回值
			return data
		} else if(statusCode == 201) {
			// 假设201为token失效，这里跳转登录
			vm.$u.toast('验证失败，请重新登录');
			setTimeout(() => {
				// 此为uView的方法，详见路由相关文档
				vm.$u.route('/pages/user/login')
			}, 1500)
			return false;
		} else {
			// 如果返回false，则会调用Promise的reject回调，
			// 并将进入this.$u.post(url).then().catch(res=>{})的catch回调中，res为服务端的返回值
			return false;
		}
	}
}

export default {
	install
}
```
## API集中管理
## vuex配置
```js
// index.js
import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

let lifeData = {};

try{
	// 尝试获取本地是否存在lifeData变量，第一次启动APP时是不存在的
	lifeData = uni.getStorageSync('lifeData');
}catch(e){
	
}

// 需要永久存储，且下次APP启动需要取出的，在state中的变量名
let saveStateKeys = ['vuex_user', 'vuex_token'];

// 保存变量到本地存储中
const saveLifeData = function(key, value){
	// 判断变量名是否在需要存储的数组中
	if(saveStateKeys.indexOf(key) != -1) {
		// 获取本地存储的lifeData对象，将变量添加到对象中
		let tmp = uni.getStorageSync('lifeData');
		// 第一次打开APP，不存在lifeData变量，故放一个{}空对象
		tmp = tmp ? tmp : {};
		tmp[key] = value;
		// 执行这一步后，所有需要存储的变量，都挂载在本地的lifeData对象中
		uni.setStorageSync('lifeData', tmp);
	}
}
const store = new Vuex.Store({
	// 下面这些值仅为示例，使用过程中请删除
	state: {
		// 如果上面从本地获取的lifeData对象下有对应的属性，就赋值给state中对应的变量
		// 加上vuex_前缀，是防止变量名冲突，也让人一目了然
		vuex_user: lifeData.vuex_user ? lifeData.vuex_user : {name: '明月'},
		vuex_token: lifeData.vuex_token ? lifeData.vuex_token : '',
		// 如果vuex_version无需保存到本地永久存储，无需lifeData.vuex_version方式
		vuex_version: '1.0.1',
	},
	mutations: {
		$uStore(state, payload) {
			// 判断是否多层级调用，state中为对象存在的情况，诸如user.info.score = 1
			let nameArr = payload.name.split('.');
			let saveKey = '';
			let len = nameArr.length;
			if(nameArr.length >= 2) {
				let obj = state[nameArr[0]];
				for(let i = 1; i < len - 1; i ++) {
					obj = obj[nameArr[i]];
				}
				obj[nameArr[len - 1]] = payload.value;
				saveKey = nameArr[0];
			} else {
				// 单层级变量，在state就是一个普通变量的情况
				state[payload.name] = payload.value;
				saveKey = payload.name;
			}
			// 保存变量到本地，见顶部函数定义
			saveLifeData(saveKey, state[saveKey])
		}
	}
})

export default store
```

```js
// $u.mixin.js
import { mapState } from 'vuex'
import store from "@/store"

// 尝试将用户在根目录中的store/index.js的vuex的state变量，全部加载到全局变量中
let $uStoreKey = [];
try{
	$uStoreKey = store.state ? Object.keys(store.state) : [];
}catch(e){
	
}

module.exports = {
	created() {
		// 将vuex方法挂在到$u中
		// 使用方法为：如果要修改vuex的state中的user.name变量为"史诗" => this.$u.vuex('user.name', '史诗')
		// 如果要修改vuex的state的version变量为1.0.1 => this.$u.vuex('version', '1.0.1')
		this.$u.vuex = (name, value) => {
			this.$store.commit('$uStore', {
				name,value
			})
		}
	},
	computed: {
		// 将vuex的state中的所有变量，解构到全局混入的mixin中
		...mapState($uStoreKey)
	}
}
```

## main.js

```js
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



app.$mount()

```
## 下面就是具体撸业务
省略...
## 项目展示


<a href="https://ibb.co/2s9kqgg"><img src="https://i.ibb.co/Sdj3NRR/image.png" alt="image" border="0"></a>
<a href="https://ibb.co/LNqVbVy"><img src="https://i.ibb.co/y4MHGHC/image.png" alt="image" border="0"></a>
<a href="https://ibb.co/vxPbK7H"><img src="https://i.ibb.co/wNWTx9R/image.png" alt="image" border="0"></a>
<a href="https://ibb.co/NrpxGdW"><img src="https://i.ibb.co/DYk4jHw/image.png" alt="image" border="0"></a>
<a href="https://ibb.co/ZV5Qt7t"><img src="https://i.ibb.co/VtRFr5r/image.png" alt="image" border="0"></a>
<a href="https://ibb.co/QXqs7kr"><img src="https://i.ibb.co/KmQCcDF/image.png" alt="image" border="0"></a>
<a href="https://ibb.co/c39t2wQ"><img src="https://i.ibb.co/H4yYGCq/image.png" alt="image" border="0"></a>