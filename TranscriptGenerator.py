"""Transcript Generator is designed to generate the rules of a creating, writing, repeating one call transcript.
The __init__ stores the config, helper methods generate the pieces, and the main method generates many files to output directory."""
import argparse
from pathlib import Path
import random
from datetime import datetime, timedelta, timezone
import uuid
import json
from dataclasses import asdict
from CallTranscript import CallTranscript

class TranscriptGenerator:
    def __init__(self, output_dir, agent_ids):
        self.output_dir = Path(output_dir)
        self.agent_ids = agent_ids
        self.categories = ["BILLING", "TECHNICAL", "SALES", "GENERAL"]
        self.category_weights = [40, 35, 15, 10]
    
    #returns string from list of categories randomly using weights
    def get_call_category(self):
        #returns one element
        return random.choices(self.categories, weights=self.category_weights, k=1)[0]
    
    #rules for getting ivr containment
    def get_ivr_containment(self, category):
        if category == "BILLING":
            return random.random() < 0.60
        elif category == "TECHNICAL":
            return False
        elif category == "SALES":
            return False
        elif category == "GENERAL":
            return random.random() < 0.40
        
    #assigns random agent, otherwise, its contained
    def get_agent_id(self, ivr_contained):
        if ivr_contained:
            return None
        return random.choice(self.agent_ids)
    
    def generate_times(self, category):
        now = datetime.now(timezone.utc)

        # random start time sometime in the last 30 days
        minutes_ago = random.randint(0, 30 * 24 * 60)
        start_time = now - timedelta(minutes=minutes_ago)

        if category == "BILLING":
            duration_seconds = random.randint(120, 420)
        elif category == "TECHNICAL":
            duration_seconds = random.randint(300, 900)
        elif category == "SALES":
            duration_seconds = random.randint(240, 720)
        else:  # GENERAL
            duration_seconds = random.randint(90, 360)

        end_time = start_time + timedelta(seconds=duration_seconds)
        return start_time, end_time
    
    #Matches the ivrpath result of each specific call.
    #In otherwords, what exactly happened within the call path.
    def build_ivr_path(self, category, ivr_contained):
        path = ["WELCOME", category]

        if category == "BILLING":
            path.append("ACCOUNT_LOOKUP")
        elif category == "TECHNICAL":
            path.append("DEVICE_CHECK")
        elif category == "SALES":
            path.append("PRODUCT_INFO")
        elif category == "GENERAL":
            path.append("MENU_HELP")

        if ivr_contained:
            path.append("RESOLVED")
        else:
            path.append("TRANSFER_TO_AGENT")

        return path
        
    
    #Assembles all the pieces into a single call transcript object
    def generate_transcript(self):
        #Python's built-in uuid module
        callId = str(uuid.uuid4())
        category = self.get_call_category()
        is_contained = self.get_ivr_containment(category)
        escalatedToAgent = not is_contained
        agentId = self.get_agent_id(is_contained)
        startTime, endTime = self.generate_times(category)
        ivrPath = self.build_ivr_path(category, is_contained)
        #CallTranscript object
        return CallTranscript(
            callId=callId,
            startTime=startTime,
            endTime=endTime,
            callCategory=category,
            ivrContained=is_contained,
            escalatedToAgent=escalatedToAgent,
            agentId=agentId,
            ivrPath=ivrPath
        )   
    
    #Writes the generated transcript object to a physical JSON file
    def write_transcript(self, transcript):
        # using asdict from dataclasses module
        transcript_dict = asdict(transcript)
        transcript_dict["startTime"] = transcript.startTime.isoformat()
        transcript_dict["endTime"] = transcript.endTime.isoformat()
        
        file_path = self.output_dir / f"{transcript.callId}.json"
        with open(file_path, "w") as f:
            json.dump(transcript_dict, f, indent=2)
        
    
    def generate_many(self, count=50):
        for _ in range(count):
            transcript = self.generate_transcript()
            self.write_transcript(transcript)

#Check if this script is being executed directly, set up argparse to accept command-line arguments for output directory and number of transcripts to generate
if __name__ == "__main__":
    #Set up argparse to accept --output and --count flags.
    parser = argparse.ArgumentParser(description="Generate call transcripts")
    parser.add_argument("--output", type=str, default="transcripts", help="Output directory for transcripts")
    parser.add_argument("--count", type=int, default=50, help="Number of transcripts to generate")
    args = parser.parse_args()
    
    generator = TranscriptGenerator(args.output, agent_ids=["agent-1", "agent-2", "agent-3", "agent-4", "agent-5"])
    generator.generate_many(args.count)