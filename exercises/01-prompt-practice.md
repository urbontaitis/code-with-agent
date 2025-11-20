# Exercise 1: Prompt Practice

**Time:** 20 minutes
**Difficulty:** Beginner
**Goal:** Master the art of writing effective prompts

## Objective

Learn how prompt quality dramatically affects AI-generated code by writing the same feature request three different ways and comparing results.

## Task

Add a `genre` field to the Film entity with proper validation, database migration, DTO updates, and tests.

## Steps

### 1. Write a BAD Prompt (5 minutes)

Write a vague, context-free prompt. Example characteristics:
- No mention of existing patterns
- No reference to files
- No testing requirements
- No specifics about validation or database

**Example BAD prompt:**
```
Add genre to film
```

Execute this with Claude and observe the results.

### 2. Write a BETTER Prompt (5 minutes)

Improve your prompt with some context. Example characteristics:
- Mention the framework (Spring Boot)
- Specify validation requirements
- Mention database migration

**Example BETTER prompt:**
```
Add a genre field to the Film entity with validation
and a database migration
```

Execute this with Claude and compare to the first attempt.

### 3. Write the BEST Prompt (5 minutes)

Write a comprehensive, context-rich prompt. Include:
- Reference to existing code patterns (`@Film.java`)
- Specific validation requirements
- Database migration details (Flyway naming)
- DTO updates (should be a record)
- Test requirements (Spock, Testcontainers)
- Reference CLAUDE.md conventions

**Example BEST prompt:**
```
Analyze @Film.java and add a genre field (String, not null,
max 50 chars, one of: ACTION, COMEDY, DRAMA, HORROR, SCI_FI,
THRILLER, DOCUMENTARY) with:

- Jakarta Bean Validation annotations matching existing pattern
- Flyway migration V3__add_genre_to_films.sql following
  PostgreSQL 17 best practices
- Update FilmDto record to include genre
- Add validation in FilmService
- Update tests in @FilmRepositorySpec.groovy and
  @FilmServiceSpec.groovy
- Follow all CLAUDE.md conventions (feature packages,
  Spock Given-When-Then, no private in Groovy)

Genre should have enum validation with proper error messages.
```

Execute this with Claude and review the comprehensive implementation.

### 4. Compare Results (5 minutes)

Compare the three implementations:

| Aspect | BAD | BETTER | BEST |
|--------|-----|--------|------|
| Flyway migration | ❌ Missing or incomplete | ⚠️ Basic | ✅ Complete with indexes |
| Validation | ❌ None | ⚠️ Basic @NotNull | ✅ Full enum + constraints |
| DTO updated | ❌ No | ⚠️ Maybe | ✅ Yes, as record |
| Tests updated | ❌ No | ❌ No | ✅ Comprehensive |
| Follows patterns | ❌ No | ⚠️ Somewhat | ✅ Perfectly |
| Time to fix | 30+ min | 15 min | 2 min review |

## Success Criteria

- [ ] Flyway migration `V3__add_genre_to_films.sql` created
- [ ] `Film` entity has `genre` field with validation
- [ ] `FilmDto` record includes genre field
- [ ] Tests in `FilmRepositorySpec` and `FilmServiceSpec` updated
- [ ] All tests pass: `./gradlew test`
- [ ] Migration applies successfully: `docker compose up -d && ./gradlew flywayMigrate`

## Key Learnings

1. **Context is King**: The more context you provide, the better the output
2. **Reference Files**: Use `@` to reference existing code patterns
3. **Be Specific**: Exact requirements eliminate ambiguity
4. **Mention Patterns**: Always reference CLAUDE.md and existing code style
5. **Include Tests**: Always specify testing requirements upfront

## Common Mistakes

❌ "Add a field" - too vague
❌ "Fix the Film entity" - what needs fixing?
❌ "Make it better" - better how?
❌ Pasting entire files - use `@` references instead
❌ No test requirements - tests are critical

## Bonus Challenge

Try writing a prompt that generates:
- A custom validation annotation `@ValidGenre`
- Integration test with Testcontainers that inserts all genres
- REST endpoint filter: `GET /api/films?genre=ACTION`

Compare how different prompts affect the completeness of this more complex feature.

## Time Breakdown

- 5 min: BAD prompt + execution
- 5 min: BETTER prompt + execution
- 5 min: BEST prompt + execution
- 5 min: Compare and discuss results

## Next Steps

This prompt engineering skill is THE foundation for successful AI-assisted development. Use what you learned here in all remaining exercises!
