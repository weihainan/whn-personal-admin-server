package com.whn.personal.modules.assignment.web;

import com.whn.personal.modules.assignment.domain.Assignment;
import com.whn.personal.modules.assignment.dto.ListDto;
import com.whn.personal.modules.assignment.service.AssignmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/assignments")
public class AssignmentController {

    @Resource
    private AssignmentService assignmentService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody Assignment assignment) {
        return assignmentService.add(assignment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable String id) {
        return assignmentService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Object completed(@PathVariable String id) {
        return assignmentService.completed(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object list(ListDto dto) {
        return assignmentService.list(dto);
    }

    @RequestMapping(value = "/complete_all")
    public Object completeAll() {
        return assignmentService.completeAll();
    }

    @RequestMapping(value = "/delete_all_completed")
    public Object deleteAllCompleted() {
        return assignmentService.deleteAllCompleted();
    }
}
