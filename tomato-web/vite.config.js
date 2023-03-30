// vite配置 环境变量通常可以从 process.env 获得
import { defineConfig , loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
// https://cn.vitejs.dev/config/
export default defineConfig(({ mode, command }) => {
  const env = loadEnv(mode, process.cwd())
  const { VITE_APP_ENV } = env
  return {
    // 配置选项
    plugins: [vue()],
  }
})
