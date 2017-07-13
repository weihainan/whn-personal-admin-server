package com.whn.personal.modules.assignment.service;

import com.whn.personal.modules.assignment.mapper.AssignmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */

@Service
public class AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;
}
