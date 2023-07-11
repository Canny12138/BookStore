<template>
	<view class="wrap">
		<view class="top"></view>
		<view class="content">
			<view class="title">图书商城</view>
			<input class="u-border-bottom u-m-b-60" type="email" v-model="username" placeholder="请输入用户名" />
			<input class="u-border-bottom u-m-b-60" type="password" v-model="password" placeholder="请输入密码"
				selection-start="10" />
			<button @tap="submit" :style="[inputStyle]" class="getCaptcha">登陆</button>
			<view class="alternative">
				<view class="password">忘记密码</view>
				<view class="issue" @click="toRegister">注册</view>
			</view>
		</view>
		<!-- <view class="buttom">
			<view class="loginType">
				<view class="wechat item">
					<view class="icon">
						<u-icon size="70" name="weixin-fill" color="rgb(83,194,64)"></u-icon>
					</view>
					微信
				</view>
			</view>
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				username: 'canny',
				password: '123456'
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				// if(this.password && this.$u.test.email(this.email)) {
				// 	style.color = "#fff";
				// 	style.backgroundColor = this.$u.color['warning'];
				// }
				if (this.password) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['warning'];
				}
				return style;
			}
		},
		methods: {
			async submit() {
				// 非法提交
				// if(!this.$u.test.email(this.email) || !this.password) return
				if (!this.password) return
				// 发送请求
				let res = await this.$u.api.login({
					username: this.username,
					password: this.password
				})
				console.log(res)
				if (res.statusCode == 200) {
					this.$u.toast('登陆成功')
					// token存入vuex
					this.$u.vuex('vuex_token', res.data.token)
					// 获取用户数据 存入vuex
					// let userInfo = await this.$u.api.getUserInfo()
					this.$u.vuex('vuex_user', res.data.user)
					// 返回去backurl
					setTimeout(() => {
						this.$u.route({
							type: 'reLaunch',
							url: uni.getStorageSync('backurl')
						})
						// uni.switchTab({
						// 	url: uni.getStorageSync('backurl')
						// })
					}, 1500)
				} else {
					this.$u.toast(res.message)
				}
			},
			toRegister() {
				this.$u.route({
					url: 'pages/center/register'
				})
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
