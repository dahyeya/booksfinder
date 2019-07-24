import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/Login.vue'
import Register from '@/components/Register.vue'
import Index from '@/components/Index.vue'
import Detail from '@/components/Detail.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Login
    },
    {
      path: '/index',
      component: Index
    },
    {
      path: '/register',
      component: Register
    },
    {
      path: '/detail',
      component: Detail
    }
  ]
})
