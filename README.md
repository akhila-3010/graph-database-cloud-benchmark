# Graph Database Cloud Benchmarking System

A full-stack benchmarking platform to evaluate and compare graph database cloud platforms using identical datasets, workloads, and measurement methodology.

This project benchmarks **CognoDB Cloud** against other graph database platforms by executing automated graph workloads and generating performance reports based on latency, throughput, and resource observations.

The system contains:

* React frontend for user interaction and visualization
* Spring Boot backend for benchmark execution
* Graph database connectors
* Dataset loading pipeline
* Query workload engine
* Performance reporting system

---

# 📌 Project Objective

Graph databases are designed for highly connected data where relationships between entities are important.

This project evaluates graph database performance by measuring:

* Data ingestion speed
* Graph traversal performance
* Lookup performance
* Aggregation performance
* Concurrent workload handling

The objective is not to declare a single database as the winner, but to provide a reproducible and honest benchmark comparison.

---

# 🏗️ System Architecture

```
                    User
                     |
                     |
              React Frontend
                     |
                     |
              REST API Layer
                     |
                     |
            Spring Boot Backend
                     |
 ------------------------------------------------
 |              |              |                 |
Connector    Dataset      Benchmark        Report
Layer        Loader       Engine           Generator
 |
 ------------------------------------------------
 |              |              |              |
CognoDB      Neo4j        Memgraph       FalkorDB
```

---

# 🛠️ Technology Stack

## Frontend

| Technology   | Purpose                |
| ------------ | ---------------------- |
| React.js     | User interface         |
| React Router | Application navigation |
| Axios        | REST API communication |
| Vite         | Frontend development   |
| CSS/Tailwind | UI styling             |

## Backend

| Technology   | Purpose                     |
| ------------ | --------------------------- |
| Java 17      | Programming language        |
| Spring Boot  | REST API framework          |
| Maven        | Build management            |
| Neo4j Driver | Bolt protocol communication |
| OpenCypher   | Graph query language        |
| JUnit        | Testing                     |

## Supported Databases

The benchmark supports:

* CognoDB Cloud
* Neo4j
* Memgraph
* FalkorDB

---

# 📂 Project Structure

## Frontend Structure

```
frontend/

src/
│
├── components/
│   ├── Navbar
│   ├── Footer
│   ├── Loader
│   └── Error Components
│
├── pages/
│   ├── Home
│   ├── Connection
│   ├── Dataset
│   ├── Verification
│   ├── Benchmark
│   └── Report
│
├── services/
│   └── API Service
│
└── App.jsx
```

---

## Backend Structure

```
backend/

src/main/java/com/graphbenchmark/

│
├── controller/
│   ├── BenchmarkController
│   ├── DatasetController
│   ├── ReportController
│   └── VerificationController
│
├── connector/
│   ├── GraphDatabaseConnector
│   ├── CognoDBConnector
│   ├── Neo4jConnector
│   ├── MemgraphConnector
│   └── FalkorDBConnector
│
├── benchmark/
│   └── BenchmarkSuite
│
├── workload/
│   ├── TraversalWorkload
│   ├── LookupWorkload
│   └── MixedWorkload
│
├── dataset/
│   └── CsvDatasetLoader
│
├── metrics/
│
├── report/
│
├── service/
│
└── BackendApplication.java

```

---

# ✨ Features

# 1. Database Connection Management

The system provides database connection verification.

Supported operations:

* Connect to graph database
* Validate credentials
* Check database availability
* Handle connection failures

Supported connectors:

```
CognoDB
Neo4j
Memgraph
FalkorDB
```

---

# 2. Dataset Loading

The dataset module imports graph data into databases.

Features:

* CSV dataset support
* Batch loading
* Relationship creation
* Node creation

Example dataset:

```
data/
 └── pokec_edges_sample_100k.csv
```

Dataset contains:

* User nodes
* Relationship edges

---

# 3. Graph Verification

Verification checks:

* Database connectivity
* Node creation
* Relationship creation
* Query execution

This ensures every database is ready before benchmarking.

---

# 4. Benchmark Engine

The benchmark engine executes different graph workloads.

## Data Loading Benchmark

Measures:

| Metric            | Description                 |
| ----------------- | --------------------------- |
| Load Time         | Total import duration       |
| Nodes/sec         | Node insertion rate         |
| Relationships/sec | Relationship insertion rate |

---

## Traversal Benchmark

Tests graph navigation depth.

Queries:

* 1-hop traversal
* 2-hop traversal
* 3-hop traversal

Reported metrics:

* p50 latency
* p95 latency

---

## Lookup Benchmark

Measures:

* Point lookup
* Property filtering
* Indexed lookup

Example:

```
Find user by ID
```

---

## Aggregation Benchmark

Measures graph aggregation operations:

Examples:

```
Count users
Count relationships
Group by category
```

---

## Mixed Workload Benchmark

Simulates realistic usage:

* Concurrent reads
* Writes
* Multiple clients

Configuration:

```
Warmup:
10 iterations

Benchmark iterations:
100

Concurrent clients:
10
```

---

# 📊 Benchmark Methodology

To maintain fairness:

## Same Dataset

Every database receives:

* Same nodes
* Same relationships
* Same properties

## Same Queries

Every database executes:

* Same logical workloads
* Same benchmark iterations

## Same Client Environment

Recorded:

* Client machine
* Database region
* Database tier

## Warmup

Before measurements:

```
Warmup iterations:
10
```

Benchmark execution:

```
100 iterations
```

---

# 📈 Metrics Collected

| Category       | Metrics                                  |
| -------------- | ---------------------------------------- |
| Loading        | Total time, nodes/sec, relationships/sec |
| Traversal      | p50 and p95 latency                      |
| Lookup         | Query latency                            |
| Aggregation    | Execution time                           |
| Mixed Workload | Queries per second                       |
| Footprint      | Storage/resource information             |

---

# 🔐 Environment Configuration

Database credentials must never be committed.

Create:

```
.env
```

Example:

```
COGNODB_URI=bolt+s://your-instance.databases.cognodb.cloud
COGNODB_USERNAME=cognodb
COGNODB_PASSWORD=your_password

NEO4J_URI=
NEO4J_USERNAME=
NEO4J_PASSWORD=

MEMGRAPH_URI=
MEMGRAPH_USERNAME=
MEMGRAPH_PASSWORD=

FALKORDB_URI=
FALKORDB_USERNAME=
FALKORDB_PASSWORD=
```

Update Spring configuration to read environment variables.

---

# 🚀 Running the Backend

## Requirements

Install:

* Java 17+
* Maven 3.9+
* Git

Check versions:

```
java -version

mvn -version
```

---

## Start Backend

Navigate:

```
cd backend
```

Build:

```
mvn clean install
```

Run:

```
mvn spring-boot:run
```

Backend runs:

```
http://localhost:8080
```

---

# 🚀 Running the Frontend

Requirements:

* Node.js 18+
* npm

Navigate:

```
cd frontend
```

Install dependencies:

```
npm install
```

Start application:

```
npm run dev
```

Frontend runs:

```
http://localhost:5173
```

---

# 🔗 API Modules

## Connection API

Handles:

* Database connection
* Connection verification

---

## Dataset API

Handles:

* Dataset loading
* Dataset information

---

## Verification API

Handles:

* Graph verification
* Database health check

---

## Benchmark API

Handles:

* Benchmark execution
* Workload execution

---

## Report API

Handles:

* Result generation
* Performance summary

---

# 📁 Dataset Information

Current benchmark dataset:

```
SNAP Pokec Social Network Dataset
```

Sample:

```
pokec_edges_sample_100k.csv
```

Approximate size:

```
Relationships:
100,000+

Format:
CSV
```

---

# 🧪 Testing

Backend tests:

```
mvn test
```

Test coverage includes:

* Controller tests
* Service tests
* Connector tests
* Benchmark tests

---

# 📋 Benchmark Execution Flow

```
Start Application

        |
        v

Connect Database

        |
        v

Load Dataset

        |
        v

Verify Graph

        |
        v

Execute Workloads

        |
        v

Collect Metrics

        |
        v

Generate Report

```

---

# 📸 Screenshots

Add screenshots:

```
screenshots/

├── dashboard.png
├── connection.png
├── dataset.png
├── verification.png
├── benchmark.png
└── report.png

```

---

# ⚠️ Limitations and Caveats

* Cloud free tiers have different limitations.
* Network latency can affect cloud benchmarks.
* Resource availability may vary.
* Some database metrics may not be publicly observable.
* Results should be interpreted with documented environment details.

---

# 🔮 Future Improvements

Possible improvements:

* Docker based benchmark environment
* Automated cloud deployment
* More graph database connectors
* Real-time benchmark charts
* CI/CD benchmark pipeline
* Advanced visualization dashboard

---

# 👨‍💻 Author

Developed as part of the **WEXA AI Graph Database Benchmarking Assignment**.

---

# 📜 License

This project is created for educational and technical evaluation purposes.
