# Graph Database Cloud Benchmarking System

A reproducible benchmarking system for comparing **CognoDB Cloud** with other managed or equivalently constrained graph database platforms using the **same dataset, workloads, client environment, and measurement methodology**.

The goal of this project is not to declare a universal winner. The goal is to produce a **fair, reproducible, and honest comparison** of graph database performance under clearly documented resource constraints.

---

## 📌 Assignment

This project was developed for the **WEXA AI — Graph Database Cloud Benchmarking** take-home assignment.

The benchmark evaluates:

* Data ingestion throughput
* 1-hop, 2-hop, and 3-hop graph traversal latency
* Point lookups
* Indexed/filtered lookups
* Aggregation queries
* Concurrent mixed read/write workloads
* Observable resource footprint
* Warm and measured query performance

---

# 🎯 Objectives

The benchmark follows these principles:

1. Use the **same dataset** on every database.
2. Execute the **same logical workloads** wherever the query language/features allow.
3. Use comparable compute and storage resources.
4. Run all benchmarks from the same client environment.
5. Warm up each database before collecting measurements.
6. Run repeated iterations rather than relying on a single query.
7. Report **p50 and p95 latency**, not only averages.
8. Preserve raw benchmark results for reproducibility.
9. Document provider-specific limitations and differences.
10. Avoid selectively hiding failed runs, timeouts, or platform limitations.

---

# 🏗️ System Architecture

```text
                         ┌─────────────────────┐
                         │   Benchmark Runner  │
                         └──────────┬──────────┘
                                    │
             ┌──────────────────────┼──────────────────────┐
             │                      │                      │
             ▼                      ▼                      ▼
      Dataset Loader          Workload Engine        Metrics Engine
             │                      │                      │
             └──────────────────────┼──────────────────────┘
                                    │
        ┌───────────────┬───────────┼───────────┬───────────────┐
        ▼               ▼           ▼           ▼               ▼
    CognoDB           Neo4j      Memgraph    FalkorDB      Database 5
        │               │           │           │               │
        └───────────────┴───────────┼───────────┴───────────────┘
                                    ▼
                         Raw Results: JSON / CSV
                                    │
                                    ▼
                           Results Processing
                                    │
                                    ▼
                             Charts / Reports
```

---

# 🛠️ Technology Stack

## Backend

| Technology        | Purpose                              |
| ----------------- | ------------------------------------ |
| Java 17           | Application language                 |
| Spring Boot       | REST API and benchmark orchestration |
| Maven             | Dependency/build management          |
| Neo4j Java Driver | Bolt/Cypher communication            |
| OpenCypher/Cypher | Graph query workloads                |
| JUnit             | Automated testing                    |

## Frontend

| Technology         | Purpose                    |
| ------------------ | -------------------------- |
| React.js           | Benchmark interface        |
| Vite               | Frontend development/build |
| Axios              | API communication          |
| Tailwind CSS / CSS | Interface styling          |

## Benchmarking

| Technology              | Purpose                                    |
| ----------------------- | ------------------------------------------ |
| Java timing APIs        | Latency measurement                        |
| CSV                     | Dataset/results interchange                |
| JSON                    | Raw benchmark results                      |
| Python / plotting tools | Optional result analysis and visualization |

---

# 🗄️ Databases Tested

The benchmark is designed to compare the following platforms:

| Database       | Deployment                          | Role                     |
| -------------- | ----------------------------------- | ------------------------ |
| CognoDB Cloud  | Managed cloud                       | Primary benchmark target |
| Neo4j          | Managed/self-hosted equivalent tier | Comparison               |
| Memgraph       | Managed/self-hosted equivalent tier | Comparison               |
| FalkorDB       | Managed/self-hosted equivalent tier | Comparison               |
| `<DATABASE_5>` | `<DEPLOYMENT>`                      | Comparison               |

> **Important:** Exact instance specifications and deployment configurations used for the final experiment are documented in the Environment section below.

---

# ⚖️ Fairness and Resource Methodology

Exact hardware parity between different cloud providers is not always possible because providers expose different instance models and resource controls.

Therefore, this benchmark uses the following strategy:

* Prefer free or entry-level configurations.
* Use equivalent CPU limits where possible.
* Use equivalent memory limits where possible.
* Keep storage within the smallest practical common limit.
* Use the same dataset for all platforms.
* Use the same client machine for all experiments.
* Use the same client-side benchmark code.
* Use the same geographic region or the closest available region.
* Document every difference between providers.

### Resource Configuration

| Database       |       CPU |       RAM |   Storage | Region     | Deployment | Cost |
| -------------- | --------: | --------: | --------: | ---------- | ---------- | ---- |
| CognoDB        | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<REGION>` | Cloud      | Free |
| Neo4j          | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<REGION>` | `<TYPE>`   | Free |
| Memgraph       | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<REGION>` | `<TYPE>`   | Free |
| FalkorDB       | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<REGION>` | `<TYPE>`   | Free |
| `<DATABASE_5>` | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<REGION>` | `<TYPE>`   | Free |

### Resource Parity Caveat

Perfect hardware parity cannot be guaranteed across independent cloud providers.

Any deviation from the target resource configuration is recorded rather than hidden.

---

# 📊 Dataset

## Dataset Used

**SNAP Pokec Social Network Dataset**

Source:

* SNAP — Stanford Network Analysis Project
* Dataset: Pokec social network

Dataset source:

`<INSERT OFFICIAL DATASET URL>`

### Dataset Size Used

| Property          |                 Value |
| ----------------- | --------------------: |
| Nodes             |      `<ACTUAL COUNT>` |
| Relationships     |      `<ACTUAL COUNT>` |
| Dataset sample    |       `<DESCRIPTION>` |
| File format       |                   CSV |
| Node type         |           Person/User |
| Relationship type | `<RELATIONSHIP TYPE>` |

The benchmark uses a dataset containing at least **100,000 relationships**, as required by the assignment.

The exact dataset sample and preprocessing steps are documented so that another user can reproduce the same dataset.

---

# 📥 Dataset Loading

The same logical dataset is loaded into every database.

The loading process consists of:

```text
Download dataset
      ↓
Validate dataset
      ↓
Prepare node data
      ↓
Prepare relationship data
      ↓
Connect to database
      ↓
Batch insert nodes
      ↓
Batch insert relationships
      ↓
Verify node count
      ↓
Verify relationship count
```

### Loading Method

Where supported, the benchmark uses batched driver operations.

For every database, the README records:

* Loading mechanism
* Batch size
* Number of nodes loaded
* Number of relationships loaded
* Total load duration
* Nodes/second
* Relationships/second
* Errors or failed batches

---

# 🔥 Benchmark Workloads

Each database is tested using the same logical workload categories.

---

## 1. Data Ingestion

Measures:

* Total load time
* Nodes/second
* Relationships/second

### Formula

```text
Nodes/sec =
Number of nodes / Total load time

Relationships/sec =
Number of relationships / Total load time
```

### Results

| Database       |  Nodes/sec | Relationships/sec | Total Load Time |
| -------------- | ---------: | ----------------: | --------------: |
| CognoDB        | `<RESULT>` |        `<RESULT>` |      `<RESULT>` |
| Neo4j          | `<RESULT>` |        `<RESULT>` |      `<RESULT>` |
| Memgraph       | `<RESULT>` |        `<RESULT>` |      `<RESULT>` |
| FalkorDB       | `<RESULT>` |        `<RESULT>` |      `<RESULT>` |
| `<DATABASE_5>` | `<RESULT>` |        `<RESULT>` |      `<RESULT>` |

---

# 2. Graph Traversal Benchmark

Three traversal depths are measured:

* 1-hop
* 2-hop
* 3-hop

Start nodes are selected from the dataset.

The same logical traversal is executed on each database.

### Example 1-hop

```cypher
MATCH (a)-[r]->(b)
WHERE a.id = $id
RETURN b
```

### Example 2-hop

```cypher
MATCH (a)-[*2]->(b)
WHERE a.id = $id
RETURN b
```

### Example 3-hop

```cypher
MATCH (a)-[*3]->(b)
WHERE a.id = $id
RETURN b
```

> Query syntax may differ between platforms. When syntax differs, the benchmark preserves the same logical operation and documents the implementation difference.

---

## Traversal Results

| Database       |  1-hop p50 |  1-hop p95 |  2-hop p50 |  2-hop p95 |  3-hop p50 |  3-hop p95 |
| -------------- | ---------: | ---------: | ---------: | ---------: | ---------: | ---------: |
| CognoDB        | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Neo4j          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Memgraph       | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| FalkorDB       | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| `<DATABASE_5>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |

All latency values are reported in milliseconds.

---

# 3. Point Lookup

A point lookup retrieves a node using a unique identifier.

Example:

```cypher
MATCH (n:Person {id: $id})
RETURN n
```

### Measurements

* p50 latency
* p95 latency
* Number of successful requests
* Number of failures

### Results

| Database       |   p50 (ms) |   p95 (ms) |     Errors |
| -------------- | ---------: | ---------: | ---------: |
| CognoDB        | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Neo4j          | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Memgraph       | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| FalkorDB       | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| `<DATABASE_5>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |

---

# 4. Indexed / Filtered Lookup

An indexed or filtered property lookup is performed using a property available in the dataset.

Example:

```cypher
MATCH (n:Person)
WHERE n.<PROPERTY> = $value
RETURN n
```

### Index Configuration

| Database       | Indexed Property | Index Type | Notes     |
| -------------- | ---------------- | ---------- | --------- |
| CognoDB        | `<PROPERTY>`     | `<TYPE>`   | `<NOTES>` |
| Neo4j          | `<PROPERTY>`     | `<TYPE>`   | `<NOTES>` |
| Memgraph       | `<PROPERTY>`     | `<TYPE>`   | `<NOTES>` |
| FalkorDB       | `<PROPERTY>`     | `<TYPE>`   | `<NOTES>` |
| `<DATABASE_5>` | `<PROPERTY>`     | `<TYPE>`   | `<NOTES>` |

### Results

| Database       |   p50 (ms) |   p95 (ms) |     Errors |
| -------------- | ---------: | ---------: | ---------: |
| CognoDB        | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Neo4j          | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Memgraph       | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| FalkorDB       | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| `<DATABASE_5>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |

---

# 5. Aggregation Benchmark

The aggregation workload measures operations over graph data.

Example:

```cypher
MATCH (n:Person)
RETURN count(n)
```

Where supported, a grouping workload is also used:

```cypher
MATCH (n:Person)
RETURN n.<PROPERTY>, count(*) AS total
ORDER BY total DESC
```

### Results

| Database       |   p50 (ms) |   p95 (ms) |     Errors |
| -------------- | ---------: | ---------: | ---------: |
| CognoDB        | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Neo4j          | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Memgraph       | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| FalkorDB       | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| `<DATABASE_5>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |

---

# 6. Mixed Read/Write Workload

The mixed workload simulates concurrent application traffic.

Example workload mix:

```text
70% reads
30% writes
```

The benchmark measures:

* Queries per second
* p50 latency
* p95 latency
* Successful operations
* Failed operations
* Timeouts

## Concurrency Sweep

The benchmark is run at:

```text
1 client
10 clients
40 clients
```

### Results

| Database       | Clients |        QPS |        p50 |        p95 |     Errors |
| -------------- | ------: | ---------: | ---------: | ---------: | ---------: |
| CognoDB        |       1 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| CognoDB        |      10 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| CognoDB        |      40 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Neo4j          |       1 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Neo4j          |      10 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Neo4j          |      40 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Memgraph       |       1 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Memgraph       |      10 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Memgraph       |      40 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| FalkorDB       |       1 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| FalkorDB       |      10 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| FalkorDB       |      40 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| `<DATABASE_5>` |       1 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| `<DATABASE_5>` |      10 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| `<DATABASE_5>` |      40 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |

---

# 7. Resource Footprint

Resource information is collected wherever the platform exposes it.

The benchmark does not claim to measure resources that are not observable.

| Database       | Instance Size |       CPU |       RAM |   Storage | Runtime Usage |
| -------------- | ------------- | --------: | --------: | --------: | ------------- |
| CognoDB        | `<VALUE>`     | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<VALUE>`     |
| Neo4j          | `<VALUE>`     | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<VALUE>`     |
| Memgraph       | `<VALUE>`     | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<VALUE>`     |
| FalkorDB       | `<VALUE>`     | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<VALUE>`     |
| `<DATABASE_5>` | `<VALUE>`     | `<VALUE>` | `<VALUE>` | `<VALUE>` | `<VALUE>`     |

If a resource metric cannot be observed:

```text
Not observable through the provider's available interface.
```

---

# ⏱️ Measurement Methodology

## Warm-up

Each database is warmed up before measurements are collected.

```text
Connection
    ↓
Warm-up queries
    ↓
Discard warm-up measurements
    ↓
Measured workload
```

Warm-up iterations:

```text
<NUMBER>
```

---

## Measured Iterations

Each read workload is executed for at least:

```text
100 iterations
```

The raw latency for every iteration is preserved.

---

# 📐 Statistical Metrics

The benchmark reports:

### p50

The median latency.

Approximately 50% of requests complete at or below this value.

### p95

The latency below which approximately 95% of requests complete.

p95 is particularly useful for identifying slower tail requests that are hidden by an average.

---

# 🧪 Benchmark Environment

All benchmark runs are performed using the same client environment.

| Property       | Value           |
| -------------- | --------------- |
| Client OS      | `<OS>`          |
| CPU            | `<CPU>`         |
| RAM            | `<RAM>`         |
| Java           | `<VERSION>`     |
| Node.js        | `<VERSION>`     |
| Maven          | `<VERSION>`     |
| Client region  | `<REGION>`      |
| Benchmark date | `<DATE>`        |
| Network        | `<DESCRIPTION>` |

---

# 🔄 Reproducibility

The benchmark is designed so that another developer can reproduce the experiment.

## Requirements

Install:

* Java 17+
* Maven 3.9+
* Node.js 18+
* npm
* Git

---

# 🔐 Environment Variables

Credentials are never committed to the repository.

Create a local `.env` file.

Example:

```env
COGNODB_URI=
COGNODB_USERNAME=cognodb
COGNODB_PASSWORD=

NEO4J_URI=
NEO4J_USERNAME=
NEO4J_PASSWORD=

MEMGRAPH_URI=
MEMGRAPH_USERNAME=
MEMGRAPH_PASSWORD=

FALKORDB_URI=
FALKORDB_USERNAME=
FALKORDB_PASSWORD=

DATABASE5_URI=
DATABASE5_USERNAME=
DATABASE5_PASSWORD=
```

Never commit:

```text
.env
```

---

# 🚀 Running the Backend

```bash
cd backend
```

Build:

```bash
./mvnw clean install
```

Run:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

---

# 🚀 Running the Frontend

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run:

```bash
npm run dev
```

The frontend runs on:

```text
http://localhost:5173
```

---

# ▶️ Running the Benchmark

After configuring database credentials:

```bash
<INSERT BENCHMARK COMMAND>
```

Example:

```bash
<INSERT COMMAND>
```

The benchmark should:

1. Validate database connections.
2. Load the dataset.
3. Verify node and relationship counts.
4. Warm up the database.
5. Execute workloads.
6. Collect latency measurements.
7. Calculate p50/p95.
8. Execute concurrency tests.
9. Save raw results.
10. Generate processed reports.

---

# 📁 Project Structure

```text
Graph-Database-Cloud-Benchmarking-System/
│
├── frontend/
│   └── src/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │               └── graphbenchmark/
│   │                   ├── benchmark/
│   │                   ├── connector/
│   │                   ├── config/
│   │                   ├── controller/
│   │                   ├── dataset/
│   │                   ├── metrics/
│   │                   ├── report/
│   │                   ├── service/
│   │                   └── workload/
│   │
│   ├── datasets/
│   └── reports/
│
├── results/
│   ├── raw/
│   ├── processed/
│   └── charts/
│
├── api_docs/
│
├── .env.example
├── .gitignore
└── README.md
```

---

# 📦 Raw Results

Raw benchmark measurements are preserved in machine-readable formats.

Example:

```json
{
  "database": "cognodb",
  "workload": "two-hop",
  "iterations": 100,
  "p50_ms": 0,
  "p95_ms": 0,
  "errors": 0
}
```

Raw results should not be manually edited after a benchmark run.

---

# 📈 Visualizations

The benchmark produces charts for:

* Data ingestion throughput
* 1-hop latency
* 2-hop latency
* 3-hop latency
* Point lookup latency
* Indexed lookup latency
* Aggregation latency
* QPS versus concurrency
* Error rates

Charts are stored under:

```text
results/charts/
```

---

# 🔍 Analysis

The results should be interpreted as observations from the tested configurations rather than universal database rankings.

## Example analysis structure

### Ingestion

Describe which systems achieved the highest and lowest ingestion throughput and discuss possible reasons such as batching, transaction overhead, network latency, or provider throttling.

### Traversals

Compare how latency changes from:

```text
1-hop → 2-hop → 3-hop
```

Discuss whether deeper traversals increase latency consistently across platforms.

### Lookups

Compare point lookups with indexed/filtered lookups and explain how indexing affects performance.

### Aggregations

Discuss the performance of count/grouping operations and possible differences in query execution.

### Mixed Workloads

Compare throughput at:

```text
1 client
10 clients
40 clients
```

Discuss how each platform behaves as concurrency increases.

### Overall Interpretation

The benchmark does not identify a universal winner.

Results depend on:

* Dataset characteristics
* Instance resources
* Query patterns
* Index configuration
* Concurrency
* Network conditions
* Provider limits
* Storage implementation
* Query engine behavior

---

# ⚠️ Limitations and Caveats

The following limitations are considered when interpreting the results:

### Cloud resource differences

Cloud providers do not expose identical hardware configurations.

### Network variability

Database requests may experience network latency and temporary congestion.

### Free-tier restrictions

Free or entry tiers may have:

* CPU throttling
* Memory limitations
* Connection limits
* Storage limits
* Automatic suspension
* Throughput restrictions

### Query-language differences

Some databases may implement Cypher/OpenCypher differently.

Where exact syntax differs, equivalent logical workloads are used and the difference is documented.

### Resource observability

Some platforms expose detailed resource metrics while others do not.

Unobservable values are reported as:

```text
Not observable
```

rather than estimated.

### Benchmark scope

The results represent the tested dataset, workloads, hardware configurations, and benchmark environment.

They should not be interpreted as a universal ranking of graph databases.

---

# ❌ Failed Runs and Anomalies

Failed runs are not silently removed.

Each failed run should record:

| Database     | Workload     | Result | Reason     | Action     |
| ------------ | ------------ | ------ | ---------- | ---------- |
| `<DATABASE>` | `<WORKLOAD>` | Failed | `<REASON>` | `<ACTION>` |

Examples of documented anomalies:

* Connection timeout
* Provider throttling
* Query timeout
* Temporary service interruption
* Resource exhaustion
* Dataset loading failure

---

# 🧪 Testing

Backend tests:

```bash
cd backend
./mvnw test
```

Testing includes:

* Connector tests
* Dataset loading tests
* Workload tests
* Metric calculation tests
* Controller tests
* Error handling tests

---

# 🔐 Security

Never commit:

```text
Database passwords
API keys
Connection credentials
Private tokens
.env files
```

Credentials are loaded from environment variables.

Example:

```text
.env
```

is intentionally excluded through `.gitignore`.

---

# 📝 Reproducibility Checklist

Before publishing benchmark results, verify:

* [ ] Same dataset used for every database
* [ ] Exact dataset size recorded
* [ ] Dataset source documented
* [ ] Database instance specifications recorded
* [ ] Client environment recorded
* [ ] Database regions recorded
* [ ] Indexes documented
* [ ] Warm-up performed
* [ ] At least 100 measured read iterations
* [ ] p50 calculated
* [ ] p95 calculated
* [ ] 1-hop tested
* [ ] 2-hop tested
* [ ] 3-hop tested
* [ ] Point lookup tested
* [ ] Indexed lookup tested
* [ ] Aggregation tested
* [ ] Mixed workload tested
* [ ] 1-client workload tested
* [ ] 10-client workload tested
* [ ] 40-client workload tested
* [ ] Raw results preserved
* [ ] Failed runs documented
* [ ] Provider limitations documented
* [ ] No credentials committed

---

# 📊 Final Results Summary

Replace the placeholders below only after completing the benchmark.

| Metric             |    CognoDB |      Neo4j |   Memgraph |   FalkorDB | Database 5 |
| ------------------ | ---------: | ---------: | ---------: | ---------: | ---------: |
| Load time          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Nodes/sec          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Relationships/sec  | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| 1-hop p50          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| 1-hop p95          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| 2-hop p50          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| 2-hop p95          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| 3-hop p50          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| 3-hop p95          | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Point lookup p50   | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Point lookup p95   | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Indexed lookup p50 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Indexed lookup p95 | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Aggregation p50    | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Aggregation p95    | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |
| Mixed workload QPS | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` | `<RESULT>` |

---

# 💡 Key Findings

After the final benchmark is completed, summarize the main findings here.

### Finding 1

`<Describe the most important measured observation.>`

### Finding 2

`<Describe a notable traversal/lookup/aggregation result.>`

### Finding 3

`<Describe how performance changed with concurrency.>`

### Finding 4

`<Describe any major provider limitation or anomaly.>`

---

# 🧭 What the Results Do and Do Not Show

## The benchmark shows

* Relative performance under the tested configuration.
* Behavior of graph traversals at different depths.
* Lookup and aggregation performance.
* Ingestion throughput.
* Behavior under concurrent mixed workloads.
* Observable resource characteristics.

## The benchmark does not show

* Universal database superiority.
* Performance for every possible graph workload.
* Performance on every hardware configuration.
* Long-term production reliability.
* Performance at very large graph sizes beyond the tested dataset.

---

# 🚀 Future Improvements

Possible extensions include:

* Automated cloud instance provisioning
* Docker-based reproducible environments
* Additional graph databases
* Larger datasets
* More workload types
* Automated CI benchmark runs
* Cold-start benchmarking
* Longer-duration stress tests
* Statistical confidence intervals
* Automated regression detection
* Public benchmark result history

---

# 👨‍💻 Author

**Akhila Chinta**

This project was developed for technical evaluation and benchmarking research.

---

# 📜 License

This project is provided for educational and technical evaluation purposes.

---

# ⭐ Final Note

Benchmark results are presented with an emphasis on **reproducibility, fairness, and transparency**.

If a platform performs poorly, fails a workload, exposes fewer resource metrics, or has a provider-specific limitation, the result is documented rather than removed.

The objective is to understand the observed behavior — not to manufacture a winner.
