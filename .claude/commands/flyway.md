---
description: Generate Flyway migration for database schema changes
---

Create a Flyway migration following PostgreSQL 17 best practices and CLAUDE.md conventions:

**Change description:** (ask user what database change is needed)

**Generate:**

1. **Determine next version number**:
   - Check existing migrations in `src/main/resources/db/migration/`
   - Use next sequential version: V{n}__description.sql

2. **Migration file** (src/main/resources/db/migration/V{n}__*.sql):
   - PostgreSQL 17 syntax
   - snake_case naming (not camelCase)
   - Proper data types
   - NOT NULL constraints
   - Foreign keys with ON DELETE/UPDATE clauses
   - Check constraints for validation
   - Helpful comments explaining purpose
   - Indexes for query performance

3. **Rollback file** (if requested):
   - U{n}__description.sql
   - Reverse all changes safely

4. **Update Entity** (if creating table):
   - JPA entity with proper annotations
   - @Table(name = "snake_case_table")
   - @Column(name = "snake_case") for camelCase fields
   - Relationships (@ManyToOne, @OneToMany)
   - Jakarta Bean Validation

5. **Repository** (if creating table):
   - Extends JpaRepository
   - Custom query methods
   - Location: feature package

**Common Migration Patterns:**

**Create Table:**
```sql
-- V{n}__create_table_name.sql
CREATE TABLE table_name (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_table_name_name ON table_name(name);

COMMENT ON TABLE table_name IS 'Purpose of this table';
```

**Add Column:**
```sql
-- V{n}__add_column_to_table.sql
ALTER TABLE table_name
ADD COLUMN new_column VARCHAR(100);

COMMENT ON COLUMN table_name.new_column IS 'Purpose of column';
```

**Add Foreign Key:**
```sql
-- V{n}__add_foreign_key.sql
ALTER TABLE child_table
ADD CONSTRAINT fk_child_parent
FOREIGN KEY (parent_id)
REFERENCES parent_table(id)
ON DELETE CASCADE;

CREATE INDEX idx_child_table_parent_id ON child_table(parent_id);
```

**Add Check Constraint:**
```sql
-- V{n}__add_constraint.sql
ALTER TABLE table_name
ADD CONSTRAINT chk_score_range
CHECK (score >= 1 AND score <= 5);
```

**Create Index:**
```sql
-- V{n}__add_indexes.sql
CREATE INDEX idx_table_name_column ON table_name(column);
CREATE INDEX idx_table_name_composite ON table_name(col1, col2);

-- Full-text search
CREATE INDEX idx_table_name_fulltext ON table_name
USING GIN(to_tsvector('english', title || ' ' || description));
```

**Validation:**
- Check SQL syntax
- Verify foreign key references exist
- Ensure indexes on foreign keys
- Add comments for clarity
- Use appropriate data types

**Follow CLAUDE.md:**
- snake_case in database
- camelCase in Java entities
- Proper @Column mapping
- Feature package structure

**Test Migration:**
```bash
# Apply migration
./gradlew flywayMigrate

# Check status
./gradlew flywayInfo

# Rollback (if U*.sql exists)
./gradlew flywayUndo
```

**Example usage:**
```
/flyway
# Claude asks: "What database change do you need?"
# You respond: "Add ratings table with foreign key to films"
# Claude generates: V4__create_ratings_table.sql, Rating.java, RatingRepository.java
```
