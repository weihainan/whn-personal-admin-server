package com.whn.personal.modules.versionable.service;

import com.whn.personal.modules.versionable.domian.SuiteData;
import com.whn.waf.common.base.service.BaseService;
import com.whn.waf.common.context.Module;
import org.springframework.stereotype.Service;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/15
 */
@Service
public class SuiteDataService extends BaseService<SuiteData, String> {

    @Override
    protected Module module() {
        return new Module("SUITE_DATA");
    }


}
