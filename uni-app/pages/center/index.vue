<template>
	<view>
		
		<view class="u-flex user-box u-p-l-30 u-p-r-20 u-p-b-30">
			<view class="u-m-r-10">
				<!-- <u-avatar :src="vuex_user.avatar_url" size="140"></u-avatar> -->
				<oss-upload></oss-upload>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-20">{{vuex_user.username}}</view>
				<view class="u-font-14 u-tips-color">{{vuex_user.id || ''}}</view>
			</view>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="setting" title="个人信息" @click="updateUserInfo"></u-cell-item>
			</u-cell-group>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="star" title="我的收藏" @click="toPage('pages/center/collect')"></u-cell-item>
				<u-cell-item icon="map" title="地址管理" @click="toPage('pages/address/address')"></u-cell-item>
				<u-cell-item icon="order" title="我的订单" @click="toPage('pages/order/order?state=0')"></u-cell-item>				
				<!-- <u-cell-item icon="setting" title="绑定微信"></u-cell-item> -->
			</u-cell-group>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item :arrow="false" icon="reload" title="退出登录" @click="logout" ></u-cell-item>
			</u-cell-group>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		onLoad() {
			// 登陆验证
			this.$u.utils.authLogin()
		},
		methods: {
			updateUserInfo(){
				this.$u.route({
					url: 'pages/center/user_update'
				})
			},
			async logout(){
				this.$u.vuex('vuex_token', '')
				this.$u.vuex('vuex_user', '')
				// await this.$u.api.logout()
				uni.setStorageSync('lifeData', {})
				this.$u.toast('成功登出')
				setTimeout(()=>{
					this.$u.route({
						type: 'reLaunch',
						url: 'pages/index/index'
					})
				}, 1500)
			},
			toPage(url) {
				this.$u.route({ url })
			}
		}
	}
</script>

<style lang="scss">
page{
	background-color: #ededed;
}

.camera{
	width: 54px;
	height: 44px;
	
	&:active{
		background-color: #ededed;
	}
}
.user-box{
	background-color: #fff;
}
</style>
