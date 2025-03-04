package com.ruoyi.wechatpay;

import com.ruoyi.common.utils.WechatPayUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    public String appPay(String description, Integer totalFee, String outTradeNo) {
        return WechatPayUtils.appPay(totalFee, outTradeNo, description);
    }

}
