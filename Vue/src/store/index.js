import Vue from 'vue'
import Vuex from 'vuex'

import ma from './module/moduleA'
import mb from './module/moduleB'
import app from './module/app'

import { getAllDic } from '@/api/modules/dictype'

Vue.use(Vuex)

export default new Vuex.Store({
  //data
  state: {
    rootCount: 1,
    list: [],
    dicMap: {},//useful=>list
    searchForm: {
      title: ""
    }
  },
  //setters
  mutations: {
    setList(state, newList) {
      state.list = newList
    },
    setDicMap(state, map) {
      state.dicMap = map
      // console.log(state.dicMap)
    }
  },
  //methods
  actions: {
    getDicMap({ commit }) {
      getAllDic().then(res => {
        //res = result
        let map = res.data
        commit('setDicMap', map)
      }).catch(err => {
        console.log(err)
      })
    },
    updateList({ commit }, val) {
      var list = [
        {
          id: 1,
          sex: 1,//boy
          name: 'xmut'
        }, {
          id: 2,
          sex: 0,//girl
          name: '19soft1'
        }
      ]

      if (val != 2) {
        list = [
          {
            id: 1,
            sex: 1,//boy
            name: 'xmut'
          }, {
            id: 2,
            sex: 0,//girl
            name: '19soft1'
          }, {
            id: 3,
            sex: 1,
            name: 'fzu'
          }
        ]
      }
      commit('setList', list)
    },
    initList(context) {
      var list = [
        {
          id: 1,
          sex: 1,//boy
          name: 'xmut'
        }, {
          id: 2,
          sex: 0,//girl
          name: '19soft1'
        }
      ]

      context.commit('setList', list)
    }
  },
  //模块化
  modules: {
    ma,
    mb,
    app
  },
  //getters
  getters: {
    getList(state) {
      return state.list.map(item => {
        item.sexName = item.sex == 1 ? 'boy' : 'girl'
        return item
      })
    }
  }
})
