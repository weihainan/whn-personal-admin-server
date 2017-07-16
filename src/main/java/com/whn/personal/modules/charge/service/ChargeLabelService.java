package com.whn.personal.modules.charge.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.modules.charge.domain.ChargeLabel;
import com.whn.personal.modules.charge.mapper.ChargeLabelMapper;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.support.PageableItems;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class ChargeLabelService {
    @Autowired
    private ChargeLabelMapper chargeLabelMapper;

    public Object add(ChargeLabel label) {
        label.setCreateTime(new Date());
        label.setId(DigestUtils.md5Hex(String.format("%s", label.getName())));

        ChargeLabel oldOne = chargeLabelMapper.selectByPrimaryKey(label.getId());
        if (oldOne != null) {
            throw WafBizException.of(ErrorCode.DATA_CONFLICT);
        }
        chargeLabelMapper.insert(label);
        return label;
    }

    public Object delete(String id) {
        ChargeLabel oldOne = chargeLabelMapper.selectByPrimaryKey(id);
        if (oldOne == null) {
            throw WafBizException.of(ErrorCode.DATA_NOT_EXIST);
        }
        chargeLabelMapper.deleteByPrimaryKey(id);
        return oldOne;
    }

    public Object selectAll(int page, int size) {
        PageHelper.startPage(page, size);
        Page<ChargeLabel> result = chargeLabelMapper.selectAll();
        return PageableItems.of(result.getResult(), result.getTotal());
    }
}
