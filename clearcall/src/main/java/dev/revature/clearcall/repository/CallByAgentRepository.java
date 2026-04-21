package dev.revature.clearcall.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import dev.revature.clearcall.model.CallByAgent;
import dev.revature.clearcall.model.CallByAgentKey;

import java.util.List;

@Repository
public interface CallByAgentRepository extends CassandraRepository<CallByAgent, CallByAgentKey> {

    
    List<CallByAgent> findByKeyAgentId(String agentId);
}