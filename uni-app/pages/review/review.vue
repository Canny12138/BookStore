<template>
	<view class="container">
		<text>订单编号: {{orderNumber}}</text>
		<view class="rate-box">
			<text class="rate-label">评价星级:</text>
			<u-rate v-model="rating" size="30" disabled></u-rate>
		</view>
		<view class="input-box">
			<text class="input-label">评价内容:</text>
			<textarea v-model="content" content disabled></textarea>
		</view>
		<text>评价时间： {{createTime}}</text>
		<!-- <button type="primary" disabled>提交评价</button> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				rating: 1,
				content: '',
				createTime: '',
				orderId: '',
				orderNumber: ''
			};
		},
		async onLoad(option) {
			this.orderId = option.orderId
			this.orderNumber = option.orderNumber
			console.log(option)
			// this.orderId = '4d812bb824c053f50ec5af11b0b6c87a'
			let res = await this.$u.api.getReview({
				pageNum: 1,
				pageSize: 100,
				orderId: this.orderId
			});
			console.log(res)
			this.rating = res.data.records[0].rating
			this.content = res.data.records[0].content
			this.createTime = res.data.records[0].createTime
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
