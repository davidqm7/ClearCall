import json
import logging
from pathlib import Path


class TranscriptParser:
    def __init__(self, input_dir: str):
        # Convert the input directory string into a Path object
        self.input_dir = Path(input_dir)

        # Fields every transcript JSON file must contain
        self.required_fields = [
            "callId",
            "startTime",
            "endTime",
            "callCategory",
            "ivrContained",
            "escalatedToAgent",
            "agentId",
            "ivrPath"
        ]

    def get_transcript_files(self) -> list[Path]:
        return list(self.input_dir.glob("*.json"))

    # Check required fields and data types. If incorrect, skip transcript
    def validate_transcript(self, transcript: dict, file_path: Path):
        missing_fields = []

        for field in self.required_fields:
            if field not in transcript:
                missing_fields.append(field)

        if missing_fields:
            logging.warning(
                f"Skipping {file_path.name}: missing required fields {missing_fields}"
            )
            return False

        if not isinstance(transcript["callId"], str):
            logging.warning(f"Skipping {file_path.name}: callId must be a string")
            return False

        if not isinstance(transcript["startTime"], str):
            logging.warning(f"Skipping {file_path.name}: startTime must be a string")
            return False

        if not isinstance(transcript["endTime"], str):
            logging.warning(f"Skipping {file_path.name}: endTime must be a string")
            return False

        if not isinstance(transcript["callCategory"], str):
            logging.warning(f"Skipping {file_path.name}: callCategory must be a string")
            return False

        if not isinstance(transcript["ivrContained"], bool):
            logging.warning(f"Skipping {file_path.name}: ivrContained must be a boolean")
            return False

        if not isinstance(transcript["escalatedToAgent"], bool):
            logging.warning(
                f"Skipping {file_path.name}: escalatedToAgent must be a boolean"
            )
            return False

        if transcript["agentId"] is not None and not isinstance(transcript["agentId"], str):
            logging.warning(
                f"Skipping {file_path.name}: agentId must be a string or null"
            )
            return False

        if not isinstance(transcript["ivrPath"], list):
            logging.warning(f"Skipping {file_path.name}: ivrPath must be a list")
            return False

        for step in transcript["ivrPath"]:
            if not isinstance(step, str):
                logging.warning(
                    f"Skipping {file_path.name}: every ivrPath item must be a string"
                )
                return False

        return True
    #Read one JSON file and return its transcript data if valid.
    #Return None if the file is invalid.
    def parse_file(self, file_path: Path):
        try:
            with open(file_path, "r", encoding="utf-8") as file:
                transcript = json.load(file)

        except json.JSONDecodeError:
            logging.warning(f"Skipping {file_path.name}: invalid JSON format")
            return None

        except OSError as error:
            logging.warning(f"Skipping {file_path.name}: could not read file ({error})")
            return None

        if not isinstance(transcript, dict):
            logging.warning(f"Skipping {file_path.name}: JSON root must be an object")
            return None

        if not self.validate_transcript(transcript, file_path):
            return None

        return transcript

    #Read all transcript files in the input directory.
    #Return only the valid transcripts.
    def parse_all(self):
        valid_transcripts = []

        for file_path in self.get_transcript_files():
            transcript = self.parse_file(file_path)

            if transcript is not None:
                valid_transcripts.append(transcript)

        return valid_transcripts