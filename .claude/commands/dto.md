---
description: Generate DTO record with mapper and comprehensive tests
---

Analyze the specified entity class and create a complete DTO implementation following CLAUDE.md:

**Entity to process:** (ask user which entity)

**Generate:**

1. **DTO Record** in same package as entity:
   - Convert all fields to record components
   - Add Jakarta Bean Validation annotations matching entity
   - Use proper Java 25 record syntax
   - No JavaDocs (CLAUDE.md policy)

2. **Mapper Class or Interface**:
   - If MapStruct available: create @Mapper interface
   - Otherwise: create manual mapper class
   - Methods: toDto(Entity) -> Dto, toEntity(Dto) -> Entity
   - Handle collections properly (List<Entity> -> List<Dto>)
   - Use method references where possible

3. **Comprehensive Spock Tests** (DtoMapperSpec.groovy):
   - "should map entity to DTO with all fields"
   - "should map DTO to entity with all fields"
   - "should handle null fields gracefully"
   - "should map list of entities to list of DTOs"
   - Use @Unroll for testing different field combinations
   - Given-When-Then structure
   - No "private" keyword in Groovy

**Follow CLAUDE.md:**
- Feature package structure
- Records for DTOs
- Stream API for collections
- Spock tests only
- 120 char line limit
- Descriptive test names

**Output files:**
- `<Entity>Dto.java` (record)
- `<Entity>Mapper.java` (or MapStruct interface)
- `<Entity>MapperSpec.groovy` (tests)

**Example usage:**
```
/dto
# Claude asks: "Which entity should I create a DTO for?"
# You respond: "Film"
# Claude generates: FilmDto.java, FilmMapper.java, FilmMapperSpec.groovy
```
