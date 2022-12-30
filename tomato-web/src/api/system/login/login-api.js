/*
 *  登录 api
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const loginApi = {
  /**
   * 登录 
   * @param param
   */
  login: (param) => {
    return postRequest('/login', param);
  },

  /**
   * 退出登录 
   * @param param
   */
  logout: () => {
    return getRequest('/login/logout');
  },

  /**
   * 获取验证码 
   * @param param
   */
  getCaptcha: () => {
    return getRequest('/captcha/getCaptcha');
  },

  /**
   * 获取登录信息 
   * @param param
   */
  getLoginInfo: () => {
    return getRequest('/login/getLoginInfo');
  },

  /**
   * 刷新用户信息（包含用户基础信息、权限信息等等）   
   */
  refresh: () => {
    return getRequest('/login/refresh');
  },
};
