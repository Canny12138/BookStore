<template>
	<view class="upload-container">
		<u-upload 
		ref="upload"
		:action="action" 
		max-count="1"
		:before-upload="beforeUpload"
		:custom-btn="true"
		:form-data='formData'
		:deletable="false"
		:multiple="false"
		:show-upload-list="false"
		width="0"
		height="0"
		:show-progress="false"
		@on-success="onSuccess"
		>
		<u-avatar slot="addBtn" class="slot-btn" :src="url" size="140"></u-avatar>
		</u-upload>
	</view>
</template>

<script>
	let _this
	export default {
		name:"oss_upload",
		data() {
			return {			
				action: '',
				formData:{},
				url:'',
				filename: '' // 生成的文件名
			};
		},
		created(){
			_this = this
			this.url = this.vuex_user.avatar_url			
		},
		methods: {
			async beforeUpload(index, list) {
				// 获取上传文件名, list中 h5 能获取文件name 小程序则是获取path（名字包含）
				// #ifdef H5
				let file = list[0].file.name
				// #endif
				// #ifndef H5
				let file = list[0].file.path
				// #endif
				
				// 截取文件后缀
				let suffix = file.slice(file.lastIndexOf('.'))
				
				// 生成随机唯一文件名
				_this.filename = _this.$u.guid(20) + suffix
		
				// 获取 oss token
				let ossData = await _this.$u.api.getOssToken()
				// 设置action
				_this.action = ossData.host
				_this.formData = {
					'key' : _this.filename,
					'policy': ossData.policy,
					'OSSAccessKeyId': ossData.accessid, 
					'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
					'signature': ossData.signature,
				}
				return true
			},
			async onSuccess(){
				// 移除预览区图片
				this.$refs.upload.remove(0)
				// 上传的图片放再自定义按钮区
				this.url = _this.action + _this.filename
				// 保存图片
				await this.$u.api.updateUserAvatar({
					avatar: this.filename
				})
				this.$u.toast('修改成功')
				// vuex 
				this.$u.vuex('vuex_user.avatar_url', this.url)
			}
		}
	}
</script>

<style>
.slot-btn {
	display: flex;
	justify-content: center;
	align-items: center;
	border-radius: 10rpx;
}
.upload-container{
	width: 140rpx;
	height: 140rpx;
}
</style>
