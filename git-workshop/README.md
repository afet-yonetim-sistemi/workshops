# Git Workshop

Welcome to the Git Workshop repository! This repo contains a simple Java project to practice real-world Git workflows.

## Prerequisites

1. Install [Git](https://git-scm.com/).
2. Install [Java 11+](https://adoptopenjdk.net/) and [Maven](https://maven.apache.org/).

## Scenarios

### 1. Create a Feature Branch

- Clone the repository:
```bash
  git clone https://github.com/your-org/git-workshop.git
  cd git-workshop
```

### 2. Pull and Merge Changes from Main

- Pull the latest changes:

```bash
git pull origin main
```

- Merge them into your branch:

```bash
git merge main
```

### 3. Cherry-pick a Security Update

- Cherry-pick the fix from main:

```bash
git cherry-pick <commit-hash>
```

### 4. Merge Conflict Resolution

- Modify Calculator to add missing operation
- Try to push the changes (see that your branch is 3 commits behind)
- Pull the latest changes
- Fix the conflicts in github/IDE or command line
- Push to your branch

### 5. Set up a basic workflow that echoes "Hello World" on every push

- Go to .github/workflows/ci.yml

```yaml
jobs:
  echoer:
    runs-on: ubuntu-latest
    steps:
      - name: Write following echo
        run: echo 'Hello World!'
```