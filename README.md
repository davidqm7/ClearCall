# ClearCall - Conversation Analytics Platform

## Team 5
* David
* Jathan

## Source Control & Branching Strategy
* **Feature Branches:** All development will happen on feature branches (e.g., `feature/transcript-generator`, `feature/etl-parser`, `feature/spring-setup`).
* **Merge Process:** Push to your feature branch, open a Pull Request, and review together before merging into `main`. Do not push directly to `main`.
* **Conflict Resolution:** If both team members modify shared files (like `main.py` or the `application.yml`), communicate before merging to resolve conflicts cleanly.

## The Data Contract
All components must adhere strictly to these values.
* **callCategory values:** `BILLING`, `TECHNICAL`, `SALES`, `GENERAL`
* **agentId values:** `agent-1`, `agent-2`, `agent-3`, `agent-4`, `agent-5`
* **Transcript Path:** `./transcripts/` (Generator writes here, ETL reads from here)

---

## Project Roadmap & Implementation Sequence

### Phase 1: Foundation & Data Generation (Component 1)
**Goal:** Create the dummy data that will fuel the rest of the system.
* [ ] **Repo Setup:** Initialize git, create the `README.md`, and set up the `./transcripts/` directory. - David and jathan
* [ ] **Generator Core:** Create the `TranscriptGenerator` class and `CallTranscript` dataclass in Python. - Jathan 
* [ ] **CLI Argument Parsing:** Implement `argparse` to allow configuring the number of transcripts (default 50) and output directory. - David
* [ ] **Logic & Randomization:** Implement weighted randomness for categories (40% BILLING, 35% TECHNICAL, 15% SALES, 10% GENERAL). Enforce IVR containment rules (Technical/Sales always escalate, Billing 60% contained).  - Jathan
* [ ] **File I/O:** Write the generated JSON files to the target directory. - David

### Phase 2: Database Initialization & Schema (Component 3)
**Goal:** Stand up the database and define the query-first architecture before building the ETL. 
* [ ] **Docker Setup:** Spin up a local Cassandra instance using Docker. 
* [ ] **Keyspace Creation:** Create the `clearcall` keyspace with `SimpleStrategy`.
* [ ] **Table 1 (`calls_by_date`):** Design table for time-of-day analysis (Partition key: `call_date`).
* [ ] **Table 2 (`calls_by_agent`):** Design table for agent metrics (Partition key: `agent_id`).
* [ ] **Table 3 (`calls_by_category`):** Design table for category trends (Partition key: `call_category`).

### Phase 3: The ETL Pipeline (Component 2)
**Goal:** Read the generated JSON, transform it, and load it into Cassandra.
* [ ] **Data Models:** Define the `CallRecord` dataclass. - David and Jathan
* [ ] **Extract (`parser.py`):** Read JSON files, validate required fields, and gracefully skip/log malformed records without crashing. - Jathan
* [ ] **Transform (`transformer.py`):** Convert raw JSON dicts into `CallRecord` objects. Calculate `duration_seconds` using `datetime` math and extract `call_date`. - David
* [ ] **Load (`loader.py`):** Use the `cassandra-driver` Python package to insert a single `CallRecord` into all three Cassandra tables simultaneously. - David
* [ ] **Orchestration (`main.py`):** Tie the Extract, Transform, and Load steps together into a single executable script with CLI arguments. - Jathan

### Phase 4: Java Reporting Service (Component 4)
**Goal:** Expose the Cassandra data via a Spring Boot REST API.
* [ ] **Project Setup:** Initialize Spring Boot with `spring-boot-starter-web` and `spring-boot-starter-data-cassandra`. Configure `application.yml`.
* [ ] **Entity Mapping:** Create `CallByDate`, `CallByAgent`, and `CallByCategory` entities using `@Table` and `@PrimaryKeyColumn`.
* [ ] **Repositories:** Create the three corresponding interfaces extending `CassandraRepository`.
* [ ] **Agent Endpoints:** Build `GET /analytics/agents/{agentId}/calls` and `GET /analytics/agents/{agentId}/handle-time`. Use Java Streams to calculate the average handle time.
* [ ] **Global Endpoints:** Build `GET /analytics/categories` and `GET /analytics/calls?date={date}`.
