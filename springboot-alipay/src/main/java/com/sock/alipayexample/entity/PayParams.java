package com.sock.alipayexample.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-09-30 22:32
 **/
public class PayParams {


    private String out_trade_no; //订单号

    private String subject;  //标题

    private String total_amount;//订单金额

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

}
