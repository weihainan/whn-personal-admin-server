package com.whn.personal.modules.admin.web;

import com.google.common.collect.Maps;
import com.whn.personal.internal.constant.GuestApi;
import com.whn.personal.modules.admin.domain.Admin;
import com.whn.personal.modules.admin.service.AdminService;
import com.whn.personal.modules.admin.vo.LoginAdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GuestApi
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginAdminVo loginAdminVo) {
        return adminService.login(loginAdminVo);
    }

    @GuestApi
    @RequestMapping(value = "/valid/{token}", method = RequestMethod.GET)
    public Object login(@PathVariable String token) {
        return adminService.valid(token);
    }

    @Autowired
    private TaskExecutor taskExecutor;


    @GuestApi
    @RequestMapping(value = "/refresh/{userId}", method = RequestMethod.GET)
    public Object refreshToken(@PathVariable String userId) {
        Map<String, Object> result = Maps.newHashMap();
        if (!"214372".equals(userId)) {
            result.put("result", "not auth");
            return result;
        }
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                adminService.refreshExpiredToken();
            }
        });
        result.put("result", "success");
        return result;
    }
}

