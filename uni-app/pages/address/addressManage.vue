<template>
	<view class="content">
		<view class="row b-b">
			<text class="tit">联系人</text>
			<input class="input" type="text" v-model="addressData.receiverName" placeholder="收货人姓名" placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">手机号</text>
			<input class="input" type="number" v-model="addressData.receiverPhone" placeholder="收货人手机号码" placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">地址</text>
			<text @click="chooseLocation" class="input">
				{{addressData.address}}
			</text>
			<text class="yticon icon-shouhuodizhi"></text>
		</view>
		<view class="row b-b"> 
			<text class="tit">门牌号</text>
			<input class="input" type="text" v-model="addressData.detailAddress" placeholder="楼号、门牌" placeholder-class="placeholder" />
		</view>
		
		<!-- <view class="row default-row">
			<text class="tit">设为默认</text>
			<switch :checked="addressData.is_default" color="#fa436a" @change="switchChange" />
		</view> -->
		<button class="add-btn" @click="confirm">提交</button>
	</view>
	
</template>
<script>
	export default {
		data() {
			return {
				addressData: {
					receiverName: '',
					receiverPhone: '',
					// addressName: '在地图选择',
					address: '在地图选择',
					detailAddress: '',
					id: ''
					// is_default: false
				}
			}
		},
		onLoad(option){
			let title = '新增收货地址';
			console.log(option)
			if(option.type==='edit'){
				title = '编辑收货地址'
				this.addressData = JSON.parse(option.data)
				// this.addressData.is_default = this.addressData.is_default ? true : false
			}
			this.manageType = option.type;
			uni.setNavigationBarTitle({
				title
			})
			
		},
		methods: {
			switchChange(e){
				this.addressData.is_default = e.detail.value
			},
			
			//地图选择地址
			chooseLocation(){
				uni.chooseLocation({
					success: (data)=> {
						// this.addressData.addressName = data.name;
						this.addressData.address = data.name;
					}
				})
			},
			
			//提交
			async confirm(){
				let data = this.addressData;
				if(!data.receiverName){
					this.$u.toast('请填写收货人姓名');
					return;
				}
				if(!this.$u.test.mobile(data.receiverPhone)){
					this.$u.toast('请输入正确的手机号码');
					return;
				}
				if(!data.address){
					this.$u.toast('请在地图选择所在位置');
					return;
				}
				if(!data.detailAddress){
					this.$u.toast('请填写门牌号信息');
					return;
				}
				
				// 添加地址
				// data.city = data.county = data.province
				// delete data.addressName
				if(this.manageType=='add'){
					await this.$u.api.updateAddress(data)
				}else{ // edit
					await this.$u.api.updateAddress(data)
				}
				this.$u.utils.prePage().refreshList(data, this.manageType);
				this.$u.toast(`地址${this.manageType=='edit' ? '修改': '添加'}成功`);
				setTimeout(()=>{
					uni.navigateBack()
				}, 800)
			},
		}
	}
</script>

<style lang="scss">
	page{
		background: $page-color-base;
		padding-top: 16upx;
	}

	.row{
		display: flex;
		align-items: center;
		position: relative;
		padding:0 30upx;
		height: 110upx;
		background: #fff;
		
		.tit{
			flex-shrink: 0;
			width: 120upx;
			font-size: 30upx;
			color: $font-color-dark;
		}
		.input{
			flex: 1;
			font-size: 30upx;
			color: $font-color-dark;
		}
		.icon-shouhuodizhi{
			font-size: 36upx;
			color: $font-color-light;
		}
	}
	.default-row{
		margin-top: 16upx;
		.tit{
			flex: 1;
		}
		switch{
			transform: translateX(16upx) scale(.9);
		}
	}
	.add-btn{
		display: flex;
		align-items: center;
		justify-content: center;
		width: 690upx;
		height: 80upx;
		margin: 60upx auto;
		font-size: $font-lg;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}
</style>
