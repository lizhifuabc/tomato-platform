package com.tomato.merchant.manager;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 加密算法
 * 加密算法使用“AES/CBC/PKCS5Padding”
 *
 * @author lizhifu
 * @since  2022/12/13
 */
@Service
public class MerchantSecurityManager {
    /**
     * phone(手机号)加密方式，加密类型：phone，后四位进行加密存储，模糊查询时，后四位进行加密查询
     * <p>手机号:18967038927</p>
     * <p>支持搜索密文:$7AnwZJ1e6BZc$AAAAAADkt0hgZkt6KLklIDxVp+F1wzHsRsPUw0s19fk=$2$$</p>
     * <p>后八位手机号:67038927</p>
     * <p>67038927查询摘要:$ 7AnwZJ1e6BZc$</p>
     * @param phone 手机号
     * @return 加密后内容
     */
    public String phone(String phone){
        return security(phone.substring(phone.length() - 4));
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("18810869700".getBytes()));
    }

    /**
     * TODO 加密算法
     * @param source 加密内容
     * @return 加密后内容
     */
    public String security(String source){
        return DigestUtils.md5DigestAsHex(source.getBytes());
    }
}
