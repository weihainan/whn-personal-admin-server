package com.whn.personal.modules.charge.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.charge.domain.Charge;
import com.whn.personal.modules.charge.mapper.ChargeMapper;
import com.whn.personal.modules.charge.vo.SearchVo;
import com.whn.waf.common.support.PageableItems;
import com.whn.waf.common.utils.CommonUtil;
import com.whn.waf.common.utils.ObjectId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class ChargeService {

    @Autowired
    private AppContext context;

    @Resource
    private ChargeMapper chargeMapper;

    public Object add(Charge charge) {
        charge.setUserId(context.getUserId());
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

    public Object search(SearchVo condition) {
        PageHelper.startPage((int) condition.getPage(), (int) condition.getSize());
        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(condition.getYearAndMonth())) {
            params.put("yearAndMonth", condition.getYearAndMonth());
        }
        Page<Charge> result = chargeMapper.selectAll(params);
        return PageableItems.of(result.getResult(), result.getTotal(), new Function<Charge, Map<String, Object>>() {
            public Map<String, Object> apply(Charge input) {
                Map<String, Object> map = CommonUtil.toMap(input);
                map.put("create_time", input.getCreateTime().getTime());
                return map;
            }
        });
    }

    public Object getYearMonth() {
        List<String> list = chargeMapper.selectYearMonth(context.getUserId());
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }
}
