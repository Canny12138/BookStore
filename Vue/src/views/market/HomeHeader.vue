<template>
  <div>
    <!-- <div style="position: fixed; top: 200px; left: 0px; z-index: 10000000">
      <img src="img/1.gif" />
    </div>
    <div style="position: fixed; top: 200px; right: 0px; z-index: 10000000">
      <img src="img/2.gif" />
    </div> -->

    <el-row>
      <a href="" @click="toHome" style="color: #2c3e50">
        <h1 style="width: 200px; float: left">
          <i class="el-icon-reading"></i> 图书商城
        </h1>
      </a>

      <el-button @click="search"></el-button>

      <div class="account top-bar-btn" style="float: right">
        <img src="@/assets/images/avatar.png" class="avatar" />
        <el-dropdown trigger="click">
          <span class="el-dropdown-link text-white">
            欢迎，{{ user.nickname || user.username || "请登录" }}
            <i class="el-icon-caret-bottom"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout"
              ><span v-if="user.nickname || user.username">退出登录</span
              ><span v-else>登录</span></el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <div
        style="
          margin-top: 35px;
          margin-right: 300px;
          width: 500px;
          float: right;
        "
      >
        <el-input
          placeholder="请输入内容"
          v-model="input"
          class="input-with-select"
        >
          <el-select v-model="select" slot="prepend" placeholder="请选择">
            <el-option label="全部" value="1"></el-option>
            <el-option label="可用" value="2"></el-option>
            <el-option label="不可用" value="3"></el-option>
          </el-select>
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="search"
          ></el-button>
        </el-input>
      </div>
    </el-row>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="1" @click="toHome">主页</el-menu-item>
      <el-submenu index="2">
        <template slot="title">我的工作台</template>
        <el-menu-item index="2-1">选项1</el-menu-item>
        <el-menu-item index="2-2">选项2</el-menu-item>
        <el-menu-item index="2-3">选项3</el-menu-item>
        <el-submenu index="2-4">
          <template slot="title">选项4</template>
          <el-menu-item index="2-4-1">选项1</el-menu-item>
          <el-menu-item index="2-4-2">选项2</el-menu-item>
          <el-menu-item index="2-4-3">选项3</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="3" @click="toCollect">收藏夹</el-menu-item>
      <el-menu-item index="4"
        ><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item
      >
    </el-menu>
    <router-view />
  </div>
</template>

<script>
import { getCookie, removeCookie } from "@/libs/cookie";
import { removeStore } from "@/libs/storage";
export default {
  data() {
    return {
      input: "",
      select: "1",
      activeIndex: "1",
      user: {
        username: "",
      },
      searchForm: {
        title: "",
        useful: null,
      },
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    init() {
      let user = JSON.parse(getCookie("user") || "{}");
      this.user = user;
    },
    logout() {
      removeStore("token");
      removeCookie("user");
      this.navigate("/MyLoginUI");
    },
    toCollect() {
      this.navigate("UserCollect");
    },
    toHome() {
      this.navigate("MarketHome");
    },
    search() {
      this.searchForm.title = this.input;
      if (this.select == "1") {
        this.searchForm.useful = null;
      } else if (this.select == "2") {
        this.searchForm.useful = 1;
      } else {
        this.searchForm.useful = 0;
      }
      this.navigate("SearchPage");
      this.$children[2].searchBook();
      // this.searchForm.title = this.input;
    },
  },
  mounted() {
    this.init();
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
  font-size: medium;
}

.account {
  float: right;
  padding-right: 12px;
}

.avatar {
  height: 40px;
  width: 40px;
  margin-right: 10px;
  margin-left: 30px;
  margin-top: 20px;
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