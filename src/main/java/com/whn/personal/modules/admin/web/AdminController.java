package com.whn.personal.modules.admin.web;

import com.whn.personal.modules.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;
}
