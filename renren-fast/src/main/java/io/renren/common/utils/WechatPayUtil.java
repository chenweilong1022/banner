package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.api.WxPayConfigStorage;
import com.egzosn.pay.wx.api.WxPayService;
import com.egzosn.pay.wx.bean.WxTransactionType;
import io.renren.config.AliConfig;
import io.renren.config.WechatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;


public class WechatPayUtil {


    private static  WechatConfig wechatConfig = SpringUtils.getBean(WechatConfig.class);

    private static final WxPayConfigStorage WX_PAY_CONFIG_STORAGE = new WxPayConfigStorage();//微信配置类
    private static WxPayService service;//
    private static PayOrder payOrder = new PayOrder();//订单配置
    static {
        WX_PAY_CONFIG_STORAGE.setMchId(wechatConfig.getMchId());
        WX_PAY_CONFIG_STORAGE.setAppid(wechatConfig.getAppid());
        WX_PAY_CONFIG_STORAGE.setSecretKey(wechatConfig.getSecretKey());
        WX_PAY_CONFIG_STORAGE.setNotifyUrl(wechatConfig.getNotifyUrl());
//        wxPayConfigStorage.setReturnUrl("同步回调地址");
        WX_PAY_CONFIG_STORAGE.setSignType("MD5");
        WX_PAY_CONFIG_STORAGE.setInputCharset("utf-8");
        service = new WxPayService(WX_PAY_CONFIG_STORAGE);
    }

    /**
     * app支付工具类
     * @param body
     * @param orderId
     * @param price
     * @return
     */
    public static Map appOrder(String body,String orderId,BigDecimal price) {
        payOrder.setBody("");
        payOrder.setSubject(body);
        payOrder.setPrice(price);
        payOrder.setOutTradeNo(orderId);
        payOrder.setTransactionType(WxTransactionType.APP);
//        JSONObject order = service.unifiedOrder(payOrder);
        Map<String, Object> map = service.orderInfo(payOrder);
        return map;
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
//        String order = appOrder("支付", UUID.randomUUID().toString().replace("-", ""), BigDecimal.valueOf(0.01));
//        System.out.println(order);

//        Map map = appOrder("aa", RandomStringUtil.generateString(10), BigDecimal.valueOf(0.01));
//        System.out.println(map);
    }

}
