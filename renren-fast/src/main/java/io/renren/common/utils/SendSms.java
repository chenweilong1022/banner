package io.renren.common.utils;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

public class SendSms {

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

//    public static void main(String[] args) {
//        System.out.println(RandomUtil.randomNumbers(6));
//    }

    public static void send(String code) {

        //必填,请参考"开发准备"获取如下数据,替换为实际值
        String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP接入地址+接口访问URI
        String appKey = "Ep9l6AcZ3tnlQrV81sWX35MHWMNE"; //APP_Key
        String appSecret = "6Na6lkLcX9NgmXJ3bFX2FWoBaNUG"; //APP_Secret
        String sender = "csms18122401"; //签名通道号
        String templateId = "a1e361c900bc40689a8e041c2e70d70f"; //模板ID

        //条件必填,当templateId指定的模板类型为通用模板时生效且必填,必须是已审核通过的,与模板类型一致的签名名称
        String signature = "香蕉充值"; //签名名称

        //必填,全局号码格式(包含国家码),示例:+8615123456789,多个号码之间用英文逗号分隔
        String receiver = "+8617638155659"; //短信接收人号码

        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "";

       /**
         * 选填,使用无变量模板时请赋空值 String templateParas = "";
         * 单变量模板示例:模板内容为"您的验证码是${NUM_6}"时,templateParas可填写为"[\"369751\"]"
         * 双变量模板示例:模板内容为"您有${NUM_2}件快递请到${TXT_32}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
         * 查看更多模板变量规则:常见问题>业务规则>短信模板内容审核标准
         */
        String templateParas = "[\""+code+"\"]"; //模板变量

        //请求Body,普通模板,使用如下代码
        //String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack);
        //请求Body,通用模板,使用如下代码
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        System.out.println("body is " + body);

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        System.out.println("wsse header is " + wsseHeader);

        //如果JDK版本低于1.8,可使用如下代码
        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        //CloseableHttpClient client = HttpClients.custom()
        //        .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
        //            @Override
        //            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        //                return true;
        //            }
        //        }).build()).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

        //如果JDK版本是1.8,可使用如下代码
        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        CloseableHttpClient client = null;
        try {
            client = HttpClients.custom()
                        .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                                (x509CertChain, authType) -> true).build())
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = client.execute(RequestBuilder.create("POST")//请求方法POST
                        .setUri(url)
                        .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                        .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                        .addHeader("X-WSSE", wsseHeader)
                        .setEntity(new StringEntity(body)).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(response.toString());
//        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    /**
     * 构造请求Body体 for 普通模板
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallbackUrl
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl) {

        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));

        //如果JDK版本是1.6,可使用:URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
        return URLEncodedUtils.format(keyValues, StandardCharsets.UTF_8);
    }
    /**
     * 构造请求Body体 for 通用模板
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallbackUrl
     * @param signature
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl, String signature) {

        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
        keyValues.add(new BasicNameValuePair("signature", signature));

        //如果JDK版本是1.6,可使用:URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
        return URLEncodedUtils.format(keyValues, StandardCharsets.UTF_8);
    }

    /**
     * 构造X-WSSE参数值
     * @param appKey
     * @param appSecret
     * @return
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);
        String passwordDigestBase64Str = Base64.encodeBase64String(hexDigest.getBytes(Charset.forName("utf-8")));
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }
}