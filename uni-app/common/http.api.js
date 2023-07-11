let url = {
	index: '/book/page',
	login: '/user/login',
	register: '/user/save',
	getCollects: '/user-collect/page',
	collect: '/user-collect/save',
	delCollect: '/user-collect/delete',
	carts: '/cart/page',
	updateCarts: '/cart/save',
	removeCarts: '/cart/delete',
	getAddress: '/address/page',
	updateAddress: '/address/save',
	removeAddress: '/address/delete',
	getOrders: '/order/page',
	createOrder: '/order/save',
	updateOrderStatus: '/order/updateStatus',
	getReview: '/review/page',
	saveReview: '/review/save',
	getReviewBook: '/review-book/page',
	getSale: '/sale/page',
	updateClicks: '/book/updateClicks',
	updateSales: '/book/updateSales',
	
	getUserInfo: '/api/user',
	updateUserInfo: '/api/user',
	updateUserAvatar: '/api/user/avatar',
	getOssToken: '/api/auth/oss/token',
	logout: '/api/auth/logout',
	getGoodsCate: '/api/goods',
	getGoodsDetail: '/api/goods/',
	
	setCartsChecked: '/api/carts/checked',
	previewOrder: '/api/orders/preview',
	orders: '/api/orders',
}

// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作，更多内容详见uView对拦截器的介绍部分：
// https://uviewui.com/js/http.html#%E4%BD%95%E8%B0%93%E8%AF%B7%E6%B1%82%E6%8B%A6%E6%88%AA%EF%BC%9F
const install = (Vue, vm) => {
	// 首页
	let getIndex = (params = {}) => vm.$u.post(url.index, params)

	// 商品
	let getGoodsCate = (params = {}) => vm.$u.get(url.getGoodsCate, params)
	let getGoodsDetail = params => vm.$u.get(url.getGoodsDetail + params)
	let getSale = (params = {}) => vm.$u.post(url.getSale, params)
	let updateSales = params => vm.$u.post(url.updateSales, params)
	let updateClicks = params => vm.$u.post(url.updateClicks, params)

	// 收藏
	let getCollects = (params = {}) => vm.$u.post(url.getCollects, params)
	let delCollect = params => vm.$u.delete(url.delCollect, params)
	let collect = params => vm.$u.post(url.collect, params)

	// 购物车
	let carts = (params = {}) => vm.$u.post(url.carts, params)
	let updateCarts = (params = {}) => vm.$u.post(url.updateCarts, params)
	let addCarts = (params = {}) => vm.$u.post(url.carts, params)
	let removeCarts = params => vm.$u.delete(url.removeCarts, params)
	let setCartsNum = ({
		id,
		num
	}) => vm.$u.put(url.carts + '/' + id, {
		num
	})
	let setCartsChecked = params => vm.$u.patch(url.setCartsChecked, params)

	// 订单
	// let previewOrder = () => vm.$u.get(url.previewOrder)
	let getOrders = params => vm.$u.post(url.getOrders, params)
	let updateOrderStatus = params => vm.$u.post(url.updateOrderStatus, params)
	let createOrder = params => vm.$u.post(url.createOrder, params)
	let addOrder = params => vm.$u.post(url.orders, params)

	// 地址
	let getAddress = (params = {}) => vm.$u.post(url.getAddress, params)
	let updateAddress = (params = {}) => vm.$u.post(url.updateAddress, params)
	let removeAddress = params => vm.$u.delete(url.removeAddress, params)
	
	let addAddress = params => vm.$u.post(url.address, params)

	// 个人中心
	let login = params => vm.$u.post(url.login, params)
	let register = params => vm.$u.post(url.register, params)
	let getUserInfo = () => vm.$u.get(url.getUserInfo)
	let updateUserInfo = params => vm.$u.put(url.updateUserInfo, params)
	let updateUserAvatar = params => vm.$u.patch(url.updateUserAvatar, params)
	let getOssToken = () => vm.$u.get(url.getOssToken)
	let logout = () => vm.$u.post(url.logout)
	
	// 评价
	let getReview = params => vm.$u.post(url.getReview, params)
	let saveReview = params => vm.$u.post(url.saveReview, params)
	let getReviewBook = params => vm.$u.post(url.getReviewBook, params)

	// 将各个定义的接口名称，统一放进对象挂载到vm.$u.api(因为vm就是this，也即this.$u.api)下
	vm.$u.api = {
		getIndex,
		login,
		register,
		getUserInfo,
		updateUserInfo,
		updateUserAvatar,
		getOssToken,
		logout,
		getGoodsCate,
		getGoodsDetail,
		collect,
		getCollects,
		carts,
		addCarts,
		removeCarts,
		setCartsNum,
		setCartsChecked,
		// previewOrder,
		getOrders,
		addOrder,
		getAddress,
		addAddress,
		updateAddress,
		removeAddress,
		delCollect,
		updateCarts,
		updateOrderStatus,
		createOrder,
		getReview,
		saveReview,
		getReviewBook,
		getSale,
		updateClicks,
		updateSales
	}
}

export default {
	install
}
