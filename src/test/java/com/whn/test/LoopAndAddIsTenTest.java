package com.whn.test;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/11.
 */
public class LoopAndAddIsTenTest {

    // 一个数组A = [1, 9, 3, 8, 4, 7, 0, 6]，如何获取两两相加等于10的那些组合？要求性能最好
    // 当前的数字只和他后面的运算 因为前面的已经算过一次
    @Test
    public void add() {

        int[] nums = new int[]{1, 9, 3, 8, 4, 6, 7, 0};
        System.out.println(Arrays.toString(nums));
        int index = 0; // 当前加数下标
        int cursor = 1; // 被加数下标
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        do {
            for (; cursor != nums.length; ++cursor) {
                if (nums[index] + nums[cursor] == 10) {
                    System.out.println(String.format("Find: %s + %s = 10", nums[index], nums[cursor]));
                }
            }
            cursor = 1 + index++;
        } while (index != nums.length);
        stopWatch.stop();
        System.out.println(String.format("Total consumes %s nanoseconds:", stopWatch.getNanoTime()));
    }
}
