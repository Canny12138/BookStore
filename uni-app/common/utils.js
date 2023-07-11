let url = {
	index: '/api/index'
}

// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作，更多内容详见uView对拦截器的介绍部分：
// https://uviewui.com/js/http.html#%E4%BD%95%E8%B0%93%E8%AF%B7%E6%B1%82%E6%8B%A6%E6%88%AA%EF%BC%9F
const install = (Vue, vm) => {
	// 判断是否登陆
	let authLogin = (params = {}) => {
		// 判断 token
		if(vm.vuex_token){
			return true
		}else{
			vm.$u.toast('请登录')
			// 记录backurl
			let pages = getCurrentPages()
			let page = pages[pages.length - 1]
			
			uni.setStorageSync('backurl', page.route)
			// 跳转登陆
			setTimeout(()=>{
				vm.$u.route({
					type: 'redirect',
					url: 'pages/center/login'
				})
			}, 1000)
			return false
		}
	}
	
	let prePage = ()=>{
		let pages = getCurrentPages();
		let prePage = pages[pages.length - 2];
		console.log(pages);
		console.log(prePage);
		
		// #ifdef H5
		return prePage;
		// #endif
		return prePage.$vm;
	}
	
	// 将各个定义的工具方法，统一放进对象挂载到vm.$u.utils(因为vm就是this，也即this.$u.utils)下
	vm.$u.utils = {
		authLogin,
		prePage
	}
}

export default {
	install
}