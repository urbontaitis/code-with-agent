# Exercise 2: Kafka Integration

**Time:** 45 minutes
**Difficulty:** Intermediate
**Goal:** Build event-driven architecture with Kafka

## Objective

Transform the Film service from REST-only to event-driven architecture using Apache Kafka, while maintaining backward compatibility with existing REST endpoints.

## Background

Modern applications use event-driven architectures for:
- Decoupling services
- Asynchronous processing
- Event sourcing
- Better scalability

You'll add Kafka to publish `FilmCreatedEvent` when films are created.

## Task Overview

Add complete Kafka integration with:
1. Kafka dependencies
2. Docker Compose configuration (Kafka + Kafka UI)
3. Event DTOs (records)
4. Kafka producer
5. Kafka consumer
6. Comprehensive Spock tests with Testcontainers

## Detailed Requirements

### 1. Add Kafka Dependencies (5 minutes)

**Prompt for Claude:**
```
Add Kafka dependencies to build.gradle for:
- Spring Kafka
- Testcontainers Kafka module for testing

Follow Spring Boot 3.5.0 conventions and ensure compatibility
with existing dependencies in @build.gradle
```

**Expected additions to `build.gradle`:**
```groovy
dependencies {
    // Existing dependencies...

    implementation 'org.springframework.kafka:spring-kafka'

    testImplementation 'org.testcontainers:kafka'
    testImplementation 'org.springframework.kafka:spring-kafka-test'
}
```

### 2. Update Docker Compose (10 minutes)

**Prompt for Claude:**
```
Update @docker-compose.yml to add:
1. Kafka broker (Bitnami Kafka image - latest version)
2. Kafka UI (provectuslabs/kafka-ui for monitoring)

Configuration requirements:
- Kafka on port 9092
- Kafka UI on port 8081
- Proper health checks
- Environment variables for development
- Network connectivity with PostgreSQL

Follow docker-compose best practices.
```

**Expected `docker-compose.yml` additions:**
```yaml
services:
  postgres:
    # ... existing config

  kafka:
    image: bitnami/kafka:latest
    ports:
      - "9092:9092"
    environment:
      - KAFKA_CFG_NODE_ID=0
      - KAFKA_CFG_PROCESS_ROLES=controller,broker
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
      - KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka:9093
      - KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER
    healthcheck:
      test: ["CMD", "kafka-topics.sh", "--list", "--bootstrap-server", "localhost:9092"]
      interval: 10s
      timeout: 5s
      retries: 5

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    ports:
      - "8081:8080"
    environment:
      - KAFKA_CLUSTERS_0_NAME=local
      - KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:9092
    depends_on:
      kafka:
        condition: service_healthy
```

### 3. Create Event DTOs (5 minutes)

**Prompt for Claude:**
```
Create FilmCreatedEvent record in film package following CLAUDE.md:
- Record type (immutable)
- Fields: filmId, title, releaseYear, genre, director, timestamp
- JSON serialization annotations
- Validation annotations
- Include JavaDoc explaining event purpose

Reference @Film.java for field types and validation patterns.
```

**Expected:** `FilmCreatedEvent.java` record

### 4. Configure Kafka (5 minutes)

**Prompt for Claude:**
```
Create Kafka configuration in application.yml and
KafkaConfig.java class:

application.yml:
- Bootstrap servers: localhost:9092
- Producer config (JSON serialization)
- Consumer config (JSON deserialization)
- Topic name: film-events

KafkaConfig.java:
- ProducerFactory bean
- ConsumerFactory bean
- KafkaTemplate bean
- Topic creation bean

Follow Spring Boot 3.5 and CLAUDE.md conventions.
```

### 5. Implement Kafka Producer (10 minutes)

**Prompt for Claude:**
```
Create FilmEventProducer service in film package:

Requirements:
- Send FilmCreatedEvent to "film-events" topic
- Use KafkaTemplate
- Async sending with CompletableFuture
- Proper error handling and logging
- Publish event after film is created in FilmService

Update @FilmService.java to:
- Inject FilmEventProducer
- Publish event after successful film creation
- Handle producer failures gracefully
- Keep REST endpoint working (backward compatibility)

Follow CLAUDE.md conventions: Optional returns, proper logging.
```

**Expected:**
- `FilmEventProducer.java` service
- `FilmService.java` updated with event publishing

### 6. Implement Kafka Consumer (10 minutes)

**Prompt for Claude:**
```
Create FilmEventConsumer in film package:

Requirements:
- Listen to "film-events" topic
- Process FilmCreatedEvent messages
- Log received events
- Demonstrate event handling (could update cache, trigger workflows, etc.)
- Error handling with @KafkaListener error handler
- Proper deserialization

Use @KafkaListener annotation and follow Spring Kafka best practices.
Implement idempotency check (don't process same event twice).
```

**Expected:** `FilmEventConsumer.java` with message handling

### 7. Write Comprehensive Tests (15 minutes)

**Prompt for Claude:**
```
Create comprehensive Spock tests for Kafka integration:

FilmEventProducerSpec.groovy:
- Test event publishing with Testcontainers Kafka
- Verify message sent to topic
- Test serialization
- Test error scenarios

FilmServiceSpec.groovy (update existing):
- Verify event published when film created
- Test producer failure handling
- Ensure REST endpoint still works

FilmEventConsumerSpec.groovy:
- Test event consumption with Testcontainers
- Verify message deserialization
- Test error handling
- Use PollingConditions for async verification

Follow CLAUDE.md Spock conventions:
- Given-When-Then structure
- @Testcontainers with @Shared KafkaContainer
- No "private" keyword in Groovy
- Use @Unroll for data-driven tests where applicable
- PollingConditions for async assertions
```

**Expected test files:**
- `FilmEventProducerSpec.groovy`
- `FilmEventConsumerSpec.groovy`
- Updated `FilmServiceSpec.groovy`

## Success Criteria

- [ ] Kafka dependencies added to `build.gradle`
- [ ] `docker-compose.yml` includes Kafka and Kafka UI
- [ ] `FilmCreatedEvent` record created
- [ ] Kafka configuration in `application.yml` and `KafkaConfig.java`
- [ ] `FilmEventProducer` publishes events
- [ ] `FilmService` publishes events on film creation
- [ ] `FilmEventConsumer` processes events
- [ ] Comprehensive Spock tests with Testcontainers
- [ ] All tests pass: `./gradlew test`
- [ ] Manual verification:
  ```bash
  docker compose up -d
  ./gradlew bootRun
  # POST film via REST
  # Check Kafka UI at http://localhost:8081
  # Verify event in "film-events" topic
  ```

## Testing Your Implementation

### 1. Start Infrastructure
```bash
docker compose up -d
# Wait for health checks
docker compose ps
```

### 2. Run Application
```bash
./gradlew bootRun
```

### 3. Create a Film
```bash
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Inception",
    "releaseYear": 2010,
    "genre": "SCI_FI",
    "director": "Christopher Nolan"
  }'
```

### 4. Verify in Kafka UI
1. Open http://localhost:8081
2. Navigate to Topics → film-events
3. Check Messages tab
4. Verify `FilmCreatedEvent` was published

### 5. Check Consumer Logs
```bash
# Should see consumer processing message
tail -f logs/application.log | grep FilmEventConsumer
```

## Common Issues & Solutions

### Issue: Kafka won't start
**Solution:** Check ports 9092 and 8081 aren't in use
```bash
lsof -i :9092
lsof -i :8081
```

### Issue: Producer can't connect
**Solution:** Verify Kafka health
```bash
docker compose exec kafka kafka-broker-api-versions.sh \
  --bootstrap-server localhost:9092
```

### Issue: Messages not consumed
**Solution:** Check consumer group and offset
```bash
# In Kafka UI, check Consumer Groups
# Verify offset is advancing
```

### Issue: Tests fail with Testcontainers
**Solution:** Ensure Docker is running and accessible
```bash
docker ps
# Should show containers
```

## Key Learnings

1. **Event-Driven Architecture**: Asynchronous communication patterns
2. **Kafka Basics**: Topics, producers, consumers, messages
3. **Spring Kafka**: Configuration, templates, listeners
4. **Testcontainers**: Integration testing with real Kafka
5. **Backward Compatibility**: Keep existing APIs while adding events
6. **Async Testing**: PollingConditions for eventual consistency

## Bonus Challenges

### Challenge 1: Add More Events
Create `FilmUpdatedEvent` and `FilmDeletedEvent`, publish from service.

### Challenge 2: Event Schema Evolution
Add a version field to events and handle multiple versions in consumer.

### Challenge 3: Dead Letter Queue
Add error handling that sends failed messages to a DLQ topic.

### Challenge 4: Saga Pattern
Implement a simple saga with Film creation and notification service.

## Time Breakdown

- 5 min: Dependencies
- 10 min: Docker Compose
- 5 min: Event DTOs
- 5 min: Kafka configuration
- 10 min: Producer implementation
- 10 min: Consumer implementation
- 15 min: Comprehensive tests

## Resources

- [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/reference/)
- [Testcontainers Kafka Module](https://java.testcontainers.org/modules/kafka/)
- [Kafka UI GitHub](https://github.com/provectus/kafka-ui)

## Next Exercise

After mastering Kafka integration, you'll work on database schema evolution with Flyway migrations in Exercise 3!
