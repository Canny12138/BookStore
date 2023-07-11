<template>
	<view class="goods-container"
		style="background: #fff; border-radius: 10rpx; overflow: hidden; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1); margin-bottom: 5rpx">
		<navigator :url="`/pages/goods/detail?id=${item.id}`" hover-class="none">
			<u-image width="100%" height="300rpx" mode="aspectFit" :src="'http://150.158.85.93:81/' + item.imgSrc"
				class="u-p-t-20 u-p-b-20"></u-image>
			<view class="goods-info">
				<view class="goods-title">{{item.title}}</view>
				<view class="goods-price">
					<text v-if="item.onSale == 1" class="sale">特价¥ {{item.price}}</text>
					<text v-else class="price">¥ {{item.price}}</text>
				</view>
				<view class="goods-stats">
					<text class="sales">销量：{{item.sales}}</text>
					<text class="clicks">浏览量：{{item.clicks}}</text>
				</view>
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
		}
	}
</script>

<style scoped>
	.goods-container {
		width: 100%;
		margin: 5rpx 0;
	}

	.goods-info {
		padding: 20rpx;
	}

	.goods-title {
		font-size: 36rpx;
		color: #333;
		font-weight: bold;
		margin-bottom: 10rpx;
		text-align: center;
	}

	.goods-price {
		font-size: 33rpx;
		margin-bottom: 20rpx;
	}

	.sale {
		color: #ff4d4f;
		font-weight: bold;
	}

	.price {
		color: #ff4d4f;
	}

	.goods-stats {
		display: flex;
		justify-content: space-between;
		font-size: 24rpx;
		color: #888;
	}
</style>
