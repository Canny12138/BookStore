<template>
	<view class="u-m-l-40 u-m-r-40">
		<u-form :model="form" ref="uForm" >
			<u-form-item label="昵称" prop="name" :required="true">
				<u-input v-model="form.name" />
			</u-form-item>
		</u-form>
		<u-button @click="submit">提交</u-button>
	</view>
</template>

<script>
export default {
	data() {
		return {
			form: {
				name:'',
			},
			rules: {
				name: [
					{ 
						required: true, 
						message: '请输入姓名', 
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change','blur'],
					}
				]
			}
		};
	},
	onLoad() {
		// this.form.name = this.vuex_user.name
	},
	methods: {
		submit() {
			this.$refs.uForm.validate(async valid => {
				if (valid) {
					await this.$u.api.updateUserInfo({
						name: this.form.name
					})
					// 修改vuex
					this.$u.vuex('vuex_user.name', this.form.name)
					this.$u.toast('修改成功')
				} else {
					console.log('验证失败');
				}
			});
		}
	},
	// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
	onReady() {
		this.$refs.uForm.setRules(this.rules);
	}
};
</script>
<style lang="scss">
	
</style>