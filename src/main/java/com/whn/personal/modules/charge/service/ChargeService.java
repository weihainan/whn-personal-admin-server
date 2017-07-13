package com.whn.personal.modules.charge.service;

import com.whn.personal.modules.charge.domain.Charge;
import com.whn.personal.modules.charge.mapper.ChargeMapper;
import com.whn.waf.common.utils.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class ChargeService {

    @Resource
    private ChargeMapper chargeMapper;

    public Object add(Charge charge) {
        charge.setId(ObjectId.id());
        charge.setCreateTime(new Date());
        chargeMapper.insert(charge);
        return charge;
    }

    public Object delete(String id) {
        Charge charge = chargeMapper.selectByPrimaryKey(id);
        chargeMapper.deleteByPrimaryKey(id);
        return charge;
    }
}
