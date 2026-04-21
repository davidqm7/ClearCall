package dev.revature.clearcall.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@PrimaryKeyClass
public class CallByCategoryKey implements Serializable {

    @PrimaryKeyColumn(name = "call_category", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String callCategory;

    @PrimaryKeyColumn(name = "call_date",  type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.DESCENDING)
    private LocalDate callDate;

    @PrimaryKeyColumn(name = "start_time", type = PrimaryKeyType.CLUSTERED, ordinal = 2, ordering = Ordering.DESCENDING)
    private Instant startTime;

    @PrimaryKeyColumn(name = "call_id", type = PrimaryKeyType.CLUSTERED, ordinal = 3)
    private UUID callId;

    public CallByCategoryKey(){}

    public CallByCategoryKey(String callCategory, LocalDate callDate, Instant startTime, UUID callId) {
        this.callCategory = callCategory;
        this.callDate = callDate;
        this.startTime = startTime;
        this.callId = callId;
    }

    public String getCallCategory() {
        return callCategory;
    }

    public void setCallCategory(String callCategory) {
        this.callCategory = callCategory;
    }

    public LocalDate getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
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
        if (o == null || getClass() != o.getClass()) return false;
        CallByCategoryKey that = (CallByCategoryKey) o;
        return Objects.equals(callCategory, that.callCategory) && Objects.equals(callDate, that.callDate) && Objects.equals(startTime, that.startTime) && Objects.equals(callId, that.callId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callCategory, callDate, startTime, callId);
    }
}
