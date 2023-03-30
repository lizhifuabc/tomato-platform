
module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true,
  },
  parser: 'vue-eslint-parser',
   //  https://eslint.vuejs.org/user-guide/#bundle-configurations
  extends: [
    'eslint:recommended',
    'plugin:vue/vue3-essential',
    'plugin:@typescript-eslint/recommended',
    "./.eslintrc-auto-import.json"
  ],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    parser: '@typescript-eslint/parser',
  },
  plugins: ['vue', '@typescript-eslint'],
  rules: {
    'vue/multi-word-component-names': 'off',
    '@typescript-eslint/no-empty-function': 'off', // 关闭空方法检查
    '@typescript-eslint/no-explicit-any': 'off', // 关闭any类型的警告
    'vue/no-v-model-argument': 'off'
  },
  // https://eslint.org/docs/latest/use/configure/language-options#specifying-globals
  globals: {
    DialogOption: 'readonly',
    OptionType:'readonly'
  }
};
