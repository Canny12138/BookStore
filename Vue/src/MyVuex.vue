<template>
  <div >
    <h1>This is an MyVuex page</h1>
    count1:{{getRootCount()}}
    <br/>
    count2:{{cGetRootCount}}
    <br/>
    count3:{{rootCount2}}
    <br/>
    count4:{{rootCount}}
    <br/>
    <!-- 模块化  -->
    <!-- 模块A-count  -->
    moduleA:{{getCountA()}}
    <br/>
    moduleA2:{{countA2}}
    <br/>
    moduleA3:{{count}}
    <br/>
    <!-- 模块B-count  -->
    moduleB:{{getCountB()}}
    <br/>


    moduleB2:{{getCountB2}}
    <br/>
    moduleB3:{{getCountB3}}
    <br/>
    moduleB4:{{getCount}}
    <br/>

    <!-- 修改器 -->
    <button @click="setCount(5)">设置moduleB的count为5</button>
    {{getCountB()}}
<br/>
    <button @click="inCount(1)">增加moduleB的count为1</button>
    {{getCountB()}}
    <br/>
     <button @click="increaseCount(2)">增加moduleB的count为2</button>
    {{getCountB()}}
    <br/>

    <!-- actions -->
    <ul>
      <li v-for="item in list" :key="item.id">
        {{item.id}}--{{item.sexName}}--{{item.name}}
      </li>
    </ul>

    <button @click="changeList(3)">修改list为3条记录</button>
    <!-- <button @click="changeList(2)">修改list为2条记录</button> -->
    <!-- <button @click="changeList2(2)">修改list为2条记录</button> -->
    <!-- <button @click="updateList(2)">修改list为2条记录</button> -->
    <button @click="myUpdate(2)">修改list为2条记录</button>
  </div>
</template>
<script>
import {mapState,mapGetters,mapMutations,mapActions} from 'vuex'
export default {
  name: 'MyVuex',
  data(){
    return{

    }
  },
  mounted(){
    this.$store.dispatch('initList')
  },
  methods:{
    ...mapActions(
      {
        'changeList2':'updateList'
      }
    ),
    ...mapActions(
      ['updateList']
    ),
    myUpdate(val){
      this['updateList'](val)
    },
    changeList(val){
       this.$store.dispatch('updateList',val)
    },
      setCount(val){
        this.$store.commit('mb/setCount',val)
      },
      ...mapMutations(
        'mb',{
          inCount:'increaseCount'
        }
      ),
      ...mapMutations(
        'mb',['increaseCount']
      ),
      // increaseCount(val){
      //   this.$store.commit('mb/increaseCount',val)
      // },
      getRootCount(){
        //console.log(this.$store)
        return this.$store.state.rootCount
      },
      getCountA(){
          return this.$store.state.ma.count
      },
      getCountB(){
          return this.$store.state.mb.count
      }
  },
  computed:{
    list(){
        return this.$store.getters['getList']
    },
    cGetRootCount(){
      return this.$store.state.rootCount
    },
    
    ...mapState({
      rootCount2:state=>state.rootCount
    }),
    ...mapState(
      ['rootCount']
    ),
    ...mapState(
      'ma',{
         countA2:'count'
      }
    ),
    ...mapState(
      'ma',['count']
    ),
    //通过vuex对象获取getter
    getCountB2(){
      return this.$store.getters['mb/getCount']
    },
    ...mapGetters(
      'mb',{
        getCountB3:'getCount'
      }
    ),
    ...mapGetters(
      'mb',['getCount']
    )
    
  }
}
</script>
<style >

</style>