---
description: Generate complete JPA entity with repository, service, tests, and migration
---

Create a complete feature package with JPA entity following CLAUDE.md conventions:

**Entity name:** (ask user for entity name and fields)

**Generate complete feature:**

## 1. Database Migration (Flyway)

**File:** `src/main/resources/db/migration/V{n}__create_{entity}_table.sql`

```sql
CREATE TABLE {entity_plural} (
    id BIGSERIAL PRIMARY KEY,
    -- fields based on user input
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX idx_{table}_{field} ON {table}({field});

-- Comments
COMMENT ON TABLE {table} IS 'Purpose of this entity';
```

## 2. JPA Entity

**File:** `src/main/java/com/exacaster/workshop/{entity}/{Entity}.java`

```java
@Entity
@Table(name = "{entity_plural}")
public class {Entity} {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fields with proper JPA annotations
    // @Column(name = "snake_case") for camelCase fields
    // Jakarta Bean Validation (@NotNull, @Size, etc.)

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Relationships if specified
    // @ManyToOne, @OneToMany with proper fetch types

    // Getters/setters (or use Lombok @Data)
}
```

## 3. Repository

**File:** `src/main/java/com/exacaster/workshop/{entity}/{Entity}Repository.java`

```java
public interface {Entity}Repository extends JpaRepository<{Entity}, Long> {

    // Custom query methods based on common use cases
    Optional<{Entity}> findByName(String name);
    List<{Entity}> findByCreatedAtAfter(Instant date);

    // Complex queries with @Query if needed
}
```

## 4. DTO and Mapper

**File:** `src/main/java/com/exacaster/workshop/{entity}/{Entity}Dto.java`

```java
public record {Entity}Dto(
    Long id,
    // fields with validation
    @NotNull String name,
    Instant createdAt,
    Instant updatedAt
) {}
```

**File:** `src/main/java/com/exacaster/workshop/{entity}/{Entity}Mapper.java`

```java
@Component
public class {Entity}Mapper {
    public {Entity}Dto toDto({Entity} entity) {
        return new {Entity}Dto(
            entity.getId(),
            entity.getName(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public {Entity} toEntity({Entity}Dto dto) {
        var entity = new {Entity}();
        // map fields
        return entity;
    }
}
```

## 5. Service Layer

**File:** `src/main/java/com/exacaster/workshop/{entity}/{Entity}Service.java`

```java
@Service
public class {Entity}Service {

    private final {Entity}Repository repository;
    private final {Entity}Mapper mapper;

    public {Entity}Service({Entity}Repository repository, {Entity}Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<{Entity}Dto> findById(Long id) {
        return repository.findById(id)
            .map(mapper::toDto);
    }

    public List<{Entity}Dto> findAll() {
        return repository.findAll().stream()
            .map(mapper::toDto)
            .toList();
    }

    public {Entity}Dto create({Entity}Dto dto) {
        var entity = mapper.toEntity(dto);
        var saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    public Optional<{Entity}Dto> update(Long id, {Entity}Dto dto) {
        return repository.findById(id)
            .map(existing -> {
                // update fields
                return repository.save(existing);
            })
            .map(mapper::toDto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
```

## 6. REST Controller

**File:** `src/main/java/com/exacaster/workshop/{entity}/{Entity}Controller.java`

```java
@RestController
@RequestMapping("/api/{entities}")
public class {Entity}Controller {

    private final {Entity}Service service;

    public {Entity}Controller({Entity}Service service) {
        this.service = service;
    }

    @GetMapping
    public List<{Entity}Dto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<{Entity}Dto> getById(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<{Entity}Dto> create(@Valid @RequestBody {Entity}Dto dto) {
        var created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<{Entity}Dto> update(
            @PathVariable Long id,
            @Valid @RequestBody {Entity}Dto dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

## 7. Comprehensive Spock Tests

### Repository Test
**File:** `src/test/groovy/com/exacaster/workshop/{entity}/{Entity}RepositorySpec.groovy`

```groovy
@Testcontainers
@SpringBootTest
class {Entity}RepositorySpec extends Specification {

    @Shared
    PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17")

    @Autowired
    {Entity}Repository repository

    def "should save and retrieve {entity}"() {
        given: "a new {entity}"
        def entity = new {Entity}(name: "Test")

        when: "saving"
        def saved = repository.save(entity)

        then: "can retrieve by ID"
        def retrieved = repository.findById(saved.id)
        retrieved.isPresent()
        retrieved.get().name == "Test"
    }
}
```

### Service Test
**File:** `src/test/groovy/com/exacaster/workshop/{entity}/{Entity}ServiceSpec.groovy`

### Controller Test
**File:** `src/test/groovy/com/exacaster/workshop/{entity}/{Entity}ControllerSpec.groovy`

**Follow CLAUDE.md:**
- Feature package structure: all files in `{entity}/` package
- Use records for DTOs
- Optional for nullable service returns
- Stream API in service methods
- Spock tests with Given-When-Then
- No JavaDocs (except OpenAPI)
- 120 char line limit

**Example usage:**
```
/entity
# Claude asks: "What entity do you want to create?"
# You respond: "Product with fields: name (String, required), price (BigDecimal, required), category (String)"
# Claude generates: complete Product feature with migration, entity, repo, service, controller, tests
```
