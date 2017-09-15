package com.whn.personal.modules.versionable.web;

import com.whn.personal.modules.versionable.domian.SyncDataDomain;
import com.whn.personal.modules.versionable.service.SyncDataDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/15
 */
@RestController
@RequestMapping("/v0.1/sync/data")
public class SyncDataController {

    @Autowired
    private SyncDataDomainService syncDataDomainService;

    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public Object sync(@RequestBody SyncDataDomain syncData) {
        return syncDataDomainService.sync(syncData);
    }
}
