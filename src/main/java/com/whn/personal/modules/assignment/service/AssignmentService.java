package com.whn.personal.modules.assignment.service;

import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.whn.personal.internal.support.AppContext;
import com.whn.personal.modules.assignment.domain.Assignment;
import com.whn.personal.modules.assignment.dto.ListDto;
import com.whn.personal.modules.assignment.mapper.AssignmentMapper;
import com.whn.waf.config.mybatis.support.PagingHelper;
import com.whn.waf.foundation.support.PageableItems;
import com.whn.waf.foundation.util.ValidatorUtil;
import com.whn.waf.foundation.util.id.ObjectId;
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

    @Transactional(rollbackFor = Exception.class)
    public Assignment add(Assignment assignment) {
        ValidatorUtil.validateAndThrow(assignment);
        assignment.setUserId(appContext.getUserId());
        assignment.setCreateTime(new Date());
        assignment.setCompleted(false);
        assignment.setId(ObjectId.ID());
        assignmentMapper.insert(assignment);
        return assignment;
    }

    @Transactional(rollbackFor = Exception.class)
    public Assignment completed(String id) {
        Assignment assignment = assignmentMapper.selectByPrimaryKey(id);
        assignment.setCompleted(true);
        assignmentMapper.updateByPrimaryKey(assignment);
        return assignment;
    }

    @Transactional(rollbackFor = Exception.class)
    public Assignment delete(String id) {
        Assignment assignment = assignmentMapper.selectByPrimaryKey(id);
        assignmentMapper.deleteByPrimaryKey(id);
        return assignment;
    }

    @Transactional(readOnly = true)
    public Object list(ListDto dto) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", appContext.getUserId());
        PagingHelper.startPage(dto.getPage(), dto.getSize());
        Page<Assignment> page = assignmentMapper.select(params);
        return PageableItems.of(page.getResult(), page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
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
