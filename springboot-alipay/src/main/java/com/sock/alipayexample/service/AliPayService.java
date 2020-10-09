package com.sock.alipayexample.service;

import com.sock.alipayexample.entity.PayParams;

/**
 * @program: springboot-example
 * @author: @ZhangHao
 * @create: 2020-10-09 23:02
 **/
public interface AliPayService {


    String tradePrecreate(PayParams payParams);

    String tradeQuery(String out_trade_no);
}
