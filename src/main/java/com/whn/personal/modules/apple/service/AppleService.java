package com.whn.personal.modules.apple.service;

import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.apple.domain.Apple;
import com.whn.personal.modules.apple.mapper.ApplesMapper;
import com.whn.waf.foundation.exception.WafBizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/8/1.
 */
@Service
public class AppleService {

    @Resource
    private AppContext appContext;

    @Resource
    private ApplesMapper applesMapper;

    @Transactional(readOnly = true)
    public List<Apple> selectList() {
        return applesMapper.selectList(appContext.getUserId());
    }

    @Transactional(rollbackFor = Exception.class)
    public Apple add() {
        Apple apple = new Apple();
        apple.setWeight(getWeightOnBase(65, 200));
        apple.setIsEaten(false);
        apple.setUserId(appContext.getUserId());
        apple.setCreateTime(new Date());
        applesMapper.insert(apple);
        return apple;
    }

    private int getWeightOnBase(int weight, int base) {
        return new Random().nextInt(weight) + base;
    }

    @Transactional(rollbackFor = Exception.class)
    public Apple eat(int id) {
        Apple apple = applesMapper.selectByPrimaryKey(id);
        if (apple == null) {
            throw WafBizException.of(ErrorCode.DATA_NOT_EXIST);
        }
        if (!appContext.getUserId().equals(apple.getUserId())) {
            throw WafBizException.of(ErrorCode.DATA_NOT_EXIST);
        }
        apple.setIsEaten(true);
        applesMapper.updateByPrimaryKey(apple);
        return apple;
    }
}
