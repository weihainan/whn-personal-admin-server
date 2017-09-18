package com.whn.test;

import com.google.common.collect.Lists;
import com.whn.waf.common.utils.Encrypt;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyMainTest {

    @Test
    public void test1(){

        System.out.println( new String(Base64Utils.decodeFromUrlSafeString("%20eq%20")));

        String format = String.format("%s%%0%dd", "ss",2);
        System.out.println(format); // ss%02d
        System.out.println(String.format(format, 9)); // ss09

        Map<String,String> map = new HashMap<>();
        map.put("1","1");
        map.put("2","3");

    }

    @Test
    public void fuhao(){ // 左移1位 x·2
        System.out.println(1 << 0); // 1  state1
        System.out.println(1 << 1); // 2  state2
        System.out.println(1 << 2); // 4  state3

        System.out.println(1 | 2); // 3   state 1 2
        System.out.println(1 | 4); // 5   state 1 3
        System.out.println(2 | 4); // 6   state 2 3
    }

    @Test
    public void test(){
        String s = "%s--%s";  // 不会改变原来的字符串
        System.out.println(s);
        System.out.println(String.format(s,"1","2"));
        System.out.println(s);

        System.out.println(String.format("%05d", 1));
        System.out.println(String.format("%05d", 11));
        System.out.println(String.format("%05d", 111));
        System.out.println(String.format("%05d", 1111));
        System.out.println(String.format("%05d", 11111));
        System.out.println(String.format("%05d", 111111));
    }

    @Test
    public void encrypt() throws Exception {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String str = Encrypt.SHA256("12233"); // 64
        System.out.println(str);
        str = Encrypt.SHA256("12233");
        System.out.println(str);

        str = Encrypt.SHA512("12233"); // 128
        System.out.println(str);
        str = Encrypt.SHA512("12233");
        System.out.println(str);

        str = Encrypt.MD5("12233"); // 32
        System.out.println(str);
        str = Encrypt.MD5("12233");
        System.out.println(str.length());

        stopWatch.stop();
        System.out.println(stopWatch.getTime());
    }

    @Test
    public void list2Array() {
        List<String> list = Arrays.asList("1", "2", "3");  // 返回的是Arrays的内部类  底层还是数组  不要做修改 否则抛出异常
        System.out.println(list);

        //list.remove("1");  java.lang.UnsupportedOperationException

        List<String> strs = Lists.newArrayList("1", "2", "3");
        String[] strArray = new String[strs.size()];  // 传入数组大小不够 创建新的数组返回
        strArray = strs.toArray(strArray);
        System.out.println(Arrays.toString(strArray));
    }

    @Test
    public void testSwitch() {
        // char byte short int Character Byte Short Integer String enum
        switch (1) {
            case 1:
                System.out.println(1);
                break;
            default:
                break;
        }
    }


    /**
     * AtomicInteger，一个提供原子操作的Integer的类
     * 来看AtomicInteger提供的接口。
     * <p>
     * //获取当前的值
     * public final int get()
     * <p>
     * //取当前的值，并设置新的值
     * public final int getAndSet(int newValue)
     * <p>
     * //获取当前的值，并自增
     * public final int getAndIncrement()
     * <p>
     * //获取当前的值，并自减
     * public final int getAndDecrement()
     * <p>
     * //获取当前的值，并加上预期的值
     * public final int getAndAdd(int delta)
     */
    @Test
    public void atomicIntegerDemo() { // 0 0 5 5 6
        AtomicInteger ai = new AtomicInteger(0); // 线程安全的int
        int i1 = ai.get();
        v(i1);
        int i2 = ai.getAndSet(5);
        v(i2);
        int i3 = ai.get();
        v(i3);
        int i4 = ai.getAndIncrement();
        v(i4);
        v(ai.get());
    }

    static void v(int i) {
        System.out.println("i : " + i);
    }

}
