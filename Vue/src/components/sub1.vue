<template>
  <div >
    <h1>This is sub1 vue</h1>
    {{myMsg}}--{{age}}--{{obj.name}}--{{ary.join('%')}}--{{test}}
    <br/>
    <button @click="changeMsg()">修改父组件的msg</button>
  </div>
</template>

<script>
export default {
  name: 'Sub1',
  data(){
    return {
      myMsg:'empty'
    }
  },
  props:{
    msg:String,
    age:{
      type:Number,
      validator(val){
                var result = val>100
                if(!result){
                    alert('age is small than 100')
                }
                
                return result
            }
    },
    obj:Object,
    ary:Array,
    test:{
      type:String,
      default:'19soft2'
    }
  },
  mounted(){
      this.myMsg = this.msg
  },
  methods:{
    changeMsg(){
      this.myMsg = '19soft2'
      this.$emit('rollback',this.myMsg,180)
    }
  }

}
</script>


