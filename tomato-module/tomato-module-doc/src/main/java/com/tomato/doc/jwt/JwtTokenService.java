package com.tomato.doc.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.jwk.RSAKey;
import java.text.ParseException;


public interface  JwtTokenService {

    /**
     * 使用HMAC算法生成TOken
     * @param payloadStr
     * @param secret
     * @return
     */
    String generateTokenByHMAC(String payloadStr,String secret) throws JOSEException;

    /**
     * 使用HMAC验证Token
     * @param token
     * @param secret
     * @return
     */
    PayloadDto verifyTokenByHMAC(String token,String secret) throws ParseException, JOSEException;

    /**
     * 使用RSA算法生成Token
     * @param payloadStr
     * @param rsaKey
     * @return
     */
    String generateTokenByRSA(String payloadStr,RSAKey rsaKey) throws JOSEException;

    /**
     * 使用RSA算法验证token
     * @param token
     * @param rsaKey
     * @return
     */
    PayloadDto verifyTokenByRSA(String token,RSAKey rsaKey) throws ParseException, JOSEException;


    /**
     * 获取默认的payload
     * @return
     */
    PayloadDto getDefaultPayloadDto();

    /**
     * 获取默认的RSAKey
     */
    RSAKey getDefaultRSAKey();
}


