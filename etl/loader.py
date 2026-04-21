from cassandra.cluster import Cluster
from model import CallRecord
import uuid

class CassandraLoader:
    
    def __init__(self, host='localhost', keyspace='clearcall'):
        #Initialize the Cluster
        self.cluster = Cluster([host])
        #Connect to the keyspace to create the session
        self.session = self.cluster.connect(keyspace)
        
        
        self.insert_agent_stmt = self.session.prepare("""
              INSERT INTO clearcall.calls_by_agent 
              (agent_id, start_time, call_id, call_category, duration_sec)     
              VALUES (?, ?, ?, ?, ?)
        """)
        
        self.insert_date_stmt = self.session.prepare("""
              INSERT INTO clearcall.calls_by_date
                (call_date, start_time, call_id, agent_id, call_category, duration_sec, escalated, ivr_contained)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """)
        
        self.insert_category_stmt = self.session.prepare("""
              INSERT INTO clearcall.calls_by_category
                (call_category, call_date, start_time, call_id, duration_sec, ivr_contained)
                VALUES (?, ?, ?, ?, ?, ?)
        """)
        
    
        
    def load(self, record: CallRecord):
        if record.agent_id is not None:
             self.session.execute(self.insert_agent_stmt, (
                record.agent_id,
                record.start_time,
                uuid.UUID(record.call_id),
                record.call_category,
                record.duration_seconds
        ))
        
        self.session.execute(self.insert_date_stmt, (
            record.call_date,
            record.start_time,
            uuid.UUID(record.call_id),
            record.agent_id,
            record.call_category,
            record.duration_seconds,
            record.escalated_to_agent,
            record.ivr_contained
        ))
        
        self.session.execute(self.insert_category_stmt, (
            record.call_category,
            record.call_date,
            record.start_time,
            uuid.UUID(record.call_id),
            record.duration_seconds,
            record.ivr_contained
        ))
 
    def close(self):
        self.cluster.shutdown()