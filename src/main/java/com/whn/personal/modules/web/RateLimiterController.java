package com.whn.personal.modules.web;

import com.google.common.util.concurrent.RateLimiter;
import com.whn.personal.internal.constant.GuestApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/18
 */
@GuestApi
@RestController
@RequestMapping(value = "/v0.1/rate_limiter")
public class RateLimiterController {

    private int miaosha = 10;
    private int timeout = 1000;

    private volatile int count = 0;

    RateLimiter rateLimiter = RateLimiter.create(1);

    @RequestMapping("/miaosha")
    public Object miaosha() {
        System.out.println("等待时间" + rateLimiter.acquire());
        if (count < miaosha) {
            count++;
            return "购买成功";
        }
        return "购买失败";
    }

    /**
     * tryAcquire(long timeout, TimeUnit unit)
     * 从RateLimiter 获取许可如果该许可可以在不超过timeout的时间内获取得到的话，
     * 或者如果无法在timeout 过期之前获取得到许可的话，那么立即返回false（无需等待）
     */
    @RequestMapping("/buy")
    public Object miao() {
        //判断能否在1秒内得到令牌，如果不能则立即返回false，不会阻塞程序
        if (!rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
            System.out.println("短期无法获取令牌，真不幸，排队也瞎排");
            return "失败";
        }
        if (count > miaosha) {
            System.out.println("购买成功");
            return "成功";
        }
        System.out.println("数据不足，失败");
        return "失败";
    }

}
