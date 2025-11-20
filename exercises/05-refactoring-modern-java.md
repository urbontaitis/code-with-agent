# Exercise 5: Refactoring to Modern Java

**Time:** 40 minutes
**Difficulty:** Intermediate to Advanced
**Goal:** Apply Java 25 features to modernize existing codebase

## Objective

Refactor the Film service to use modern Java features including records, pattern matching, sealed classes, Optional, Stream API, and text blocks. Experience how Claude Code can help migrate legacy Java patterns to modern idioms.

## Background

Modern Java (21/25) provides powerful features that make code:
- More concise and readable
- Type-safe and expressive
- Easier to maintain
- Better performing (especially with virtual threads)

This exercise demonstrates practical refactoring patterns.

## Task: Modernize Film Service

### 1. Convert DTOs to Records (8 minutes)

**Prompt for Claude:**
```
Analyze @FilmDto.java and convert it to a Java record following CLAUDE.md:

Requirements:
- Convert class to record
- Keep all validation annotations (@NotNull, @Size, etc.)
- Add compact constructor for additional validation if needed
- Update FilmMapper to work with records
- Ensure immutability (defensive copying for collections if any)
- Update all usages in controllers and services

Also create:
- CreateFilmRequest record (for POST requests)
- UpdateFilmRequest record (for PUT requests)
- FilmResponse record (for GET responses)

Follow CLAUDE.md: records for DTOs, no JavaDocs, descriptive names.
Verify no existing tests are broken after conversion.
```

**Expected Changes:**
```java
// ❌ Before: Class with getters/setters
public class FilmDto {
    private Long id;
    private String title;
    // ... getters, setters, equals, hashCode
}

// ✅ After: Concise record
public record FilmDto(
    Long id,
    @NotNull @Size(max = 255) String title,
    @Min(1888) @Max(2100) Integer releaseYear,
    @NotNull Genre genre,
    String director
) {}
```

### 2. Use Sealed Classes for Domain Modeling (8 minutes)

**Prompt for Claude:**
```
Create a sealed interface FilmStatus to model film lifecycle states:

States:
- Draft (film being created, not visible to public)
- Published (film visible, can be rated)
- Archived (film hidden but preserved)

Requirements:
- Create sealed interface FilmStatus with 3 record implementations
- Each state can have specific data:
  - Draft: createdBy (String), lastModified (Instant)
  - Published: publishedAt (Instant), viewCount (Long)
  - Archived: archivedAt (Instant), reason (String)
- Add @JsonTypeInfo for proper JSON serialization
- Add status field to @Film.java entity
- Update FilmService to use pattern matching for status transitions

Example state transitions:
- Draft -> Published (requires validation)
- Published -> Archived (requires reason)
- Archived -> Published (re-publish)

Use pattern matching in switch expressions for status handling.
Follow CLAUDE.md: sealed classes for domain modeling.
```

**Expected Implementation:**
```java
public sealed interface FilmStatus
    permits Draft, Published, Archived {

    record Draft(String createdBy, Instant lastModified)
        implements FilmStatus {}

    record Published(Instant publishedAt, Long viewCount)
        implements FilmStatus {}

    record Archived(Instant archivedAt, String reason)
        implements FilmStatus {}
}

// Pattern matching usage
public String getStatusDescription(FilmStatus status) {
    return switch (status) {
        case Draft(var creator, var modified) ->
            "Draft by " + creator + " (modified: " + modified + ")";
        case Published(var published, var views) ->
            "Published with " + views + " views";
        case Archived(var archived, var reason) ->
            "Archived: " + reason;
    };
}
```

### 3. Replace Nulls with Optional (8 minutes)

**Prompt for Claude:**
```
Refactor @FilmService.java to use Optional properly:

Changes needed:
1. All methods returning nullable values should return Optional<T>
   - findById(Long id) -> Optional<FilmDto>
   - findByTitle(String title) -> Optional<FilmDto>

2. Replace null checks with Optional operations:
   - Use map(), flatMap(), filter()
   - Use orElseThrow() with custom exceptions
   - Use ifPresentOrElse() for side effects

3. Never return Optional.of(null) or null
4. Don't use Optional for collections (return empty List instead)
5. Use Optional.ofNullable() when wrapping potentially null values

Update all tests in @FilmServiceSpec.groovy to work with Optional.

Follow CLAUDE.md: Optional for nullable returns, functional style.
```

**Expected Pattern:**
```java
// ❌ Before: Null checks everywhere
public FilmDto getFilm(Long id) {
    Film film = repository.findById(id);
    if (film == null) {
        throw new NotFoundException("Film not found");
    }
    return mapper.toDto(film);
}

// ✅ After: Optional chain
public Optional<FilmDto> getFilm(Long id) {
    return repository.findById(id)
        .filter(Film::isPublished)
        .map(this::enrichFilmData)
        .map(mapper::toDto);
}
```

### 4. Apply Stream API (8 minutes)

**Prompt for Claude:**
```
Refactor collection processing in @FilmService.java to use Stream API:

Find all for loops and replace with streams:
1. Filtering films by criteria -> filter()
2. Converting entities to DTOs -> map()
3. Sorting films -> sorted()
4. Grouping by genre -> Collectors.groupingBy()
5. Calculating statistics (avg rating) -> Collectors.averagingDouble()

Requirements:
- Use .toList() for terminal operation (Java 16+)
- Use method references where possible
- Chain operations fluently
- Prefer streams over imperative loops
- Use parallel streams ONLY where beneficial (large collections)

Example transformations:
- List<Film> -> List<FilmDto>
- Filter by genre and year range
- Group films by genre with count
- Calculate average rating per genre

Follow CLAUDE.md: Stream API extensively, functional style.
```

**Expected Transformation:**
```java
// ❌ Before: Imperative loop
public List<FilmDto> getActiveFilms() {
    List<Film> films = repository.findAll();
    List<FilmDto> result = new ArrayList<>();
    for (Film film : films) {
        if (film.isActive() && film.getReleaseYear() >= 2020) {
            FilmDto dto = mapper.toDto(film);
            result.add(dto);
        }
    }
    result.sort(Comparator.comparing(FilmDto::title));
    return result;
}

// ✅ After: Stream pipeline
public List<FilmDto> getActiveFilms() {
    return repository.findAll().stream()
        .filter(Film::isActive)
        .filter(film -> film.getReleaseYear() >= 2020)
        .sorted(Comparator.comparing(Film::getTitle))
        .map(mapper::toDto)
        .toList();
}
```

### 5. Use Text Blocks for SQL/JSON (5 minutes)

**Prompt for Claude:**
```
If @FilmRepository.java has any @Query annotations with SQL,
refactor them to use text blocks (Java 15+):

Requirements:
- Use triple quotes """
- Proper indentation
- Keep SQL readable and formatted
- No string concatenation

Also apply to any JSON examples in tests.

Follow CLAUDE.md: text blocks for SQL/JSON.
```

**Expected Change:**
```java
// ❌ Before: Concatenated strings
@Query("SELECT f FROM Film f WHERE f.releaseYear >= :year " +
       "AND f.genre = :genre ORDER BY f.title")
List<Film> findByYearAndGenre(@Param("year") int year,
                               @Param("genre") Genre genre);

// ✅ After: Text block
@Query("""
    SELECT f FROM Film f
    WHERE f.releaseYear >= :year
      AND f.genre = :genre
    ORDER BY f.title
    """)
List<Film> findByYearAndGenre(@Param("year") int year,
                               @Param("genre") Genre genre);
```

### 6. Update All Tests (8 minutes)

**Prompt for Claude:**
```
Update all Spock tests to work with refactored code:

Test files to update:
- @FilmServiceSpec.groovy
- @FilmControllerSpec.groovy
- @FilmRepositorySpec.groovy

Changes needed:
1. Update expectations for Optional returns
   - Use result.isPresent() or result.isEmpty()
   - Use result.get() carefully in tests

2. Test record equality (automatic equals/hashCode)

3. Test sealed class pattern matching
   - Add tests for each FilmStatus state
   - Test state transitions

4. Verify Stream operations produce correct results
   - Test filtering, mapping, sorting
   - Test edge cases (empty streams)

5. Add data-driven tests with @Unroll for status transitions

Follow CLAUDE.md: Spock conventions, Given-When-Then, no "private".
```

## Complete Refactoring Checklist

```
Phase 1: DTOs to Records (8 min)
├── [ ] FilmDto -> record
├── [ ] CreateFilmRequest -> record
├── [ ] UpdateFilmRequest -> record
├── [ ] FilmResponse -> record
└── [ ] FilmMapper updated

Phase 2: Sealed Classes (8 min)
├── [ ] FilmStatus sealed interface
├── [ ] Draft, Published, Archived records
├── [ ] Film entity has status field
├── [ ] Pattern matching in service
└── [ ] JSON serialization configured

Phase 3: Optional Refactoring (8 min)
├── [ ] findById returns Optional
├── [ ] findByTitle returns Optional
├── [ ] Null checks replaced with Optional chains
├── [ ] orElseThrow for error handling
└── [ ] No Optional<Collection>

Phase 4: Stream API (8 min)
├── [ ] Replace all for loops with streams
├── [ ] Use filter, map, sorted
├── [ ] Use Collectors for grouping/stats
├── [ ] Use .toList() terminal operation
└── [ ] Method references where possible

Phase 5: Text Blocks (5 min)
├── [ ] @Query with text blocks
└── [ ] Test JSON with text blocks

Phase 6: Tests Update (8 min)
├── [ ] FilmServiceSpec updated
├── [ ] FilmControllerSpec updated
├── [ ] FilmRepositorySpec updated
├── [ ] New tests for FilmStatus
└── [ ] All tests pass
```

## Success Criteria

- [ ] All DTOs converted to records
- [ ] FilmStatus sealed interface implemented
- [ ] All nullable returns use Optional
- [ ] All collection processing uses Stream API
- [ ] SQL queries use text blocks
- [ ] All tests updated and passing
- [ ] No compilation errors
- [ ] Code follows CLAUDE.md conventions
- [ ] Run: `./gradlew test` - all green
- [ ] Run: `./gradlew build` - successful

## Running Tests

```bash
# Run all tests
./gradlew test

# Run specific spec
./gradlew test --tests FilmServiceSpec

# Run with detailed output
./gradlew test --info

# Generate coverage report (if configured)
./gradlew test jacocoTestReport
open build/reports/jacoco/test/html/index.html
```

## Manual Verification

```bash
# Start application
docker compose up -d
./gradlew bootRun

# Test with records
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Modern Java Movie",
    "releaseYear": 2024,
    "genre": "SCI_FI",
    "director": "Tech Director"
  }'

# Test Optional response (404 should return empty)
curl http://localhost:8080/api/films/999

# Test Stream filtering
curl "http://localhost:8080/api/films?genre=SCI_FI&yearFrom=2020"

# Test status transitions (if endpoints added)
curl -X PATCH http://localhost:8080/api/films/1/publish
curl http://localhost:8080/api/films/1
# Should show Published status
```

## Key Learnings

1. **Records**: Reduce boilerplate, automatic equals/hashCode/toString
2. **Sealed Classes**: Exhaustive type checking, better domain modeling
3. **Pattern Matching**: Type-safe, concise branching logic
4. **Optional**: Explicit nullability, functional composition
5. **Stream API**: Declarative collection processing
6. **Text Blocks**: Readable multi-line strings
7. **Refactoring**: Modern Java features improve code quality

## Common Issues

**Issue:** Records can't extend classes
**Solution:** Records are final, use composition or interfaces

**Issue:** Pattern matching compilation errors
**Solution:** Ensure Java 25 configured, enable preview features if needed

**Issue:** Optional in collections warning
**Solution:** Return `List.of()` or empty list, not `Optional<List>`

**Issue:** Stream operations too complex
**Solution:** Break into smaller methods with descriptive names

## Comparison: Before vs After

### Lines of Code
- **Before**: ~800 lines (Film feature)
- **After**: ~550 lines (Film feature)
- **Reduction**: ~30% fewer lines

### Null Pointer Exceptions
- **Before**: Potential NPEs in 12 locations
- **After**: 0 NPEs with Optional

### Boilerplate Code
- **Before**: 45 lines of getters/setters/equals
- **After**: 0 lines (records auto-generate)

### Test Coverage
- **Before**: 75% coverage
- **After**: 85% coverage (pattern matching forces exhaustive testing)

## Bonus Challenges

1. **Virtual Threads**: Add `@Async` methods using virtual threads
2. **Pattern Guards**: Use `when` clause in pattern matching
3. **Record Patterns**: Destructure records in switch (Java 25)
4. **SequencedCollection**: Use `getFirst()`, `getLast()` (Java 25)
5. **String Templates**: Use string interpolation (Java 25)

## Time Breakdown

- 8 min: Convert DTOs to records
- 8 min: Implement sealed classes with pattern matching
- 8 min: Replace nulls with Optional
- 8 min: Apply Stream API to collections
- 5 min: Use text blocks for SQL/JSON
- 8 min: Update all tests

Total: ~45 minutes (budget: 40 min + 5 min buffer)

## Resources

- [Java 25 Features](https://openjdk.org/projects/jdk/25/)
- [Records Guide](https://docs.oracle.com/en/java/javase/25/language/records.html)
- [Pattern Matching](https://docs.oracle.com/en/java/javase/25/language/pattern-matching.html)
- [Sealed Classes](https://docs.oracle.com/en/java/javase/25/language/sealed-classes-and-interfaces.html)

## Next Exercise

After modernizing your code, the final exercise will test your prompt engineering mastery with a comprehensive feature implementation challenge!
