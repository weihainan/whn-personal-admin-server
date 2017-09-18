package com.whn.test;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RateLimiter是guava提供的基于令牌桶算法的实现类，可以非常简单的完成限流特技，并且根据系统的实际情况来调整生成token的速率。
 * 通常可应用于抢购限流防止冲垮系统；限制某接口、服务单位时间内的访问量，譬如一些第三方服务会对用户访问量进行限制；限制网速，单位时间内只允许上传下载多少字节等。
 * 该例子是多个线程依次执行，限制每2秒最多执行一个。
 *
 * @author weihainan
 * @since 0.1 created on 2017/9/18
 */
public class RateLimiterDemo {

    public static void main(String[] args) {
        //0.5代表一秒最多多少个 -- 2秒1个
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new UserRequest(i));
        }
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (Runnable runnable : tasks) {
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);
        }
    }

    private static class UserRequest implements Runnable {
        private int id;

        public UserRequest(int id) {
            this.id = id;
        }

        public void run() {
            System.out.println(id);
        }
    }
}

/*
等待时间：0.0
0
等待时间：1.993366
1
等待时间：1.980993
2
等待时间：1.999779
3
等待时间：1.999724
4
等待时间：1.999804
5
等待时间：1.999783
6
等待时间：1.999721
7
等待时间：1.999677
8
等待时间：1.99983
9
 */