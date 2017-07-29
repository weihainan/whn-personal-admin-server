package com.whn.personal.modules.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.internal.constant.GustApi;
import com.whn.waf.common.exception.WafBizException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author Administrator.
 * @since 0.1 created on 2017/7/29 0029.
 */
@GustApi
@RestController
@RequestMapping(value = "/v0.1")
public class WebController {

    List<Apple> list = Lists.newArrayList();
    int id = 0;

    @RequestMapping(value = "/apples", method = RequestMethod.GET)
    public Object apples() {
        return list;
    }

    @RequestMapping(value = "/apples", method = RequestMethod.POST)
    public Object get() {
        Apple apple = new Apple();
        apple.setId(id++);
        apple.setWeight(new Random().nextInt(50) + 200);
        apple.setEaten(false);
        list.add(apple);
        return apple;
    }

    @RequestMapping(value = "/apples/{id}", method = RequestMethod.POST)
    public Object eaten(@PathVariable int id) {
        Apple apple = null;
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                apple = list.get(i);
                apple.setEaten(true);
            }
        }
        return apple;
    }
}


class Apple {
    private int id;
    private int weight;

    @JsonProperty(value = "isEaten")
    private boolean eaten;

    private String myTest;

    public String getMyTest() {
        return myTest;
    }

    public void setMyTest(String myTest) {
        this.myTest = myTest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}