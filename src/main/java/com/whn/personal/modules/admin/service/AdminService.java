package com.whn.personal.modules.admin.service;

import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.modules.admin.domain.Admin;
import com.whn.personal.modules.admin.mapper.AdminMapper;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.utils.CommonUtil;
import com.whn.waf.common.utils.Encrypt;
import com.whn.waf.common.utils.ValidatorUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Object add(@RequestBody Admin admin) {

        ValidatorUtil.validateAndThrow(admin);
        if (adminMapper.selectByPrimaryKey(admin.getId()) != null) {
            throw WafBizException.of(ErrorCode.DATA_CONFLICT);
        }
        admin.setPassword(Encrypt.SHA256(admin.getPassword()));
        admin.setCreateTime(new Date());
        admin.setToken(CommonUtil.uuid());
        admin.setExpireTime(DateTime.now().dayOfMonth().addToCopy(7).getMillis());
        return adminMapper.insert(admin);
    }


    public Admin getById(String id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
