package dev.revature.clearcall.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;

@PrimaryKeyClass
public class CallByAgentKey implements Serializable {

    @PrimaryKeyColumn(name = "agent_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String agentId;

    @PrimaryKeyColumn(name = "start_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime startTime;

    @PrimaryKeyColumn(name = "call_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID callId;

    public CallByAgentKey() {
    }

    public CallByAgentKey(String agentId, LocalDateTime startTime, UUID callId) {
        this.agentId = agentId;
        this.startTime = startTime;
        this.callId = callId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public UUID getCallId() {
        return callId;
    }

    public void setCallId(UUID callId) {
        this.callId = callId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CallByAgentKey))
            return false;
        CallByAgentKey that = (CallByAgentKey) o;
        return Objects.equals(agentId, that.agentId)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(callId, that.callId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agentId, startTime, callId);
    }
}