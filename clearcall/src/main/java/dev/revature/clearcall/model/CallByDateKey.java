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
public class CallByDateKey implements Serializable {

    @PrimaryKeyColumn(name = "call_date", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private LocalDate callDate;

    @PrimaryKeyColumn(name = "start_time", type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.DESCENDING)
    private Instant startTime;

    @PrimaryKeyColumn(name = "call_id", type = PrimaryKeyType.CLUSTERED, ordinal = 2)
    private UUID callId;

    public CallByDateKey() {}

    public CallByDateKey(LocalDate call_date, Instant startTime, UUID callId) {
        this.callDate = call_date;
        this.startTime = startTime;
        this.callId = callId;
    }

    public LocalDate getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }

    public UUID getCallId() {
        return callId;
    }

    public void setCallId(UUID callId) {
        this.callId = callId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CallByDateKey that = (CallByDateKey) o;
        return Objects.equals(callDate, that.callDate) && Objects.equals(startTime, that.startTime) && Objects.equals(callId, that.callId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callDate, startTime, callId);
    }
}
