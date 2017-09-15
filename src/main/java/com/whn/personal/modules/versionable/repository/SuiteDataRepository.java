package com.whn.personal.modules.versionable.repository;

import com.whn.personal.modules.versionable.domian.SuiteData;
import com.whn.waf.common.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/15
 */
@Repository
public interface SuiteDataRepository extends BaseRepository<SuiteData, String> {
}
