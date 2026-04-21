package dev.revature.clearcall.model;


import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table("calls_by_date")
public class CallByDate {

    @PrimaryKey
    private CallByDateKey key;

    @Column("call_category")
    private String callCategory;

    @Column("ivr_contained")
    private Boolean ivrContained;

    @Column("escalated")
    private Boolean escalated;

    @Column("agent_id")
    private String agentId;

    @Column("duration_sec")
    private int durationSec;

    public CallByDate() {}

    public CallByDate(CallByDateKey key, String call_category, Boolean ivr_contained, Boolean escalated, String agentId, int durationSec) {
        this.key = key;
        this.callCategory = call_category;
        this.ivrContained = ivr_contained;
        this.escalated = escalated;
        this.agentId = agentId;
        this.durationSec = durationSec;
    }

    public CallByDateKey getKey() {
        return key;
    }

    public void setKey(CallByDateKey key) {
        this.key = key;
    }

    public String getCallCategory() {
        return callCategory;
    }

    public void setCallCategory(String callCategory) {
        this.callCategory = callCategory;
    }

    public Boolean getIvrContained() {
        return ivrContained;
    }

    public void setIvrContained(Boolean ivrContained) {
        this.ivrContained = ivrContained;
    }

    public Boolean getEscalated() {
        return escalated;
    }

    public void setEscalated(Boolean escalated) {
        this.escalated = escalated;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }


}
