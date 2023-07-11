<template>
	<view class="container">
		<!-- 空白页 -->
		<view v-if="!hasLogin || empty===true" class="empty">
			<image src="/static/emptyCart.jpg" mode="aspectFit"></image>
			<view v-if="hasLogin" class="empty-tips">
				空空如也
				<navigator class="navigator" v-if="hasLogin" url="../index/index" open-type="switchTab">随便逛逛>
				</navigator>
			</view>
			<view v-else class="empty-tips">
				空空如也
				<view class="navigator" @click="navToLogin">去登陆></view>
			</view>
		</view>
		<view v-else>
			<!-- 列表 -->
			<view class="cart-list">
				<block v-for="(item, index) in cartList" :key="item.id">
					<view class="cart-item" :class="{'b-b': index!==cartList.length-1}">
						<view class="image-wrapper">
							<image :src="'http://150.158.85.93:81/' + item.book.imgSrc" :class="[item.loaded]"
								mode="aspectFill" lazy-load @load="onImageLoad('cartList', index)"
								@error="onImageError('cartList', index)"></image>
							<view class="yticon icon-xuanzhong2 checkbox" :class="{checked: item.checked}"
								@click="check('item', index)"></view>
						</view>
						<view class="item-right">
							<navigator :url="`/pages/goods/detail?id=${item.bookId}`" hover-class="none">
								<text class="clamp title">{{item.book.title}}</text>
							</navigator>
							<text class="attr u-line-1">(剩余 {{item.book.stock}} 件)</text>
							<text class="price">¥{{item.book.price}}</text>
							<uni-number-box class="step" :min="item.book.stock?1: 0" :max="item.book.stock"
								:value="item.quantity>item.book.stock?item.book.stock:item.quantity"
								:isMax="item.quantity>=item.book.stock?true:false" :isMin="item.quantity===1"
								:index="index" @eventChange="numberChange"></uni-number-box>
						</view>
						<text class="del-btn yticon icon-fork" @click="deleteCartItem(index)"></text>
					</view>
				</block>
			</view>
			<!-- 底部菜单栏 -->
			<view class="action-section">
				<view class="checkbox">
					<image :src="allChecked?'/static/selected.png':'/static/select.png'" mode="aspectFit"
						@click="check('all')"></image>
					<view class="clear-btn" :class="{show: allChecked}" @click="clearCart">
						清空
					</view>
				</view>
				<view class="total-box">
					<text class="price">¥{{total}}</text>
					<!-- <text class="coupon">
						已优惠
						<text>74.35</text>
						元
					</text> -->
				</view>
				<button type="primary" class="no-border confirm-btn" @click="createOrder">去结算</button>
			</view>
		</view>
	</view>
</template>

<script>
	import uniNumberBox from '@/components/uni-number-box.vue'
	export default {
		components: {
			uniNumberBox
		},
		data() {
			return {
				total: 0, //总价格
				allChecked: false, //全选状态  true|false
				empty: false, //空白页现实  true|false
				cartList: [],
				hasLogin: false
			};
		},
		onLoad(option) {
			this.hasLogin = this.vuex_token ? true : false
			if (this.hasLogin) this.loadData()
		},
		onShow() {

		},
		onTabItemTap() {

			let page = getCurrentPages().pop();
			// 利用 reLaunch 重置 当前页面  
			this.$u.route({
				url: page.route, // 获取当前页面路由  
				type: "reLaunch", // 关闭当前页面，跳转到应用内的某个页面。  
				params: page.options // 获取当前页面传参  
			})

		},
		watch: {
			//显示空白页
			cartList(e) {
				let empty = e.length === 0 ? true : false;
				if (this.empty !== empty) {
					this.empty = empty;
				}
			},

		},
		methods: {
			//请求数据
			async loadData() {

				let res = await this.$u.api.carts({
					pageNum: 1,
					pageSize: 1000
				})
				let {
					data: list
				} = res.data.records
				// let cartList = list.map(item => {
				// 	item.checked = item.is_checked
				// 	return item;
				// });
				let cartList = res.data.records
				// cartList = cartList.map(cart => {
				// 	return {
				// 		...cart,
				// 		checked: false
				// 	};
				// });
				this.cartList = cartList;

				this.calcTotal(); //计算总价
			},
			//监听image加载完成
			onImageLoad(key, index) {
				this.$set(this[key][index], 'loaded', 'loaded');
			},
			//监听image加载失败
			onImageError(key, index) {
				this[key][index].image = '/static/errorImage.jpg';
			},
			navToLogin() {
				uni.navigateTo({
					url: '/pages/center/login'
				})
			},
			//选中状态处理
			async check(type, index) {
				let cart_ids = [] // 会把提交过来的购物车数据设置为选中， 未提交过来的购物车数据设置为非选中
				if (type === 'item') {
					this.cartList[index].checked = !this.cartList[index].checked;
				} else {
					const checked = !this.allChecked
					const list = this.cartList;
					list.forEach(item => {
						item.checked = checked;
					})
					this.allChecked = checked;
				}
				// 请求接口
				await Promise.all(this.cartList.map(async item => {
					await this.$u.api.updateCarts({
						bookId: item.bookId,
						quantity: item.quantity,
						checked: item.checked ? 1 : 0
					});
				}));


				// await this.$u.api.setCartsChecked({
				// 	cart_ids
				// })

				this.calcTotal(type);
			},
			//数量
			async numberChange(data) {

				let item = this.cartList[data.index]
				console.log(this.cartList[data.index])
				item.quantity = data.number
				// 调用接口		
				await this.$u.api.updateCarts({
					bookId: item.bookId,
					quantity: item.quantity
				})
				this.calcTotal();
			},
			//删除
			async deleteCartItem(index) {
				await this.$u.api.removeCarts({
					bookId: this.cartList[index].bookId
				})
				this.cartList.splice(index, 1);
				this.calcTotal();
				uni.hideLoading();
			},
			//清空
			clearCart() {
				uni.showModal({
					content: '清空购物车？',
					success: async (e) => {
						if (e.confirm) {
							this.cartList = [];
							// 调用接口删除数据 缺乏接口 TODO
							await this.$u.api.removeCarts({})
						}
					}
				})
			},
			//计算总价
			calcTotal() {

				let list = this.cartList;

				if (list.length === 0) {
					this.empty = true;
					return;
				}
				let total = 0;
				let checked = true;
				list.forEach(item => {
					if (item.checked == true) {
						total += item.book.price * item.quantity;
					} else if (checked == true) {
						checked = false;
					}
				})
				this.allChecked = checked;
				this.total = Number(total.toFixed(2));
			},
			//创建订单
			createOrder() {
				// let list = this.cartList;
				// let goodsData = [];
				// list.forEach(item => {
				// 	if (item.checked) {
				// 		goodsData.push({
				// 			attr_val: item.attr_val,
				// 			number: item.number
				// 		})
				// 	}
				// })
				// this.$u.toast('结算中..')
				setTimeout(() => {
					uni.navigateTo({
						url: `/pages/order/createOrder?`
					})
				}, 150)


			}
		}
	}
</script>

<style lang='scss'>
	.container {
		padding-bottom: 134upx;

		/* .cart-image{
			width: 230rpx;
			height: 230rpx;
		} */
		/* 空白页 */
		.empty {
			position: fixed;
			left: 0;
			top: 0;
			width: 100%;
			height: 100vh;
			padding-bottom: 100upx;
			display: flex;
			justify-content: center;
			flex-direction: column;
			align-items: center;
			background: #fff;

			image {
				width: 240upx;
				height: 160upx;
				margin-bottom: 30upx;
			}

			.empty-tips {
				display: flex;
				font-size: $font-sm+2upx;
				color: $font-color-disabled;

				.navigator {
					color: $uni-color-primary;
					margin-left: 16upx;
				}
			}
		}
	}

	/* 购物车列表项 */
	.cart-item {
		display: flex;
		position: relative;
		padding: 30upx 40upx;

		.image-wrapper {
			width: 230upx;
			height: 230upx;
			flex-shrink: 0;
			position: relative;

			image {
				border-radius: 8upx;
			}
		}

		.checkbox {
			position: absolute;
			left: -16upx;
			top: -16upx;
			z-index: 8;
			font-size: 44upx;
			line-height: 1;
			padding: 4upx;
			color: $font-color-disabled;
			background: #fff;
			border-radius: 50px;
		}

		.item-right {
			display: flex;
			flex-direction: column;
			flex: 1;
			overflow: hidden;
			position: relative;
			padding-left: 30upx;

			.title,
			.price {
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				height: 40upx;
				line-height: 40upx;
			}

			.attr {
				font-size: $font-sm + 2upx;
				color: $font-color-light;
				height: 50upx;
				line-height: 50upx;
			}

			.price {
				height: 50upx;
				line-height: 50upx;
			}
		}

		.del-btn {
			padding: 4upx 10upx;
			font-size: 34upx;
			height: 50upx;
			color: $font-color-light;
		}
	}

	/* 底部栏 */
	.action-section {
		/* #ifdef H5 */
		margin-bottom: 100upx;
		/* #endif */
		position: fixed;
		left: 30upx;
		bottom: 30upx;
		z-index: 95;
		display: flex;
		align-items: center;
		width: 690upx;
		height: 100upx;
		padding: 0 30upx;
		background: rgba(255, 255, 255, .9);
		box-shadow: 0 0 20upx 0 rgba(0, 0, 0, .5);
		border-radius: 16upx;

		.checkbox {
			height: 52upx;
			position: relative;

			image {
				width: 52upx;
				height: 100%;
				position: relative;
				z-index: 5;
			}
		}

		.clear-btn {
			position: absolute;
			left: 26upx;
			top: 0;
			z-index: 4;
			width: 0;
			height: 52upx;
			line-height: 52upx;
			padding-left: 38upx;
			font-size: $font-base;
			color: #fff;
			background: $font-color-disabled;
			border-radius: 0 50px 50px 0;
			opacity: 0;
			transition: .2s;

			&.show {
				opacity: 1;
				width: 120upx;
			}
		}

		.total-box {
			flex: 1;
			display: flex;
			flex-direction: column;
			text-align: right;
			padding-right: 40upx;

			.price {
				font-size: $font-lg;
				color: $font-color-dark;
			}

			.coupon {
				font-size: $font-sm;
				color: $font-color-light;

				text {
					color: $font-color-dark;
				}
			}
		}

		.confirm-btn {
			padding: 0 38upx;
			margin: 0;
			border-radius: 100px;
			height: 76upx;
			line-height: 76upx;
			font-size: $font-base + 2upx;
			background: $uni-color-primary;
			box-shadow: 1px 2px 5px rgba(217, 60, 93, 0.72)
		}
	}

	/* 复选框选中状态 */
	.action-section .checkbox.checked,
	.cart-item .checkbox.checked {
		color: $uni-color-primary;
	}
</style>