```mermaid
%%{
  init: {
    'theme': 'dark',
    'themeVariables': {
      'primaryColor': '#BB2528',
      'primaryTextColor': '#fff',
      'primaryBorderColor': '#7C0000',
      'lineColor': '#F8B229',
      'secondaryColor': '#006100',
      'tertiaryColor': '#fff'
    }
  }
}%%
graph TD
    subgraph "Monolith Deployment Problem"
        A[Single Line Change in User Module] -->|Triggers| B[Full Build Process]
        
        subgraph "Build Process (30-45 mins)"
            B -->|1| C[Compile All Modules]
            C -->|2| D[Run All Unit Tests]
            D -->|3| E[Run All Integration Tests]
            E -->|4| F[Package Entire Application]
        end
        
        F -->|5| G[Deploy Entire Application]
        
        G --> H{Deployment Success?}
        
        H -->|No| I[Rollback Entire System]
        H -->|Yes| J[Monitor System]
        
        I -->|Problem| K[Debug Entire Codebase]
        J -->|Issue Found| K
        
        subgraph "Affected Modules"
            L[User Module<br/>Minor Change]
            M[Payment Module<br/>No Change]
            N[Inventory Module<br/>No Change]
            O[Order Module<br/>No Change]
        end
        
        G -.->|Impacts| L
        G -.->|Impacts| M
        G -.->|Impacts| N
        G -.->|Impacts| O
    end


```

[02-deployment-coupling-solution-feature-flag.md](02-deployment-coupling-solution-feature-flag.md) \
[02-deployment-coupling-solution-modular-monolith.md](02-deployment-coupling-solution-modular-monolith.md)