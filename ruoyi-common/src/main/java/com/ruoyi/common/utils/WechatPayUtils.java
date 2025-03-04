package com.ruoyi.common.utils;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.h5.H5Service;
import com.wechat.pay.java.service.payments.app.AppService;
import com.wechat.pay.java.service.payments.app.model.Amount;
import com.wechat.pay.java.service.payments.app.model.PrepayRequest;
import com.wechat.pay.java.service.payments.app.model.PrepayResponse;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;

public class WechatPayUtils {


    /** Appid */
    public static String appId = "190000****";
    /** 商户号 */
    public static String merchantId = "190000****";
    /** 商户API私钥路径 */
    public static String privateKeyPath = "/Users/yourname/your/path/apiclient_key.pem";
    /** 商户证书序列号 */
    public static String merchantSerialNumber = "5157F09EFDC096DE15EBE81A47057A72********";
    /** 商户APIV3密钥 */
    public static String apiV3key = "...";

    public static String notifyUrl = "";

    private static Config getConfig(){
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3key)
                .build();
    }

    public static AppService getAppService() {
        return new AppService.Builder().config(getConfig()).build();
    }

    public static H5Service getH5Service() {
        return new H5Service.Builder().config(getConfig()).build();
    }

    public static JsapiService getJsapiService() {
        return new JsapiService.Builder().config(getConfig()).build();
    }

    public static NativePayService getNativePayService() {
        return new NativePayService.Builder().config(getConfig()).build();
    }

    public static String appPay(Integer total, String outTradeNo, String description){
        AppService appService = WechatPayUtils.getAppService();
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(total);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription(description);
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(outTradeNo);
        appService.prepay(request);
        // 调用下单方法，得到应答
        PrepayResponse response = appService.prepay(request);
        // 生成prepay_id
        System.out.println(response.getPrepayId());
        return response.getPrepayId();
    }

    public static String h5Pay(Integer total, String outTradeNo, String description){
        H5Service h5Service = WechatPayUtils.getH5Service();
        com.wechat.pay.java.service.payments.h5.model.PrepayRequest request = new com.wechat.pay.java.service.payments.h5.model.PrepayRequest();
        com.wechat.pay.java.service.payments.h5.model.Amount amount = new com.wechat.pay.java.service.payments.h5.model.Amount();
        amount.setTotal(total);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription(description);
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(outTradeNo);
        h5Service.prepay(request);
        // 调用下单方法，得到应答
        com.wechat.pay.java.service.payments.h5.model.PrepayResponse response = h5Service.prepay(request);
        // 生成prepay_id
        System.out.println(response.getH5Url());
        return response.getH5Url();
    }

    public static String jsAPIPay(Integer total, String outTradeNo, String description){
        JsapiService jsapiService = WechatPayUtils.getJsapiService();
        com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest request = new com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest();
        com.wechat.pay.java.service.payments.jsapi.model.Amount amount = new com.wechat.pay.java.service.payments.jsapi.model.Amount();
        amount.setTotal(total);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription(description);
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(outTradeNo);
        jsapiService.prepay(request);
        // 调用下单方法，得到应答
        com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse response = jsapiService.prepay(request);
        // 生成prepay_id
        System.out.println(response.getPrepayId());
        return response.getPrepayId();
    }

    public static String nativePay(Integer total, String outTradeNo, String description){
        NativePayService nativePayService = WechatPayUtils.getNativePayService();
        com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest request = new com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest();
        com.wechat.pay.java.service.payments.nativepay.model.Amount amount = new com.wechat.pay.java.service.payments.nativepay.model.Amount();
        amount.setTotal(total);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription(description);
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(outTradeNo);
        nativePayService.prepay(request);
        // 调用下单方法，得到应答
        com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse response = nativePayService.prepay(request);
        // 生成prepay_id
        System.out.println(response.getCodeUrl());
        return response.getCodeUrl();
    }

}
