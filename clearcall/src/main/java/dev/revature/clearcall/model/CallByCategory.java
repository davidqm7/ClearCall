package dev.revature.clearcall.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("calls_by_category")
public class CallByCategory {

    @PrimaryKey
    private CallByCategoryKey key;

    @Column("duration_sec")
    private Integer durationSec;

    @Column("ivr_contained")
    private Boolean ivrContained;

    public CallByCategory() {
    }

    public CallByCategory(CallByCategoryKey key, Integer durationSec, Boolean ivrContained) {
        this.key = key;
        this.durationSec = durationSec;
        this.ivrContained = ivrContained;
    }

    public CallByCategoryKey getKey() {
        return key;
    }

    public void setKey(CallByCategoryKey key) {
        this.key = key;
    }

    public Integer getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(Integer durationSec) {
        this.durationSec = durationSec;
    }

    public Boolean getIvrContained() {
        return ivrContained;
    }

    public void setIvrContained(Boolean ivrContained) {
        this.ivrContained = ivrContained;
    }
}