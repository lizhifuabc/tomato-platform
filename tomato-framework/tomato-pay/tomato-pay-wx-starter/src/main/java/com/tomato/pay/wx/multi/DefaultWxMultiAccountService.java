package com.tomato.pay.wx.multi;

import com.tomato.pay.wx.multi.domain.WxMultiAccount;
import com.tomato.pay.wx.properties.WxPayProperties;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.security.*;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 默认从配置文件加载数据
 *
 * @author lizhifu
 * @since 2023/7/28
 */
@AllArgsConstructor
public class DefaultWxMultiAccountService implements WxMultiAccountService {
    /**
     * 证书别名
     */
    private static final String CERT_ALIAS = "Tenpay Certificate";
    /**
     * PKCS12
     */
    private static final KeyStore PKCS12_KEY_STORE;
    static {
        try {
            PKCS12_KEY_STORE = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 配置文件信息
     */
    private final WxPayProperties wxPayProperties;
    /**
     * 资源加载器
     */
    private final ResourceLoader resourceLoader;
    /**
     * 存储商户配置信息
     */
    private final Set<WxMultiAccount> cache = new HashSet<>();
    @Override
    public Set<WxMultiAccount> load() {
        // 缓存有数据，直接返回
        if (!CollectionUtils.isEmpty(cache)) {
            return cache;
        }
        Map<String, WxPayProperties.V3> v3Map = wxPayProperties.getV3();
        Set<WxMultiAccount> data = v3Map.entrySet().stream()
                .map(entity->{
                    WxPayProperties.V3 v3 = entity.getValue();
                    String certPath = v3.getCertPath();
                    String certAbsolutePath = v3.getCertAbsolutePath();
                    // 优先使用绝对路径,如果没有则使用相对路径,如果都没有则抛出异常
                    Resource resource = Optional.ofNullable(certAbsolutePath)
                            .<Resource>map(FileSystemResource::new)
                            .orElseGet(() -> resourceLoader.getResource(
                                    Optional.ofNullable(certPath)
                                            .filter(path -> !path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX))
                                            .map(path -> ResourceUtils.CLASSPATH_URL_PREFIX + path)
                                            .orElseThrow(() -> new IllegalArgumentException("微信支付证书路径不能为空"))));
                    WxMultiAccount wxMultiAccount = wxMultiAccount(resource, v3.getMchId());
                    wxMultiAccount.setMultiAccountId(entity.getKey());
                    wxMultiAccount.setV3(v3);
                    return wxMultiAccount;
                })
                .collect(Collectors.toSet());
        cache.addAll(data);
        return cache;
    }

    /**
     * 获取公私钥
     *
     * @param resource resource 资源
     * @param keyPass  keyPass 商户号
     * @return WxMultiAccount
     */
    private WxMultiAccount wxMultiAccount(Resource resource,String keyPass) {
        char[] pem = keyPass.toCharArray();
        try {
            PKCS12_KEY_STORE.load(resource.getInputStream(), pem);
            X509Certificate certificate = (X509Certificate) PKCS12_KEY_STORE.getCertificate(CERT_ALIAS);
            certificate.checkValidity();
            String serialNumber = certificate.getSerialNumber().toString(16).toUpperCase();
            PublicKey publicKey = certificate.getPublicKey();
            PrivateKey storeKey = (PrivateKey) PKCS12_KEY_STORE.getKey(CERT_ALIAS, pem);
            WxMultiAccount wxMultiAccount = new WxMultiAccount();
            wxMultiAccount.setKeyPair(new KeyPair(publicKey, storeKey));
            wxMultiAccount.setSerialNumber(serialNumber);
            return wxMultiAccount;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
