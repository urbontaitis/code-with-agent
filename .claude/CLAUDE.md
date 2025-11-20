# CLAUDE.md

This file provides comprehensive guidance to Claude Code when working with Java code in this repository.

## Core Development Philosophy

### Data-First Design
**Start with data, not abstractions.** Design your data structures and flow first, then build the code around them. Data shapes the application architecture.

### KISS (Keep It Simple, Stupid)
Simplicity is paramount. Choose straightforward solutions over complex ones. Simple solutions are easier to understand, maintain, and debug.

### YAGNI (You Aren't Gonna Need It)
Implement features only when needed, not on speculation. Build what's required now.

### Modern Java First
- **Use Java 25 features** (check build.gradle for version):
  - Pattern matching
  - Records for DTOs
  - Sealed classes for domain modeling
  - Text blocks for SQL/JSON
  - Virtual threads
  - Switch expressions
  - Stream API extensively
  - Optional for null safety
- Always replace qualified name with import (unless the same class name already exists)

## 🤖 AI Assistant Guidelines

### Context Awareness
- Check build.gradle for Java version (25)
- Always check existing patterns first
- Use existing utilities before creating new ones
- Prefer composition over inheritance
- Think in terms of data transformation pipelines

### Common Pitfalls to Avoid
- Creating duplicate functionality
- Overwriting existing tests
- Writing JavaDocs or unnecessary comments
- Using old Java patterns when modern alternatives exist
- Creating anemic domain models

### Search Command Requirements

**CRITICAL**: Always use `rg` (ripgrep) instead of traditional `grep` and `find`:

```bash
# ✅ Use rg
rg "pattern"
rg --files -g "*.java"

# ❌ Don't use grep or find
grep -r "pattern" .
find . -name "*.java"
```

## 🏗️ Project Structure

### Feature Package Structure (NOT Layer-based)
Organize by business feature, not technical layers:

```
app/
├── build.gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/exacaster/workshop/
│   │   │       ├── user/                    # Feature package
│   │   │       │   ├── User.java           # Domain entity
│   │   │       │   ├── UserController.java
│   │   │       │   ├── UserService.java
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── UserDto.java        # Use records
│   │   │       │   └── UserMapper.java
│   │   │       ├── order/                   # Feature package
│   │   │       │   ├── Order.java
│   │   │       │   ├── OrderController.java
│   │   │       │   ├── OrderService.java
│   │   │       │   ├── OrderRepository.java
│   │   │       │   └── OrderEvents.java
│   │   │       └── shared/                  # Shared utilities
│   │   │           ├── config/
│   │   │           ├── exception/
│   │   │           └── util/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/                # Flyway migrations
│   └── test/
│       ├── groovy/                          # Spock tests
│       │   └── com/exacaster/workshop/
│       │       ├── user/
│       │       │   ├── UserServiceSpec.groovy
│       │       │   └── UserControllerSpec.groovy
│       │       └── order/
│       │           └── OrderServiceSpec.groovy
│       └── resources/
│           └── application-it.yml           # Testcontainers config
```

## 📋 Code Style & Conventions

### Line Length & Formatting
- **Line length**: 120 characters maximum
- **Indentation**: 4 spaces (no tabs)
- **Follow consistent formatting**

### NO Documentation Policy
- **NO JavaDocs** - Code should be self-explanatory
- **NO code comments** - Use better naming instead
- **Exception**: Only write comments when absolutely unclear despite good naming
- **Use descriptive method and variable names** that eliminate need for comments

### Naming Conventions
- **Classes**: `PascalCase`
- **Methods**: `camelCase` - descriptive names (e.g., `findActiveUsersByDepartment`)
- **Constants**: `UPPER_SNAKE_CASE`
- **Packages**: `lowercase` feature names
- **Variables**: Descriptive names over comments, use `var` when type is obvious

## 🚀 Modern Java Features (25)

### Records for DTOs
```java
// ✅ Use records for immutable DTOs
public record UserDto(
    Long id,
    String username,
    String email,
    LocalDateTime createdAt
) {}

// ❌ Don't use classes with getters/setters for DTOs
```

### Pattern Matching
```java
// ✅ Use pattern matching
return switch (status) {
    case ACTIVE -> processActiveUser(user);
    case SUSPENDED -> processSuspendedUser(user);
    case DELETED -> throw new IllegalStateException("Cannot process deleted user");
};

// Pattern matching with instanceof
if (obj instanceof User user && user.isActive()) {
    return user.getName();
}
```

### Optional Usage
```java
// ✅ Always use Optional for nullable returns
public Optional<UserDto> findUserById(Long id) {
    return repository.findById(id)
        .filter(User::isActive)
        .map(this::enrichUserData);
}

// ❌ Never return null
```

### Stream API & Functional Style
```java
// ✅ Use streams for data transformation
public List<UserDto> getActiveUsers() {
    return userRepository.findAll().stream()
        .filter(User::isActive)
        .sorted(Comparator.comparing(User::getCreatedAt).reversed())
        .map(UserMapper::toDto)
        .toList();  // Java 16+ toList()
}

// ✅ Use Stream operations extensively
var processedOrders = orders.stream()
    .filter(order -> order.getStatus() == Status.PENDING)
    .flatMap(order -> order.getItems().stream())
    .collect(Collectors.groupingBy(
        Item::getCategory,
        Collectors.summingDouble(Item::getPrice)
    ));
```

### Virtual Threads (Java 25)
```java
// Enabled in application.yml
spring.threads.virtual.enabled=true

// Use for I/O bound operations
@Async
public CompletableFuture<Result> processDataAsync(Data data) {
    return CompletableFuture.supplyAsync(() -> {
        // Virtual thread handles this efficiently
        return processData(data);
    });
}
```

### Sealed Classes for Domain Modeling
```java
// ✅ Use sealed classes for exhaustive type checking
public sealed interface PaymentMethod
    permits CreditCard, DebitCard, PayPal, BankTransfer {
}

public record CreditCard(String number, String cvv) implements PaymentMethod {}
public record PayPal(String email) implements PaymentMethod {}
```

## 🧪 Testing with Spock Framework

### Spock Test Structure
```groovy
// ✅ Write ALL tests in Groovy using Spock
class UserServiceSpec extends Specification {

    @Subject
    UserService userService

    UserRepository userRepository = Mock()

    def setup() {
        userService = new UserService(userRepository)
    }

    def "should find active users when department exists"() {
        given: "a department with active users"
        def department = "Engineering"
        def activeUsers = [
            new User(id: 1L, name: "Alice", active: true),
            new User(id: 2L, name: "Bob", active: true)
        ]

        when: "finding users by department"
        def result = userService.findActiveUsersByDepartment(department)

        then: "repository is called and active users returned"
        1 * userRepository.findByDepartment(department) >> activeUsers
        result.size() == 2
        result.every { it.active }
    }
}
```

### Spock Best Practices
- Never use `value == true` or `value == false`, use `value` or `!value` instead
- Never use "private" in groovy tests for methods
- If testing with different values, use `@Unroll` and where table to avoid code duplication
- Use descriptive test names with "should ... when ..."

### Testing Async Code with Polling
```groovy
// ✅ Use Spock's polling for async operations
def "should process message from Kafka asynchronously"() {
    given: "a Kafka message"
    def message = new KafkaMessage(topic: "users", payload: userData)

    when: "message is sent"
    kafkaProducer.send(message)

    then: "message is eventually processed"
    new PollingConditions(timeout: 10, delay: 0.5).eventually {
        assert messageProcessor.getProcessedCount() == 1
        assert messageProcessor.getLastProcessedId() == message.id
    }
}

// Alternative with BlockingVariable
def "should complete async operation"() {
    given:
    def result = new BlockingVariable<String>(5) // 5 second timeout

    when:
    service.processAsync() { response ->
        result.set(response)
    }

    then:
    result.get() == "Expected Result"
}
```

### Data-Driven Testing
```groovy
@Unroll
def "should calculate price with #discount% discount"() {
    expect:
    priceCalculator.calculate(basePrice, discount) == expectedPrice

    where:
    basePrice | discount | expectedPrice
    100.0     | 10       | 90.0
    100.0     | 20       | 80.0
    100.0     | 0        | 100.0
    50.0      | 50       | 25.0
}
```

### Testcontainers Integration
```groovy
@Testcontainers
@SpringBootTest
class UserRepositorySpec extends Specification {

    @Shared
    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test")

    @Autowired
    UserRepository userRepository

    def "should save and retrieve user from PostgreSQL"() {
        given: "a new user"
        def user = new User(username: "alice", email: "alice@example.com")

        when: "user is saved"
        def saved = userRepository.save(user)

        then: "user can be retrieved"
        saved.id != null
        userRepository.findById(saved.id).isPresent()
    }
}
```

## 🛠️ Gradle Configuration

### Essential build.gradle
```groovy
plugins {
    id 'java'
    id 'groovy'
    id 'org.springframework.boot' version '3.5.0'
    id 'io.spring.dependency-management' version '1.1.5'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    // Spring Boot
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    // PostgreSQL
    runtimeOnly 'org.postgresql:postgresql'

    // Flyway
    implementation 'org.flywaydb:flyway-core'
    runtimeOnly 'org.flywaydb:flyway-database-postgresql'

    // Testing with Spock
    testImplementation 'org.spockframework:spock-core:2.4-M4-groovy-4.0'
    testImplementation 'org.spockframework:spock-spring:2.4-M4-groovy-4.0'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.boot:spring-boot-testcontainers'
    testImplementation 'org.testcontainers:postgresql'
    testRuntimeOnly 'net.bytebuddy:byte-buddy'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

## 🎯 Data-First Design Patterns

### Data Transformation Pipeline
```java
// ✅ Think in terms of data flow
public record OrderProcessingPipeline(
    OrderRepository repository,
    PricingService pricingService,
    InventoryService inventoryService
) {

    public ProcessedOrder processOrder(OrderRequest request) {
        return Optional.of(request)
            .map(this::validateOrder)
            .map(this::checkInventory)
            .map(this::calculatePricing)
            .map(this::applyDiscounts)
            .map(this::persistOrder)
            .orElseThrow(() -> new OrderProcessingException());
    }
}
```

### Immutable Data Structures
```java
// ✅ Use records and immutable collections
public record UserProfile(
    String username,
    List<String> roles,
    Map<String, String> attributes
) {
    public UserProfile {
        // Defensive copying in compact constructor
        roles = List.copyOf(roles);
        attributes = Map.copyOf(attributes);
    }

    public UserProfile withRole(String newRole) {
        var updatedRoles = new ArrayList<>(roles);
        updatedRoles.add(newRole);
        return new UserProfile(username, updatedRoles, attributes);
    }
}
```

## 💾 Database & Flyway

### Flyway Migration Naming
```
V1__initial_schema.sql
V2__add_users_table.sql
V3__add_orders_table.sql
```

### Migration Best Practices
- Use descriptive names
- One logical change per migration
- Never modify existing migrations
- Test migrations against PostgreSQL 17

## ⚠️ Critical Guidelines

1. **Check Java version** in build.gradle first (Java 25)
2. **Feature packages** - Organize by business capability, not layers
3. **Data-first** - Design data structures before code
4. **Use modern Java** - Records, pattern matching, streams, Optional
5. **NO JavaDocs/comments** - Self-explanatory code only
6. **Spock tests only** - Write in Groovy with descriptive test names
7. **120 char line limit** - Follow consistent formatting
8. **Immutability** - Prefer immutable data structures
9. **Functional style** - Use streams and functional operations
10. **Async with polling** - Use Spock's PollingConditions for async tests
11. **Testcontainers** - Use PostgreSQL containers for integration tests
12. **Descriptive naming** - Method/variable names replace documentation

## 🔄 Git Workflow

### Commit Message Format
```
<type>(<feature>): <description>

<optional body>
```

Types: feat, fix, test, refactor, chore

Example:
```
feat(user): add user profile management with reactive streams

- Implement reactive user profile service
- Add event-driven profile updates
- Use Java 25 pattern matching for status handling
```

## 📋 Pre-commit Checklist

- [ ] Using Java 25 features appropriately
- [ ] Code organized in feature packages
- [ ] No JavaDocs or unnecessary comments
- [ ] Self-explanatory variable/method names
- [ ] All tests written in Groovy with Spock
- [ ] Async tests use PollingConditions
- [ ] Line length ≤ 120 characters
- [ ] Using Optional for nullable returns
- [ ] Using records for DTOs
- [ ] Using streams for data transformation
- [ ] Data-first design approach followed
- [ ] Testcontainers used for integration tests
- [ ] Flyway migrations tested
