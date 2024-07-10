[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Concept](https://img.shields.io/badge/Status-Concept-white)](https://unitvectory-labs.github.io/uvy-labs-guide/bestpractices/status/#concept) [![codecov](https://codecov.io/gh/UnitVectorY-Labs/serviceauditreport/graph/badge.svg?token=OI0wXYykNb)](https://codecov.io/gh/UnitVectorY-Labs/serviceauditreport)

# serviceauditreport
A tool for auditing and reporting on the configuration compliance of various services

This project is under development and is in a non-working state.

## Process

The serviceauditreport application is comprised of threee independent applications that are used in conjunction to generate the reports.  This divides the work into three distinct phases.

**Collector**: The collector is responsible for calling out to services to collect data.  This data is stored for use in the successive phases.

**Analyzer**: The analyzer loads the data that was stored in the collection phase and generates additional derived data that is built on top of the original data.

**Reporter**: The reporter loads in the data from both the collection and analyzer phases and generates reports that are the final product for consumption.

```mermaid
sequenceDiagram
    participant Service
    participant Collector
    participant Analyzer
    participant Reporter
    participant Storage

    note over Collector: Collection Phase
    Collector ->> Service: Call Services
    Service ->> Collector: Collect Data
    Collector ->> Storage: Save Collected Data

    note over Analyzer: Analysis Phase
    Analyzer ->> Storage: Read Data
    Storage ->> Analyzer: Load Data
    Analyzer ->> Analyzer: Run Algorithms
    Analyzer ->> Storage: Save Analysis Output
    
    note over Reporter: Reporter Phase
    Reporter ->> Storage: Read Data
    Storage ->> Reporter: Load Data
    Reporter ->> Reporter: Generate Reports
    Reporter ->> Storage: Save Analysis Output
```
