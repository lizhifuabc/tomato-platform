<!--
  * 首页 用户头部信息
  *
-->
<template>
  <div class="user-header">
    <a-page-header :title="welcomeSentence" :sub-title="departmentName" >
      <template #tags>
        <a-tag color="blue">努力工作</a-tag>
        <a-tag color="success">主动 / 皮实 / 可靠 </a-tag>
        <a-tag color="error">自省 / 精进 / 创新</a-tag>
      </template>
      <template #extra>
        <p>{{ dayInfo }}</p>
      </template>
      <a-row class="content">
        <span class="heart-sentence">
          <h3>{{ heartSentence }}</h3>
          <p class="last-login-info">{{ lastLoginInfo }}</p>
          <div></div>
        </span>
        <div class="weather">
          <iframe
            width="100%"
            scrolling="no"
            height="60"
            frameborder="0"
            allowtransparency="true"
            src="//i.tianqi.com/index.php?c=code&id=12&icon=1&num=5&site=12"
          ></iframe>
        </div>
      </a-row>
    </a-page-header>
  </div>
</template>
<script setup>
  import { computed } from 'vue';
  import { useUserStore } from '/@/store/modules/system/user';
  import uaparser from 'ua-parser-js';
  import { Solar, Lunar } from 'lunar-javascript';
  import _ from 'lodash';

  const userStore = useUserStore();

  const departmentName = computed(() => useUserStore.departmentName);

  // 欢迎语
  const welcomeSentence = computed(() => {
    let sentence = '';
    let now = new Date().getHours();
    if (now > 0 && now <= 6) {
      sentence = '午夜好，';
    } else if (now > 6 && now <= 11) {
      sentence = '早上好，';
    } else if (now > 11 && now <= 14) {
      sentence = '中午好，';
    } else if (now > 14 && now <= 18) {
      sentence = '下午好，';
    } else {
      sentence = '晚上好，';
    }
    return sentence + userStore.$state.actualName;
  });

  //上次登录信息
  const lastLoginInfo = computed(() => {
    let info = '';
    if (userStore.$state.lastLoginTime) {
      info = info + '上次登录:' + userStore.$state.lastLoginTime;
    }
    if (userStore.$state.lastLoginIp) {
      info = info + '; IP:' + userStore.$state.lastLoginIp;
    }
    if (userStore.$state.lastLoginUserAgent) {
      let ua = uaparser(userStore.$state.lastLoginUserAgent);
      info = info + '; 设备:';
      if (ua.browser.name) {
        info = info + ' ' + ua.browser.name;
      }
      if (ua.os.name) {
        info = info + ' ' + ua.os.name;
      }
      let device = ua.device.vendor ? ua.device.vendor + ua.device.model : null;
      if (device) {
        info = info + ' ' + device;
      }
    }
    return info;
  });

  //日期、节日、节气
  const dayInfo = computed(() => {
    //阳历
    let solar = Solar.fromDate(new Date());
    let day = solar.toString();
    let week = solar.getWeekInChinese();
    //阴历
    let lunar = Lunar.fromDate(new Date());
    let lunarMonth = lunar.getMonthInChinese();
    let lunarDay = lunar.getDayInChinese();
    //节气
    let jieqi = lunar.getPrevJieQi().getName();
    let next = lunar.getNextJieQi();
    let nextJieqi = next.getName() + ' ' + next.getSolar().toYmd();

    return `${day} 星期${week}，农历${lunarMonth}${lunarDay}（当前${jieqi}，${nextJieqi} ）`;
  });

  // 毒鸡汤
  const heartSentenceArray = [
    '毒鸡汤',
  ];
  const heartSentence = computed(() => {
    return heartSentenceArray[_.random(0, heartSentenceArray.length - 1)];
  });
</script>
<style scoped lang="less">
  .user-header {
    width: 100%;
    background-color: #fff;
    margin-bottom: 10px;

    .heart-sentence {
      width: calc(100% - 500px);
      h3 {
        color: rgba(0, 0, 0, 0.75);
      }
    }

    .content {
      display: flex;
      justify-content: space-between;
      .weather {
        width: 440px;
      }
    }

    .last-login-info {
      font-size: 13px;
      color: rgba(0, 0, 0, 0.45);
      overflow-wrap: break-word;
    }
  }
</style>
