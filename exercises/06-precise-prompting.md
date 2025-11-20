# Exercise 6: The Precise Prompting Challenge

**Time:** 25 minutes
**Difficulty:** Advanced
**Goal:** Demonstrate the dramatic impact of prompt precision on code quality, completeness, and token usage

## Objective

Implement the **same feature** (Film Search API) three different ways using increasingly precise prompts. Compare the results to see how prompt quality affects:
- Code completeness
- Test coverage
- Edge case handling
- Token consumption
- Time to working solution

## Task: Advanced Film Search API

Add a comprehensive search endpoint that allows filtering films by multiple criteria with pagination, sorting, and full-text search.

## The Challenge: Three Prompt Approaches

### Round 1: The Vague Prompt (8 minutes)

**Your Prompt:**
```
Add search for films
```

**Execute this with Claude Code and observe:**
- What gets generated?
- What's missing?
- How many follow-up questions does Claude ask?
- How many tokens consumed? (check with `/usage`)
- Does it compile?
- Do tests exist?

**Expected Result:** Minimal implementation, probably just filtering by one field, no tests, no pagination.

---

### Round 2: The Basic Prompt (8 minutes)

**Your Prompt:**
```
Add a search endpoint GET /api/films/search with query parameters
for title, genre, and year range. Include pagination and sorting.
Write tests.
```

**Execute this with Claude Code and observe:**
- Better than Round 1?
- Are all requirements covered?
- What assumptions did Claude make?
- Test coverage quality?
- Token usage vs Round 1?

**Expected Result:** Working endpoint with basic tests, but missing edge cases, validation, documentation.

---

### Round 3: The Precise Prompt (8 minutes)

**Your Prompt:**
```
Analyze @FilmController.java and @FilmService.java and implement
comprehensive film search following CLAUDE.md conventions:

Endpoint: GET /api/films/search

Query Parameters:
- title: Optional<String> - case-insensitive partial match
- genre: Optional<Genre> - exact match (validate enum)
- directorName: Optional<String> - case-insensitive partial match
- yearFrom: Optional<Integer> - inclusive (validate >= 1888)
- yearTo: Optional<Integer> - inclusive (validate <= 2100)
- minRating: Optional<Double> - minimum avg rating (0.0-5.0)
- page: int - default 0, min 0
- size: int - default 20, min 1, max 100
- sort: String - default "title,asc" (options: title, releaseYear, avgRating)

Response:
- Use Spring Data Pageable
- Return Page<FilmDto> with metadata (totalElements, totalPages, etc.)
- If no results, return empty page (not 404)

Implementation:
1. FilmSearchCriteria record (all search params, validation annotations)
2. FilmController.searchFilms() method
   - @Valid on criteria
   - Build Pageable from page/size/sort
   - Call service
   - Return 200 OK with Page<FilmDto>

3. FilmService.searchFilms(FilmSearchCriteria, Pageable)
   - Use Specification pattern for dynamic queries
   - Combine criteria with AND logic
   - Apply pagination and sorting
   - Return Optional.empty() -> empty Page (not throw)

4. FilmSpecifications utility class
   - Static methods for each criterion
   - hasTitleLike(String title)
   - hasGenre(Genre genre)
   - hasDirectorLike(String director)
   - hasYearBetween(Integer from, Integer to)
   - hasMinRating(Double minRating)
   - Combine with Specification.where()

5. Comprehensive Spock tests:

FilmSearchCriteriaSpec.groovy:
- Validate each parameter constraint
- Use @Unroll for data-driven validation tests

FilmServiceSpec.groovy (add search tests):
- "should find films matching all criteria"
- "should return empty page when no matches"
- "should handle null criteria gracefully"
- "should apply pagination correctly"
- "should sort by #sortField #direction" (@Unroll)
- Mock repository with Specification matcher

FilmControllerSpec.groovy (add search tests):
- "should search with valid criteria and return 200"
- "should return 400 for invalid year range"
- "should return 400 for invalid page/size"
- "should return empty page for no results (not 404)"
- "should apply default pagination when not specified"
- Integration test with @Testcontainers
- Use @Unroll for validation scenarios:
  | scenario | yearFrom | yearTo | expectedStatus |
  | "valid range" | 2000 | 2020 | 200 |
  | "invalid yearFrom" | 1800 | 2020 | 400 |
  | "invalid yearTo" | 2000 | 2200 | 400 |
  | "yearFrom > yearTo" | 2020 | 2000 | 400 |

Edge Cases to Test:
- Empty criteria (should return all films, paginated)
- Special characters in search strings
- Very large page numbers
- Invalid sort field (should default or 400)
- All criteria combined
- Case sensitivity verification

Dependencies (if missing):
- Add to @build.gradle if not present

Performance:
- Add database indexes on: title, genre, director, release_year
- Create Flyway migration: V5__add_search_indexes.sql

OpenAPI Documentation:
- @Operation with description
- @ApiResponses for 200, 400
- @Parameter descriptions for each query param
- Example requests in JavaDoc (exception: allowed here for API docs)

Follow CLAUDE.md:
- Feature package structure
- Use records for DTOs
- Optional for nullable service returns
- Stream API where applicable
- Spock tests only (Groovy)
- Given-When-Then test structure
- No "private" in Groovy tests
- Descriptive test method names
- @Unroll for data-driven tests
- No JavaDocs except OpenAPI annotations
- 120 char line limit
```

**Execute this with Claude Code and observe:**
- Complete implementation?
- All edge cases covered?
- Test quality?
- Database indexes created?
- OpenAPI documentation?
- Token usage vs previous rounds?
- Time to working solution?

**Expected Result:** Production-ready feature with comprehensive tests, proper validation, edge cases handled, documented.

---

## Comparison Matrix

After completing all three rounds, fill in this comparison:

| Aspect | Vague | Basic | Precise |
|--------|-------|-------|---------|
| **Lines of Code** | ___ | ___ | ___ |
| **Test Coverage** | ___% | ___% | ___% |
| **Tests Written** | ___ | ___ | ___ |
| **Edge Cases Handled** | ___ | ___ | ___ |
| **Validation** | ❌/⚠️/✅ | ❌/⚠️/✅ | ❌/⚠️/✅ |
| **Documentation** | ❌/⚠️/✅ | ❌/⚠️/✅ | ❌/⚠️/✅ |
| **DB Indexes** | ❌/⚠️/✅ | ❌/⚠️/✅ | ❌/⚠️/✅ |
| **Compilation Errors** | ___ | ___ | ___ |
| **Follow-up Prompts Needed** | ___ | ___ | ___ |
| **Tokens Consumed** | ___ | ___ | ___ |
| **Time to Working Code** | ___ min | ___ min | ___ min |
| **Ready for Production?** | ❌/⚠️/✅ | ❌/⚠️/✅ | ❌/⚠️/✅ |

## Success Criteria

- [ ] All three rounds completed
- [ ] Comparison matrix filled in
- [ ] Precise prompt produces production-ready code
- [ ] Tests pass: `./gradlew test`
- [ ] Search endpoint works manually
- [ ] Database indexes created
- [ ] OpenAPI docs generated

## Testing Your Search Implementation

### 1. Run Tests
```bash
./gradlew test --tests '*Search*'
./gradlew test --info
```

### 2. Manual API Testing

**Prepare test data:**
```bash
# Start infrastructure
docker compose up -d
./gradlew flywayMigrate
./gradlew bootRun

# Create test films
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{"title":"Inception","releaseYear":2010,"genre":"SCI_FI","director":"Christopher Nolan"}'

curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{"title":"Interstellar","releaseYear":2014,"genre":"SCI_FI","director":"Christopher Nolan"}'

curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{"title":"The Dark Knight","releaseYear":2008,"genre":"ACTION","director":"Christopher Nolan"}'

curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{"title":"Tenet","releaseYear":2020,"genre":"SCI_FI","director":"Christopher Nolan"}'
```

**Test search scenarios:**

```bash
# Search by director
curl "http://localhost:8080/api/films/search?directorName=Nolan"
# Expected: All 4 films

# Search by genre
curl "http://localhost:8080/api/films/search?genre=SCI_FI"
# Expected: Inception, Interstellar, Tenet

# Search with year range
curl "http://localhost:8080/api/films/search?yearFrom=2010&yearTo=2020"
# Expected: Inception, Interstellar, Tenet

# Partial title match
curl "http://localhost:8080/api/films/search?title=dark"
# Expected: The Dark Knight

# Combined criteria
curl "http://localhost:8080/api/films/search?genre=SCI_FI&directorName=Nolan&yearFrom=2010"
# Expected: Inception, Interstellar, Tenet

# Pagination
curl "http://localhost:8080/api/films/search?page=0&size=2&sort=releaseYear,desc"
# Expected: First 2 films, sorted by year descending

# No results
curl "http://localhost:8080/api/films/search?genre=HORROR"
# Expected: Empty page (not 404)

# Invalid parameters (should return 400)
curl "http://localhost:8080/api/films/search?yearFrom=1800"
# Expected: 400 Bad Request

curl "http://localhost:8080/api/films/search?size=200"
# Expected: 400 Bad Request (max 100)
```

## Key Learnings

### The Cost of Vague Prompts

**Vague Prompt Economics:**
- Initial tokens: ~500
- Follow-up clarifications: ~2,000 (4 back-and-forth)
- Manual fixes: ~1,500
- **Total: ~4,000 tokens + 30 minutes of your time**

**Precise Prompt Economics:**
- Initial tokens: ~3,500 (longer prompt)
- Follow-ups: 0
- Manual fixes: 0
- **Total: ~3,500 tokens + 5 minutes of your time**

**Savings:** 12.5% fewer tokens, **83% less time**, production-ready result

### Prompt Precision Pyramid

```
        ┌─────────────────┐
        │  Vague Prompt   │  Token waste, time waste, frustration
        │   "Add search"  │
        ├─────────────────┤
        │  Basic Prompt   │  Better, but needs refinement
        │ "Add search API"│
        ├─────────────────┤
        │ Precise Prompt  │  Production-ready, comprehensive
        │ (with context)  │
        └─────────────────┘
             Efficiency ↑
```

### What Makes a Prompt "Precise"?

✅ **DO Include:**
1. Reference existing code patterns (`@FilmController.java`)
2. Exact API contract (endpoint, params, response)
3. Validation requirements with constraints
4. Test requirements (specs to create, scenarios to cover)
5. Edge cases to handle
6. Performance considerations (indexes)
7. Reference to project conventions (`CLAUDE.md`)
8. Expected file structure
9. Technology-specific details (Specification pattern, Pageable)
10. Success criteria

❌ **DON'T Include:**
1. Implementation details (let Claude choose)
2. Redundant context (Claude can read referenced files)
3. Multiple unrelated features in one prompt
4. Ambiguous requirements ("make it fast", "add validation")

### Real-World Impact

Based on 10 years of architecture experience:

**Vague Prompts:**
- ❌ 60% chance of missing edge cases
- ❌ 70% chance of inadequate tests
- ❌ 80% chance of validation gaps
- ❌ 90% chance of missing documentation

**Precise Prompts:**
- ✅ 95% chance of production-ready code
- ✅ Comprehensive test coverage
- ✅ All edge cases handled
- ✅ Proper documentation

## Advanced Challenges

### Challenge 1: Full-Text Search
Add PostgreSQL full-text search to title and director fields:
```
Add full-text search using PostgreSQL tsvector for title and director.
Create GIN index for performance.
Update FilmSpecifications.hasFullTextSearch(String query).
```

### Challenge 2: Search Analytics
Track search queries for analytics:
```
Create SearchQuery entity to log:
- criteria used
- results count
- timestamp
- user (if authenticated)

Publish SearchPerformedEvent to Kafka.
Add async consumer to store analytics.
```

### Challenge 3: Faceted Search
Add facet counts to response:
```
Return facets with search results:
{
  "films": [...],
  "facets": {
    "genres": {"SCI_FI": 12, "ACTION": 8},
    "years": {"2020": 5, "2019": 7},
    "directors": {"Nolan": 4, "Spielberg": 3}
  }
}

Use Spring Data Specification with groupBy queries.
```

## Token Usage Tracking

**Track your token usage with `/usage` command:**

```bash
# Before Round 1
claude /usage

# After Round 1
claude /usage
# Note the difference

# Repeat for Rounds 2 and 3
```

**Expected Token Costs (approximate):**
- **Round 1**: 500 (initial) + 2,000 (clarifications) + 1,500 (fixes) = 4,000 tokens
- **Round 2**: 1,500 (initial) + 800 (refinements) = 2,300 tokens
- **Round 3**: 3,500 (initial) + 0 (no refinements) = 3,500 tokens

**Paradox:** The longest, most detailed prompt uses the FEWEST total tokens!

## Retrospective Questions

1. **Which prompt felt most efficient?**
2. **Which result was most complete on first try?**
3. **How much time did you spend fixing vague prompt results?**
4. **Would you use the vague prompt result in production? Why not?**
5. **What elements of the precise prompt were most valuable?**
6. **How can you apply this to your daily work?**

## Best Practices Summary

### The Precise Prompt Template

```
Analyze @ExistingFile.java and implement [FEATURE] following CLAUDE.md:

[FEATURE DESCRIPTION]

[API CONTRACT / INTERFACE]
- Endpoint/Method signature
- Parameters with types and validation
- Response format

[IMPLEMENTATION REQUIREMENTS]
1. [Component A] with [specific details]
2. [Component B] with [specific details]
3. [Component C] with [specific details]

[TEST REQUIREMENTS]
- [TestSpec1]: scenarios to cover
- [TestSpec2]: edge cases
- Use @Unroll for [parameterized scenarios]

[EDGE CASES TO HANDLE]
- [Edge case 1]
- [Edge case 2]

[PERFORMANCE / DATA]
- Indexes on [fields]
- Migration: [name]

[DOCUMENTATION]
- OpenAPI annotations

Follow CLAUDE.md:
- [Relevant conventions]
- [Project patterns]
```

## Time Breakdown

- 8 min: Round 1 (Vague prompt + fixing)
- 8 min: Round 2 (Basic prompt + refinements)
- 8 min: Round 3 (Precise prompt - working immediately)
- 1 min: Fill comparison matrix

Total: ~25 minutes

## Conclusion

**The Precise Prompting Principle:**

> "Time spent writing a precise prompt is ALWAYS less than time spent fixing code from a vague prompt."

**Your new workflow:**
1. Spend 2-3 minutes crafting a precise prompt
2. Reference existing files with `@`
3. Specify exact requirements
4. Include test scenarios
5. Reference CLAUDE.md conventions
6. Get production-ready code on first try

**ROI of Precision:**
- 83% time savings
- 95% first-try success rate
- Fewer tokens consumed
- Better code quality
- Less frustration

## Workshop Complete! 🎉

You've now mastered:
1. ✅ Prompt engineering fundamentals
2. ✅ Kafka integration
3. ✅ Database evolution with Flyway
4. ✅ Advanced Spock testing
5. ✅ Modern Java refactoring
6. ✅ Precise prompting for production code

**Go forth and code with AI like a pro!**

---

## Bonus: Your Prompt Checklist

Before hitting Enter on your next prompt, check:

- [ ] Referenced existing code with `@file`
- [ ] Specified exact API contract
- [ ] Listed validation requirements
- [ ] Defined test requirements
- [ ] Mentioned edge cases
- [ ] Included performance considerations
- [ ] Referenced CLAUDE.md or project conventions
- [ ] Specified expected file structure
- [ ] Defined success criteria
- [ ] Kept it focused on ONE feature

**If you checked 8+: Your prompt is precise! 🚀**
**If you checked 5-7: Refine before sending**
**If you checked <5: Read Exercise 1 again 😉**
