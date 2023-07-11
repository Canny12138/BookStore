<template>
  <div style="margin-top: 200px">
    <h1 style="color: #e4e7ed">登录</h1>
    <div style="margin-bottom: 10px; margin-left: 20px">
      <!-- <el-radio v-model="radio" label="2"
        ><span style="color: #dcdfe6">前台商城</span></el-radio
      > -->
      <!-- <el-radio v-model="radio" label="1"
        ><span style="color: #dcdfe6">后台管理</span></el-radio
      > -->
    </div>
    <el-form
      :model="formData"
      status-icon
      :rules="rules"
      ref="formData"
      label-width="70px"
      class="demo-ruleForm"
      style="width: 300px; margin: auto; border: 10px"
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
    <!-- <el-button type="primary" @click="clear()">清空localstorage</el-button> -->
  </div>
</template>
<script>
import util from "@/libs/util";
import util2 from "@/libs/util2";
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
        username: "admin",
        password: "123456",
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
      radio: "1",
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
          setStore("pagerole", this.radio);
          setStore("userid", res.data.user.id);
          if (this.radio == "1") {
            util.initRouter(this);
          } else {
            util2.initRouter(this);
          }
        })
        .catch((err) => {
          this.toast(err, 2);
        });
    },
  },
  mounted() {
    document
      .querySelector("html")
      .setAttribute(
        "style",
        "background: url(" +
          require("../../public/img/loginback.jpeg") +
          ");background-size: cover;" +
          "background-size: 100%;"
      );
  },
  destroyed() {
    document.querySelector("html").removeAttribute("style");
  },
};
</script>
<style >
/* html {
  background-color: #f0f2f5;
  background: url("../../public/img/loginback.jpeg");
  background-size: 100%;
} */
.el-form-item__label {
  color: #dcdfe6;
}
</style>