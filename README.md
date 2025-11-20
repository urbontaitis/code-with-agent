# Java + Claude Code: The AI-Assisted Dev Flow

A comprehensive 4-hour workshop exploring AI-assisted Java development with Claude Code. Learn to leverage Claude Code as your pair programming partner while building a modern Spring Boot application.

## Workshop Overview

This hands-on workshop teaches you how to:
- Write effective prompts that generate production-ready code
- Master Claude Code CLI commands and custom templates
- Build event-driven architectures with Kafka
- Manage database schema evolution with Flyway
- Write comprehensive tests using Spock Framework
- Refactor code using modern Java 25 features
- Optimize token usage and development workflow

**Duration:** 4 hours (theory + practice)
**Level:** Intermediate Java developers
**Prerequisites:** Java 25, Docker, basic Spring Boot knowledge

## Technology Stack

- **Java 25** with modern features (records, pattern matching, virtual threads)
  - Uses Java 25 toolchain for compatibility with Groovy 4.0
  - Gradle runs with Java 25 (Temurin)
- **Spring Boot 3.5.0** for application framework
- **PostgreSQL 17** as the primary database
- **Testcontainers** for integration testing with PostgreSQL
- **Gradle 9** for build automation
- **Spock Framework** (Groovy 4.0) for testing
- **Flyway** for database migrations

## Workshop Slides

The workshop includes comprehensive terminal-styled slides with presenter notes:

```bash
# Build the slides
marp slides.md -o docs/intex.html --html --allow-local-files

```

## Workshop Exercises

The workshop includes 6 hands-on exercises located in the `exercises/` directory:

1. **Prompt Practice** (20 min) - Master effective prompt engineering
2. **Kafka Integration** (45 min) - Build event-driven architecture
3. **Database Evolution** (30 min) - Schema changes with Flyway migrations
4. **Advanced Spock Testing** (30 min) - Comprehensive testing patterns
5. **Refactoring Modern Java** (40 min) - Apply Java 25 features
6. **Precise Prompting Challenge** (25 min) - Compare prompt quality impact

Each exercise includes:
- Detailed task description
- Step-by-step instructions
- Success criteria checklist
- Testing procedures
- Common issues and solutions

## Prerequisites

### Required Software

* **Java 25**
  ```bash
  # Install SDKMAN
  curl -s "https://get.sdkman.io" | bash
  source "$HOME/.sdkman/bin/sdkman-init.sh"

  # Install Java 25
  sdk install java 25.0.1-tem
  sdk use java 25.0.1-tem
  ```

* **Docker** (for PostgreSQL, Kafka, and Testcontainers)
  - [Docker Desktop](https://www.docker.com/products/docker-desktop/) for Mac/Windows
  - Docker Engine for Linux

* **Claude Code CLI**
  ```bash
  # Install via npm (requires Node.js 18+)
  npm install -g @anthropic-ai/claude-code

  # Verify installation
  claude --version
  ```

* **Node.js 18+** (for Marp slides)
  ```bash
  # Via nvm
  nvm install 18
  nvm use 18
  ```

### Optional but Recommended

* **Git** for version control
* **IDE** with Java support (IntelliJ IDEA, VS Code with Java extensions)
* **Postman/curl** for API testing

## Getting Started

### Running the Application

1. Start PostgreSQL database instance:
   ```bash
   docker compose up -d
   ```

2. Start the application:
   ```bash
   source ~/.sdkman/bin/sdkman-init.sh && sdk use java 25.0.1-tem
   ./gradlew bootRun
   ```

### Executing Tests

Tests use Testcontainers to automatically spin up PostgreSQL containers:

```bash
source ~/.sdkman/bin/sdkman-init.sh && sdk use java 25.0.1-tem
./gradlew clean test
```

## Claude Code Optimization

### Workshop Configuration

This repository includes pre-configured Claude Code settings for optimal Java development:

**`.claude/CLAUDE.md`** - Project-specific guidelines including:
- Data-first design philosophy
- Modern Java 25 feature usage
- Spock testing conventions
- Feature-based package structure
- Code style rules (120 char limit, no JavaDocs)
- KISS & YAGNI principles

**`.claude/commands/`** - Custom reusable commands:
- `/dto` - Generate DTO record with mapper and tests
- `/entity` - Create complete entity feature (migration, repository, service, controller, tests)
- `/flyway` - Generate Flyway migration following PostgreSQL 17 best practices
- `/review` - Automated code review against CLAUDE.md standards
- `/test` - Generate comprehensive Spock tests for existing code

### Using Custom Commands

```bash
# Generate DTO for existing entity
claude /dto
# Claude asks: "Which entity should I create a DTO for?"
# You respond: "Film"
# Claude generates: FilmDto.java, FilmMapper.java, FilmMapperSpec.groovy

# Create database migration
claude /flyway
# Claude asks: "What database change do you need?"
# You respond: "Add ratings table"
# Claude generates: V4__create_ratings_table.sql

# Automated code review
claude /review
# Reviews current file against CLAUDE.md conventions
```

### Global Configuration

To use these conventions in all your Java projects, copy to your global config:

```bash
# Copy CLAUDE.md to home directory
cp .claude/CLAUDE.md ~/.claude/CLAUDE.md

# Copy custom commands
cp -r .claude/commands/* ~/.claude/commands/
```

## Project Structure

```
code-with-agent/
├── .claude/                     # Claude Code configuration
│   ├── CLAUDE.md               # Project guidelines and conventions
│   └── commands/               # Custom reusable commands
│       ├── dto.md             # Generate DTO with mapper
│       ├── entity.md          # Create complete entity feature
│       ├── flyway.md          # Database migration generator
│       ├── review.md          # Code review command
│       └── test.md            # Test generator
├── exercises/                   # Workshop exercises
│   ├── 01-prompt-practice.md
│   ├── 02-kafka-integration.md
│   ├── 03-database-evolution.md
│   ├── 04-spock-testing.md
│   ├── 05-refactoring-modern-java.md
│   └── 06-precise-prompting.md
├── app/                         # Main application module
│   ├── src/main/
│   │   ├── java/               # Java 25 source code
│   │   │   └── com/exacaster/workshop/
│   │   │       └── film/       # Feature package
│   │   └── resources/
│   │       ├── application.yml # PostgreSQL, Kafka config
│   │       └── db/migration/   # Flyway migrations
│   └── src/test/
│       ├── groovy/             # Spock tests
│       │   └── com/exacaster/workshop/
│       │       └── film/
│       └── resources/
│           └── application-it.yml  # Testcontainers config
├── slides.md                    # Marp presentation
├── package.json                 # Marp CLI configuration
├── build.gradle                 # Gradle build configuration
├── settings.gradle              # Project structure
└── docker-compose.yml           # PostgreSQL + Kafka for local dev
```

## Key Features Demonstrated

- Modern Java 25+ syntax and features
- PostgreSQL with Flyway migrations
- Testcontainers for integration testing with automatic container management
- Spock Framework for expressive BDD-style tests
- Virtual threads are enabled for better concurrency
- Feature-based package structure
- Data-first design patterns
- JPA with Spring Data for database access

## Workshop Schedule

### Part 1: Introduction (30 min)
- What is Claude Code and why use it?
- Prerequisites setup verification
- Understanding token economics
- The AI-assisted development workflow

### Part 2: Prompt Engineering (35 min)
- The anatomy of effective prompts
- Reference existing code with `@` notation
- Exercise 1: Prompt Practice (20 min)

**Break** (10 min)

### Part 3: Claude Code Features (50 min)
- Essential CLI commands (`/init`, `/clear`, `/model`, `/usage`)
- Custom commands and templates
- Skills vs Agents comparison
- MCP (Model Context Protocol) introduction

### Part 4: Hands-On Practice (2 hours)
- Exercise 2: Kafka Integration (45 min)
- Exercise 3: Database Evolution (30 min)

**Break** (10 min)

- Exercise 4: Advanced Spock Testing (30 min)
- Exercise 5: Refactoring Modern Java (40 min)

**Break** (10 min)

### Part 5: Mastery (40 min)
- Exercise 6: Precise Prompting Challenge (25 min)
- Best practices and workflow optimization
- Token usage analysis
- Q&A and wrap-up

## Development Philosophy

This workshop teaches modern Java development following these principles:

1. **Data-first approach** - Design data structures before code
2. **Modern Java** - Leverage Java 25 features (records, pattern matching, sealed classes, virtual threads)
3. **Immutability** - Use records and immutable collections
4. **Functional style** - Streams, Optional, and functional patterns
5. **Testcontainers** - Real PostgreSQL/Kafka for integration tests
6. **Self-documenting code** - Clear naming over comments
7. **KISS & YAGNI** - Simple solutions, build what's needed now
8. **Feature packages** - Organize by business capability, not technical layers

## Resources

### Official Documentation
- [Claude Code Documentation](https://docs.anthropic.com/claude-code)
- [Spring Boot 3.5.0](https://docs.spring.io/spring-boot/index.html)
- [Spock Framework](https://spockframework.org/)
- [Testcontainers](https://testcontainers.com/)
- [Flyway](https://flywaydb.org/)

### Community Resources
- [Awesome Claude Skills](https://github.com/travisvn/awesome-claude-skills)
- [Giuseppe's Medium Article](https://medium.com/@giuseppetrisciuoglio/claude-code-one-month-of-practical-experience)

## Contributing

This is a workshop repository. Feel free to:
- Report issues or improvements
- Share your experience
- Suggest additional exercises
- Contribute custom commands

## License

MIT License - Feel free to use this material for your own workshops or learning.

## About the Presenter

**Mindaugas** - Software architect with 10+ years of Java development experience, exploring AI-assisted development workflows and best practices.

---

**Ready to code with AI like a pro? Let's begin!** 🚀
