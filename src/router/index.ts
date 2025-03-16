// 这段代码的作用是配置和创建一个Vue Router实例，用于管理Vue应用中的路由
// 需要先安装vue-router依赖: npm install vue-router

import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
// - createRouter ：用于创建Vue Router实例。
// - createWebHistory ：用于创建基于HTML5 History API的路由模式。
// - RouteRecordRaw ：TypeScript类型，用于定义路由配置对象的类型

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/seek',
    name: 'seek',
    component: () => import('../views/Seek.vue'),
  },
  {
    path: '/index',
    name: 'index',
    component: () => import('../views/index.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue'),
  },
  
]

// - routes 数组定义了应用中的路由，每个路由对象包含路径（ path ）、名称（ name ）和对应的组件（ component ）。
// - 使用动态导入（ import() ）来按需加载组件。


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// - 使用 createRouter 方法创建路由实例。
// - history 属性指定路由模式为 createWebHistory 。
// - routes 属性指定路由配置。


export default router
// 导出创建的路由实例，以便在Vue应用中使用