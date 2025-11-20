---
description: Generate comprehensive Spock tests for existing code
---

Generate comprehensive Spock tests following CLAUDE.md conventions:

**File to test:** (ask user which class needs tests)

**Generate:**

## Test File Structure

**File:** `src/test/groovy/com/exacaster/workshop/{feature}/{Class}Spec.groovy`

```groovy
class {Class}Spec extends Specification {

    @Subject
    {Class} subjectUnderTest

    // Mock dependencies
    Dependency1 dependency1 = Mock()
    Dependency2 dependency2 = Mock()

    def setup() {
        subjectUnderTest = new {Class}(dependency1, dependency2)
    }

    // Test methods...
}
```

## Test Coverage Requirements

### For Repository Classes
- [ ] Save and retrieve entity
- [ ] Find by various criteria (custom query methods)
- [ ] Update existing entity
- [ ] Delete entity
- [ ] Unique constraint violations
- [ ] Foreign key relationships
- [ ] Cascade operations
- [ ] Use @Testcontainers with real database
- [ ] Data-driven tests with @Unroll for different queries

### For Service Classes
- [ ] All public methods tested
- [ ] Success scenarios (happy path)
- [ ] Error scenarios (not found, validation errors)
- [ ] Optional handling (present/empty)
- [ ] Mock interactions verified (1 *, 0 *)
- [ ] Exception throwing scenarios
- [ ] Business logic edge cases
- [ ] Data-driven tests with @Unroll for validation

### For Controller Classes
- [ ] All endpoints tested (GET, POST, PUT, DELETE)
- [ ] HTTP status codes (200, 201, 204, 400, 404)
- [ ] Request validation (@Valid)
- [ ] Response body structure
- [ ] Integration test with @SpringBootTest
- [ ] Use TestRestTemplate or MockMvc
- [ ] Data-driven tests for validation scenarios

## Spock Best Practices (CLAUDE.md)

### Structure
```groovy
def "should do something when condition"() {
    given: "setup context"
    def input = "test"

    when: "action performed"
    def result = service.doSomething(input)

    then: "verify outcome"
    result == "expected"
    1 * dependency.method(_) >> "mocked"

    where: "test with different values"
    input     | expected
    "value1"  | "result1"
    "value2"  | "result2"
}
```

### Critical Rules
- ✅ Use "value" NOT "value == true"
- ✅ NO "private" keyword in Groovy
- ✅ Use @Unroll for parameterized tests
- ✅ Descriptive test names: "should X when Y"
- ✅ Given-When-Then structure
- ✅ Mock with Mock(), Stub(), Spy()
- ✅ Verify interactions: 1 *, 0 *, _ *
- ✅ Use >> for return values
- ✅ Use _ for "any argument"
- ✅ PollingConditions for async

### Async Testing
```groovy
def "should eventually complete async operation"() {
    given:
    def conditions = new PollingConditions(timeout: 5, delay: 0.1)

    when:
    service.asyncOperation()

    then:
    conditions.eventually {
        assert service.isCompleted()
    }
}
```

### Data-Driven Testing
```groovy
@Unroll
def "should validate #scenario"() {
    expect:
    validator.isValid(input) == valid

    where:
    scenario          | input  | valid
    "valid input"     | "test" | true
    "null input"      | null   | false
    "empty input"     | ""     | false
}
```

### Mocking Patterns
```groovy
// Mock with return value
1 * repository.save(_) >> savedEntity

// Mock with exception
1 * repository.findById(999L) >> { throw new NotFoundException() }

// Verify no interaction
0 * repository.delete(_)

// Any number of calls
_ * logger.debug(_)

// Argument matching
1 * service.process({ it.status == "ACTIVE" })

// Multiple return values
repository.getStatus() >>> ["PENDING", "PROCESSING", "COMPLETE"]
```

## Test Templates by Type

### Repository Test Template
```groovy
@Testcontainers
@SpringBootTest
class {Entity}RepositorySpec extends Specification {

    @Shared
    PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17")

    @Autowired
    {Entity}Repository repository

    def cleanup() {
        repository.deleteAll()
    }

    def "should save and retrieve {entity}"() {
        given: "a new {entity}"
        def entity = new {Entity}(name: "Test")

        when: "saving {entity}"
        def saved = repository.save(entity)

        then: "can retrieve by ID"
        def found = repository.findById(saved.id)
        found.isPresent()
        found.get().name == "Test"
    }

    @Unroll
    def "should find {entity} by #field with value #value"() {
        given: "multiple entities"
        repository.saveAll([
            new {Entity}(name: "Entity1"),
            new {Entity}(name: "Entity2")
        ])

        when: "searching by {field}"
        def results = repository.findByName(value)

        then: "correct entities returned"
        results.size() == expectedCount

        where:
        field  | value     | expectedCount
        "name" | "Entity1" | 1
        "name" | "Entity2" | 1
        "name" | "Missing" | 0
    }
}
```

### Service Test Template
```groovy
class {Entity}ServiceSpec extends Specification {

    @Subject
    {Entity}Service service

    {Entity}Repository repository = Mock()
    {Entity}Mapper mapper = Mock()

    def setup() {
        service = new {Entity}Service(repository, mapper)
    }

    def "should find {entity} by ID when exists"() {
        given: "an existing {entity}"
        def entity = new {Entity}(id: 1L, name: "Test")
        def dto = new {Entity}Dto(1L, "Test")

        when: "finding by ID"
        def result = service.findById(1L)

        then: "repository called and DTO returned"
        1 * repository.findById(1L) >> Optional.of(entity)
        1 * mapper.toDto(entity) >> dto
        result.isPresent()
        result.get().name() == "Test"
    }

    def "should return empty when {entity} not found"() {
        when: "finding non-existent {entity}"
        def result = service.findById(999L)

        then: "repository returns empty"
        1 * repository.findById(999L) >> Optional.empty()
        0 * mapper.toDto(_)
        result.isEmpty()
    }
}
```

### Controller Test Template
```groovy
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class {Entity}ControllerSpec extends Specification {

    @Shared
    PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17")

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    {Entity}Repository repository

    def cleanup() {
        repository.deleteAll()
    }

    def "should create {entity} via POST"() {
        given: "a valid {entity} request"
        def request = new {Entity}Dto(null, "Test")

        when: "posting to API"
        def response = restTemplate.postForEntity(
            "/api/{entities}",
            request,
            {Entity}Dto
        )

        then: "entity created"
        response.statusCode == HttpStatus.CREATED
        response.body.name() == "Test"
        repository.count() == 1
    }

    @Unroll
    def "should return #status for #scenario"() {
        when: "making request"
        def response = restTemplate.getForEntity(
            "/api/{entities}/${id}",
            {Entity}Dto
        )

        then: "correct status returned"
        response.statusCode == status

        where:
        scenario            | id   | status
        "existing entity"   | 1L   | HttpStatus.OK
        "non-existent"      | 999L | HttpStatus.NOT_FOUND
    }
}
```

**Follow CLAUDE.md:**
- Spock framework only (no JUnit)
- Given-When-Then structure
- @Unroll for data-driven tests
- Descriptive test names
- Mock dependencies
- Testcontainers for integration tests
- No "private" in Groovy
- PollingConditions for async

**Example usage:**
```
/test
# Claude asks: "Which class needs tests?"
# You respond: "FilmService"
# Claude generates: FilmServiceSpec.groovy with comprehensive tests
```
