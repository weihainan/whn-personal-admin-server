package com.whn.personal.modules.assignment.service;

import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.internal.support.PageHelperUtils;
import com.whn.personal.modules.assignment.domain.Assignment;
import com.whn.personal.modules.assignment.dto.ListDto;
import com.whn.personal.modules.assignment.mapper.AssignmentMapper;
import com.whn.waf.common.support.PageableItems;
import com.whn.waf.common.utils.ObjectId;
import com.whn.waf.common.utils.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */

@Service
public class AssignmentService {

    @Resource
    private AssignmentMapper assignmentMapper;

    @Resource
    private AppContext appContext;

    @Transactional
    public Assignment add(Assignment assignment) {
        ValidatorUtil.validateAndThrow(assignment);
        assignment.setUserId(appContext.getUserId());
        assignment.setCreateTime(new Date());
        assignment.setCompleted(false);
        assignment.setId(ObjectId.ID());
        assignmentMapper.insert(assignment);
        return assignment;
    }

    @Transactional
    public Assignment completed(String id) {
        Assignment assignment = assignmentMapper.selectByPrimaryKey(id);
        assignment.setCompleted(true);
        assignmentMapper.updateByPrimaryKey(assignment);
        return assignment;
    }

    @Transactional
    public Assignment delete(String id) {
        Assignment assignment = assignmentMapper.selectByPrimaryKey(id);
        assignmentMapper.deleteByPrimaryKey(id);
        return assignment;
    }

    @Transactional(readOnly = true)
    public Object list(ListDto dto) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", appContext.getUserId());
        PageHelperUtils.paging(dto.getPage(), dto.getSize());
        Page<Assignment> page = assignmentMapper.select(params);
        return PageableItems.of(page.getResult(), page.getTotal());
    }

    @Transactional
    public Object completeAll() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", appContext.getUserId());
        params.put("completed", false);
        List<Assignment> assignmentList = assignmentMapper.select(params).getResult();
        for (Assignment assignment : assignmentList) {
            assignment.setCompleted(true);
            assignmentMapper.updateByPrimaryKey(assignment);
        }
        return PageableItems.of(assignmentList, assignmentList.size());
    }

    @Transactional
    public Object deleteAllCompleted() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", appContext.getUserId());
        params.put("completed", true);
        List<Assignment> assignmentList = assignmentMapper.select(params).getResult();
        for (Assignment assignment : assignmentList) {
            assignmentMapper.deleteByPrimaryKey(assignment.getId());
        }
        return PageableItems.of(assignmentList, assignmentList.size());
    }
}
