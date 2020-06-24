import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    redirect: "/home"
  },
  {
    path: '/login&register',
    name: 'login&register',
    redirect: "/login",
    component: () => import("../views/login&register.vue"),
    children: [{
        path: '/login',
        name: 'login',
        component: () => import("../views/login&register/login.vue"),
      },
      {
        path: '/register',
        name: 'register',
        component: () => import("../views/login&register/register.vue"),
      }
    ]
  },
  {
    path: '/home',
    name: 'home',
    redirect: "/homePage",
    component: () => import("../views/home.vue"),
    children: [{
        path: '/manager/',
        name: 'manager',
        component: () => import("../views/home/manager.vue"),
        children: [{
            path: '/userInfo',
            name: 'userInfo',
            component: () => import("../views/home/manager/userInfo.vue"),
          },
          {
            path: '/userList',
            name: 'userList',
            component: () => import("../views/home/manager/userList.vue"),
          },
          {
            path: '/userAdd',
            name: 'userAdd',
            component: () => import("../views/home/manager/userAdd.vue"),
          },
          {
            path: '/userModify',
            name: 'userModify',
            component: () => import("../views/home/manager/userModify.vue"),
          },
          {
            path: '/goodsManager',
            name: 'goodsManager',
            component: () => import("../views/home/manager/goodsManager.vue"),
          },
          {
            path: '/sort',
            name: 'sort',
            component: () => import("../views/home/manager/sort.vue"),
          },
          {
            path: '/goodsAdd',
            name: 'goodsAdd',
            component: () => import("../views/home/manager/goodsAdd.vue"),
          },
          {
            path: '/newsList',
            name: 'newsList',
            component: () => import("../views/home/manager/newsList.vue"),
          },
          {
            path: '/myOrder',
            name: 'myOrder',
            component: () => import("../views/home/manager/myOrder.vue"),
          },
          {
            path: '/allOrder',
            name: 'allOrder',
            component: () => import("../views/home/manager/allOrder.vue"),
          },
          {
            path: '/goodsEdit',
            name: 'goodsEdit',
            component: () => import("../views/home/manager/goodsEdit.vue"),
          },
          {
            path: '/LeavingBook',
            name: 'LeavingBook',
            component: () => import("../views/home/manager/LeavingBook.vue"),
          },
        ]
      },
      {
        path: '/goodsDetail',
        name: 'goodsDetail',
        component: () => import("../views/home/index/goodsDetail.vue"),
      },
      {
        path: '/pushOrder',
        name: 'pushOrder',
        component: () => import("../views/home/index/pushOrder.vue"),
      },
      {
        path: '/homePage',
        name: 'index',
        component: () =>
          import("../views/home/index.vue"),
      }
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router