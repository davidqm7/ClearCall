package dev.revature.clearcall.repository;

import dev.revature.clearcall.model.CallByDate;
import dev.revature.clearcall.model.CallByDateKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CallByDateRepository extends CassandraRepository<CallByDate, CallByDateKey> {
    List<CallByDate> findByKeyCallDate(LocalDate date);

}
