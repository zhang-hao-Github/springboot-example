package com.sock.alipayexample.controller;

import com.sock.alipayexample.config.AliPayConfig;
import com.sock.alipayexample.entity.PayParams;
import com.sock.alipayexample.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-example
 * @author: @ZhangHao
 * @create: 2020-10-09 22:18
 **/
@RestController
public class AlipayController {

    @Autowired
    private AliPayService aliPayService;

    /**
     * @description:
     * @author: zhanghao
     * @date: 2020/10/9 22:29
     * @param: PayParams  生成交易二维码的参数
     */
    @PostMapping("/tradePay")
    public String tradePay(@RequestBody PayParams payParams) {

        String qrCode = aliPayService.tradePrecreate(payParams);
        return qrCode;
    }

    @PostMapping("/tradeQuery")
    public String tradeQuery(@RequestParam("tradeNo") String tradeNo) {

        String result = aliPayService.tradeQuery(tradeNo);

        return result;
    }

}
