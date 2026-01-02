import { createWebHistory, createRouter } from 'vue-router'

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '/',
    component: () => import('@/views/index'),
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/HomePage/index'),
        redirect: '/index/home',
        children: [
          {
            path: 'profile',
            component: () => import('@/views/system/user/profile/index'),
            hidden: true
          },
          {
            path: 'home',
            component: () => import('@/views/HomePage/home'),
            hidden: true,
          },
          {
            path: 'heritage',
            component: () => import('@/views/HomePage/heritage'),
            hidden: true,
          },
          {
            path: 'heritageDetail/:id',
            component: () => import('@/views/HomePage/heritageDetail'),
            hidden: true,
          },
          {
            path: 'news',
            component: () => import('@/views/HomePage/news'),
            hidden: true,
          },
          {
            path: 'newsDetail/:id',
            component: () => import('@/views/HomePage/newsDetail'),
            hidden: true,
          },
          {
            path: 'eventDetail/:id',
            component: () => import('@/views/HomePage/eventDetail'),
            hidden: true,
          },
          {
            path: 'projects',
            component: () => import('@/views/HomePage/projects'),
            hidden: true,
          },
        ],
        hidden: true
      }
    ]
  },
]


const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    return { top: 0 }
  },
})

export default router
