package com.whn.personal.modules.charge.service;

import com.github.pagehelper.Page;
import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.modules.charge.domain.ChargeLabel;
import com.whn.personal.modules.charge.mapper.ChargeLabelMapper;
import com.whn.waf.config.mybatis.support.PagingHelper;
import com.whn.waf.foundation.exception.WafBizException;
import com.whn.waf.foundation.support.PageableItems;
import com.whn.waf.foundation.support.SimpleItems;
import com.whn.waf.foundation.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class ChargeLabelService {
    @Resource
    private ChargeLabelMapper chargeLabelMapper;

    @Transactional(rollbackFor = Exception.class)
    public Object add(ChargeLabel label) {
        label.setCreateTime(new Date());
        label.setId(DigestUtils.md5Hex(String.format("%s", label.getName())));

        ChargeLabel oldOne = chargeLabelMapper.selectByPrimaryKey(label.getId());
        if (oldOne != null) {
            throw WafBizException.of(ErrorCode.DATA_CONFLICT);
        }
        chargeLabelMapper.insert(label);
        allLabelCache = null;
        return label;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object delete(String id) {
        ChargeLabel oldOne = chargeLabelMapper.selectByPrimaryKey(id);
        if (oldOne == null) {
            throw WafBizException.of(ErrorCode.DATA_NOT_EXIST);
        }
        chargeLabelMapper.deleteByPrimaryKey(id);
        allLabelCache = null;
        return oldOne;
    }

    private List<ChargeLabel> allLabelCache = null;

    @Transactional(readOnly = true)
    public Object selectAll(int page, int size, boolean all) {
        if (CommonUtil.isNotEmpty(allLabelCache)) {
            return SimpleItems.of(allLabelCache);
        }
        if (!all) {
            PagingHelper.startPage(page, size);
            Page<ChargeLabel> result = chargeLabelMapper.selectAll();
            return PageableItems.of(result.getResult(), result.getTotal());
        } else {
            Page<ChargeLabel> result = chargeLabelMapper.selectAll();
            allLabelCache = result.getResult();
            return SimpleItems.of(result.getResult());
        }
    }
}
