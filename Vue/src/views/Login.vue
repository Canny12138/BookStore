<template>
  <div style="margin-top: 200px">
    <h1 style="color: #E4E7ED">登录</h1>
    <el-form
      :model="formData"
      status-icon
      :rules="rules"
      ref="formData"
      label-width="70px"
      class="demo-ruleForm"
      style="width: 300px; margin: auto; border:10px"
    >
      <el-form-item label="用户名" prop="username">
        <el-input type="text" v-model="formData.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          type="password"
          v-model="formData.password"
          autocomplete="off"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submit()" style="margin-left: -40px"
          >提交</el-button
        >
        <router-link to="myLogin"
          ><el-button type="primary" style="margin-left: 20px"
            >注册</el-button
          ></router-link
        >
        <!-- <el-button @click="resetForm('formData')">重置</el-button> -->
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="clear()">清空localstorage</el-button>
  </div>
</template>
<script>
import util from "@/libs/util2";
import { login, register } from "@/api/modules/user";
import { setStore, getStore, removeStore } from "@/libs/storage";
import { setCookie } from "@/libs/cookie";
import MyLoginVue from "./MyLogin.vue";
export default {
  name: "MyLoginUI",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value.length < 6) {
        callback(new Error("密码长度不能小于6"));
      }
    };
    return {
      formData: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, message: "请填写用户名", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请填写密码", trigger: "blur" },
          { validator: validatePass, trigger: "blur" },
          // { pattern: "[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+"}
        ],
      },
    };
  },

  methods: {
    clear() {
      removeStore("token");
    },

    submit() {
      login({
        ...this.formData,
      })
        .then((res) => {
          setCookie("user", JSON.stringify(res.data.user));
          let token = res.data.token;
          setStore("token", token);

          util.initRouter(this);
        })
        .catch((err) => {
          this.toast(err, 2);
        });
    },
  },
};
</script>
<style >
html {
  background-color: #f0f2f5;
  background: url("../../public/img/loginback.jpeg");
  background-size: 100%;
}
.el-form-item__label {
  color: #DCDFE6;
}
</style>