<template>
  <div style="margin-top: 200px">
    <h1 style="color: #e4e7ed">注册</h1>

    <!-- 用户名：<input type="text" v-model="formData.username" /><br />
    密码：<input type="text" v-model="formData.password" /><br />
    <button @click="submit()">提交</button>
    <br /> -->
    <el-form
      :model="formData"
      status-icon
      :rules="rules"
      ref="formData"
      label-width="70px"
      class="demo-ruleForm"
      style="width: 300px; margin: auto"
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
        <el-button type="primary" @click="register()" style="margin-left: -40px"
          >提交</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 用户名：<input type="text" v-model="formData.username" /><br />
    密码：<input type="text" v-model="formData.password" /><br />
    <button @click="register()">提交</button> -->
  </div>
</template>
<script>
import { login, register } from "@/api/modules/user";
import { updateRoleByUserId } from "@/api/modules/user_role";
export default {
  name: "MyLogin",
  props: {
    user: {
      type: Object,
    },
  },
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
    register() {
      register({
        ...this.formData,
      })
        .then((res) => {
          this.toast(res.message);
        })
        .catch((err) => {
          this.toast(err, 2);
        });
      this.$router.push("/MyLoginUI");
    },

    role() {
      updateRoleByUserId({
        id: this.user.id,
        ids: checkedKeys.join(","),
      }).then((res) => {
        this.loading = false;
        if (res.success) {
          this.refresh = true;
          this.toast(res.message);
          this.close();
        } else {
          this.toast(res.message, "error");
        }
      });
    },

    submit() {
      login({
        ...this.formData,
      })
        .then((res) => {
          this.toast(res.message);
          console.log(res.data);
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
    console.log(document.querySelector("html"));
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