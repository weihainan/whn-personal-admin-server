package com.whn.personal.modules.admin.service;

import com.whn.personal.modules.admin.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;
}
