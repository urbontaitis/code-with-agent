---
description: Automated code review following CLAUDE.md conventions
---

Perform a comprehensive code review of the specified file(s) following CLAUDE.md standards:

**Files to review:** (ask user which files or use current file)

**Review Checklist:**

## 1. Modern Java Usage (21/25)
- [ ] Using records for DTOs instead of classes?
- [ ] Using Optional for nullable returns?
- [ ] Using Stream API instead of for loops?
- [ ] Using pattern matching in switch?
- [ ] Using text blocks for SQL/JSON?
- [ ] Using sealed classes where appropriate?
- [ ] Proper var usage with meaningful names?

## 2. CLAUDE.md Compliance
- [ ] Feature package structure (not layers)?
- [ ] NO JavaDocs present (unless OpenAPI)?
- [ ] NO code comments (self-explanatory code)?
- [ ] Line length ≤ 120 characters?
- [ ] Descriptive method/variable names?
- [ ] Data-first design approach?

## 3. Code Quality
- [ ] YAGNI: No speculative features?
- [ ] KISS: Simple solutions over complex?
- [ ] Immutability: Using final, records, List.of()?
- [ ] Null safety: Using Optional, @NonNull?
- [ ] Exception handling: Proper custom exceptions?
- [ ] Resource management: Try-with-resources?

## 4. Testing (If Spock tests)
- [ ] Given-When-Then structure?
- [ ] NO "private" keyword in Groovy?
- [ ] Using @Unroll for data-driven tests?
- [ ] Descriptive test method names?
- [ ] Using "value" not "value == true"?
- [ ] PollingConditions for async?
- [ ] Mocking with Mock(), Stub()?

## 5. Spring Boot Best Practices
- [ ] Constructor injection (not @Autowired fields)?
- [ ] Proper @Transactional usage?
- [ ] DTOs at controller boundary?
- [ ] Entities not exposed in API?
- [ ] Validation at DTO level (@Valid)?
- [ ] Proper HTTP status codes?

## 6. Performance & Security
- [ ] Database indexes for queries?
- [ ] Lazy/Eager fetch appropriate?
- [ ] N+1 query prevention?
- [ ] SQL injection prevention?
- [ ] Input validation?
- [ ] Sensitive data not logged?

**Output Format:**

```markdown
# Code Review: <FileName>

## Summary
[Overall assessment: Good / Needs Improvement / Refactor Needed]

## Issues Found

### Critical ❌
- [Issue 1 with line number and fix]
- [Issue 2 with line number and fix]

### Important ⚠️
- [Issue 1 with line number and suggestion]

### Minor 💡
- [Improvement 1]

## Suggestions

### Refactoring Opportunities
[Specific refactorings to apply modern Java features]

### Code Examples
```java
// ❌ Current code (line X-Y)
<problematic code>

// ✅ Suggested improvement
<improved code>
```

## CLAUDE.md Compliance Score
- Modern Java: X/10
- Code Style: X/10
- Testing: X/10
- Overall: X/10

## Next Steps
1. [Priority 1 fix]
2. [Priority 2 fix]
```

**Example usage:**
```
/review
# Reviews current file or asks which files to review
```
