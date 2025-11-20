# Exercise 3: Database Schema Evolution

**Time:** 30 minutes
**Difficulty:** Intermediate
**Goal:** Add Rating system with Flyway migrations, JPA entities, and comprehensive tests

## Objective

Extend the Film service with a Rating system where users can rate films 1-5 stars with optional review text. Practice database schema evolution using Flyway migrations following PostgreSQL 17 best practices.

## Task: Build Complete Rating System

### Requirements

Create a full-featured Rating system with:
- Flyway migration for `ratings` table
- JPA entity with proper relationships
- Spring Data repository
- Service layer with business logic
- REST endpoint
- Comprehensive Spock tests with Testcontainers

## Detailed Steps

### 1. Design Database Schema (5 minutes)

**Prompt for Claude:**
```
Design a ratings table for PostgreSQL 17 following these requirements:

Columns:
- id: bigserial primary key
- film_id: bigint foreign key to films(id) ON DELETE CASCADE
- user_email: varchar(255) not null
- score: integer not null (check constraint: 1-5)
- review_text: text (nullable)
- created_at: timestamp not null default CURRENT_TIMESTAMP
- updated_at: timestamp not null default CURRENT_TIMESTAMP

Indexes:
- Index on film_id for fast lookups
- Unique index on (film_id, user_email) - one rating per user per film
- Index on created_at for sorting

Create Flyway migration V4__create_ratings_table.sql following:
- PostgreSQL 17 syntax
- snake_case naming
- Proper constraints
- Include helpful comments
- Rollback script: U4__create_ratings_table.sql

Reference @V1__init.sql for migration style.
```

### 2. Create JPA Entity (5 minutes)

**Prompt for Claude:**
```
Create Rating JPA entity in film/rating package:

Requirements:
- Follow @Film.java entity pattern
- @Entity annotation with table name "ratings"
- Fields matching database schema (camelCase in Java, snake_case in DB)
- @ManyToOne relationship to Film (fetch = LAZY)
- Jakarta Bean Validation:
  - @NotNull on required fields
  - @Email on userEmail
  - @Min(1) @Max(5) on score
  - @Size(max=2000) on reviewText
- Lombok or Java 25 record pattern per CLAUDE.md
- @CreatedDate and @LastModifiedDate for timestamps

Also update @Film.java to add:
- @OneToMany(mappedBy="film") for ratings
- Method to calculate average rating
```

### 3. Create Repository (3 minutes)

**Prompt for Claude:**
```
Create RatingRepository extending JpaRepository:

Custom query methods:
- findByFilmId(Long filmId): List<Rating>
- findByFilmIdAndUserEmail(Long filmId, String email): Optional<Rating>
- countByFilmId(Long filmId): Long
- findTop10ByOrderByCreatedAtDesc(): List<Rating> (recent ratings)

Use Spring Data JPA query derivation.
Location: film/rating package following CLAUDE.md structure.
```

### 4. Implement Service Layer (10 minutes)

**Prompt for Claude:**
```
Create RatingService in film/rating package:

Methods:
1. addOrUpdateRating(Long filmId, String userEmail, Integer score, String reviewText)
   - Validate film exists
   - Check if user already rated (update if so)
   - Validate score 1-5
   - Save rating
   - Return Optional<RatingDto>

2. getRatingsForFilm(Long filmId): List<RatingDto>
   - Get all ratings for a film
   - Sort by created_at descending

3. getAverageRating(Long filmId): OptionalDouble
   - Calculate average score
   - Return empty if no ratings

4. getUserRating(Long filmId, String userEmail): Optional<RatingDto>
   - Get specific user's rating

5. deleteRating(Long ratingId, String userEmail)
   - Only allow user to delete their own rating
   - Validate ownership

Error handling:
- Custom exceptions: FilmNotFoundException, UnauthorizedDeletionException
- Use Optional for not-found scenarios
- Proper validation error messages

Create RatingDto record with all fields.
Create RatingMapper (MapStruct or manual).

Follow CLAUDE.md: feature packages, Optional returns, stream API.
```

### 5. Create REST Endpoint (5 minutes)

**Prompt for Claude:**
```
Create RatingController in film/rating package:

Endpoints:
- POST /api/films/{filmId}/ratings
  Body: { userEmail, score, reviewText }
  Returns: 201 Created with RatingDto

- GET /api/films/{filmId}/ratings
  Returns: 200 OK with List<RatingDto>

- GET /api/films/{filmId}/ratings/average
  Returns: 200 OK with { average: 4.5, count: 10 }

- DELETE /api/films/{filmId}/ratings/{ratingId}
  Query param: userEmail
  Returns: 204 No Content

Validation:
- @Valid on request bodies
- @PathVariable validation
- Proper error responses (404, 400, 403)

OpenAPI/Swagger annotations for documentation.
Follow existing @FilmController.java patterns.
```

### 6. Write Comprehensive Tests (15 minutes)

**Prompt for Claude:**
```
Create comprehensive Spock tests:

RatingRepositorySpec.groovy:
- Use @Testcontainers with PostgreSQL
- Test custom query methods
- Verify unique constraint (film_id, user_email)
- Test foreign key cascade delete
- Data-driven tests with @Unroll

RatingServiceSpec.groovy:
- Mock RatingRepository and FilmRepository
- Test all service methods
- Test validation (score 1-5)
- Test update existing rating
- Test average calculation
- Test error scenarios (film not found, unauthorized delete)
- Given-When-Then structure

RatingControllerSpec.groovy:
- @SpringBootTest with @AutoConfigureMockMvc
- Test all endpoints
- Test validation errors (400)
- Test not found errors (404)
- Test unauthorized errors (403)
- Use TestRestTemplate or MockMvc

Integration Test - RatingIntegrationSpec.groovy:
- @Testcontainers with PostgreSQL
- Full flow: create film → rate → get ratings → calculate average
- Test multiple users rating same film
- Test pagination of ratings
- PollingConditions if any async operations

Follow CLAUDE.md:
- No "private" in Groovy
- Use "value" not "value == true"
- Descriptive test names
- @Unroll for parameterized tests
```

## Success Criteria

- [ ] Flyway migration V4__create_ratings_table.sql created
- [ ] Rollback script U4__create_ratings_table.sql created
- [ ] `Rating` entity with proper JPA annotations
- [ ] `Film` entity updated with @OneToMany ratings
- [ ] `RatingRepository` with custom queries
- [ ] `RatingService` with all business logic
- [ ] `RatingDto` record and mapper
- [ ] `RatingController` with REST endpoints
- [ ] Comprehensive test suite (Repository, Service, Controller, Integration)
- [ ] All tests pass: `./gradlew test`
- [ ] Migration applies: `./gradlew flywayMigrate`
- [ ] Manual testing works

## Manual Testing

```bash
# Start infrastructure
docker compose up -d

# Run migrations
./gradlew flywayMigrate

# Start application
./gradlew bootRun

# Create a film
curl -X POST http://localhost:8080/api/films \
  -H "Content-Type: application/json" \
  -d '{"title":"Inception","releaseYear":2010,"genre":"SCI_FI","director":"Christopher Nolan"}'

# Rate the film (filmId=1)
curl -X POST http://localhost:8080/api/films/1/ratings \
  -H "Content-Type: application/json" \
  -d '{"userEmail":"alice@example.com","score":5,"reviewText":"Mind-blowing!"}'

curl -X POST http://localhost:8080/api/films/1/ratings \
  -H "Content-Type: application/json" \
  -d '{"userEmail":"bob@example.com","score":4,"reviewText":"Great movie"}'

# Get all ratings
curl http://localhost:8080/api/films/1/ratings

# Get average rating
curl http://localhost:8080/api/films/1/ratings/average
# Expected: {"average":4.5,"count":2}

# Try duplicate rating (should update)
curl -X POST http://localhost:8080/api/films/1/ratings \
  -H "Content-Type: application/json" \
  -d '{"userEmail":"alice@example.com","score":4,"reviewText":"Changed my mind"}'

# Get ratings again (should show updated rating)
curl http://localhost:8080/api/films/1/ratings
```

## Common Issues

**Issue:** Flyway migration fails
**Solution:** Check PostgreSQL connection and existing schema version

**Issue:** Unique constraint violation
**Solution:** Check if user already rated film - implement update logic

**Issue:** Foreign key cascade not working
**Solution:** Verify ON DELETE CASCADE in migration

## Key Learnings

1. **Flyway Migrations**: Version-controlled database changes
2. **JPA Relationships**: @ManyToOne and @OneToMany with proper fetch types
3. **Validation**: Jakarta Bean Validation at entity and DTO level
4. **Service Layer**: Business logic separation from controllers
5. **Testing Strategy**: Repository, Service, Controller, Integration test layers
6. **Data-First Design**: Schema design drives entity design

## Bonus Challenges

1. **Pagination**: Add paginated endpoint `GET /api/films/{id}/ratings?page=0&size=10`
2. **Sorting**: Allow sorting by score, created date
3. **Filtering**: Get only 5-star ratings, only with review text
4. **Statistics**: Add endpoint for rating distribution (how many 1-star, 2-star, etc.)
5. **Aggregate**: Update Film entity with cached avgRating and ratingCount fields (updated via event or scheduled job)

## Time Breakdown

- 5 min: Database schema design & migration
- 5 min: JPA entity creation
- 3 min: Repository with custom queries
- 10 min: Service layer implementation
- 5 min: REST controller
- 15 min: Comprehensive test suite

Total: ~43 minutes (budget: 30 min + 13 min buffer)
