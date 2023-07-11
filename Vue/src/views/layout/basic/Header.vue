<template>
  <div>
    <el-row class="container" style="background-color:#f0f2f5">
      <!--头部-->
      <el-row id="header" class="top-bar-wrap" style="margin-top:10px">
        <div style="font-size:25px;font-weight:bold;width:200px;margin-left:20px;float:left">
          <div class="top-bar-title" @click="navToHome()">
            {{ "后台管理系统" }}
          </div>
        </div>
        <div class="account top-bar-btn" style="float:right">
          <img src="@/assets/images/avatar.png" class="avatar" />
          <el-dropdown trigger="click">
            <span class="el-dropdown-link text-white">
              欢迎，{{ user.nickname || user.username || "暂无用户名" }}
              <i class="el-icon-caret-bottom"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="logout"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-row>
    </el-row>
  </div>
</template>

<script>
import "element-ui/lib/theme-chalk/display.css";
import { getCookie, removeCookie } from "@/libs/cookie";
import { removeStore } from "@/libs/storage";

export default {
  data() {
    return {
      visible: {
        up: false, // updatePassword
      },
      user: {
        username: "",
      },
      isFullScreen: false,
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      let user = JSON.parse(getCookie("user") || "{}");
      this.user = user;
    },
    logout() {
      removeStore("token");
      removeCookie("user");
      this.navigate("/MyLoginUI");
    },
    fullScreenChange(isFullScreen) {},
    navToHome() {
      this.navigate(this.$store.state.app.firstRouter.path);
    },
    close(val) {
      this.visible.up = false;
    },
  },
};
</script>

<style scoped>
.el-dropdown-link {
  display: flex;
  align-items: center;
  cursor: pointer;
  -moz-user-select: none; /*火狐*/
  -webkit-user-select: none; /*webkit浏览器*/
  -ms-user-select: none; /*IE10*/
  -khtml-user-select: none; /*早期浏览器*/
  user-select: none;
  border: none;
  outline: none;
}

.account {
  float: right;
  padding-right: 12px;
}

.avatar {
  height: 30px;
  width: 30px;
  margin-right: 10px;
  margin-left: 30px;
  border-radius: 50%;
}

.message {
  font-size: 20px;
  margin-top: -2px;
}

.dot {
  position: relative;
  width: 8px;
  right: 5px;
  top: -8px;
  background-color: #f56c6c;
  border-radius: 10px;
  color: #fff;
  display: inline-block;
  font-size: 12px;
  height: 8px;
  line-height: 8px;
  text-align: center;
  white-space: nowrap;
  border: 1px solid #fff;
}

.toolbar {
  margin: 0 10px;
}
</style>
