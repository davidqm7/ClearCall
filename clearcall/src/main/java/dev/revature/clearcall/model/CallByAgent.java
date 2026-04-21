package dev.revature.clearcall.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("calls_by_agent")
public class CallByAgent {

    @PrimaryKey
    private CallByAgentKey key;
    @Column("call_category")
    private String callCategory;
    @Column("duration_sec")
    private Integer durationSec;

    public CallByAgent() {
    }

    public CallByAgent(CallByAgentKey key, String callCategory, Integer durationSec) {
        this.key = key;
        this.callCategory = callCategory;
        this.durationSec = durationSec;
    }

    public CallByAgentKey getKey() {
        return key;
    }

    public void setKey(CallByAgentKey key) {
        this.key = key;
    }

    public String getCallCategory() {
        return callCategory;
    }

    public void setCallCategory(String callCategory) {
        this.callCategory = callCategory;
    }

    public Integer getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(Integer durationSec) {
        this.durationSec = durationSec;
    }
}