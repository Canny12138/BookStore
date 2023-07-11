<template>
	<view>
		<scroll-view class="list-scroll-content" scroll-y @scrolltolower="getData">
			<view class="goods-item" v-for="(item, index) in list" :key="index">
				<navigator :url="`/pages/goods/detail?id=${item.bookId}`" hover-class="none">
					<view class="image-container">
						<u-image width="200rpx" height="200rpx" :src="'http://150.158.85.93:81/' + item.imgSrc">
						</u-image>
					</view>
				</navigator>
				<view class="details">
					<view class="title">{{item.title}}</view>
					<view class="desc">分类: </view>
					<view class="price">¥ {{item.price}}</view>
				</view>

				<view class="unfavorite-btn" @click="unfavorite(item.bookId)">
					<text>取消收藏</text>
				</view>

			</view>
		</scroll-view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				list: [],
				isLoad: true,
				currentPage: 0
			};
		},
		onLoad(options) {
			this.getData();
		},
		methods: {
			async getData() {
				// 更新当前请求的页数
				// console.log('load');
				if (!this.isLoad) {
					return
				}

				this.currentPage += 1;
				let res = await this.$u.api.getCollects({
					pageNum: this.currentPage,
					pageSize: 10
				});
				if (this.currentPage == res.data.pages) {
					this.isLoad = false
				}
				const newRecords = res.data.records;


				// 使用 Promise.all() 并行发起请求
				const promises = newRecords.map(async (item) => {
					let titleRes = await this.$u.api.getIndex({
						pageNum: 1,
						pageSize: 1,
						bookid: item.bookId
					});
					item.title = titleRes.data.records[0].title;
					item.price = titleRes.data.records[0].price;
					item.imgSrc = titleRes.data.records[0].imgSrc;
				});

				// 等待所有请求完成
				await Promise.all(promises);

				// 将新数据添加到 list 中
				this.list = this.list.concat(newRecords);
				// console.log(this.list);
			},
			async unfavorite(id) {
				// 调用API以取消收藏
				await this.$u.api.delCollect({
					bookId: id,
				});
				this.$u.toast('取消收藏')
				// 如果API调用成功，则从列表中移除取消收藏的项目
				this.list = this.list.filter((item) => item.bookId !== id);
			}
		},
	};
</script>

<style lang="scss" scoped>
	.list-scroll-content {
		height: 93vh;
	}

	.goods-item {
		display: flex;
		flex-direction: row;
		align-items: center;
		padding: 20rpx;
		// padding-right: 20rpx;
		// padding-top: 5rpx;
		// padding-bottom: 5rpx;
		border-top: 1rpx solid #ccc;
		background-color: #fff;
		border-radius: 10rpx;

		.image-container {
			flex: 0 0 200rpx;
			margin-right: 20rpx;
		}

		.details {
			flex-grow: 1;
		}

		.title {
			font-size: 32rpx;
			font-weight: bold;
			color: #333;
			margin-bottom: 10rpx;
		}

		.desc {
			font-size: 24rpx;
			color: #999;
			margin-bottom: 10rpx;
		}

		.price {
			font-size: 28rpx;
			color: $u-type-error;
			font-weight: bold;
		}

		.unfavorite-btn {
			background-color: #ff3b30;
			color: #fff;
			padding: 10rpx 20rpx;
			border-radius: 5rpx;
			font-size: 24rpx;
			margin-left: 20rpx;
		}
	}
</style>
