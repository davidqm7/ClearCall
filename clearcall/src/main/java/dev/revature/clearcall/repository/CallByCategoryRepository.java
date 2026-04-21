package dev.revature.clearcall.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import dev.revature.clearcall.model.CallByCategory;
import dev.revature.clearcall.model.CallByCategoryKey;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CallByCategoryRepository extends CassandraRepository<CallByCategory, CallByCategoryKey> {

    
    List<CallByCategory> findByKeyCallCategoryAndKeyCallDate(String callCategory, LocalDate callDate);
}