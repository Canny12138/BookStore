<template>
	<view class="u-wrap">
		<u-row gutter="16" class="goods-list u-p-t-10">
			<!-- u-p-b-20 u-p-l-10 u-p-r-10z -->
			<u-col span="6" v-for="(item,index) in goodsData" class="u-p-b-10" :key="index">
				<!-- 商品组件 -->
				<goods :item="item.book"></goods>
			</u-col>
		</u-row>
	</view>
</template>
<script>
	import empty from "@/components/empty";
	export default {
		data() {
			return {
				current: 0,
				goodsData: [],
				page: 1, // 分页
				isLastPage: false,
				type: '', // 首页类型 ,
				title: '',
				category: 0,
				array: ['全部', '文学', '科技', '教育', '历史', '艺术'],
				index: 0,
			}
		},
		onLoad() {
			this.getData()
		},
		methods: {
			async getData() {
				let params = {
					pageNum: this.page,
					pageSize: 10,
				}
				this.type ? params[this.type] = 1 : ''
				let res = await this.$u.api.getSale(params)
				console.log(res)
				// 最后一页 停止发送请求
				if (this.page == res.data.pages) this.isLastPage = true
				// this.slides = res.slides 
				this.goodsData = this.goodsData ? [...this.goodsData, ...res.data.records] : res.data.records

			},
			change() {
				// 清空首页数据
				this.goodsData = []
				// 获取当前tab数据
				this.page = 1
				this.isLastPage = false
				this.getData()
			},
			searchGoods() {
				this.change()
			},
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.detail.value)
				this.index = e.detail.value
				this.category = e.detail.value
				this.change()
			}
		},

		onReachBottom() {
			if (this.isLastPage) return
			this.page++
			this.getData()
			console.log(111)
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: #f3f4f6;
	}
	.u-wrap {
		height: calc(100vh);
		/* #ifdef H5 */
		height: calc(100vh - var(--window-top));
		/* #endif */
		display: flex;
		flex-direction: column;
	}

	.cate-bold {
		font-weight: 600 !important;
		color: #000 !important;
	}

	.u-search-box {
		padding: 18rpx 30rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.u-menu-wrap {
		flex: 1;
		display: flex;
		overflow: hidden;
	}

	.u-search-inner {
		background-color: rgb(234, 234, 234);
		border-radius: 100rpx;
		display: flex;
		align-items: center;
		padding: 10rpx 16rpx;
	}

	.u-search-text {
		font-size: 26rpx;
		color: $u-tips-color;
		margin-left: 10rpx;
	}

	.u-tab-view {
		width: 200rpx;
		height: 100%;
	}

	.u-tab-item {
		height: 110rpx;
		background: #f6f6f6;
		box-sizing: border-box;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 26rpx;
		color: #444;
		font-weight: 400;
		line-height: 1;
	}

	.u-tab-item-active {
		position: relative;
		color: #000;
		font-size: 30rpx;
		font-weight: 600;
		background: #fff;
	}

	.u-tab-item-active::before {
		content: "";
		position: absolute;
		border-left: 4px solid $u-type-primary;
		height: 32rpx;
		left: 0;
		top: 39rpx;
	}

	.u-tab-view {
		height: 100%;
	}

	.right-box {
		background-color: rgb(250, 250, 250);
	}

	.page-view {
		padding: 16rpx;
		width: 100%;
		flex-wrap: wrap;

		.empty {
			height: 400rpx;
		}
	}

	.class-item {
		margin-bottom: 30rpx;
		background-color: #fff;
		padding: 16rpx;
		border-radius: 8rpx;
	}

	.class-item:last-child {
		min-height: 100vh;
	}

	.item-title {
		font-size: 26rpx;
		color: $u-main-color;
		font-weight: bold;
	}

	.item-menu-name {
		font-weight: normal;
		font-size: 24rpx;
		color: $u-main-color;
	}

	.item-container {
		display: flex;
		flex-wrap: wrap;
	}

	.thumb-box {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		margin-top: 20rpx;
		width: 50%;
	}

	.item-menu-image {
		width: 120rpx;
		height: 120rpx;
	}

	.goods-title {
		font-weight: bold;
		padding: 20rpx 0 80rpx 0;
	}

	.cate-level-1 {
		font-weight: bold;
		font-size: 30rpx;
		color: #000000;
	}

	.goods-list {
		background-color: $u-bg-color;
	}

	.picker-container {
		display: flex;
		align-items: center;
		padding: 10px;
	}

	.picker {
		display: flex;
		align-items: center;
	}
</style>