package com.whn.personal.modules.admin.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.modules.admin.domain.Admin;
import com.whn.personal.modules.admin.mapper.AdminMapper;
import com.whn.personal.modules.admin.vo.LoginAdminVo;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.utils.CommonUtil;
import com.whn.waf.common.utils.Encrypt;
import com.whn.waf.common.utils.ValidatorUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.whn.personal.internal.constant.ErrorCode.LOGIN_ERROR;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
@Transactional
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

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

    private Cache<String, Admin> adminsCache = CacheBuilder.newBuilder().
            maximumSize(100).expireAfterWrite(3600, TimeUnit.SECONDS).build();

    public Admin getById(final String id) {
        try {
            return adminsCache.get(id, new Callable<Admin>() {
                @Override
                public Admin call() throws Exception {
                    return adminMapper.selectByPrimaryKey(id);
                }
            });
        } catch (ExecutionException e) {
            LOGGER.error("getCaseStandardLaborTime error", e);
        }
        return null;
    }

    public Object valid(String token) {
        Map<String, String> result = Maps.newHashMap();
        result.put("state", "ok");
        String[] tokens = token.split("\\-", 2);
        Admin admin = this.getById(tokens[0]);
        if (admin == null || !admin.getToken().equals(tokens[1]) || DateTime.now().getMillis() >= admin.getExpireTime()) {
            result.put("state", "expired");
        }
        return result;
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

//    Cron表达式的格式：秒 分 时 日 月 周 年(可选)。
//    字段名                 允许的值                   允许的特殊字符
//    秒                       0-59                    , - * /
//    分                       0-59                    , - * /
//    小时                     0-23                    , - * /
//    日                       1-31                    , - * ? / L W C
//    月                       1-12 or JAN-DEC         , - * /
//    周几                     1-7 or SUN-SAT          , - * ? / L C #
//    年 (可选字段)            empty, 1970-2099         , - * /
//
//
//    “?”字符：表示不确定的值
//    “,”字符：指定数个值
//    “-”字符：指定一个值的范围
//    “/”字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m
//    “L”字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期X
//    “W”字符：指定离给定日期最近的工作日(周一到周五)
//    “#”字符：表示该月第几个周X。6#3表示该月第3个周五

//    每隔5秒执行一次：*/5 * * * * ?
//    每隔1分钟执行一次：0 */1 * * * ?
//    每天23点执行一次：0 0 23 * * ?
//    每天凌晨1点执行一次：0 0 1 * * ?
//    每月1号凌晨1点执行一次：0 0 1 1 * ?
//    每月最后一天23点执行一次：0 0 23 L * ?
//    每周星期天凌晨1点实行一次：0 0 1 ? * L
//    在26分、29分、33分执行一次：0 26,29,33 * * * ?
//    每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
}
