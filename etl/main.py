import argparse

from parser import TranscriptParser
from transformer import TranscriptTransformer
from loader import CassandraLoader


def main():
    parser = argparse.ArgumentParser(description="Run the ClearCall ETL pipeline")
    parser.add_argument("--input",required=True,help="Path to the directory containing transcript JSON files")
    parser.add_argument("--host",default="localhost",help="Cassandra host")
    parser.add_argument("--keyspace",default="clearcall",help="Cassandra keyspace")
    args = parser.parse_args()

    transcript_parser = TranscriptParser(args.input)
    transformer = TranscriptTransformer()
    loader = CassandraLoader(host=args.host, keyspace=args.keyspace)

    raw_transcripts = transcript_parser.parse_all()

    try:
        for raw in raw_transcripts:
            record = transformer.transform(raw)
            loader.load(record)
    finally:
        loader.close()


if __name__ == "__main__":
    main()