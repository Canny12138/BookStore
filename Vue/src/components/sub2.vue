<template>
  <div >
    <h1>This is sub1 vue</h1>
    <input type="text" v-model="content" @input="change()"/><br/>
    son:{{content}}
    <br/>
    
  </div>
</template>

<script>
export default {
  name: 'Sub2',
  data(){
    return {
      content:''
    }
  },
  model:{
        prop: "modelMsg",
        event: "cb"
  },
  props:{
    modelMsg:String
  },
  mounted(){
      this.content = this.modelMsg
  },
  methods:{
    change(){
      this.$emit('cb',this.content)
    }
  }

}
</script>


