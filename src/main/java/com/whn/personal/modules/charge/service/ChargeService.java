package com.whn.personal.modules.charge.service;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.charge.domain.Charge;
import com.whn.personal.modules.charge.enums.ChargeType;
import com.whn.personal.modules.charge.enums.TimePatten;
import com.whn.personal.modules.charge.mapper.ChargeMapper;
import com.whn.personal.modules.charge.vo.SearchVo;
import com.whn.waf.config.mybatis.support.PagingHelper;
import com.whn.waf.foundation.exception.WafBizException;
import com.whn.waf.foundation.support.PageableItems;
import com.whn.waf.foundation.support.ParamsBuilder;
import com.whn.waf.foundation.util.id.ObjectId;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class ChargeService {

    @Resource
    private AppContext appContext;

    @Resource
    private ChargeMapper chargeMapper;

    @Transactional(rollbackFor = Exception.class)
    public Object add(Charge charge) {
        if (StringUtils.isBlank(charge.getId())) {
            charge.setUserId(appContext.getUserId());
            charge.setId(ObjectId.id());
            charge.setCreateTime(new Date());
            chargeMapper.insert(charge);
            return charge;
        } else {
            Charge old = chargeMapper.selectByPrimaryKey(charge.getId());
            checkOwner(old);
            old.setAmount(charge.getAmount());
            old.setLabel(charge.getLabel());
            old.setMark(charge.getMark());
            old.setType(charge.getType());
            chargeMapper.updateByPrimaryKey(old);
            return old;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Object delete(String id) {
        Charge charge = chargeMapper.selectByPrimaryKey(id);
        checkOwner(charge);
        chargeMapper.deleteByPrimaryKey(id);
        return charge;
    }

    private void checkOwner(Charge charge) {
        if (!charge.getUserId().equals(appContext.getUserId())) {
            throw WafBizException.of(ErrorCode.DATA_NOT_EXIST);
        }
    }

    @Transactional(readOnly = true)
    public Object search(SearchVo condition) {
        PagingHelper.startPage(condition.getPage(), condition.getSize());
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", appContext.getUserId());
        if (StringUtils.isNotBlank(condition.getYearAndMonth())) {
            params.put("yearAndMonth", condition.getYearAndMonth());
        }
        Page<Charge> result = chargeMapper.selectAll(params);
        return PageableItems.of(result.getResult(), result.getTotal());
    }

    @Transactional(readOnly = true)
    public Object getYearMonth() {
        List<Map> result = Lists.newArrayList();
        String currentYearAndMonth = DateTime.now().toString("yyyy-MM");

        Map<String, Object> map = ParamsBuilder
                .of("userId", appContext.getUserId())
                .withParam("timePatten", TimePatten.yearmonth.getValue())
                .build();
        List<String> list = chargeMapper.selectYearMonth(map);
        if (!list.contains(currentYearAndMonth)) {
            result.add(ParamsBuilder.of("time", currentYearAndMonth).build());
        }
        for (String value : list) {
            result.add(ParamsBuilder.of("time", value).build());
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Object statistics() {
        Map<String, Object> statistics = Maps.newHashMap();

        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", appContext.getUserId());
        map.put("timePatten", TimePatten.year.getValue());
        List<String> years = chargeMapper.selectYearMonth(map);
        for (String year : years) {
            Map<String, Object> yearDatas = Maps.newHashMap();

            params(map, year, ChargeType.disbursements, TimePatten.year.getValue());
            Map<String, Object> res = chargeMapper.statisticsTotal(map);
            BigDecimal disbursementsTotal = (BigDecimal) defaultIfNull(res, "total", BigDecimal.ZERO);
            yearDatas.put(ChargeType.disbursements.name() + "_total", disbursementsTotal);

            List<Map<String, Object>> yearDetails = chargeMapper.statisticsDetails(map);
            Map<String, Object> yearDetailsMap = Maps.newHashMap();
            if (!CollectionUtils.isEmpty(yearDetails)) {
                for (Map<String, Object> deatil : yearDetails) {
                    yearDetailsMap.put((String) deatil.get("label"), deatil.get("total"));
                }
            }
            yearDatas.put(ChargeType.disbursements.name(), yearDetailsMap);

            params(map, year, ChargeType.receipts, TimePatten.year.getValue());
            res = chargeMapper.statisticsTotal(map);
            BigDecimal receiptsTotal = (BigDecimal) defaultIfNull(res, "total", BigDecimal.ZERO);
            yearDatas.put(ChargeType.receipts.name() + "_total", receiptsTotal);

            yearDatas.put("surplus", receiptsTotal.subtract(disbursementsTotal));

            yearDetails = chargeMapper.statisticsDetails(map);
            yearDetailsMap = Maps.newHashMap();
            if (!CollectionUtils.isEmpty(yearDetails)) {
                for (Map<String, Object> deatil : yearDetails) {
                    yearDetailsMap.put((String) deatil.get("label"), deatil.get("total"));
                }
            }
            yearDatas.put(ChargeType.receipts.name(), yearDetailsMap);

            Map<String, Object> details = Maps.newHashMap();
            map = Maps.newHashMap();
            map.put("userId", appContext.getUserId());
            map.put("timePatten", TimePatten.yearmonth.getValue());
            map.put("year", year);
            List<String> monthAndYears = chargeMapper.selectYearMonth(map);
            for (String monthAndYear : monthAndYears) {
                Map<String, Object> monthDates = Maps.newHashMap();

                params(map, monthAndYear, ChargeType.disbursements, TimePatten.yearmonth.getValue());
                res = chargeMapper.statisticsTotal(map);
                disbursementsTotal = (BigDecimal) defaultIfNull(res, "total", BigDecimal.ZERO);
                monthDates.put(ChargeType.disbursements.name() + "_total", disbursementsTotal);

                List<Map<String, Object>> monthDetails = chargeMapper.statisticsDetails(map);
                Map<String, Object> monthDetailsMap = Maps.newHashMap();
                if (!CollectionUtils.isEmpty(monthDetails)) {
                    for (Map<String, Object> detail : monthDetails) {
                        monthDetailsMap.put((String) detail.get("label"), detail.get("total"));
                    }
                }
                monthDates.put(ChargeType.disbursements.name(), monthDetailsMap);

                params(map, monthAndYear, ChargeType.receipts, TimePatten.yearmonth.getValue());
                res = chargeMapper.statisticsTotal(map);
                receiptsTotal = (BigDecimal) defaultIfNull(res, "total", BigDecimal.ZERO);
                monthDates.put(ChargeType.receipts.name() + "_total", receiptsTotal);
                monthDetails = chargeMapper.statisticsDetails(map);
                monthDetailsMap = Maps.newHashMap();
                if (!CollectionUtils.isEmpty(monthDetails)) {
                    for (Map<String, Object> detail : monthDetails) {
                        monthDetailsMap.put((String) detail.get("label"), detail.get("total"));
                    }
                }
                monthDates.put(ChargeType.receipts.name(), monthDetailsMap);

                monthDates.put("surplus", receiptsTotal.subtract(disbursementsTotal));


                details.put(monthAndYear, monthDates);
            }

            yearDatas.put("details", details);

            statistics.put(year, yearDatas);
        }
        return statistics;
    }

    private void params(Map<String, Object> map, String monthAndYear, ChargeType receipts, int value) {
        map.clear();
        map.put("userId", appContext.getUserId());
        map.put("time", monthAndYear);
        map.put("type", receipts);
        map.put("timePatten", value);
    }

    private Object defaultIfNull(Map<String, Object> map, String key, Object defaultValue) {
        if (CollectionUtils.isEmpty(map) || null == map.get(key)) {
            return defaultValue;
        }
        return map.get(key);
    }
}
