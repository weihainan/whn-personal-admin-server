package com.whn.personal.modules.apple.service;

import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.apple.domain.Apple;
import com.whn.personal.modules.apple.mapper.ApplesMapper;
import com.whn.waf.common.exception.WafBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/8/1.
 */
@Service
@Transactional
public class AppleService {

    @Autowired
    private AppContext context;

    @Autowired
    private ApplesMapper applesMapper;

    public List<Apple> selectList() {
        return applesMapper.selectList(context.getUserId());
    }

    public Apple add() {
        Apple apple = new Apple();
        apple.setWeight(new Random().nextInt(65) + 200);
        apple.setIsEaten(false);
        apple.setUserId(context.getUserId());
        apple.setCreateTime(new Date());
        applesMapper.insert(apple);
        return apple;
    }

    public Apple eat(int id) {
        Apple apple = applesMapper.selectByPrimaryKey(id);
        if (apple == null) {
            throw WafBizException.of(ErrorCode.DATA_NOT_EXIST);
        }
        if (!context.getUserId().equals(apple.getUserId())) {
            throw WafBizException.of(ErrorCode.DATA_NOT_EXIST);
        }
        apple.setIsEaten(true);
        applesMapper.updateByPrimaryKey(apple);
        return apple;
    }
}
