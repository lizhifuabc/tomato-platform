import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
export const Layout = () => import('@/layout/index.vue');
// 静态路由
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/redirect',
    component: Layout,
    meta: { hidden: true },
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    meta: { hidden: true }
  },
 ]

/**
 * 创建路由
 */
const router = createRouter({
    history: createWebHashHistory(),
    routes: constantRoutes as RouteRecordRaw[],
    // 刷新时，滚动条位置还原
    scrollBehavior: () => ({ left: 0, top: 0 })
  });
  
  /**
   * 重置路由
   */
  export function resetRouter() {
    router.replace({ path: '/login' });
    location.reload();
  }
  
  export default router;