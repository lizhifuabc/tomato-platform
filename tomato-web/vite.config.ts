import vue from '@vitejs/plugin-vue'
import { UserConfig, ConfigEnv, loadEnv, defineConfig } from 'vite';
import path from 'path';

import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';

import Icons from 'unplugin-icons/vite';
import IconsResolver from 'unplugin-icons/resolver';

const pathSrc = path.resolve(__dirname, 'src');
// https://vitejs.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  const env = loadEnv(mode, process.cwd());
  return {
    plugins: [
      vue(),
      AutoImport({
        // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
        imports: ['vue', '@vueuse/core'],
        eslintrc: {
          enabled: false, //  Default `false`
          filepath: './.eslintrc-auto-import.json', // Default `./.eslintrc-auto-import.json`
          globalsPropValue: true // Default `true`, (true | false | 'readonly' | 'readable' | 'writable' | 'writeable')
        },
        resolvers: [
          // 自动导入 Element Plus 相关函数，如：ElMessage, ElMessageBox... (带样式)
          ElementPlusResolver(),
          // 自动导入图标组件
          IconsResolver({})
        ],
        vueTemplate: true, // 是否在 vue 模板中自动导入
        dts: path.resolve(pathSrc, 'types', 'auto-imports.d.ts') //  自动导入组件类型声明文件位置，默认根目录; false 关闭自动生成
      }),

      Components({
        resolvers: [
          // 自动注册图标组件
          IconsResolver({
            enabledCollections: ['ep'] //@iconify-json/ep 是 Element Plus 的图标库
          }),
          // 自动导入 Element Plus 组件
          ElementPlusResolver()
        ],
        dts: path.resolve(pathSrc, 'types', 'components.d.ts') //  自动导入组件类型声明文件位置，默认根目录; false 关闭自动生成
      }),

    ],
  }
})