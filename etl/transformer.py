from datetime import datetime
from model import CallRecord

class TranscriptTransformer:
    def __init__(self):
        pass
    
    def transform(self, raw_transcript: dict) -> CallRecord:
        raw_transcript["startTime"] = datetime.fromisoformat(raw_transcript["startTime"])
        raw_transcript["endTime"] = datetime.fromisoformat(raw_transcript["endTime"])
        
        duration_seconds = raw_transcript["endTime"] - raw_transcript["startTime"]
        call_date = raw_transcript["startTime"].date()
        
        
        return CallRecord(
            call_id=raw_transcript["callId"],
            call_date=call_date,
            start_time=raw_transcript["startTime"],
            end_time=raw_transcript["endTime"],
            call_category=raw_transcript["callCategory"],
            ivr_contained=raw_transcript["ivrContained"],
            escalated_to_agent=raw_transcript["escalatedToAgent"],
            agent_id=raw_transcript["agentId"],
            duration_seconds=int(duration_seconds.total_seconds()),
            ivr_path=raw_transcript["ivrPath"]
            )
        
        
# unblock to test the transformer with a mock input      
# if __name__ == "__main__":
#     # 1. Create a fake dictionary that looks like the parser output
#     mock_raw_data = {
#         "callId": "1234-abcd",
#         "startTime": "2026-04-01T10:00:00+00:00",
#         "endTime": "2026-04-01T10:05:30+00:00",  # 5 mins, 30 secs
#         "callCategory": "BILLING",
#         "ivrContained": True,
#         "escalatedToAgent": False,
#         "agentId": None,
#         "ivrPath": ["WELCOME", "BILLING", "RESOLVED"]
#     }

#     # 2. Run it through your transformer
#     transformer = TranscriptTransformer()
#     result = transformer.transform(mock_raw_data)

#     # 3. Print the resulting dataclass
#     print(result)