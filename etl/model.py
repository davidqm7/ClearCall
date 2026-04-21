from dataclasses import dataclass
from datetime import date, datetime



@dataclass
class CallRecord:
    call_id: str
    call_date: date
    start_time: datetime
    end_time: datetime
    call_category: str
    ivr_contained: bool
    escalated_to_agent: bool
    agent_id: str | None
    duration_seconds: int
    ivr_path: list[str]