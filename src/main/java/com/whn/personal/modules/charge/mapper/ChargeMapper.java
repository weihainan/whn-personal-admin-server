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

    List<String> selectYearMonth(String userId);
}