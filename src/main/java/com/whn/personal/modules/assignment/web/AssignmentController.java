package com.whn.personal.modules.assignment.web;

import com.whn.personal.modules.assignment.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
}
