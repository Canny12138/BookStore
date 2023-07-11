import Vue from 'vue'
import VueRouter from 'vue-router'
//同步路由
import Home from '../views/Home.vue'

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

//异步路由
// const About =  () => import( '../views/About.vue')
// const Basic = () => import('../views/Basic.vue')
// const Basic2 = () => import('../views/Basic2.vue')
// const Life = () => import('../views/Life.vue')
// const MyRoute = () => import('../views/MyRoute.vue')
// const Child1 = () => import('../views/Child1.vue')
// const Child2 = () => import('../views/Child2.vue')
// const MyComponent = () => import('../views/MyComponent.vue')
// const Student = () => import('../views/Student.vue')
// const MyDiretive = () => import('../views/MyDirective.vue')
// const MyMixin = () => import('../views/MyMixin.vue')
// const MyVuex = () => import('../views/MyVuex.vue')
// const MyAxios = () => import('../views/MyAxios.vue')
// const MyAxios2 = () => import('../views/MyAxios2.vue')
// const MyAxios3 = () => import('../views/MyAxios3.vue')
const MyLogin = () => import('../views/MyLogin.vue')
const Login = () => import('../views/Login.vue')
const MyLoginUI = () => import('../views/MyLoginUI.vue')
const MarketHome = () => import('../views/market/MarketHome.vue')
const HomeHeader = () => import('../views/market/HomeHeader.vue')
const MyBook = () => import('../views/MyBook.vue')
const MyDic = () => import('../views/dic/MyDic.vue')
const MyUser = () => import('../views/urm/user/MyUser.vue')
const MyRole = () => import('../views/urm/role/MyRole.vue')
const MyMenu = () => import('../views/urm/menu/MyMenu.vue')

// const Layout = () => import('../views/myLayout/layout.vue')

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   name: 'layout',
  //   component: Layout,
  //   children:[
  //     {
  //       path:'/',
  //       name:'Home',
  //       component:Home
  //     },
  //     {
  //       path: '/myBook',
  //       name: 'MyBook',
  //       component: MyBook
  //     },
  //     {
  //       path: '/myDic',
  //       name: 'MyDic',
  //       component: MyDic
  //     },
  //     {
  //       path: '/myUser',
  //       name: 'MyUser',
  //       component: MyUser
  //     },
  //     {
  //       path: '/myRole',
  //       name: 'MyRole',
  //       component: MyRole
  //     },
  //     {
  //       path: '/myMenu',
  //       name: 'MyMenu',
  //       component: MyMenu
  //     }
  //   ]
  // },
  {
    path: '/myLoginUI',
    name: 'MyLoginUI',
    component: MyLoginUI
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/homeHeader',
    name: 'HomeHeader',
    component: HomeHeader,
    children:[
      {
        path: '/marketHome',
        name: 'MarketHome',
        component: MarketHome
      },
    ]
  },
  // {
  //   path: '/',
  //   name: 'Home',
  //   component: Home
  // },
  // {
  //   path: '/about',
  //   alias: '/myAbout',
  //   redirect:'/',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   //异步路由
  //   component: About
  // },
  // {
  //   path: '/basic',
  //   name: 'Basci',
  //   component: Basic
  // },
  // {
  //   path: '/basic2',
  //   name: 'Basci2',
  //   component: Basic2
  // },
  // {
  //   path: '/life',
  //   name: 'Life',
  //   component: Life
  // },
  // {
  //   path: '/myRoute/:id?',
  //   name: 'MyRoute',
  //   component: MyRoute,
  //   children:[
  //     {
  //       path:'/child1',
  //       name:'Child1',
  //       component:Child1
  //     },
  //     {
  //       path:'/child2',
  //       name:'Child2',
  //       component:Child2
  //     }
  //   ]
  // },
  // {
  //   path: '/myComponent',
  //   name: 'MyComponent',
  //   component: MyComponent
  // },
  // {
  //   path: '/student',
  //   name: 'Student',
  //   component: Student
  // },
  // {
  //   path: '/myDiretive',
  //   name: 'MyDiretive',
  //   component: MyDiretive
  // },
  // {
  //   path: '/myMixin',
  //   name: 'MyMixin',
  //   component: MyMixin
  // },
  // {
  //   path: '/myVuex',
  //   name: 'MyVuex',
  //   component: MyVuex
  // },
  // {
  //   path: '/myAxios',
  //   name: 'MyAxios',
  //   component: MyAxios
  // },
  // {
  //   path: '/myAxios2',
  //   name: 'MyAxios2',
  //   component: MyAxios2
  // },
  // {
  //   path: '/myAxios3',
  //   name: 'MyAxios3',
  //   component: MyAxios3
  // },
  {
    path: '/myLogin',
    name: 'MyLogin',
    component: MyLogin
  },
  // {
  //   path: '/myLoginUI',
  //   name: 'MyLoginUI',
  //   component: MyLoginUI
  // },
  // {
  //   path: '/myBook',
  //   name: 'MyBook',
  //   component: MyBook
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // console.log('beforeEach')
  // console.log(from)
  // console.log(to)
  // if(to.path=='/life'){
  //   next('/myRoute')
  // }else{
  //   next()
  // }
  next()
})

export default router
