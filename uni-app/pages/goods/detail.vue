<template>
	<view class="goods-detail-container">
		<!-- 商品图片 -->
		<view class="u-flex u-row-center u-p-t-40 u-p-b-20">
			<u-image height="500rpx" mode="aspectFit" :src="this.imgSrc">
				<u-loading slot="loading" mode="circle"></u-loading>
			</u-image>
		</view>
		<!-- 商品信息 -->
		<u-cell-group :border="false">
			<u-cell-item :title="goodsData.title" :title-style="{color:'#000',fontSize: '36rpx', fontWeight: 800}"
				:arrow="false" :border-bottom="false"></u-cell-item>
			<uni-countdown v-if="goodsData.onSale == 1" :show-day="false" :hour="this.hour" :minute="this.minute"
				:second="this.second" color="#FFFFFF" background-color="#fa3534" style="margin-left: 50rpx;" />
			<u-cell-item v-if="goodsData.onSale == 1" :title="'¥ '+goodsData.price+' 限时秒杀'"
				:title-style="{color:'#fa3534',fontSize: '30rpx', fontWeight: 800}" :value="'库存 '+goodsData.stock"
				:arrow="false" :border-bottom="false"></u-cell-item>
			<u-cell-item v-if="goodsData.onSale != 1" :title="'¥ '+goodsData.price"
				:title-style="{color:'#fa3534',fontSize: '30rpx', fontWeight: 800}" :value="'库存 '+goodsData.stock"
				:arrow="false" :border-bottom="false"></u-cell-item>
		</u-cell-group>
		<!-- 选项卡 -->
		<u-tabs :list="list" :is-scroll="false" :current="current" @change="change"></u-tabs>
		<!-- 富文本商品详情 -->
		<view v-show="current == 0">
			<u-parse :html="goodsData.introduction"></u-parse>
		</view>
		<!-- 商品评论 -->
		<view v-show="current == 1">
			<view class="review-section">
				<view class="review" v-for="(review, index) in reviews" :key="index">
					<view class="review-header">
						<view class="review-user">匿名用户</view>
						<view class="review-date">{{ review.review.createTime }}</view>
					</view>
					<view class="review-rating">
						<u-rate v-model="review.review.rating" size="20" disabled></u-rate>
					</view>
					<view class="review-content">{{ review.review.content }}</view>
				</view>
			</view>
		</view>
		<!-- 推荐商品 -->
		<view v-show="current == 2">
			<!-- uView 12栅格布局 -->
			<!-- <u-row gutter="16" class="goods-list u-p-t-10"> -->
			<!-- u-p-b-20 u-p-l-10 u-p-r-10z -->
			<!-- <u-col span="6" v-for="(item,index) in likeGoodsData" class="u-p-b-10" :key="index"> -->
			<!-- 商品组件 -->
			<!-- <goods :item="item"></goods> -->
			<!-- </u-col> -->
			<!-- </u-row> -->
		</view>

		<!-- 底部导航 -->
		<view class="navigation">
			<view class="left">

				<view class="item" @click="collect">
					<u-icon name="star" :size="45" :color="this.isCollect? '#fa3534':$u.color['contentColor']">
					</u-icon>
					<view class="text u-line-1" :class="{'is-collect': this.isCollect}">收藏</view>
				</view>
				<view class="item car" @click="toCart">
					<u-badge class="car-num" :count="cartCount" type="error" :offset="[-3, -6]"></u-badge>
					<u-icon name="shopping-cart" :size="45"></u-icon>
					<view class="text u-line-1">购物车</view>
				</view>
			</view>
			<view class="right">
				<view class="cart btn u-line-1" @click="addCart">加入购物车</view>
				<view class="buy btn u-line-1">立即购买</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [{
					name: '商品详情'
				}, {
					name: '商品评论',
					count: '0'
				}, {
					name: '推荐商品'
				}],
				current: 0,
				params: {
					pageNum: 1,
					pageSize: 1,
					bookid: '',
				},
				isCollect: false,
				goodsData: {
					// price: '',
					// sales: '',
					// is_collect: 0
				}, // 当前商品信息
				likeGoodsData: [], // 推荐商品
				cartCount: 0, // 购物车产品数量
				imgSrc: '',
				reviews: [], // 评价数组
				allLoaded: false, // 是否已加载所有评价
				hour: 0,
				minute: 0,
				second: 0,
				saleTime: 0
			};
		},
		async onLoad(option) {
			// 读取产品详情
			// this.params.bookid = '42422da79504f9936e569b5beda0336d'
			this.params.bookid = option.id
			let res = await this.$u.api.getIndex(this.params)
			this.goodsData = res.data.records[0]
			this.imgSrc = 'http://150.158.85.93:81/' + res.data.records[0].imgSrc
			await this.$u.api.updateClicks({
				bookId: option.id
			})
			if (res.data.records[0].onSale == 1) {
				let saleRes = await this.$u.api.getSale({
					pageNum: 1,
					pageSize: 1,
					bookId: res.data.records[0].id
				})
				if (saleRes.data.records[0].times == 6) {
					this.addTime = 2 * 60 * 1000 //2min
				}
				if (saleRes.data.records[0].times == 15) {
					this.addTime = 20 * 60 * 1000 //20min
				}
				if (saleRes.data.records[0].times == 18) {
					this.addTime = 120 * 60 * 1000 //2h
				}
				let leftTime = saleRes.data.records[0].createTimeId - new Date().getTime() + this.addTime
				// console.log(new Date().getTime().toString())
				this.hour = Math.floor(leftTime / 3600000)
				this.minute = Math.floor(leftTime % 3600000 / 60000)
				this.second = Math.floor(leftTime % 3600000 % 60000 / 1000)
				console.log(this.hour)
				console.log(this.minute)
				console.log(this.second)
				console.log(leftTime)
			}
			// console.log("aaa" + this.vuex_token)
			if (this.vuex_token) {
				let collect = await this.$u.api.getCollects({
					pageNum: 1,
					pageSize: 1,
					bookId: this.params.bookid
				})
				if (collect.data.total != 0) {
					this.isCollect = true
				}
			}
			// let res = await this.$u.api.getGoodsDetail(option.id)
			// console.log(this.goodsData)
			// this.likeGoodsData = res.like_goods
			// this.list[1].count = this.goodsData.comments.length
			// cart count
			if (this.vuex_token) await this.getCartCount()

		},
		onShow() {
			this.loadMoreReviews();
		},
		methods: {
			change(index) {
				this.current = index;
			},
			async collect() {
				if (this.isCollect) {
					console.log(this.goodsData)
					await this.$u.api.delCollect({
						bookId: this.goodsData.id,
					});
					this.$u.toast('取消收藏')
					this.isCollect = false;
				} else {
					console.log(this.goodsData)
					await this.$u.api.collect({
						bookId: this.goodsData.id,
					});
					this.$u.toast('收藏成功')
					this.isCollect = true;
				}
			},
			async getCartCount() {
				// 购物车数量
				let cartData = await this.$u.api.carts({
					pageNum: 1,
					pageSize: 1000
				})
				this.cartCount = cartData.data.total
			},
			async addCart(id) {
				await this.$u.api.updateCarts({
					bookId: this.goodsData.id,
					quantity: 1,
				})
				await this.getCartCount()

				this.$u.toast('添加成功')
			},
			toCart() {
				this.$u.route({
					type: 'reLaunch',
					url: 'pages/cart/index'
				})
			},
			async loadMoreReviews() {
				let res = await this.$u.api.getReviewBook({
					pageNum: 1,
					pageSize: 100,
					bookId: this.params.bookid, // 需要替换为你的书籍id
				})
				this.reviews.push(...res.data.records)
			},
		}
	}
</script>

<style lang="scss" scoped>
	.goods-detail-container {
		padding-bottom: 120rpx;

		.goods-list {
			background-color: $u-bg-color;
		}

		.is-collect {
			color: $u-type-error;
		}
	}

	.navigation {
		position: fixed;
		left: 0;
		bottom: 0;
		display: flex;
		justify-content: center;
		width: 100%;
		margin-top: 100rpx;
		border: solid 2rpx #f2f2f2;
		background-color: #ffffff;
		padding: 16rpx 0;

		.left {
			display: flex;
			font-size: 20rpx;

			.item {
				margin: 0 30rpx;

				&.car {
					text-align: center;
					position: relative;

					.car-num {
						position: absolute;
						top: -10rpx;
						right: -10rpx;
					}
				}
			}
		}

		.right {
			display: flex;
			font-size: 28rpx;
			align-items: center;

			.btn {
				line-height: 66rpx;
				padding: 0 30rpx;
				border-radius: 36rpx;
				color: #ffffff;
			}

			.cart {
				background-color: #ed3f14;
				margin-right: 30rpx;
			}

			.buy {
				background-color: #ff7900;
			}

			.u-image {
				margin: 0 auto;
			}
		}
	}

	.review-section {
		padding: 15px;
	}

	.section-title {
		font-size: 18px;
		font-weight: bold;
	}

	.review {
		margin-top: 15px;
		padding: 15px;
		border-radius: 5px;
		border: 1px solid #ccc;
	}

	.review-header {
		display: flex;
		justify-content: space-between;
	}

	.review-user {
		font-weight: bold;
	}

	.review-rating {
		margin-top: 5px;
	}

	.review-content {
		margin-top: 10px;
	}

	.load-more {
		margin-top: 15px;
		text-align: center;
	}
</style>