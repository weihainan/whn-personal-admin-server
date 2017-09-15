package com.whn.personal.modules.versionable.domian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whn.waf.common.base.domain.BaseDomain;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author weihainan
 * @since 0.1 created on 2017/9/15
 */
@Document(collection = "sync_datas")
public class SyncDataDomain extends BaseDomain<String> {

    public static final int STATUS_NORMAL = 1 << 0; //正常状态  1
    public static final int STATUS_DELETED = 1 << 1; //已删除的 2
    public static final int STATUS_WAITE_MIGRATE = 1 << 2; //待转移的 4 5 6

    private String sourceId;
    private String data;

    private int state = 1;

    private int innerState = STATUS_NORMAL;

    /**
     * 版本号
     */
    private long version = 0;

    /**
     * 是否锁定,0表示未锁定，大于0表示锁定
     */
    @JsonIgnore
    @Field(value = "_lock")
    private int _lock = 0;

    /**
     * 锁定时，为锁定时间
     * 非锁定时，为最后一次解锁时间
     */
    @JsonIgnore
    @Field(value = "_lock_times")
    private long[] _lockTimes = new long[]{};

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public int get_lock() {
        return _lock;
    }

    public void set_lock(int _lock) {
        this._lock = _lock;
    }

    public long[] get_lockTimes() {
        return _lockTimes;
    }

    public void set_lockTimes(long[] _lockTimes) {
        this._lockTimes = _lockTimes;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
