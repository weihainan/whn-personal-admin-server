package com.whn.personal.modules.assignment.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.assignment.domain.Assignment;
import com.whn.personal.modules.assignment.dto.ListDto;
import com.whn.personal.modules.assignment.mapper.AssignmentMapper;
import com.whn.waf.common.support.PageableItems;
import com.whn.waf.common.utils.CommonUtil;
import com.whn.waf.common.utils.ObjectId;
import com.whn.waf.common.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private AppContext context;

    public Assignment add(Assignment assignment) {
        ValidatorUtil.validateAndThrow(assignment);
        assignment.setUserId(context.getUserId());
        assignment.setCreateTime(new Date());
        assignment.setCompleted(false);
        assignment.setId(ObjectId.ID());
        assignmentMapper.insert(assignment);
        return assignment;
    }

    public Assignment completed(String id) {
        Assignment assignment = assignmentMapper.selectByPrimaryKey(id);
        assignment.setCompleted(true);
        assignmentMapper.insert(assignment);
        return assignment;
    }

    public Assignment delete(String id) {
        Assignment assignment = assignmentMapper.selectByPrimaryKey(id);
        assignmentMapper.deleteByPrimaryKey(id);
        return assignment;
    }

    public Object list(ListDto dto) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", context.getUserId());
        PageHelper.startPage((int) dto.getPage(), (int) dto.getSize());
        Page<Assignment> page = assignmentMapper.select(params);
        return PageableItems.of(page.getResult(), page.getTotal());
    }
}
