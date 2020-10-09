package com.sock.alipayexample.service.imp;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sock.alipayexample.config.AliPayConfig;
import com.sock.alipayexample.entity.PayParams;
import com.sock.alipayexample.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: springboot-example
 * @author: @ZhangHao
 * @create: 2020-10-09 23:03
 **/
@Service
public class AliPayServiceImp implements AliPayService {

    @Autowired
    private AlipayClient alipayClient;

    /**
     * 生成交易二维码
     *
     * @param payParams
     * @return
     */
    @Override
    public String tradePrecreate(PayParams payParams) {
        if (StringUtils.isEmpty(payParams.getOut_trade_no()) || StringUtils.isEmpty(payParams.getSubject()) || StringUtils.isEmpty(payParams.getTotal_amount()))
            throw new RuntimeException("异常参数");

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        JSONObject params = JSONUtil.createObj();
        params.set("out_trade_no", payParams.getOut_trade_no());
        params.set("total_amount", payParams.getTotal_amount());
        params.set("subject", payParams.getSubject());
        params.set("timeout_express", AliPayConfig.TIMEOUT_EXPRESS);

        request.setBizContent(params.toString());
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
//            AlipayResponse alipayResponse = alipayClient.pageExecute();
            //获取到支付二维码
            String qrCode = response.getQrCode();
            return qrCode;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String tradeQuery(String out_trade_no) {

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        JSONObject params = JSONUtil.createObj();
        params.set("out_trade_no", out_trade_no);

        request.setBizContent(params.toString());

        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            String body = response.getBody();
            // * 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
            String tradeStatus = response.getTradeStatus();
            // 具体业务具体分析
            return body;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return null;

    }
}
