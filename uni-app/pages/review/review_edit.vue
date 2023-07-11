<template>
	<view class="container">
		<text>订单编号: {{orderNumber}}</text>
		<view class="rate-box">
			<text class="rate-label">评价星级:</text>
			<u-rate v-model="rating" size="30"></u-rate>
		</view>
		<view class="input-box">
			<text class="input-label">评价内容:</text>
			<textarea v-model="content" content placeholder="请输入您的评价内容"></textarea>
		</view>
		<!-- <text>评价时间： {{createTime}}</text> -->
		<button type="primary" @click="submit()">提交评价</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				rating: 5,
				content: '',
				createTime: '',
				orderId: '',
				orderNumber: ''
			};
		},
		onLoad(option) {
			this.orderId = option.orderId
			this.orderNumber = option.orderNumber
			console.log(option)

		},
		methods: {
			async submit() {
				let res = await this.$u.api.saveReview({
					orderId: this.orderId,
					rating: this.rating,
					content: this.content
				});
				await this.$u.api.updateOrderStatus({
					orderId: this.orderId,
					status: 5,
				})
				// console.log(res)
				this.$u.toast('评价成功')
				uni.redirectTo({
					url: "/pages/order/order?state=0"
				});
			}
		}
	};
</script>

<style lang="scss">
	.container {
		padding: 20px;
	}

	.rate-box {
		margin-top: 20px;
	}

	.rate-label {
		font-size: 16px;
		margin-right: 10px;
	}

	.input-box {
		margin-top: 20px;
	}

	.input-label {
		font-size: 16px;
		margin-right: 10px;
	}

	textarea {
		width: 100%;
		height: 120px;
		padding: 10px;
		border: 1px solid #ccc;
		border-radius: 4px;
	}

	button {
		margin-top: 20px;
	}
</style>
