<template>
	<view class="wrap">
		<view class="top"></view>
		<view class="content">
			<view class="title">欢迎注册会员</view>

			<input class="u-border-bottom u-m-b-60" type="name" v-model="name" placeholder="请输入昵称" />
			<!-- <input class="u-border-bottom u-m-b-60" type="email" v-model="email" placeholder="请输入邮箱" /> -->
			<input class="u-border-bottom u-m-b-60" type="password" v-model="password" placeholder="请输入密码"
				selection-start="10" />
			<input class="u-border-bottom u-m-b-60" type="password" v-model="password_confirmation" placeholder="请确认密码"
				selection-start="10" />
			<button @tap="submit" :style="[inputStyle]" class="getCaptcha">注册</button>

		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				name: '',
				password: '',
				password_confirmation: '',
				email: ''
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if (this.name && this.password && this.password == this.password_confirmation) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['warning'];
				}
				return style;
			}
		},
		methods: {
			async submit() {
				// 发送请求
				let res = await this.$u.api.register({
					username: this.name,
					password: this.password
				})

				this.$u.toast('注册成功')

				// 返回去backurl
				setTimeout(() => {
					this.$u.route({
						url: 'pages/center/login'
					})
				}, 1500)

			}
		}
	};
</script>

<style lang="scss" scoped>
	.wrap {
		font-size: 28rpx;

		.content {
			width: 600rpx;
			margin: 80rpx auto 0;

			.title {
				text-align: left;
				font-size: 60rpx;
				font-weight: 500;
				margin-bottom: 100rpx;
			}

			input {
				text-align: left;
				margin-bottom: 10rpx;
				padding-bottom: 6rpx;
			}

			.tips {
				color: $u-type-info;
				margin-bottom: 60rpx;
				margin-top: 8rpx;
			}

			.getCaptcha {
				background-color: rgb(253, 243, 208);
				color: $u-tips-color;
				border: none;
				font-size: 30rpx;
				// padding: 12rpx 0;

				&::after {
					border: none;
				}
			}

			.alternative {
				color: $u-tips-color;
				display: flex;
				justify-content: space-between;
				margin-top: 30rpx;
			}
		}

		.buttom {
			.loginType {
				display: flex;
				padding: 200rpx 150rpx 150rpx 150rpx;
				justify-content: center;

				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					color: $u-content-color;
					font-size: 28rpx;
				}
			}

			.hint {
				padding: 20rpx 40rpx;
				font-size: 20rpx;
				color: $u-tips-color;

				.link {
					color: $u-type-warning;
				}
			}
		}
	}
</style>