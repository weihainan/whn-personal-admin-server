package com.whn.personal.modules.charge.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.charge.domain.Charge;
import com.whn.personal.modules.charge.enums.ChargeType;
import com.whn.personal.modules.charge.enums.TimePatten;
import com.whn.personal.modules.charge.mapper.ChargeMapper;
import com.whn.personal.modules.charge.vo.SearchVo;
import com.whn.waf.common.support.PageableItems;
import com.whn.waf.common.support.ParamBuilder;
import com.whn.waf.common.utils.ObjectId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
@Transactional
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
        return PageableItems.of(result.getResult(), result.getTotal());
    }

    public Object getYearMonth() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", context.getUserId());
        map.put("timePatten", 7);
        List<String> list = chargeMapper.selectYearMonth(map);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        List<Map> result = Lists.newArrayList();
        for (String value : list) {
            result.add(ParamBuilder.of("time", value).build());
        }
        return result;
    }

    public Object statistics() {
        Map<String, Object> statistics = Maps.newHashMap();

        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", context.getUserId());
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
            map.put("userId", context.getUserId());
            map.put("timePatten", TimePatten.yearmonth.getValue());
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
        map.put("userId", context.getUserId());
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
