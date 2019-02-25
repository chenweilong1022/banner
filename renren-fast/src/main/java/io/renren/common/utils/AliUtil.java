package io.renren.common.utils;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.http.UriVariables;
import io.renren.config.AliConfig;
import io.renren.config.WechatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class AliUtil {

    private static  AliConfig aliConfig = SpringUtils.getBean(AliConfig.class);

    private static final AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
    private static final HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
    private static final AliPayService service;
    private static PayOrder payOrder = new PayOrder();
    static {
        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
//        aliPayConfigStorage.setPid(AlipayConfig.appid);
        aliPayConfigStorage.setAppId(aliConfig.getAppId());
        aliPayConfigStorage.setKeyPublic(aliConfig.getKeyPublic());
        aliPayConfigStorage.setKeyPrivate(aliConfig.getKeyPrivate());
        aliPayConfigStorage.setNotifyUrl(aliConfig.getNotifyUrl());
        aliPayConfigStorage.setKeyPrivateCertPwd(aliConfig.getKeyPrivateCertPwd());
//        aliPayConfigStorage.setReturnUrl("同步回调地址");
        aliPayConfigStorage.setSignType("RSA2");
//        aliPayConfigStorage.setSeller("收款账号");
        aliPayConfigStorage.setInputCharset("utf-8");
        //是否为测试账号，沙箱环境
        aliPayConfigStorage.setTest(false);
        //最大连接数
        httpConfigStorage.setMaxTotal(20);
        //默认的每个路由的最大连接数
        httpConfigStorage.setDefaultMaxPerRoute(10);
        service = new AliPayService(aliPayConfigStorage,httpConfigStorage);
    }

    public static String appOrder(String body, String orderId, BigDecimal price) {
        PayOrder payOrder = new PayOrder();
        payOrder.setSubject(body);
        payOrder.setBody("");
        payOrder.setPrice(price);
        payOrder.setOutTradeNo(orderId);
        payOrder.setTransactionType(AliTransactionType.APP);

//        String order = UriVariables.getMapToParameters(service.orderInfo(payOrder));
//        Map<String, Object> order = service.orderInfo(payOrder);

//        AliPayConfigStorage payConfigStorage = service.getPayConfigStorage();
        String order = UriVariables.getMapToParameters(service.orderInfo(payOrder));
        return order;
    }

    public static String payBack(HttpServletRequest request) throws IOException {
        //获取支付方返回的对应参数
        Map<String, Object> params = service.getParameter2Map(request.getParameterMap(), request.getInputStream());
        if (null == params){
            return null;
        }
        //校验
        if (service.verify(params)){
            //这里处理业务逻辑
            return  params.get("out_trade_no").toString();
        }
        return null;
    }

    public static void main(String[] args) {
//        String order = appOrder("aa", UUID.randomUUID().toString().replace("-", ""), BigDecimal.valueOf(0.01));
//        System.out.println(order);
    }

}
