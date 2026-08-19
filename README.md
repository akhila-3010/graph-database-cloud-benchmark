# Graph Database Cloud Benchmarking System


A full-stack benchmarking platform designed to perform a **fair, reproducible, and transparent comparison of CognoDB Cloud with other graph database platforms** using the same dataset, equivalent resources, logical workloads, and measurement methodology.


The project was developed for the **WEXA AI Graph Database Cloud Benchmarking take-home assignment**.


The primary goal is not to declare a universal winner. Instead, the system measures and reports how different graph database platforms behave under comparable conditions.


---


## 🎯 Project Objective


Graph databases are optimized for highly connected data and relationship-based queries.


This project provides an automated benchmark system for evaluating graph database performance across multiple workload categories.


The benchmark evaluates:


- Data ingestion throughput
- 1-hop graph traversal latency
- 2-hop graph traversal latency
- 3-hop graph traversal latency
- Point lookup latency
- Indexed/filtered lookup latency
- Aggregation query latency
- Concurrent read/write throughput
- Observable resource footprint


The benchmark emphasizes:


- Fairness
- Reproducibility
- Automation
- Statistical measurement
- Transparent reporting
- Honest documentation of limitations


---


# 🏗️ System Architecture


```text
                         User
                           |
                           v
                   React Frontend
                           |
                           v
                     REST API
                           |
                           v
                  Spring Boot Backend
                           |
          +----------------+----------------+
          |                |                |
          v                v                v
    Dataset Loader   Benchmark Engine   Report Engine
          |                |                |
          +----------------+----------------+
                           |
                           v
                  Graph DB Connectors
                           |
       +-------------------+-------------------+
       |                   |                   |
       v                   v                   v
   CognoDB Cloud         Neo4j             Memgraph
       |
       +-------------------+
       |
       v
     FalkorDB

The system separates database connectivity, dataset loading, benchmark execution, metric calculation, and reporting so that additional graph database platforms can be added without changing the benchmark methodology.

🛠️ Technology Stack
Frontend
Technology	Purpose
React.js	User interface
React Router	Application navigation
Axios	REST API communication
Vite	Frontend development and build
CSS / Tailwind CSS	User interface styling
Backend
Technology	Purpose
Java 17	Application development
Spring Boot	REST API and backend services
Maven	Dependency and build management
Neo4j Java Driver	Bolt-based graph database communication
Cypher / OpenCypher	Graph query workloads
JUnit	Automated testing
Data and Benchmarking
Technology	Purpose
CSV	Dataset input
JSON	Benchmark result storage
Java timing APIs	Latency measurement
Python	Optional result analysis and visualization
🗄️ Database Platforms

The benchmark is designed to compare CognoDB Cloud with at least four graph database platforms.

The current connector architecture supports:

CognoDB Cloud
Neo4j
Memgraph
FalkorDB

Additional graph database connectors can be added through the common connector interface.

The final benchmark should only report a database as tested after it has actually been deployed, loaded with the benchmark dataset, and measured.

⚖️ Fairness Methodology

Fairness is a core requirement of this project.

Every database should be tested using comparable conditions.

The benchmark follows these rules:

Same Dataset

Every database receives the same:

Nodes
Relationships
Properties
Dataset sample
Same Logical Workloads

Every database executes equivalent:

Traversal workloads
Lookup workloads
Aggregation workloads
Mixed read/write workloads

If query syntax differs between platforms, the implementation is adapted while preserving the same logical operation.

Comparable Resources

The benchmark uses free or entry-level configurations where possible.

The target is to maintain comparable:

vCPU
RAM
Storage
Region

CognoDB's free C0 tier is intentionally small, so the dataset must remain small enough to fit within the smallest tested configuration.

Any unavoidable resource difference is documented rather than hidden.

Same Client Environment

All databases are benchmarked from the same client environment.

The benchmark records:

Client operating system
CPU
RAM
Java version
Network environment
Client region
Benchmark date
📊 Dataset

The benchmark uses a public graph dataset containing at least 100,000 relationships.

The planned dataset is based on the:

SNAP Pokec Social Network Dataset

Source:

Stanford Network Analysis Project (SNAP)

The dataset is converted into a graph representation containing nodes and relationships.

The benchmark records the exact:

Dataset source
Dataset version
Number of nodes
Number of relationships
Sampling method
Preprocessing steps

The identical processed dataset is loaded into every database.

📥 Dataset Loading

The dataset loading pipeline follows:

Public Dataset
      |
      v
Dataset Validation
      |
      v
Data Preprocessing
      |
      v
Node Preparation
      |
      v
Relationship Preparation
      |
      v
Database Connection
      |
      v
Batch Loading
      |
      v
Node / Relationship Verification
      |
      v
Benchmark Ready

The loading benchmark measures:

Total wall-clock loading time
Nodes loaded
Relationships loaded
Nodes per second
Relationships per second
Loading errors

The loading mechanism used for each database is documented.

Where driver-based batching is used, the batch size and loading strategy are recorded.

🔥 Benchmark Workloads

The benchmark consists of several workload categories required by the assignment.

1. Data Ingestion Benchmark

The ingestion benchmark measures how quickly the database can load the benchmark dataset.

Metrics
Total wall-clock load time
Nodes/second
Relationships/second
Formulas
Nodes/second =
Total nodes / Total load time


Relationships/second =
Total relationships / Total load time
2. Graph Traversal Benchmark

The traversal benchmark evaluates graph navigation at different depths.

The required workloads are:

1-hop traversal
2-hop traversal
3-hop traversal

A start node is selected from the dataset and the benchmark measures the time required to traverse the graph.

Example logical query:

MATCH (a)-[*1]->(b)
WHERE a.id = $id
RETURN b

2-hop:

MATCH (a)-[*2]->(b)
WHERE a.id = $id
RETURN b

3-hop:

MATCH (a)-[*3]->(b)
WHERE a.id = $id
RETURN b

The exact syntax may differ between databases.

The benchmark preserves the same logical operation across platforms.

Metrics
p50 latency
p95 latency
Successful requests
Failed requests
Timeouts
3. Point Lookup Benchmark

The point lookup benchmark retrieves a node using a unique identifier.

Example:

MATCH (n:Person {id: $id})
RETURN n

The benchmark measures:

p50 latency
p95 latency
Successful requests
Failed requests
Timeout count
4. Indexed / Filtered Lookup Benchmark

The indexed or filtered lookup benchmark evaluates property-based searches.

Example:

MATCH (n:Person)
WHERE n.property = $value
RETURN n

The benchmark records the indexing configuration used by each database.

The following information is documented:

Indexed property
Index type
Index availability
Query implementation
p50 latency
p95 latency

If an equivalent index is unavailable on a platform, the limitation is documented.

5. Aggregation Benchmark

The aggregation workload evaluates operations over graph data.

Example:

MATCH (n:Person)
RETURN count(n)

Where supported, a grouping workload can also be executed:

MATCH (n:Person)
RETURN n.property, count(*) AS total
ORDER BY total DESC
Metrics
p50 latency
p95 latency
Successful operations
Failed operations
Timeouts
6. Mixed Read/Write Workload

The mixed workload evaluates database behavior under concurrent application-style traffic.

The workload contains both read and write operations.

The benchmark records:

Sustained queries per second
p50 latency
p95 latency
Successful operations
Failed operations
Timeouts

The read/write ratio is recorded as part of the benchmark configuration.

👥 Concurrency Testing

The benchmark supports concurrency testing.

The recommended concurrency sweep is:

1 client
10 clients
40 clients

This allows the benchmark to observe how database performance changes as concurrent workload increases.

For each concurrency level, the benchmark records:

Queries per second
p50 latency
p95 latency
Error count
Timeout count
⏱️ Warm-up and Measurement

Each database is warmed up before collecting measured results.

The workflow is:

Connect
   |
   v
Warm-up Queries
   |
   v
Discard Warm-up Measurements
   |
   v
Measured Workload
   |
   v
Calculate Statistics

The assignment recommends at least 100 iterations per read workload after warm-up.

The benchmark therefore supports repeated iterations and preserves the individual measurements.

Warm-up results are excluded from the final latency statistics.

📐 Statistical Measurements

The benchmark reports percentile-based latency rather than relying only on averages.

p50

p50 represents the median latency.

Approximately half of the measured requests complete at or below this value.

p95

p95 represents the latency below which approximately 95% of requests complete.

p95 is useful for identifying slower tail requests that may not be visible from an average alone.

📈 Required Metrics

The benchmark collects the following metrics.

Category	Metrics
Data Loading	Load time, nodes/sec, relationships/sec
Traversals	1-hop p50/p95, 2-hop p50/p95, 3-hop p50/p95
Lookups	Point lookup p50/p95, indexed lookup p50/p95
Aggregations	Aggregation p50/p95
Mixed Workload	Sustained QPS, p50, p95, errors
Footprint	Storage, memory, CPU, instance specifications where observable
🖥️ Benchmark Environment

All databases are tested from the same client environment.

The benchmark records:

Operating System
CPU
RAM
Java Version
Maven Version
Node.js Version
Network Environment
Client Region
Benchmark Date

For every database, the benchmark records:

Provider
Deployment Type
Instance Tier
CPU
RAM
Storage
Region
Free-tier limitations

This information is used to evaluate the fairness of the comparison.

🔐 Environment Configuration

Database credentials must never be committed to GitHub.

Credentials are loaded using environment variables.

Example:

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

Create a local .env file for development.

The .env file must remain in .gitignore.

A .env.example file can be committed with empty values.

🚀 CognoDB Cloud Setup

Create a CognoDB Cloud account and provision a free C0 instance.

The connection details are provided by the CognoDB Cloud console.

The benchmark connects using the official Neo4j driver and the provided Bolt connection URI.

Example:

bolt+s://<instance-id>.databases.cognodb.cloud

Credentials are read from environment variables and are never stored in source code.

🚀 Running the Backend
Requirements

Install:

Java 17+
Maven 3.9+
Git

Verify the installations:

java -version
mvn -version
git --version

Navigate to the backend:

cd backend

Build:

mvn clean install

Run:

mvn spring-boot:run

The backend is available at:

http://localhost:8080
🚀 Running the Frontend
Requirements

Install:

Node.js 18+
npm

Navigate to the frontend:

cd frontend

Install dependencies:

npm install

Start the development server:

npm run dev

The frontend is available at:

http://localhost:5173
🔗 API Modules
Connection API

Responsible for:

Database connection
Credential validation
Connection verification
Connection error handling
Dataset API

Responsible for:

Dataset loading
Dataset processing
Dataset information
Load verification
Verification API

Responsible for:

Database health checks
Node verification
Relationship verification
Query execution checks
Benchmark API

Responsible for:

Benchmark execution
Workload execution
Latency collection
Concurrency testing
Report API

Responsible for:

Processing benchmark results
Generating performance summaries
Preparing benchmark reports
📂 Project Structure
Graph-Database-Cloud-Benchmarking-System/
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── App.jsx
│   │
│   └── package.json
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │               └── graphbenchmark/
│   │                   ├── controller/
│   │                   ├── connector/
│   │                   ├── benchmark/
│   │                   ├── workload/
│   │                   ├── dataset/
│   │                   ├── metrics/
│   │                   ├── report/
│   │                   └── service/
│   │
│   ├── pom.xml
│   └── README.md
│
├── data/
│   └── dataset files
│
├── results/
│   ├── raw/
│   ├── processed/
│   └── charts/
│
├── screenshots/
│
├── .env.example
├── .gitignore
└── README.md
📋 Benchmark Execution Flow
Start Application
       |
       v
Configure Database Credentials
       |
       v
Connect to Database
       |
       v
Load Identical Dataset
       |
       v
Verify Nodes and Relationships
       |
       v
Warm Up Database
       |
       v
Run Ingestion Benchmark
       |
       v
Run Traversal Workloads
       |
       v
Run Lookup Workloads
       |
       v
Run Aggregation Workloads
       |
       v
Run Mixed Read/Write Workload
       |
       v
Run Concurrency Tests
       |
       v
Collect Metrics
       |
       v
Calculate p50 / p95 / QPS
       |
       v
Generate Results
       |
       v
Generate Charts and Report
📦 Benchmark Results

Benchmark results are generated only after the workloads have actually been executed.

The repository can contain:

results/
├── raw/
├── processed/
└── charts/

Raw results are preserved in machine-readable formats such as:

JSON
CSV

This allows the reported statistics to be reproduced from the original measurements.

No benchmark values are manually invented or entered into the README.

📊 Results Reporting

The final report is intended to contain a comparison across all tested databases.

The report will cover:

Ingestion throughput
1-hop latency
2-hop latency
3-hop latency
Point lookup latency
Indexed lookup latency
Aggregation latency
Mixed workload throughput
Concurrency behavior
Observable resource footprint

The results are interpreted together with:

Instance specifications
Dataset characteristics
Query configuration
Network conditions
Free-tier limitations
Failed runs
Provider-specific differences
🔍 Analysis Approach

The benchmark does not select a winner before the experiment.

The analysis focuses on explaining the observed results.

Ingestion

The analysis examines differences in:

Nodes/sec
Relationships/sec
Total loading time

Possible factors include batching, transaction overhead, network latency, and storage behavior.

Traversals

The analysis compares how latency changes between:

1-hop → 2-hop → 3-hop
Lookups

Point lookup performance is compared with indexed or filtered lookup performance.

Aggregations

Aggregation results help evaluate how databases process operations across graph data.

Mixed Workloads

The analysis examines how throughput and latency change as concurrency increases.

Overall Interpretation

Results are specific to the tested:

Dataset
Workloads
Resource configuration
Database version
Region
Client environment

Therefore, the benchmark should not be interpreted as a universal ranking of graph databases.

⚠️ Limitations and Caveats

Cloud benchmarking has several unavoidable limitations.

Resource Differences

Different providers expose different CPU, RAM, storage, and service configurations.

The benchmark documents these differences rather than pretending that perfect hardware parity exists.

Network Variability

Cloud database performance can be affected by:

Network latency
Temporary congestion
Internet routing
Provider infrastructure
Free-Tier Restrictions

Free or entry-level tiers may have:

CPU throttling
Memory limits
Connection limits
Storage limits
Throughput restrictions
Automatic suspension
Query Differences

Graph database implementations may differ in Cypher or OpenCypher support.

Where exact syntax differs, equivalent logical operations are used.

Resource Observability

Some providers expose detailed resource information while others expose limited information.

Metrics that cannot be observed are reported as:

Not observable

rather than estimated.

❌ Failed Runs and Anomalies

Failed runs are not silently removed.

The benchmark records issues such as:

Connection failures
Query failures
Timeouts
Provider throttling
Dataset loading failures
Resource exhaustion
Temporary service interruptions

Documenting failed runs is part of maintaining an honest benchmark.

🧪 Testing

Backend tests can be executed using:

cd backend
mvn test

Tests cover areas such as:

Controller functionality
Service functionality
Database connectors
Dataset loading
Benchmark logic
Metric calculations
Error handling
📸 Screenshots

The frontend provides interfaces for:

Database connection
Dataset configuration
Graph verification
Benchmark execution
Benchmark results
Performance reporting

Screenshots can be added to:

screenshots/

Example:

screenshots/
├── dashboard.png
├── connection.png
├── dataset.png
├── verification.png
├── benchmark.png
└── report.png
🔮 Future Improvements

Potential improvements include:

Automated cloud instance provisioning
Docker-based controlled benchmark environments
Additional graph database connectors
Automated benchmark scheduling
CI/CD benchmark execution
Cold-start benchmarking
Longer-duration stress testing
Statistical confidence intervals
Historical benchmark comparison
Automated performance regression detection
More detailed visualization dashboards
🔐 Security

This repository does not contain:

Database passwords
API keys
Private tokens
Authentication credentials
.env files

All sensitive configuration is supplied through environment variables.

Before pushing to GitHub, verify that:

.env

is included in .gitignore.

📝 Reproducibility Checklist

Before considering a benchmark run complete:

 Same dataset used for every database
 Dataset source documented
 Exact node count recorded
 Exact relationship count recorded
 Dataset preprocessing documented
 Database resources documented
 Database regions documented
 Client environment documented
 Index configuration documented
 Warm-up completed
 At least 100 measured read iterations
 p50 calculated
 p95 calculated
 1-hop benchmark completed
 2-hop benchmark completed
 3-hop benchmark completed
 Point lookup completed
 Indexed/filtered lookup completed
 Aggregation benchmark completed
 Mixed read/write workload completed
 Concurrency testing completed
 Raw results preserved
 Failed runs documented
 Timeouts documented
 Provider limitations documented
 No credentials committed
📜 Assignment Alignment

This project is designed around the requirements of the WEXA AI Graph Database Cloud Benchmarking assignment.

The implementation addresses the major evaluation areas:

Methodology & Fairness

Same dataset, equivalent logical workloads, comparable resources, warm-up, repeated measurements, and documented caveats.

Completeness

The benchmark covers ingestion, traversal, lookup, aggregation, mixed workload, and observable footprint metrics.

Reproducibility

Benchmark execution is automated through the backend and raw measurements can be preserved for verification.

README & Analysis

The project documents its architecture, methodology, dataset, workload design, execution process, limitations, and analysis approach.

Communication

The frontend and reporting components are designed to make benchmark execution and results easier to understand.

👨‍💻 Author

Akhila Chinta

Developed as part of the WEXA AI Graph Database Cloud Benchmarking Take-Home Assignment.

📜 License

This project is created for educational and technical evaluation purposes.

⭐ Final Note

The purpose of this project is to produce a fair and reproducible graph database benchmark, not to manufacture a preferred result.

Performance results should always be interpreted together with the tested resources, dataset, workloads, environment, and provider limitations.

Any failed benchmark, timeout, resource limitation, or platform-specific behavior should be reported transparently rather than removed from the final analysis.
