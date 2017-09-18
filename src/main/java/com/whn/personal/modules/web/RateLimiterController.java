package com.whn.personal.modules.web;

import com.google.common.util.concurrent.RateLimiter;
import com.whn.personal.internal.constant.GuestApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/18
 */
@GuestApi
@RestController
@RequestMapping(value = "/v0.1/rate_limiter")
public class RateLimiterController {

    private volatile int count = 0;

    RateLimiter rateLimiter = RateLimiter.create(1);

    @RequestMapping("/miaosha")
    public Object miaosha() {
        System.out.println("等待时间" + rateLimiter.acquire());
        if (count < 10) {
            count++;
            return "购买成功";
        }
        return "购买失败";
    }
}
