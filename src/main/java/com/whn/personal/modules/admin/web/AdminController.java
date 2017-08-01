package com.whn.personal.modules.admin.web;

import com.whn.personal.internal.constant.GustApi;
import com.whn.personal.modules.admin.domain.Admin;
import com.whn.personal.modules.admin.service.AdminService;
import com.whn.personal.modules.admin.vo.LoginAdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody Admin admin) {
        return adminService.add(admin);
    }

    @GustApi
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginAdminVo loginAdminVo) {
        return adminService.login(loginAdminVo);
    }

    @GustApi
    @RequestMapping(value = "/valid/{token}", method = RequestMethod.POST)
    public Object login(@PathVariable String token) {
        return adminService.valid(token);
    }
}

