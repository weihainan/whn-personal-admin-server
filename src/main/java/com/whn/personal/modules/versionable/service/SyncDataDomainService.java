package com.whn.personal.modules.versionable.service;

import com.whn.personal.modules.versionable.domian.SyncDataDomain;
import com.whn.personal.modules.versionable.repository.SyncDataDomainRepository;
import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.base.service.BaseService;
import com.whn.waf.common.context.Module;
import com.whn.waf.common.exception.WafBizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/15
 */
@Service
public class SyncDataDomainService extends BaseService<SyncDataDomain, String> {


    @Autowired
    private SyncDataDomainRepository syncDataDomainRepository;

    @Override
    protected Module module() {
        return new Module("SYNC_DATA");
    }

    public SyncDataDomain sync(SyncDataDomain syncData) {
        if (syncData == null) {
            throw WafBizException.of(ErrorCode.INVALID_ARGUMENT);
        }

        // lock

        SyncDataDomain serverData = null;

        serverData = lock(syncData.getSourceId());

        if (syncData.getVersion() == 0) {//新增
            if (StringUtils.isBlank(syncData.getSourceId())) {
                syncData.setSourceId(UUID.randomUUID().toString());
            }
        } else {//更新

        }


        //unlock

        return syncDataDomainRepository.save(syncData);
    }


    public SyncDataDomain lock(String sourceId) {
        SyncDataDomain serverData = syncDataDomainRepository.findBySourceId(sourceId);
        if (null == serverData) {
            serverData = new SyncDataDomain();
        }
        return serverData;
    }
}
