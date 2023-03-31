import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
// 静态路由
export const constantRoutes: RouteRecordRaw[] = [
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