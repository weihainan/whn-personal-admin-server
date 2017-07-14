package com.whn.personal.modules.admin.service;

import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.modules.admin.domain.Admin;
import com.whn.personal.modules.admin.mapper.AdminMapper;
import com.whn.personal.modules.admin.vo.LoginAdminVo;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.utils.CommonUtil;
import com.whn.waf.common.utils.Encrypt;
import com.whn.waf.common.utils.ValidatorUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static com.whn.personal.internal.constant.ErrorCode.LOGIN_ERROR;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Object add(Admin admin) {

        ValidatorUtil.validateAndThrow(admin);
        if (adminMapper.selectByPrimaryKey(admin.getId()) != null) {
            throw WafBizException.of(ErrorCode.DATA_CONFLICT);
        }
        admin.setPassword(Encrypt.SHA256(admin.getPassword()));
        admin.setCreateTime(new Date());
        refreshToken(admin);
        adminMapper.insert(admin);
        return admin;
    }

    private void refreshToken(Admin admin) {
        admin.setToken(CommonUtil.uuid());
        admin.setExpireTime(DateTime.now().dayOfMonth().addToCopy(7).getMillis());
    }


    public Admin login(LoginAdminVo loginAdminVo) {
        ValidatorUtil.validateAndThrow(loginAdminVo);
        Admin admin = getById(loginAdminVo.getId());
        String password = Encrypt.SHA256(loginAdminVo.getPassword());
        if (admin == null || !password.equals(admin.getPassword())) {
            throw WafBizException.of(LOGIN_ERROR);
        }
        admin.setLastLoginTime(new Date());
        refreshToken(admin);
        adminMapper.updateByPrimaryKey(admin);
        return admin;
    }

    public Admin getById(String id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 每天0点更新过期的token
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    public void refreshAdminToken() {
        List<Admin> adminList = adminMapper.selectExpired(DateTime.now().getMillis());
        if (!CollectionUtils.isEmpty(adminList)) {
            for (int i = 0; i < adminList.size(); i++) {
                Admin admin = adminList.get(i);
                refreshToken(admin);
                adminMapper.updateByPrimaryKey(admin);
            }
        }
    }
}
