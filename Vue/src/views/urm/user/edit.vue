<template>
  <el-dialog :title="title" :visible="visible" :close-on-click-modal="false"
             :before-close="handleClose" width="500px">
    <el-form :model="formData" :rules="rules" ref="formData" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="formData.username"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="text" @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleSubmit"
                 :loading="loading">{{loading?'提交中...':'确认提交'}}</el-button>
    </span>
  </el-dialog>
</template>

<script>
 import {add} from '@/api/modules/user'
  export default {
    name: "edit",
    props: {
      visible: {
        type: Boolean
      },
      title: {
        type: String,
        default: '新增'
      },
      defaultFormData: {
        type: Object
      },
    },
    data() {
      return {
        loading: false,
        
        formData: {
          name: ''
        },
       
        
        refresh: false,
        rules:{
             username: [
                { required: true,message: '请填写用户名', trigger: 'blur' },
                { min: 3, max: 5, message: '长度在 3 到 45 个字符', trigger: 'blur' }
                
              ]
        }
      }
    },
    mounted() {
     
      if (JSON.stringify(this.defaultFormData) == "{}") {

      }else{
        //console.log('before:'+this.formData)
        this.formData = JSON.parse(JSON.stringify(this.defaultFormData))
       // console.log('after:'+this.formData)
      }
    },
    methods: {
      
      handleClose() {
        this.$emit('close',this.refresh)
      },
      handleSubmit() {

        this.$refs.formData.validate(valid => {
          if (valid) {
            
             add({    
                ...this.formData
              }).then(res =>{
                  this.refresh = true
                  this.handleClose()
              }).catch(err =>{
                  this.toast(err,2)
              })
           
          }
        })


       
        
      }
    }
  }
</script>

<style scoped>

</style>
