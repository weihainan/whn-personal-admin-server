package com.whn.personal.modules.charge.mapper;


import com.github.pagehelper.Page;
import com.whn.personal.modules.charge.domain.Charge;

import java.util.List;
import java.util.Map;

public interface ChargeMapper {

    int deleteByPrimaryKey(String id);

    int insert(Charge record);

    int insertSelective(Charge record);

    Charge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Charge record);

    int updateByPrimaryKey(Charge record);

    Page<Charge> selectAll(Map<String, Object> params);

    /**
     * userId 必填
     * timePatten 4 代表获取年 2017   7获取年月 2017-01
     * year 指定年份获取年月
     * <p>
     * {time:2017}
     * {time:2017-01}
     */
    List<String> selectYearMonth(Map<String, Object> params);

    Map<String, Object> statisticsTotal(Map<String, Object> params);

    List<Map<String, Object>> statisticsDetails(Map<String, Object> params);
}