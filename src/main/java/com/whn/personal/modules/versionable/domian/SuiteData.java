package com.whn.personal.modules.versionable.domian;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/15
 */
@Document(collection = "suite_datas")
public class SuiteData extends SyncDataDomain {
}
