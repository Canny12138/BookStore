<template>
	<view class="index-container">
		<!-- 标签页 筛选首页展示 -->
		<view class="u-p-l-60 u-p-r-60">
			<u-tabs :list="list" :is-scroll="false" :current="current" @change="change" height="60"
				class="u-p-t-20 u-p-b-20" bar-width="100"></u-tabs>
		</view>
		<!-- uView 12栅格布局 -->
		<u-row gutter="16" class="goods-list u-p-t-10">
			<!-- u-p-b-20 u-p-l-10 u-p-r-10z -->
			<u-col span="6" v-for="(item,index) in goodsData" class="u-p-b-10" :key="index">
				<!-- 商品组件 -->
				<goods :item="item"></goods>
			</u-col>
		</u-row>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				slides: [{
					img_url: 'http://150.158.85.93:81/img/banner3.jpg'
				}, {
					img_url: 'http://150.158.85.93:81/img/banner2.jpg'
				}, {
					img_url: 'http://150.158.85.93:81/img/banner1.jpg'
				}],
				list: [{
					name: '默认'
				}, {
					name: '热销'
				}, {
					name: '推荐'
				}, {
					name: '最新'
				}],
				current: 0,
				goodsData: [],
				page: 1, // 分页
				isLastPage: false,
				type: '' // 首页类型 
			}
		},
		onLoad() {
			this.getData()
		},
		methods: {
			async getData() {
				let params = {
					pageNum: this.page,
					pageSize: 10
				}
				this.type ? params[this.type] = 1 : ''
				let res = await this.$u.api.getIndex(params)
				// console.log(res)
				// 最后一页 停止发送请求
				if (this.page == res.data.pages) this.isLastPage = true
				// this.slides = res.slides 
				this.goodsData = this.goodsData ? [...this.goodsData, ...res.data.records] : res.data.records

			},
			change(index) {
				console.log(this.current = index);
				switch (index) {
					case 1:
						this.type = 'sales'
						break
					case 2:
						this.type = "recommend"
						break
					case 3:
						this.type = 'new'
						break
					default:
						this.type = ''
				}
				// 清空首页数据
				this.goodsData = []
				// 获取当前tab数据
				this.page = 0
				this.isLastPage = false
				this.getData()
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
	.goods-list {
		background-color: $u-bg-color;
	}
</style>