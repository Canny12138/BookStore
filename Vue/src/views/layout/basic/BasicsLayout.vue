<template>
  <el-row class="container">
    <!--头部-->
    <el-row :span="24" class="header">
      <Header></Header>
    </el-row>
    <el-col :span="24" class="main">
      <!--左侧导航-->
      <Sidebar></Sidebar>
      <!--右侧内容区-->
      <section class="content">
        <el-col :span="24">
          <transition name="fade" mode="out-in">
            <router-view></router-view>
          </transition>
        </el-col>
      </section>
    </el-col>
  </el-row>
</template>

<script>
  import Header from './Header.vue'
  import Sidebar from './Sidebar.vue'

  export default {
    name: "BasicsLayout",
    components: {Header, Sidebar},
    data() {
      return {
        ops: {
          bar: {
            vuescroll: {
              mode: 'slide',
              pullRefresh: {
                enable: false
              },
              pushLoad: {
                enable: false
              }
            },
            background: 'rgb(0,0,0,.3)'
          }
        },
      }
    },
    methods: {}
  }
</script>

<style lang="less">
  .container {
    overflow-x: hidden;
    height: 1000px;
  
    .header {
      width: auto;
    }

    .main {
      display: -ms-flexbox;
      display: flex;
      position: absolute;
      top: 56px;
      bottom: 0;
      overflow: hidden;

      .content {
        background: #f0f2f5;
        -ms-flex: 1;
        flex: 1;
        overflow-y: auto;
        
      }
    }
  }
</style>
