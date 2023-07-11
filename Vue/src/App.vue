<template>
  <div id="app">
     <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/myAbout">About</router-link> |
      <router-link to="/basic">vue基础</router-link>|
      <router-link to="/basic2">vue基础2</router-link>|
      <router-link to="/life">vue生命周期</router-link>|
      <router-link to="/myRoute/xmut1234">vue路由</router-link>|
      <router-link to="/myComponent">vue自定义组件</router-link>|
      <router-link to="/student">Student增删改查</router-link>|
      <router-link to="/myDiretive">vue自定义组件</router-link>|
      <router-link to="/myMixin">vue混入</router-link>|
      <router-link to="/myVuex">vue状态管理</router-link>|
      <router-link to="/myAxios">axios</router-link>|
      <router-link to="/myAxios2">axios-2</router-link>|
      <router-link to="/myAxios3">axios-3</router-link>|
      <router-link to="/myLogin">登陆</router-link>|
      <router-link to="/myLoginUI">登陆UI</router-link>|
      <router-link to="/myBook">book管理</router-link>
    </div>  -->
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
  
  a {
    font-weight: bold;
    color: #2c3e50;
    height: 500px;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
