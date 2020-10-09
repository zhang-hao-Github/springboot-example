package com.sock.alipayexample.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: @ZhangHao
 * @create: 2020-10-09 22:32
 **/
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private static final String ALIPAYGATEWAY = "https://openapi.alipay.com/gateway.do";
    private static final String SIGN_TYPE = "RSA2";
    private static final String CHARSET = "UTF-8";
    public static final String TIMEOUT_EXPRESS = "60m";
    public static String ALIPAY_PUBLIC_KEY;

    public static String APPID;

    public static String APP_PRIVATE_KEY;

    public void setALIPAY_PUBLIC_KEY(String alipay_public_key) {
        this.ALIPAY_PUBLIC_KEY = alipay_public_key;
    }

    public void setAPPID(String APPID) {
        AliPayConfig.APPID = APPID;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        APP_PRIVATE_KEY = appPrivateKey;
    }

    @Bean
    public AlipayClient alipayClient() {

        AlipayClient alipayClient = new DefaultAlipayClient(ALIPAYGATEWAY, AliPayConfig.APPID,
                AliPayConfig.APP_PRIVATE_KEY, "json", CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        return alipayClient;
    }
}
