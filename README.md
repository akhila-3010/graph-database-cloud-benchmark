# Graph Database Cloud Benchmarking System

A full-stack benchmarking platform that evaluates and compares **CognoDB Cloud** against other managed graph database platforms using an identical dataset, identical workloads, and a single reproducible measurement methodology.

Built for the Wexa AI take-home assignment: *"Benchmark CognoDB Cloud against other managed graph database platforms and publish a reproducible, honest comparison."* The goal is not to declare a winner — it's fair methodology, full automation, and honest reporting.

**Live demo:** graph-database-cloud-benchmarking-s.vercel.app

---

## Table of Contents

- [Project Objective](#project-objective)
- [Databases Compared](#databases-compared)
- [System Architecture](#system-architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Dataset](#dataset)
- [Resource Parity (Fairness Setup)](#resource-parity-fairness-setup)
- [Benchmark Methodology](#benchmark-methodology)
- [Required Metrics](#required-metrics)
- [Results](#results)
- [Analysis](#analysis)
- [Caveats & Limitations](#caveats--limitations)
- [Environment Configuration](#environment-configuration)
- [Running the Backend](#running-the-backend)
- [Running the Frontend](#running-the-frontend)
- [Reproducing the Benchmarks End-to-End](#reproducing-the-benchmarks-end-to-end)
- [API Modules](#api-modules)
- [Testing](#testing)
- [Screenshots](#screenshots)
- [Future Improvements](#future-improvements)

---

## Project Objective

Graph databases are optimized for highly connected data where relationship traversal — not just row lookup — is the primary access pattern. This project measures, under **equal resource constraints**, how CognoDB Cloud compares to other managed/self-hosted graph platforms on:

- Data ingestion speed
- Graph traversal performance (1-hop, 2-hop, 3-hop)
- Point and indexed lookup performance
- Aggregation performance
- Concurrent mixed read/write throughput
- Observable resource footprint

Every result is produced by an automated harness so the numbers are reproducible by anyone with free-tier accounts on the same platforms.

## Databases Compared

| # | Platform | Tier used | Role |
|---|----------|-----------|------|
| 1 | **CognoDB Cloud** | Free (c0) — burstable 0.5 vCPU / 256 MB RAM / 1 GB disk | Subject of the benchmark |
| 2 | Neo4j (AuraDB Free) | Free tier, matched to CognoDB's spec | Comparison |
| 3 | Memgraph | Self-hosted, container capped to 0.5 vCPU / 256 MB RAM | Comparison |
| 4 | FalkorDB | Self-hosted, container capped to 0.5 vCPU / 256 MB RAM | Comparison |
| 5 | *[Add 5th platform — e.g. ArangoDB Free / Amazon Neptune Serverless minimum / TigerGraph Cloud free tier]* | Free tier, matched to CognoDB's spec | Comparison |

> The assignment requires CognoDB plus **at least four** other platforms. Replace row 5 with whichever platform you actually run — see [Resource Parity](#resource-parity-fairness-setup) for why it was chosen.

**Why these platforms:** all expose an OpenCypher-compatible or Bolt-compatible driver interface (or a documented equivalent), all offer a free/low-cost tier that can be capped to comparable resources, and together they span fully-managed (CognoDB, Neo4j AuraDB), self-hosted-but-resource-capped (Memgraph, FalkorDB), and *[reason for 5th]* deployment models — giving a fair spread rather than comparing only similar architectures.

## System Architecture

```
                    User
                     |
              React Frontend
                     |
              REST API Layer
                     |
            Spring Boot Backend
                     |
 ------------------------------------------------
 |              |              |                 |
Connector    Dataset      Benchmark        Report
Layer        Loader       Engine           Generator
 |
 ------------------------------------------------
 |              |              |              |              |
CognoDB      Neo4j        Memgraph       FalkorDB      <5th DB>
```

## Technology Stack

### Frontend

| Technology | Purpose |
|---|---|
| React.js | User interface |
| React Router | Application navigation |
| Axios | REST API communication |
| Vite | Frontend development/build |
| CSS/Tailwind | UI styling |

### Backend

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot | REST API framework |
| Maven | Build management |
| Neo4j Bolt Driver | Bolt protocol communication (CognoDB, Neo4j, Memgraph, FalkorDB all speak Bolt/OpenCypher) |
| OpenCypher | Graph query language |
| JUnit | Testing |

## Project Structure

```
frontend/
  src/
    components/   # Navbar, Footer, Loader, Error Components
    pages/        # Home, Connection, Dataset, Verification, Benchmark, Report
    services/     # API Service
    App.jsx

backend/
  src/main/java/com/graphbenchmark/
    controller/   # BenchmarkController, DatasetController, ReportController, VerificationController
    connector/    # GraphDatabaseConnector, CognoDBConnector, Neo4jConnector, MemgraphConnector, FalkorDBConnector, <FifthDbConnector>
    benchmark/    # BenchmarkSuite
    workload/     # TraversalWorkload, LookupWorkload, AggregationWorkload, MixedWorkload
    dataset/      # CsvDatasetLoader
    metrics/      # Latency/throughput collectors, percentile calculators
    report/       # Results matrix + chart generation
    service/
    BackendApplication.java
```

## Dataset

- **Source:** SNAP `soc-Pokec` social network dataset
- **File:** `datasets/pokec_edges_sample_100k.csv`
- **Size:** *[fill in exact node count]* nodes, **100,000+** relationships (within the assignment's 100k–500k target range)
- **Load method:** identical CSV batch-load via the Neo4j Bolt driver's batched `UNWIND` writes for every platform (documented per-platform in `backend/src/main/java/com/graphbenchmark/dataset/CsvDatasetLoader.java`)
- **Loaded identically** into all five platforms — same nodes, same relationships, same properties, same indexes where the platform supports them

## Resource Parity (Fairness Setup)

Per the assignment's fairness rule, every platform is run on matched resources so no database gets a hardware advantage.

| Platform | vCPU | RAM | Disk | Region/Host |
|---|---|---|---|---|
| CognoDB Cloud (c0 free) | 0.5 (burstable) | 256 MB | 1 GB | *[region]* |
| Neo4j AuraDB Free | 0.5 (burstable) | 256 MB (advertised free-tier limit) | 1 GB | *[region]* |
| Memgraph (self-hosted, capped) | 0.5 (cgroup limit) | 256 MB (container limit) | 1 GB (volume limit) | *[host]* |
| FalkorDB (self-hosted, capped) | 0.5 (cgroup limit) | 256 MB (container limit) | 1 GB (volume limit) | *[host]* |
| *[5th platform]* | 0.5 | 256 MB | 1 GB | *[region/host]* |

All benchmark clients ran from the same machine (*[spec]*) in/near the same region as the databases to keep network variance consistent across platforms.

## Benchmark Methodology

- **Same dataset** — identical nodes, relationships, and properties loaded into every platform.
- **Same queries** — identical logical Cypher/OpenCypher workloads executed against every platform.
- **Same client environment** — one client machine, documented specs, same region where the platform allows region selection.
- **Warm-up before measurement** — 10 warm-up iterations discarded before each workload; 100 measured iterations per read workload; cold-start numbers (if collected) reported separately.
- **Everything automated** — one harness (`BenchmarkSuite` + per-workload runners) drives load → verify → benchmark → report for every platform from a single command.
- **Concurrency sweep** — mixed workload run at 1 / 10 / 40 concurrent clients where the tier allows it.
- **Every caveat recorded** — free-tier throttling, timeouts, failed runs, and query-language differences are logged in [Caveats & Limitations](#caveats--limitations) rather than hidden.

## Required Metrics

| Category | Metric | What's reported |
|---|---|---|
| Data loading | Ingest throughput | Nodes/sec, relationships/sec, total wall-clock load time |
| Traversals | 1-hop / 2-hop / 3-hop | p50 and p95 latency (ms) per hop depth |
| Lookups | Point lookup, indexed/filtered lookup | p50 and p95 latency (ms); indexed properties noted per platform |
| Aggregations | Count / group-by | p50 and p95 latency (ms) over a label or relationship type |
| Mixed workload | Concurrent read/write throughput | Sustained queries/sec at stated concurrency and read/write mix |
| Footprint | Resource usage | Stored data size, memory usage, instance specs — "not observable" where the platform doesn't expose it |

## Results

> Fill in after each benchmark run. Keep every row for every platform — an empty cell should say *not observable* or *failed*, never be left blank.

### Data Loading

| Platform | Nodes/sec | Relationships/sec | Total load time |
|---|---|---|---|
| CognoDB Cloud | | | |
| Neo4j AuraDB Free | | | |
| Memgraph | | | |
| FalkorDB | | | |
| *[5th]* | | | |

### Traversals (p50 / p95, ms)

| Platform | 1-hop | 2-hop | 3-hop |
|---|---|---|---|
| CognoDB Cloud | / | / | / |
| Neo4j AuraDB Free | / | / | / |
| Memgraph | / | / | / |
| FalkorDB | / | / | / |
| *[5th]* | / | / | / |

### Lookups (p50 / p95, ms)

| Platform | Point lookup | Indexed/filtered lookup | Indexed property |
|---|---|---|---|
| CognoDB Cloud | / | / | |
| Neo4j AuraDB Free | / | / | |
| Memgraph | / | / | |
| FalkorDB | / | / | |
| *[5th]* | / | / | |

### Aggregations (p50 / p95, ms)

| Platform | Count/group-by latency |
|---|---|
| CognoDB Cloud | / |
| Neo4j AuraDB Free | / |
| Memgraph | / |
| FalkorDB | / |
| *[5th]* | / |

### Mixed Workload (queries/sec)

| Platform | 1 client | 10 clients | 40 clients | Read/write mix |
|---|---|---|---|---|
| CognoDB Cloud | | | | |
| Neo4j AuraDB Free | | | | |
| Memgraph | | | | |
| FalkorDB | | | | |
| *[5th]* | | | | |

### Footprint

| Platform | Stored data size | Memory usage | Notes |
|---|---|---|---|
| CognoDB Cloud | | | |
| Neo4j AuraDB Free | | | |
| Memgraph | | | |
| FalkorDB | | | |
| *[5th]* | | | |

## Analysis

*[Write 1–3 short paragraphs once results are in. Cover: which platforms led on which workload and by roughly how much; whether managed vs. self-hosted made a visible difference under matched resource caps; whether traversal depth (1→2→3 hop) degraded similarly or differently across platforms; and any plausible root cause — e.g. storage engine, index strategy, driver overhead, or network path — rather than just restating the numbers.]*

## Caveats & Limitations

- Free-tier CPU on managed platforms is typically burstable, not guaranteed — throughput can vary run to run even with fixed nominal specs.
- Network latency to each platform's region differs slightly even when "same region" was targeted, since not all platforms offer the same region list.
- Some platforms' Cypher dialects differ in minor ways (e.g. index syntax); query text is kept logically identical but not always byte-identical — differences are noted inline in the workload code where they exist.
- Resource usage/footprint is only as observable as each platform's console/API exposes; anything not exposed is marked "not observable" rather than estimated.
- *[Add any run-specific issues: timeouts, failed iterations, throttling events, retries.]*

## Environment Configuration

Database credentials must never be committed. Create a `.env` (or backend `application.yml` override) with:

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

FIFTHDB_URI=
FIFTHDB_USERNAME=
FIFTHDB_PASSWORD=
```

The Spring configuration reads all connection details from environment variables only — no credentials or connection URIs are stored in the repository.

## Running the Backend

**Requirements:** Java 17+, Maven 3.9+, Git

```
java -version
mvn -version
```

```
cd backend
mvn clean install
mvn spring-boot:run
```

Backend runs at `http://localhost:8080`.

## Running the Frontend

**Requirements:** Node.js 18+, npm

```
cd frontend
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`.

## Reproducing the Benchmarks End-to-End

1. Create free-tier accounts on all five platforms and populate `.env` per [Environment Configuration](#environment-configuration).
2. Start the backend (`mvn spring-boot:run`).
3. From the frontend or via the Benchmark API, trigger, in order, for each platform: **Connect → Load Dataset → Verify → Run Workloads → Generate Report**.
4. The report module writes the full results matrix (data loading, traversals, lookups, aggregations, mixed workload, footprint) to the Report page and to `backend/reports/`.
5. Copy the generated numbers into the [Results](#results) tables above.

Execution flow:

```
Start Application
        |
Connect Database
        |
Load Dataset
        |
Verify Graph
        |
Execute Workloads
        |
Collect Metrics
        |
Generate Report
```

## API Modules

| Module | Handles |
|---|---|
| Connection API | Database connection, connection verification |
| Dataset API | Dataset loading, dataset information |
| Verification API | Graph verification, database health check |
| Benchmark API | Benchmark execution, workload execution |
| Report API | Result generation, performance summary |

## Testing

```
cd backend
mvn test
```

Coverage includes controller tests, service tests, connector tests, and benchmark workload tests.

## Screenshots

```
screenshots/
├── dashboard.png
├── connection.png
├── dataset.png
├── verification.png
├── benchmark.png
└── report.png
```

## Future Improvements

- Docker-based benchmark environment for one-command, fully reproducible runs
- Automated cloud deployment of the benchmark harness itself
- Additional graph database connectors beyond the current five
- Real-time benchmark charts in the frontend during a run
- CI/CD pipeline that re-runs benchmarks on a schedule and tracks drift over time
- Advanced visualization dashboard (percentile distributions, not just p50/p95 points)

---

Developed as part of the **Wexa AI Graph Database Cloud Benchmarking** take-home assignment.

**License:** Created for educational and technical evaluation purposes.
