/**
 * uView已经为用户考虑好了所有的情况，并详细指导您如何在外部JS中引用vuex变量以及Vue的this实例等。
   建议在根目录下新建/common/http.interceptor.js文件，也即创建common目录(如果没有的话)，再创建http.interceptor.js文件，将拦截器相关代码写在里面。
 */
const install = (Vue, vm) => {
	// 此为自定义配置参数，具体参数见上方说明
	Vue.prototype.$u.http.setConfig({
		baseUrl: 'http://localhost:8888/springboot/soft', // 请求的本域名
		// 设置为json，返回后会对数据进行一次JSON.parse()
		dataType: 'json',
		showLoading: true, // 是否显示请求中的loading
		loadingText: '请求中...', // 请求loading中的文字提示
		loadingTime: 800, // 在此时间内，请求还没回来的话，就显示加载中动画，单位ms
		originalData: true, // 是否在拦截器中返回服务端的原始数据
		loadingMask: true, // 展示loading的时候，是否给一个透明的蒙层，防止触摸穿透
		// 配置请求头信息
		header: {
			'content-type': 'application/x-www-form-urlencoded'
		},
	});

	// 请求拦截部分，如配置，每次请求前都会执行，见上方说明
	Vue.prototype.$u.http.interceptor.request = (config) => {
		// config.header.token = token;
		config.header.token = vm.vuex_token // 每个请求都带了token  如果有需要可以筛选部分请求notoken
		// 可以对某个url进行特别处理，此url参数为this.$u.get(url)中的url值
		// if(config.url == '/user/login') config.header.noToken = true;
		// 最后需要将config进行return
		// 根据实际情况判断是否需要修改 Content-Type
		if (config.url.includes('/user/login')) {
			config.header['content-type'] = 'application/json';
		}
		if (config.url.includes('/user/save')) {
			config.header['content-type'] = 'application/json';
		}
		return config;
	}

	// 响应拦截，如配置，每次请求结束都会执行本方法
	Vue.prototype.$u.http.interceptor.response = ({
		statusCode,
		data
	}) => {
		// console.log(statusCode);

		// console.log(data)
		if (statusCode < 400) { // 200 201 204 301 302  省事
			// statusCode为服务端返回值，可能有code，result等字段
			// 这里对res.result进行返回，将会在this.$u.post(url).then(res => {})的then回调中的res的到
			// 如果配置了originalData为true，请留意这里的返回值
			return data
		} else if (statusCode == 400) {
			let pages = getCurrentPages()
			let page = pages[pages.length - 1]

			// xx商品 库存不足, 请重新选择商品	
			vm.$u.toast(data.message)
			setTimeout(() => {
				// 此为uView的方法，详见路由相关文档
				vm.$u.route(page.route)
			}, 1500)
			return false;
		} else if (statusCode == 401) { // 后台接口401 有两种情况 一种是 账号密码错误 一种是 token无效
			let toastText = data.message == 'Unauthorized' ? '账号密码错误' : '请先登录'
			vm.$u.toast(toastText);
			setTimeout(() => {
				// 此为uView的方法，详见路由相关文档
				vm.$u.route('/pages/center/login')
			}, 1500)
			return false;
		} else if (statusCode == 422) {
			let pages = getCurrentPages()
			let page = pages[pages.length - 1]

			// 密码格式错误
			vm.$u.toast(Object.values(data.errors)[0][0])
			setTimeout(() => {
				// 此为uView的方法，详见路由相关文档
				vm.$u.route(page.route)
			}, 1500)
			return false;
		} else {
			// 如果返回false，则会调用Promise的reject回调，
			// 并将进入this.$u.post(url).then().catch(res=>{})的catch回调中，res为服务端的返回值
			return false;
		}
	}
	Vue.prototype.$u.patch = (url, params) => {
		let _params = {
			...params,
			_method: 'patch'
		}
		vm.$u.post(url, _params)
	}
}

export default {
	install
}
