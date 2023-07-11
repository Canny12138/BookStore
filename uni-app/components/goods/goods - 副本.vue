<template>
	<view class="goods-container"
		style="box-shadow: 0 5rpx 10rpx 0 rgba(0, 0, 0, .1); margin-bottom: 5rpx; border-radius: 10rpx">
		<navigator :url="`/pages/goods/detail?id=${item.id}`" hover-class="none">
			<u-image width="100%" height="300rpx" mode="aspectFit" :src="'http://150.158.85.93:81/' + item.imgSrc"
				class="u-p-t-20 u-p-b-20"></u-image>
			<view class="title u-text-center u-line-1">{{item.title}}</view>
			<view class="u-flex u-p-t-20 u-p-b-40">
				<text v-if="item.onSale != 1" class="price">¥ {{item.price}}</text>
				<text v-if="item.onSale == 1" class="sale">特价¥ {{item.price}}</text>
				<text class="sales">销量：{{item.sales}}</text>
				<text class="sales">浏览量：{{item.clicks}}</text>
			</view>
		</navigator>
	</view>
</template>

<script>
	export default {
		name: "goods",
		data() {
			return {};
		},
		props: {
			item: {
				type: Object,
				default: {}
			}
		},
		methods: {
			test() {
				console.log(11123)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.goods-container {
		background-color: #FFFFFF;

		.u-flex {
			justify-content: space-around;

			.price {
				color: $u-type-error;
			}

			.sale {
				color: $u-type-error;
				font-weight: bold;
			}

			.sales {
				color: $u-type-info;
			}
		}

		.title {
			background-color: #FFFFFF;
			font-weight: 800;
			font-size: 36rpx;
		}
	}
</style>