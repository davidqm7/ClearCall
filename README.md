# ClearCall - CCaaS Conversation Analytics Platform

## Overview
ClearCall is the analytics layer of a Contact Center as a Service (CCaaS) environment. While operational systems like RouteIQ handle live call routing, ClearCall processes what happens after the call concludes. It ingests synthetic Interactive Voice Response (IVR) interaction records, transforms the data through an ETL pipeline, and stores it in a query-optimized NoSQL database. A Spring Boot REST API then exposes this data to provide insights into agent handle times, IVR containment rates, and call category trends.

## Team & Collaboration
This project was developed collaboratively with [Jathan](https://github.com/jlazala3102). We utilized a professional Git workflow, ensuring all development happened on isolated feature branches and was merged into the main branch via code reviews and Pull Requests.

### Division of Labor
* **Data Generation (Python):** * Script core, weighted randomness, and IVR logic (Jathan)
  * CLI implementation and File I/O (David)
* **ETL Pipeline (Python):** * JSON Extraction and Data Validation (Jathan)
  * Data Transformation and Cassandra Loading (David)
  * Pipeline Orchestration (Jathan)
* **Database (Cassandra):** Schema design, table creation, and Docker configuration (Co-authored)
* **Reporting Service (Spring Boot):** * Spring setup and initial configuration (David)
  * Data Models, Repositories, and Service logic (Co-authored)
  * Category and Date Analytics Endpoints (Jathan)
  * Agent Analytics Endpoints (David & Jathan)

## Technology Stack
* Data Generation & ETL: Python 3.11+, argparse, dataclasses
* Database: Apache Cassandra (Docker)
* Backend API: Java, Spring Boot, Spring Data Cassandra
* Architecture: Microservices, ETL Pipeline, Event-Driven Data Modeling

## Project Architecture

### 1. Synthetic Data Generator (Python)
A highly configurable Python script that produces synthetic JSON transcript files representing completed IVR interactions. It implements dynamic business logic to simulate realistic contact center traffic, including weighted call categories (Billing, Technical, Sales, General) and conditional IVR containment rules.

### 2. ETL Pipeline (Python)
An automated Extract, Transform, Load pipeline:
* Extract: Parses raw JSON transcripts from the generator, performing strict type-checking and data validation. Malformed records are gracefully skipped and logged.
* Transform: Converts raw dictionaries into strongly-typed `CallRecord` dataclasses. Calculates total call durations and extracts partition keys using Python's `datetime` module.
* Load: Connects to Cassandra using the DataStax driver. Utilizes prepared statements to duplicate and insert records across multiple query-optimized tables simultaneously.

### 3. Database (Apache Cassandra)
The database utilizes a strict Query-First Design. Because Cassandra does not support SQL JOINs, the schema intentionally denormalizes and duplicates incoming data across three distinct tables, each optimized for a specific read access pattern:
* `calls_by_date`: Partitioned by date for time-series analysis.
* `calls_by_agent`: Partitioned by agent ID for performance tracking.
* `calls_by_category`: Partitioned by call category for volume trends.

### 4. Reporting Service (Spring Boot)
A Java REST API built with Spring Boot and Spring Data Cassandra. It maps the complex composite primary keys (Partition + Clustering keys) to entity classes. Because Cassandra cannot perform distributed aggregations (like `AVG()` or `COUNT()`), the service layer leverages Java 8 Streams to calculate average handle times and category groupings in memory before returning formatted Data Transfer Objects (DTOs) to the client.

## Setup and Installation

### Prerequisites
* Docker
* Python 3.11 or higher
* Java 17 or higher
* Maven

### Step 1: Database Initialization
1. Start a local Cassandra instance using Docker:
   docker run --name clearcall-cassandra -p 9042:9042 -d cassandra:latest
2. Wait 60 seconds for the container to initialize.
3. Open the Cassandra shell:
   docker exec -it clearcall-cassandra cqlsh
4. Execute the schema commands located in the database documentation to create the keyspace and tables.

### Step 2: Run the Pipeline
1. Install the Python Cassandra driver:
   pip install cassandra-driver
2. Run the data generator to create transcripts:
   python TranscriptGenerator.py --output ./transcripts --count 50
3. Run the ETL pipeline to load the data into Cassandra:
   python etl/main.py --input ./transcripts --host localhost --keyspace clearcall

### Step 3: Start the API
1. Navigate to the Spring Boot project directory.
2. Run the application using Maven:
   mvn spring-boot:run
3. The server will start on `http://localhost:8090`.

## API Endpoints

### Global Analytics
* `GET /analytics/categories` - Returns call counts and average handle times grouped by category.
* `GET /analytics/calls?date={YYYY-MM-DD}` - Returns a list of all calls that occurred on a specific date.

### Agent Analytics
* `GET /analytics/agents/{agentId}/calls` - Returns a list of all calls handled by a specific agent.
* `GET /analytics/agents/{agentId}/handle-time` - Returns the average handle time (in seconds) for a specific agent.
