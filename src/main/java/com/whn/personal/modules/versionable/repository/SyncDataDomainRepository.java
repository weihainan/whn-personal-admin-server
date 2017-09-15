package com.whn.personal.modules.versionable.repository;

import com.whn.personal.modules.versionable.domian.SyncDataDomain;
import com.whn.waf.common.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/15
 */
@Repository
public interface SyncDataDomainRepository extends BaseRepository<SyncDataDomain, String> {

    SyncDataDomain findBySourceId(String sourceId);
}
