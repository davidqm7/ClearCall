from dataclasses import dataclass
from datetime import datetime
        
    
@dataclass
class CallTranscript:
    
    callId : str
    startTime : datetime
    endTime : datetime
    callCategory : str
    ivrContained : bool
    escalatedToAgent : bool
    agentId : str | None
    ivrPath : list[str]
    
