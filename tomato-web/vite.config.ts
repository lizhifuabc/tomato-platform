import { defineConfig, loadEnv, ConfigEnv, UserConfig } from "vite";
import vue from '@vitejs/plugin-vue'
import { resolve } from "path";

// https://vitejs.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  return {
    plugins: [vue()],
  }
})
