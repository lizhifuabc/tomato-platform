import vue from '@vitejs/plugin-vue'
import { UserConfig, ConfigEnv, loadEnv, defineConfig } from 'vite';
import path from 'path';
const pathSrc = path.resolve(__dirname, 'src');
// https://vitejs.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  const env = loadEnv(mode, process.cwd());
  return {
    plugins: [
      vue()
    ],
  }
})