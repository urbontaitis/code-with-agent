# Exercise 4: Advanced Spock Testing

**Time:** 30 minutes
**Difficulty:** Intermediate
**Goal:** Master Spock Framework with Testcontainers for comprehensive testing

## Objective

Write professional-grade tests using Spock Framework (Groovy) with Testcontainers, covering unit tests, integration tests, data-driven tests, and asynchronous testing patterns.

## Background

Spock is a testing framework for Java and Groovy with:
- Expressive Given-When-Then syntax
- Built-in mocking (`Mock()`, `Stub()`)
- Data-driven testing (`@Unroll`, `where:` blocks)
- Powerful assertions
- Testcontainers integration for real dependencies

## Task: Comprehensive Film Service Test Suite

### 1. Repository Integration Tests (10 minutes)

**Prompt for Claude:**
```
Create FilmRepositorySpec.groovy in test/groovy following CLAUDE.md:

Requirements:
- Extend Specification
- Use @Testcontainers with @Shared PostgreSQLContainer
- @SpringBootTest with test configuration
- @Autowired FilmRepository

Tests to implement:
1. "should save and retrieve film"
   - Given: a new Film entity
   - When: repository.save(film)
   - Then: can retrieve by ID, all fields match

2. "should find films by release year"
   - Given: multiple films with different years
   - When: findByReleaseYear(2020)
   - Then: returns only 2020 films

3. "should enforce unique title constraint"
   - Given: a film with title "Inception"
   - When: save another film with same title
   - Then: throws DataIntegrityViolationException

4. "should handle null director gracefully"
   - Given: film without director
   - When: save film
   - Then: saves successfully, director is null

5. "should find films by genre with #genre"
   - Use @Unroll for data-driven testing
   - Test multiple genres: ACTION, SCI_FI, DRAMA
   - Where block with genre and expected count

Spock Best Practices:
- NO "private" keyword in Groovy test methods
- Use "film" not "film == true"
- Descriptive test names with "should ... when ..."
- Use setup: block for common setup
- Use cleanup: block if needed
- @Shared for Testcontainer (created once)

Reference @Film.java for entity structure.
```

### 2. Service Unit Tests (10 minutes)

**Prompt for Claude:**
```
Create FilmServiceSpec.groovy with comprehensive service tests:

Setup:
- @Subject FilmService filmService
- Mock() FilmRepository
- Mock() FilmEventProducer (if Kafka integration done)
- setup: block to instantiate service with mocks

Tests:
1. "should create film and return DTO"
   - Given: valid film request
   - When: filmService.createFilm(request)
   - Then:
     - 1 * repository.save(_) >> savedFilm
     - 1 * eventProducer.publishFilmCreated(_)
     - result.isPresent()
     - result.get().title == "Inception"

2. "should return empty when film not found"
   - Given: filmId = 999L
   - When: filmService.getFilmById(999L)
   - Then:
     - 1 * repository.findById(999L) >> Optional.empty()
     - result.isEmpty()

3. "should validate film creation with invalid #field"
   - Use @Unroll for data-driven validation tests
   - Where block:
     | field      | value | expectedError |
     | title      | null  | "Title required" |
     | title      | ""    | "Title required" |
     | year       | 1800  | "Invalid year" |
     | year       | 2100  | "Invalid year" |

4. "should update film and preserve ID"
   - Given: existing film in database
   - When: update with new data
   - Then: ID unchanged, other fields updated

5. "should delete film and verify cascade"
   - Given: film with ratings
   - When: deleteFilm(filmId)
   - Then:
     - 1 * repository.deleteById(filmId)
     - ratings also deleted (verify in integration test)

6. "should calculate average rating correctly"
   - Given: film with ratings [5, 4, 5, 3, 4]
   - When: getAverageRating(filmId)
   - Then: average == 4.2

Mocking Patterns:
- Use >> for return values
- Use _ for "any argument"
- Use { it.title == "Inception" } for argument matching
- Verify call counts: 1 *, 0 *, _ *
```

### 3. Async Testing with Polling (5 minutes)

**Prompt for Claude:**
```
Add async tests to FilmServiceSpec for Kafka event publishing:

Test: "should eventually publish event after film creation"
- Given: film creation request
- When: createFilm() called
- Then: use PollingConditions to verify event published

Example:
```groovy
def "should eventually publish event after film creation"() {
    given: "a new film request"
    def request = new CreateFilmRequest(
        title: "Inception",
        releaseYear: 2010,
        genre: "SCI_FI"
    )
    def conditions = new PollingConditions(timeout: 5, delay: 0.1)

    when: "film is created"
    def result = filmService.createFilm(request)

    then: "event is eventually published"
    conditions.eventually {
        1 * eventProducer.publishFilmCreated(_ as FilmCreatedEvent)
    }
    result.isPresent()
}
```

Also test:
- Event publishing failure doesn't break film creation
- Async event processing in consumer
```

### 4. Controller Integration Tests (10 minutes)

**Prompt for Claude:**
```
Create FilmControllerSpec.groovy for REST endpoint testing:

Setup:
- @SpringBootTest(webEnvironment = RANDOM_PORT)
- @Testcontainers with PostgreSQL
- @Autowired TestRestTemplate
- @Autowired FilmRepository for verification

Tests:
1. "should create film via POST /api/films"
   - When: POST with valid FilmRequest JSON
   - Then:
     - Status 201 CREATED
     - Response body contains film with ID
     - Film exists in database

2. "should return 400 for invalid film data"
   - Use @Unroll with invalid payloads
   - Where:
     | payload | expectedError |
     | no title | "Title required" |
     | year 1800 | "Invalid year" |
     | invalid genre | "Genre must be valid" |

3. "should get film by ID via GET /api/films/{id}"
   - Given: film in database
   - When: GET /api/films/1
   - Then: 200 OK with film data

4. "should return 404 for non-existent film"
   - When: GET /api/films/999
   - Then: 404 NOT FOUND

5. "should list all films via GET /api/films"
   - Given: 5 films in database
   - When: GET /api/films
   - Then: returns all 5 films

6. "should filter films by genre via GET /api/films?genre=SCI_FI"
   - Given: films of various genres
   - When: GET with genre filter
   - Then: only SCI_FI films returned

7. "should update film via PUT /api/films/{id}"
   - Given: existing film
   - When: PUT with updated data
   - Then: 200 OK, film updated in DB

8. "should delete film via DELETE /api/films/{id}"
   - Given: existing film
   - When: DELETE
   - Then: 204 NO CONTENT, film removed from DB

Use TestRestTemplate.exchange() or specific methods.
Verify responses with ResponseEntity assertions.
```

### 5. Data-Driven Testing Showcase (5 minutes)

**Prompt for Claude:**
```
Create FilmValidationSpec.groovy showcasing @Unroll:

Test: "should validate film with #scenario"
- Test various validation scenarios in table format
- Where block with multiple columns:
  | scenario | title | year | genre | valid |
  | "valid film" | "Inception" | 2010 | "SCI_FI" | true |
  | "missing title" | null | 2010 | "SCI_FI" | false |
  | "empty title" | "" | 2010 | "SCI_FI" | false |
  | "year too old" | "Old Movie" | 1800 | "DRAMA" | false |
  | "year in future" | "Future" | 2100 | "SCI_FI" | false |
  | "invalid genre" | "Test" | 2020 | "INVALID" | false |

Use Validator to test Jakarta Bean Validation programmatically.
```

## Complete Test Structure

```
test/
└── groovy/
    └── com/exacaster/workshop/film/
        ├── FilmRepositorySpec.groovy
        ├── FilmServiceSpec.groovy
        ├── FilmControllerSpec.groovy
        ├── FilmValidationSpec.groovy
        └── FilmIntegrationSpec.groovy (bonus)
```

## Success Criteria

- [ ] FilmRepositorySpec with Testcontainers PostgreSQL
- [ ] FilmServiceSpec with mocked dependencies
- [ ] Async tests with PollingConditions
- [ ] FilmControllerSpec with @SpringBootTest
- [ ] Data-driven tests with @Unroll
- [ ] All tests follow CLAUDE.md Spock conventions
- [ ] No "private" keywords in test methods
- [ ] Given-When-Then structure in all tests
- [ ] Descriptive test names
- [ ] All tests pass: `./gradlew test`

## Running Tests

```bash
# Run all tests
./gradlew test

# Run only Spock specs
./gradlew test --tests '*Spec'

# Run specific spec
./gradlew test --tests FilmServiceSpec

# Run with verbose output
./gradlew test --info

# Generate test report
./gradlew test
open build/reports/tests/test/index.html
```

## Key Learnings

1. **Spock Syntax**: Given-When-Then is more readable than JUnit
2. **Mocking**: Built-in Mock(), Stub(), Spy() > Mockito
3. **Data-Driven**: @Unroll + where: blocks > parameterized tests
4. **Testcontainers**: Real database > H2 for integration tests
5. **Async Testing**: PollingConditions for eventual consistency
6. **Groovy Benefits**: No semicolons, concise syntax, powerful assertions

## Common Spock Patterns

```groovy
// Mocking
def repository = Mock(FilmRepository)
1 * repository.save(_) >> savedFilm
0 * repository.delete(_)

// Stubbing
def producer = Stub(FilmEventProducer)
producer.publish(_) >> CompletableFuture.completedFuture(null)

// Verification
then:
result.isPresent()
result.get().id == 1L
thrown(NotFoundException)

// Where blocks
where:
title       | year | valid
"Inception" | 2010 | true
null        | 2010 | false

// Polling
def conditions = new PollingConditions(timeout: 5)
conditions.eventually {
    assert someCondition
}
```

## Bonus Challenges

1. **Parameterized Fixture**: Use `@Unroll` with complex object creation
2. **Interaction Testing**: Verify exact method calls with specific arguments
3. **Exception Testing**: `thrown()`, `notThrown()`, `thrownBy()`
4. **Hamcrest Matchers**: Combine Spock with Hamcrest for complex assertions
5. **Performance Testing**: Add timing assertions with Spock

## Time Breakdown

- 10 min: Repository integration tests
- 10 min: Service unit tests with mocks
- 5 min: Async testing patterns
- 10 min: Controller integration tests
- 5 min: Data-driven testing showcase

Total: ~40 minutes (budget: 30 min + 10 min buffer)
