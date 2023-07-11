export default {
    //必须的
    namespaced: true,
    state:{
        count: 3
    },
    getters:{
       getCount(state){
           return state.count
       }
    },
    //setters
    mutations: {
       setCount(state,newVal){
           state.count = newVal
       },
       increaseCount(state,newVal){
        state.count = state.count+newVal
       }
    }

};