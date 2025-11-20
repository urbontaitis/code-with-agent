---
title: Java + Claude code
marp: true
theme: default
html: true
paginate: true
backgroundColor: rgb(25, 29, 39)
color: rgb(224, 224, 224)
footer: 'Metasite | 2025-11-20'
style: |
  @import url('https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;700&display=swap');

  section {
    font-family: 'MonoLisa', 'JetBrains Mono', 'Courier New', monospace;
    background: rgb(25, 29, 39);
    color: rgb(224, 224, 224);
    padding: 40px 60px 60px 60px;
  }

  h1, h2, h3 {
    color: rgb(229, 200, 144);
    font-weight: bold;
    text-shadow: 0 0 10px rgba(229, 200, 144, 0.3);
  }

  h1 {
    font-size: 2.5em;
    border-bottom: 2px solid rgb(109, 151, 255);
    padding-bottom: 10px;
  }

  code {
    background: rgb(53, 66, 109);
    color: rgb(103, 191, 206);
    padding: 3px 8px;
    border-radius: 4px;
    font-family: 'MonoLisa', 'JetBrains Mono', monospace;
  }

  pre {
    background: rgb(35, 42, 61);
    border: 1px solid rgb(109, 151, 255);
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  }

  pre code {
    background: transparent;
    color: rgb(221, 229, 235);
    padding: 0;
  }

  strong {
    color: rgb(210, 123, 206);
    font-weight: bold;
  }

  em {
    color: rgb(103, 191, 206);
    font-style: italic;
  }

  ul, ol {
    color: rgb(224, 224, 224);
  }

  li {
    margin: 8px 0;
  }

  table {
    border-collapse: collapse;
    margin: 20px 0;
    font-size: 0.9em;
  }

  th {
    background: rgb(109, 151, 255);
    color: rgb(25, 29, 39);
    padding: 12px;
    font-weight: bold;
  }

  td {
    border: 1px solid rgb(70, 93, 137);
    padding: 10px;
  }

  tr:nth-child(even) {
    background: rgb(35, 42, 61);
  }

  footer {
    color: rgb(221, 229, 235);
    font-size: 0.4em;
  }
---

# Java + Claude Code

## AI-Assisted Development Workflow

```
$ whoami

Mindaugas Urbontaitis - Team 8


$ date

2025-11-20
```


<!--
Slide 1: Java + Claude Code intro
-->

---

# What is Claude Code?

```
NAME
    claude-code - AI-powered coding assistant

FEATURES
    * Understands entire codebases
    * Executes commands and tests
    * Context-aware suggestions
    * Git, Gradle, npm integration
    * Learns project patterns
```

<!--
Slide 2: What is Claude Code?
- Official Anthropic CLI tool
- More than autocomplete - understands context
- Can read files, execute commands, run tests
- Integrates with development tools
- Learns from your project structure and conventions
- Available across multiple platforms - CLI focus today
-->

---

# Why Claude Code for Java?

```
Traditional Dev         AI-Assisted Dev
────────────────        ───────────────
Manual boilerplate  →   Instant generation
Write tests         →   Auto-create tests
Google errors       →   Intelligent debug
StackOverflow copy  →   Context-aware code
Slow iteration      →   Rapid prototyping

Result: Focus on architecture, not syntax
```

<!--
Slide 3: Why Claude Code for Java?
- Modern Java + AI = 3-4x productivity on repetitive tasks
- Shift from typing code to designing systems
- Metrics from real-world architect experience
- Boilerplate, DTOs, tests generated instantly
- More time for architecture and business logic
- Still need to review and understand the code
- AI assists, doesn't replace developer judgment
-->

---

# Workshop Timeline

```
• Introduction
• Claude Code Commands
• Effective Prompting
• EXERCISE 1: Prompting
• Configuration (CLAUDE.md)
• EXERCISE 2: CLAUDE.md
• BREAK
• Custom Commands
• EXERCISE 3: Commands
• Skills & Agents
• EXERCISE 4: Kafka
• MCP Overview
• Final Exercises
• Wrap-up & Q&A
```

<!--
Slide 4: Workshop Timeline
- Workshop starts at 13:00
- Setup should be completed beforehand
- 4-hour intensive workshop
- Mix of theory and hands-on practice
- Exercises interleaved with theory for better retention
- One 10-minute break
- Total 8 exercises covering all concepts
- Final 15 minutes for Q&A and discussion
-->

---

# Learning Outcomes

- Write effective prompts for Claude
- Configure Claude Code for projects
- Create custom commands and skills
- Understand when to use agents
- Build event-driven Spring Boot apps
- Master Spock testing with Testcontainers
- Optimize token usage

<!--
Slide 5: Learning Outcomes
- Practical, production-ready skills
- Not theoretical - real workflows
- Applicable immediately to daily work
- Focus on sustainable AI-assisted development (Dėmesys tvariam AI development'ui
- Understanding trade-offs and tool selection
- Cost optimization strategies
- Modern Java best practices with AI assistance
-->

---

# Prerequisites

```
Required:
─────────
[ ] Java 25 (we'll install via SDKMAN)
[ ] Claude Code CLI (npm install)
[ ] Docker (PostgreSQL & Testcontainers)
[ ] Claude Code paid plan
```

<!--
Slide 6: Prerequisites
- Java 25 recommended (21+ works, but we use 25 features)
- Node.js 18+ required for Claude CLI
- Docker Desktop on macOS, Docker Engine on Linux
- Claude Code paid plan includes usage, no API key needed
- Workshop uses `claude code login` for authentication
-->

---

# Setup: SDKMAN

```
$ curl -s "https://get.sdkman.io" | bash
$ source ~/.sdkman/bin/sdkman-init.sh
$ sdk version
SDKMAN 5.18.2

$ sdk install java 25.0.1-tem
$ sdk default java 25.0.1-tem
$ java --version
openjdk version "25.0.1"
```

**Why SDKMAN?**
- Manage multiple Java versions
- Switch per-project
- Zero conflicts

<!--
Slide 7: Setup - SDKMAN
- SDKMAN is the JVM version manager
- One command installation
- Temurin = Eclipse's open-source, production-ready JDK
- Essential for multi-project work with different Java versions
- Can also install Gradle, Maven, and other JVM tools
- Takes 1-2 minutes to install Java
- Alternative: Manual download from adoptium.net
-->

---

# Setup: Claude Code CLI

```
$ npm install -g @anthropic-ai/claude-code
$ claude --version
@anthropic-ai/claude-code 1.5.0

$ claude code login

Opening browser for authentication...
✓ Logged in successfully!

$ claude "Hello, Claude!"
Hello! How can I help you today?
```

<!--
Slide 8: Setup - Claude Code CLI
- Global npm installation
- Use `claude code login` for authentication (browser-based)
- Claude Code paid plan includes usage
- No API key needed from console.anthropic.com
- Login stored securely
- Test with simple prompt to verify
-->

---

# Verification Checklist

```
Verify all tools are working:
─────────────────────────────
$ java --version
openjdk version "25.0.1"
✓ Java 25
$ claude --version
@anthropic-ai/claude-code 1.5.0
✓ Claude CLI
$ docker ps
CONTAINER ID   IMAGE   STATUS   PORTS
✓ Docker running
$ claude "test"
Hello! How can I help you today?
✓ Claude authenticated

All set! Let's start coding.
```

<!--
Slide 9: Verification Checklist
- Single verification slide for all tools
- Java 25 via SDKMAN
- Claude Code CLI installed and authenticated
- Docker running (for PostgreSQL and Testcontainers)
- All must work without errors before continuing
- Raise hand if any issues
- Docker Desktop for macOS, Docker Engine for Linux
-->

---

# Claude Code Commands

## Essential Commands Overview

```
/init       Analyze codebase, generate CLAUDE.md
/clear      Reset conversation context
/model      Switch AI models (haiku/sonnet/opus)
/context    View context usage

Keyboard:
──────────
Shift+Tab   Switch plan ↔ edit mode
Ctrl+C      Cancel operation
```

<!--
Slide 10: Claude Code Commands - Essential Commands Overview
- Built-in commands for common tasks
- Slash commands vs keyboard shortcuts
- /init creates initial project configuration
- /clear manages token usage
- /model optimizes cost/quality
- Shift+Tab is THE most important keyboard shortcut
- We'll demo each command with real examples
- More commands available, these are essentials
-->

---

# Shift+Tab: Plan vs Edit Mode

```
PLAN MODE               EDIT MODE
──────────              ─────────
• Claude proposes       • Claude executes
• Shows plan first      • Makes changes
• You approve           • Faster workflow
• Safer for learning    • For experienced users

Press Shift+Tab to switch modes anytime
```
**When to use Plan Mode:**
```
- Learning Claude Code      - Unfamiliar codebase
- Complex changes           - Review before execution
```
<!--
Slide 11: Shift+Tab - Plan vs Edit Mode
- Plan mode is DEFAULT and recommended
- Claude shows plan, waits for approval
- Edit mode executes immediately (faster but riskier)
- Shift+Tab toggles between modes
- Plan mode great for workshop learning
- Can switch mid-conversation
- Plan mode prevents mistakes
- Edit mode for experienced users with trusted prompts
- Start with plan mode, graduate to edit mode
-->

---

# Rewind Functionality

```
Conversation history:
1. > Create Film entity
2. > Add validation
3. > Oops, wrong approach

$ /rewind 2

Back to step 2, continue from there

Use cases:
──────────
• Wrong implementation path
• Made a mistake
• Want to try different approach
• Undo recent changes
```

<!--
Slide 12: Rewind Functionality
- Rewind goes back in conversation history
- Preserves earlier context
- Useful when implementation goes wrong direction
- Can specify step number to rewind to
- Or use /rewind without number for last step
- Context preserved up to rewind point
- Helpful for experimentation
- Saves starting over from scratch
- Like git reset but for conversations
-->

---

# Context Management

```
$ claude
> /context

Context Usage:
██████████████▓▓▓▓▓▓ 70% full

Interpretation:
───────────────
Green:  Plenty of room
Yellow: Getting full
Orange: Consider /clear
Red:    Clear soon

$ /clear
Context reset, CLAUDE.md preserved
```

<!--
Slide 13: Context Management
- Context window has limits (200K tokens for Sonnet)
- /context shows visual usage meter
- Long conversations accumulate tokens
- /clear resets context but keeps CLAUDE.md
- Best practice: clear every 30-40 minutes
- Or between unrelated features
- Prevents hitting context limits
- Manages costs effectively
- CLAUDE.md always loaded, so project knowledge preserved
-->

---

# File References with @

```
Prompt without @:
─────────────────
> Create a service like the user service

Claude guesses what you mean

Prompt with @:
──────────────
> Analyze @UserService.java and create
> similar ProfileService

Claude sees exact code, follows patterns

Benefits:
─────────
        • Precise context       • Token efficient
        • Pattern matching      • Consistent output
```

<!--
Slide 14: File References with @
- @ symbol references specific files
- Claude reads and understands the file
- More accurate than describing patterns
- Tab completion for file paths
- Can reference multiple files: @User.java @UserDto.java
- Also works with directories: @src/user/
- Token efficient - only loads when needed
- Perfect consistency with existing code
- Essential for maintaining code style
- Use @ liberally for best results
-->

---

# Model Switching

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Terminal Example:**

```
$ claude --model sonnet
> Create FilmService...
[Development work]

> /model opus
> Design event-driven
> architecture...
[Architectural decision]

> /model haiku
> Format this JSON...
[Simple task]
```
</div>
<div>


**Model Comparison:**

<style scoped>
table {
  color: #000;
}
</style>

| Model  | Cost | Speed | Use Case     |
|--------|------|-------|--------------|
| Haiku  | $    | Fast  | Formatting   |
| Sonnet | $$   | Mid   | Features     |
|  Opus  | $$$  | Slow  | Architecture |

**Match model to complexity**

</div>
</div>

<!--
Slide 15: Model Switching
- Left: Terminal workflow example
- Right: Quick reference table
- Match model power to task complexity
- /model switches mid-conversation
- Context preserved when switching
- Haiku: Simple tasks, formatting, quick answers
- Sonnet: Daily development, features, tests
- Opus: Architecture, complex decisions, design
- Cost optimization: Use cheapest model that works
- Sonnet default for most work
- Can start session with --model flag
- Or switch anytime with /model command
-->

---

# Practical Command Workflow

```
Session start:                              Context management:
──────────────                              ───────────────────
$ claude                                    > /context
[Plan mode by default]                      70% full
During development:                         > /clear
───────────────────                         Context reset
> Analyze @Film.java...                       
> Create similar Book entity...             Model optimization: 
[Shift+Tab to edit mode if confident]       ───────────────────
                                            > /model haiku
                                            > Format code...
                                            > /model sonnet
                                            > Back to features...
```

<!--
Slide 16: Practical Command Workflow
- Typical workflow example
- Start in plan mode for safety
- Use @ for file references
- Check context periodically
- Clear between unrelated features
- Switch models based on task
- Shift+Tab when you want faster execution
- These commands form foundation of efficient Claude usage
- Practice makes perfect
- Will use these throughout workshop
-->

---

# Claude Code Commands

## Most Used Commands Reference

```
/init               Initialize CLAUDE.md    /memory             Edit CLAUDE.md files
/clear              Reset context           /todos              Show task list
/model <name>       Switch AI model         /rewind             Go back in history
/context            View token usage        /review             Request code review
/compact            Compress context        /permissions        Check allowed commands
/add-dir <path>     Allow access to dir     /status             Version & account info
```
**Check your permissions:**
```
$ cat ~/.claude/settings.json
```
More: /help shows all 30+ commands | https://code.claude.com/docs/en/slash-commands
<!--
Slide 17: Claude Code Commands - Most Used Commands Reference
- /init: Initialize project with CLAUDE.md setup
- /memory: Edit CLAUDE.md files directly in session
- /clear: Reset conversation history (fresh start)
- /todos: Display current task list from TodoWrite
- /model: Switch between haiku/sonnet/opus mid-session
- /rewind: Go back in conversation history (undo approach)
- /context: Visual token usage meter (manage context window)
- /review: Request code review from Claude
- /compact: Compress conversation history to save context space
  - Summarizes older messages while preserving key information
  - Alternative to /clear when you need to retain some history
- /permissions: Shows what commands Claude can execute
- /add-dir: Grant Claude access to additional directories outside current project
  - Useful for monorepos or referencing code in sibling directories
  - Example: /add-dir ../shared-library to access shared code
  - Security: Only adds access for current session
- /status: Check Claude Code version, model, account info
- 30+ total commands available via /help
- Settings.json controls allowed operations
- Official docs link for complete reference
-->

---

# Effective Prompting

## The Critical Skill

**Prompt quality determines output quality**

```
Generic prompt:
───────────────
"Create a user service"
Result: 30+ minutes of fixes

Specific prompt:
────────────────
"Analyze @UserService.java pattern, create
ProfileService with AWS Cognito, BaseService
error handling, Spring Cache, Spock tests"
Result: 5 minutes to review
```

<!--
Slide 18: Effective Prompting - The Critical Skill
- Prompting is THE most important skill
- Quality output requires quality input
- Difference can be 10 minutes vs 2 hours
- Bad prompts waste time and tokens
- Good prompts produce production-ready code
- Think of prompt as task for senior developer
- Provide context, constraints, examples
- We'll practice this extensively
- Mastering prompts = mastering AI assistance
-->

---

# Bad vs Good Prompts

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**BAD PROMPT:**

```
> Create a user service
```
**Claude thinks:**
- No context provided
- Assume generic Spring Boot?
- JUnit or Spock?
- What error handling?
- Which patterns?

**Result:** 
Generic, doesn't fit project

</div>

<div>

**GOOD PROMPT:**

```
> Analyze @UserService.java
> Create ProfileService that:
> • Uses BaseService errors
> • Spring Cache + Redis
> • Spock Given-When-Then
> • Records for DTOs
> • Optional for nulls
```

**Result:** Production-ready, follows patterns

</div>

</div>

<!--
Slide 19: Bad vs Good Prompts
- Side-by-side comparison shows contrast
- Left: Vague prompt leads to guessing
- Right: Specific prompt with clear requirements
- Generic prompts force Claude to guess
- Claude fills gaps with assumptions
- Often guesses wrong for your project
- Specific prompts provide clear direction
- Reference existing code with @
- List requirements explicitly
- Specify patterns and frameworks
- Good prompts save massive time
- Bad prompts require extensive fixes
-->

---

# Prompt Structure Formula

```
        1. CONTEXT
           ┌──────────────────────────┐
           │ Spring Boot 3.5 app      │
           │ PostgreSQL + DDD         │
           │ Feature packages         │
           └──────────────────────────┘
        
        2. GOAL
           ┌──────────────────────────┐
           │ Create Rating system     │
           │ 1-5 stars + review text  │
           └──────────────────────────┘
        
        3. CONSTRAINTS
           ┌──────────────────────────┐
           │ • Flyway migrations      │
           │ • Follow Film pattern    │
           │ • Spock + Testcontainers │
           └──────────────────────────┘
        
        4. PATTERNS
           ┌──────────────────────────┐
           │ @Film.java @FilmRepo     │
           └──────────────────────────┘
```

<!--
Slide 20: Prompt Structure Formula
- Four-part formula for effective prompts
- Context: What's the project environment?
- Goal: What do you want to achieve?
- Constraints: What rules must be followed?
- Patterns: What existing code to reference?
- Think of it as a ticket for senior developer
- Include all necessary information
- Don't make Claude guess
- Reference files with @ when possible
- This structure works for 90%+ of prompts
-->

---

# Anti-Patterns in Prompting

```
Common mistakes:                                Always provide:
────────────────                                ───────────────
"Improve this code"                             • Specific goals
└─> Improve what? How?                          • Context and constraints    
                                                • Pattern references                                                    
"Fix this bug"                                  • Success criteria
└─> No stacktrace, no context                   

"Make it faster"
└─> What's slow? Acceptable?

"Add tests"
└─> What kind? Coverage? Patterns?

Pasting entire files
└─> Use @ references instead

No existing patterns
└─> Leads to inconsistent code
```

<!--
Slide 21: Anti-Patterns in Prompting
- Common mistakes waste time
- Vague prompts lead to vague results
- Always be specific about what you want
- Provide error messages and stacktraces
- Define performance requirements
- Specify test frameworks and patterns
- Use @ instead of pasting code
- Reference existing patterns for consistency
- Think: "Would I understand this as a developer?"
- Golden rule: Be specific, contextual, reference patterns
-->

---

# Token Economics

```
Model Pricing (per 1M tokens):
═══════════════════════════════

┌────────┬────────┬────────┬─────────────┐
│ Model  │ Input  │ Output │ Best For    │
├────────┼────────┼────────┼─────────────┤
│ Haiku  │ $0.25  │ $1.25  │ Simple      │
│ Sonnet │ $3.00  │ $15.00 │ Daily dev   │
│ Opus   │ $15.00 │ $75.00 │ Complex     │
└────────┴────────┴────────┴─────────────┘
Optimization strategies:
────────────────────────
• Use @ for file references
• /clear between unrelated tasks
• Match model to complexity
• Review context usage
```
<!--
Slide 22: Token Economics
- Pricing source: https://www.anthropic.com/pricing
- Tokens = real money, optimize usage
- Input tokens: What you send to Claude
- Output tokens: What Claude generates
- Haiku: 20x cheaper than Opus for input
- Use cheapest model that works
- @ references more efficient than pasting
- /clear prevents context bloat
- Monitor with /context
- Typical session: $0.50-$2.00 with Sonnet
- Smart model selection saves 40%+ on costs
-->

---

# EXERCISE 1: Prompt Practice

**Task:** Add "genre" field to Film entity

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
<div>

**Steps:**
```
1. Write a BAD prompt (vague, no context)
2. Write a BETTER prompt (some context)
3. Write a BEST prompt (full context + patterns)
4. Execute BEST prompt with Claude
5. Review generated code
```

</div>
<div>

**Success Criteria:**
- Flyway migration created
- Entity updated with validation
- DTO updated (record)
- All tests pass

</div>
</div>

<!--
Slide 23: EXERCISE 1 - Prompt Practice
- First hands-on exercise
- Practice the concepts just learned
- Write all three prompts before executing
- Compare quality of prompts
- Only execute the BEST prompt
- Review what Claude generates
- Discuss results as group
- Common patterns will emerge
- Learn from each other's prompts
- This builds prompt-writing muscle memory

PROMPT EXAMPLE:
"Analyze @Film.java and add a 'genre' field:
1. Create Flyway migration V3__add_genre_to_films.sql
2. Add genre VARCHAR(50) NOT NULL with validation
3. Update Film entity with @NotBlank validation
4. Update FilmDto record
5. Update mapper and all Spock tests
6. Follow CLAUDE.md conventions for naming and structure
Success: All tests passing, migration applied"
-->

---

# Configuration: CLAUDE.md

```
What CLAUDE.md stores:
──────────────────────
• Project conventions
• Architecture decisions
• Code style & patterns
• Testing strategies
• Dependencies & frameworks

Result: Claude writes YOUR code style
Location:
─────────
.claude/CLAUDE.md        ← Project (team)
~/.claude/CLAUDE.md      ← Personal (you)
```

<!--
Slide 24: Configuration - CLAUDE.md
- CLAUDE.md is the secret weapon
- Auto-referenced in every conversation
- Contains your project's coding DNA
- 1 hour setup saves days of corrections
- Claude learns your patterns and style
- Team can share via git (.claude/ directory)
- Personal config in home directory
- Both loaded and merged
- Project config overrides personal
- This is what makes Claude write code that matches your project
-->

---

# Generating CLAUDE.md

```
$ claude
> /init
Analyzing project structure...
████████████████████████ 100%

Detected:
├─ Spring Boot 3.5.0
├─ PostgreSQL 17
├─ Spock Framework 2.4-M4
├─ Testcontainers 1.20.4
├─ Gradle 9
└─ Java 25
Architecture:
└─ Feature-based packages (DDD)
Generated: .claude/CLAUDE.md
This is a STARTING POINT
Customize for YOUR patterns
```

<!--
Slide 25: Generating CLAUDE.md
- /init auto-generates CLAUDE.md
- Analyzes your codebase automatically
- Detects frameworks, tools, patterns
- Creates initial configuration
- Good starting point but needs customization
- Review and enhance the generated file
- Add your team's specific conventions
- Include error handling patterns
- Document testing strategies
- Update as project evolves
-->

---

# Customizing CLAUDE.md

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
$ vim .claude/CLAUDE.md

## Code Conventions
──────────────────
Java 17+ Features:
• Records for DTOs
• Pattern matching
• Sealed classes
• Virtual threads
• Stream Gatherers

NEVER:
• Lombok (use records)
• Null returns (use Optional)
```

</div>
<div>

```
Style:
• 120 char line limit
• Prefer Optional<T>
• Stream API for collections

## Testing Strategy
────────────────────
Spock Framework (Groovy 4.0)
• Given-When-Then structure
• NEVER use value == true
• Use @Unroll for data-driven
• Testcontainers for PostgreSQL
```
</div>
</div>

<!--
Slide 26: Customizing CLAUDE.md
- Customization transforms output quality
- Add your team's specific rules
- Document what to use and what to avoid
- Include code examples where helpful
- Testing patterns especially important
- Error handling conventions
- Database naming standards
- Package structure rules
- Review and update regularly
- This gets Claude to 90%+ accuracy on first try
-->

---

# Multiple CLAUDE.md Files

```
$ tree .claude/
.claude/
├─ CLAUDE.md              # Main conventions
├─ CLAUDE_AWS.md          # Cloud patterns
├─ CLAUDE_TESTING.md      # Test strategies
├─ CLAUDE_DATABASE.md     # Data layer
└─ CLAUDE_SECURITY.md     # Auth patterns
```
**Why split?**
```
• Focused per domain
• Easier to maintain
• Team ownership (different authors)
• All auto-loaded by Claude
Note: ALL CLAUDE*.md files loaded automatically
Cannot selectively choose which files to load
```


<!--
Slide 27: Multiple CLAUDE.md Files
- Large projects benefit from split files
- Separate domains for clarity
- Different team members can own different files
- IMPORTANT: All CLAUDE*.md automatically loaded
- Cannot selectively choose individual files
- Claude merges all matching files at startup
- Pattern: CLAUDE*.md in .claude/ directory
- Easier to maintain than single huge file
- Domain experts can update their area
- Example: Security team maintains CLAUDE_SECURITY.md
- Keep main CLAUDE.md for core conventions
- Use others for specialized domains
-->

---

# Settings Configuration

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
$ cat ~/.claude/settings.json || .claude/settings.local.json
{
  "permissions": {    
      "allow": [
        "Bash(cat:*)",
        "Bash(ls:*)",
        "Bash(rg:*)",
        "Bash(git:log,status,diff,branch)",
        "Bash(./gradlew:clean,build,test)"
      ],
      "deny": [],
      "ask": []
  }
}
```

</div>
<div>

**Security:**
─────────
• Permissive: read-only tools
• Restrictive: write operations
• Control what Claude can execute

</div>
</div>

<!--
Slide 28: Settings Configuration
- settings.json controls permissions
- Define which commands Claude can run
- Security for enterprise environments
- Allow read-only operations broadly
- Restrict write operations carefully
- Set default models
- Environment variables
- Project-level: .claude/settings.json
- User-level: ~/.claude/settings.json
- Project settings override user settings
- Important for team consistency and security
-->

---

# EXERCISE 2: Customize CLAUDE.md

**Task:** Add project-specific sections

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Add:**
```
1. Error handling pattern (with example)
2. Database naming conventions
3. Spock test examples (Given-When-Then)
4. Package structure rules
5. Always/Never rules for your team
```

</div>
<div>

**Test:**
Ask Claude to create a service
Verify it follows your CLAUDE.md

**Location:**
`.claude/CLAUDE.md`

</div>
</div>

<!--
Slide 29: EXERCISE 2 - Customize CLAUDE.md
- Hands-on customization practice
- Add patterns from your real projects
- Or use Film service as example
- Be specific about conventions
- Include code snippets where helpful
- Test by asking Claude to generate code
- Verify output matches your rules
- Share interesting patterns with group
- This exercise builds your project knowledge base
- Time well spent - saves hours later

PROMPT EXAMPLE:
"Update .claude/CLAUDE.md with these sections:
1. Error Handling: Add example using Optional and custom exceptions like @Film.java
2. Database Naming: snake_case tables, camelCase Java fields, explicit @Column names
3. Spock Tests: Given-When-Then example from FilmServiceSpec with @Unroll pattern
4. Package Structure: Feature-based (user/, film/, rating/) not layer-based
5. ALWAYS: Records for DTOs, Optional for nulls, Spock for tests
6. NEVER: null returns, Lombok, JUnit
Test by creating GenreService and verify it follows all conventions"
-->

---

# Custom Commands

## Reusable Prompt Templates

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
Location:
─────────
.claude/commands/     ← Project (team)
~/.claude/commands/   ← Personal (you)

Format:
───────
Markdown + YAML frontmatter

Usage:
──────
> /dto Film
> /review
> /flyway "add rating table"
```
</div>
<div>

**Benefits:**
─────────
• Consistent output
• Team shortcuts
• Save time on repetitive tasks

</div>
</div>
<!--
Slide 30: Custom Commands - Reusable Prompt Templates
- Custom commands = reusable prompts
- Simple markdown files with frontmatter
- Project commands shared via git
- Personal commands for individual workflow
- Arguments with $1, $2 or $ARGUMENTS
- Consistent results every time
- Great for common tasks
- Saves 10-15 minutes per use
- Team can standardize on commands
- Build library over time
-->

---

# Creating Custom Commands

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
$ cat .claude/commands/dto.md

---
description: Generate DTO+mapper+tests
model: sonnet
argument-hint: <EntityName>
---

Analyze $1 entity and create:

1. $1Dto record with all fields
2. $1Mapper using MapStruct
3. $1MapperSpec with Spock tests:
   • Entity → DTO mapping
   • DTO → Entity mapping
   • Null handling
   • Collections (if any)

Follow CLAUDE.md conventions
```

</div>
<div>

```
Usage:
──────
$ claude
> /dto Film

Generates FilmDto, FilmMapper, tests
```
</div>
</div>
<!--
Slide 31: Creating Custom Commands
- Simple markdown file structure
- Frontmatter defines metadata
- $1, $2 for positional arguments
- $ARGUMENTS for all arguments
- description shown in help
- model specifies which AI model to use
- argument-hint helps users
- Clear structure in the prompt
- Reference CLAUDE.md for consistency
- Test command before sharing with team
-->

---

# Example Commands

```
/dto <Entity>
Generate DTO record + mapper + tests

/review
Code review: security, performance, tests

/flyway "description"
Generate Flyway migration + rollback

/entity <Name>
Full entity: JPA + Repository + Service + tests

/test <ClassName>
Generate comprehensive Spock tests
```

<!--
Slide 32: Example Commands
- Common patterns for Java projects
- DTO generation saves 10-15 minutes each
- Review command for quality checks
- Flyway for database migrations
- Entity scaffolding for new features
- Test generation for coverage
- These are examples, create your own
- Tailor to your team's workflow
- Build command library over time
- Share best commands with team
-->

---

# EXERCISE 3: Create Custom Command

**Task:** Build a useful command for your workflow

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Ideas:**
```
- /entity <name> - JPA entity + repository + tests
- /endpoint <path> - REST controller with CRUD
- /kafka-consumer <topic> - Consumer + Testcontainers
- /migration <desc> - Flyway helper
```

</div>
<div>

**Steps:**
```
1. Create `.claude/commands/X.md`
2. Add frontmatter (description, model, hint)
3. Write prompt with arguments ($1, $ARGUMENTS)
4. Reference CLAUDE.md for consistency
5. Test with Claude
```
</div>
</div>

<!--
Slide 33: EXERCISE 3 - Create Custom Command
- Create command for real task you do often
- Think about repetitive work in your projects
- What takes 15-30 minutes every time?
- Turn it into a command
- Test it works as expected
- Refine the prompt if needed
- Share interesting commands with others
- Learn from each other's creativity
- Build your personal command library
- Commands compound in value over time

PROMPT EXAMPLE (/entity command):
"Create .claude/commands/entity.md:
---
description: Generate complete JPA entity with repo, service, tests
model: sonnet
argument-hint: <EntityName>
---

Analyze @Film.java pattern and create $1 entity:
1. JPA entity with @Entity, @Table, validation
2. $1Repository extending JpaRepository
3. $1Service with CRUD operations
4. $1Controller REST endpoints
5. $1Dto record with mapper
6. Flyway migration V*__create_$1_table.sql
7. $1ServiceSpec and $1ControllerSpec with Spock
Follow ALL CLAUDE.md conventions and Film entity patterns

Test: /entity Genre and verify complete implementation"
-->

---

# Skills

## Reusable Procedural Knowledge

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Characteristics:**
```
- Progressive disclosure
- Bundled resources (scripts, templates)
- Reusable across projects
- Can execute code, not just generate
- Portable (CLI, VS Code, claude.ai)
```

</div>
<div>

**vs Commands:**

```
- Commands: Simple prompt templates
- Skills: Complex workflows with execution
```

**Official Skills:**
```
- pdf, docx, xlsx, pptx
- webapp-testing
- mcp-builder
```
https://claudeskillshub.org

</div>
</div>

<!--
Slide 34: Skills - Reusable Procedural Knowledge
- Skills are next level beyond commands
- Progressive disclosure saves tokens
- Only loads full content when relevant
- Can include scripts and resources
- Execute code, not just generate
- More complex than commands
- Portable across Claude platforms
- Official skills from Anthropic
- Community skills on GitHub
- Use for specialized, complex tasks
-->

---

# Skills vs Commands

```
┌────────────┬──────────────┬───────────────┐
│ Aspect     │ Commands     │ Skills        │
├────────────┼──────────────┼───────────────┤
│ Complexity │ Simple       │ Complex       │
│ Execution  │ No           │ Yes           │
│ Structure  │ Single .md   │ Folder        │
│ Loading    │ Always       │ Progressive   │
│ Scope      │ Project      │ Cross-project │
│ Resources  │ No           │ Yes           │
└────────────┴──────────────┴───────────────┘

When to use:
────────────
Commands: Project shortcuts, simple tasks
Skills:   Domain capabilities, execution
```

<!--
Slide 35: Skills vs Commands
- Clear distinction between commands and skills
- Commands for simple, project-specific tasks
- Skills for complex, reusable capabilities
- Commands always loaded (lightweight)
- Skills load progressively (heavy)
- Commands generate code
- Skills can execute code
- Both have their place
- Use commands for 80% of tasks
- Skills for specialized 20%
-->

---

# Creating Skills

```
$ tree my-skill/
my-skill/
├── SKILL.md
├── scripts/
│   └── process.sh
└── resources/
    └── template.json
$ cat my-skill/SKILL.md
---
name: java-test-generator
description: Spock tests for Java classes
---

# Java Test Generator Skill

When user asks for tests:

1. Analyze Java class
2. Identify edge cases
3. Generate Spock spec
4. Ensure >80% coverage


```

<!--
Slide 36: Creating Skills
- Skills have folder structure
- SKILL.md is entry point with metadata
- Can include scripts and resources
- Progressive disclosure crucial for efficiency
- Initial scan very lightweight
- Full load only when needed
- Name and description for discovery
- Claude decides when to activate skill
- Can bundle templates, configs, scripts
- More powerful but more complex than commands
-->

---

# Essential Skills to Install (1/2)

## Development Skills

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
obra/superpowers
─────────────────
Enhanced development capabilities
General coding assistance

$ claude skill install obra/superpowers
```

</div>
<div>

```
web-asset-generator
───────────────────
Generate images, icons, assets
Frontend development helper

$ claude skill install web-asset-generator
```

</div>
</div>

<!--
Slide 37: Essential Skills to Install (1/2) - Development Skills
- Recommended skills for developers
- obra/superpowers: General dev enhancements, code analysis
- web-asset-generator: For frontend work, UI assets
- Install command is simple
- Takes a few seconds to install
- Skills work across all Claude interfaces
-->

---

# Essential Skills to Install (2/2)

## Documentation & Discovery

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
yusufkaraaslan/Skill_Seekers
────────────────────────────
Convert documentation to Claude Skills
Turn any docs into usable skills

$ claude skill install yusufkaraaslan/Skill_Seekers

```

</div>
<div>

```
Find more skills:
─────────────────
https://github.com/travisvn/awesome-claude-skills
Community-curated collection

Installation tips:
──────────────────
• Install what you need, not everything
• Skills can slow down if too many installed
• Start with essentials, add as needed
• Test each skill after installation
```

</div>
</div>

<!--
Slide 38: Essential Skills to Install (2/2) - Documentation & Discovery
- Skill_Seekers: Create skills from documentation
- Powerful for turning API docs into Claude skills
- GitHub has many community skills
- awesome-claude-skills repo is curated list
- Be selective - don't install everything
- Too many skills can cause slowdowns
- Start with 3-5 essential skills
- Add more as you identify needs
- Skills work across CLI, VS Code, JetBrains
-->

---

# Agents

## Autonomous AI Workers

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**What are Agents?**
- Specialized AI roles (Tester, Architect, Coder)
- Autonomous task execution
- Multi-agent orchestration
- Long-running background tasks

</div>
<div>

**Characteristics:**
- Own context and memory
- Specific tools and permissions
- Can delegate to other agents
- Coordinate via orchestrator

</div>

</div>

<!--
Slide 39: Agents - Autonomous AI Workers
- Agents are specialized AI workers
- Different from Skills (execution) and Commands (templates)
- Each agent has specific role and expertise
- Can work autonomously on complex tasks
- Orchestrator coordinates multiple agents
- Background execution while you work on other things
- More complex setup than Skills/Commands
- Powerful but requires understanding when to use
- Not needed for most daily tasks
-->

---

# Agent Roles

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
Available Agent Types:
──────────────────────

Tester Agent
├─ Runs tests
├─ Analyzes failures
├─ Suggests fixes
└─ Ensures coverage

Architect Agent
├─ Reviews design
├─ Checks patterns
├─ Suggests improvements
└─ SOLID principles

```

</div>
<div>

```

Coder Agent
├─ Implements features
├─ Follows patterns
└─ Writes production code

Context Analyzer
├─ Understands codebase
├─ Finds patterns
└─ Provides insights
```

</div>
</div>

<!--
Slide 40: Agent Roles
- Each agent type has specific expertise
- Tester: Focus on test quality and coverage
- Architect: High-level design decisions
- Coder: Feature implementation
- Context Analyzer: Codebase understanding
- Agents can specialize beyond these
- Can create custom agent types
- Each agent sees partial view (their domain)
- Orchestrator coordinates between agents
- Like team members with specific roles
-->

---

# Orchestrator Pattern

```
Task: Create Rating system

Orchestrator delegates:
1. Context Analyzer
   └─> "Analyze Film entity structure"
2. Architect
   └─> "Design Rating system (DDD)"
3. Coder
   └─> "Implement the design"
4. Tester
   └─> "Create Spock + Testcontainers tests"
5. Orchestrator
   └─> "Review & integrate"

Each agent contributes expertise
Orchestrator ensures consistency
```

<!--
Slide 41: Orchestrator Pattern
- Orchestrator coordinates multiple agents
- Breaks complex task into agent-specific subtasks
- Each agent handles their specialty

COMPLETE SUB-AGENT PROMPT EXAMPLES:

1. Context Analyzer Agent:
"Analyze @Film.java entity and identify:
- JPA relationships and mappings
- Validation rules (@NotBlank, @Size, etc.)
- Repository patterns (Spring Data)
- DTO mapping strategies
- Service layer structure
- Test patterns in FilmServiceSpec
Provide summary of patterns for Rating system design"

2. Architect Agent:
"Design Rating system following DDD principles:
- Rating entity (1-5 score validation)
- @ManyToOne relationship with Film
- RatingRepository extending JpaRepository
- RatingService with business logic
- Calculate average rating for Film
- POST /api/films/{id}/ratings endpoint
- Use patterns from @Film.java
- Spock + Testcontainers test strategy
Provide class diagram and migration schema"

3. Coder Agent:
"Implement Rating system based on architect design:
1. Flyway migration V4__create_ratings_table.sql
2. Rating JPA entity with validation
3. RatingRepository interface
4. RatingService with average calculation
5. RatingController REST endpoint
6. RatingDto record
7. RatingMapper
Follow ALL CLAUDE.md conventions and Film patterns"

4. Tester Agent:
"Create comprehensive Spock tests:
- RatingServiceSpec with Given-When-Then
- Test CRUD operations
- Test validation (1-5 score)
- Test average rating calculation
- @Unroll for data-driven tests
- Testcontainers PostgreSQL integration
- Test Film relationship
Ensure >80% coverage, all tests passing"

- Orchestrator reviews and integrates
- More overhead than single-agent approach
- Useful for very large, complex features
- Most tasks don't need this level of orchestration
-->

---

# Agents vs Skills vs Commands

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
┌───────────┬─────────┬────────┬──────────┐
│ Feature   │ Command │ Skill  │ Agent    │
├───────────┼─────────┼────────┼──────────┤
│ Execution │ No      │ Yes    │ Yes      │
│ Autonomy  │ No      │ No     │ Yes      │
│ Context   │ Shared  │ Shared │ Own      │
│ Roles     │ No      │ No     │ Yes      │
│ Delegation│ No      │ No     │ Yes      │
│ Complexity│ Low     │ Medium │ High     │
│ Setup     │ Easy    │ Medium │ Complex  │
│ Daily Use │ High    │ Medium │ Low      │
└───────────┴─────────┴────────┴──────────┘
```

</div>
<div>

```
Recommendation:
───────────────
Commands: 70% of tasks
Skills:   20% of tasks
Agents:   10% of tasks
```

</div>
</div>

<!--
Slide 42: Agents vs Skills vs Commands
- Clear comparison of all three tools
- Commands: Simple, no execution, shared context
- Skills: Can execute, shared context, progressive load
- Agents: Autonomous, own context, can delegate
- Complexity increases: Commands < Skills < Agents
- Setup difficulty increases proportionally
- Daily use decreases with complexity
- Most work done with Commands
- Skills for specialized tasks
- Agents rarely needed in practice
- Start simple, add complexity only when needed
-->

---

# When to Use Agents

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
Good use cases:
───────────────
• Large-scale refactoring
  (Multiple services, dependencies)

• Multi-perspective analysis
  (Security + Performance + Design)

• Long-running background tasks
  (While you work on other code)

• Complex orchestration
  (Multiple phases, dependencies)
```

</div>
<div>

```
Reality check:
──────────────
How often do you do these?
Monthly? Quarterly?

For daily development:
Commands + Skills = 95%+ coverage

Don't overcomplicate workflow
```

</div>
</div>

<!--
Slide 43: When to Use Agents
- Be honest about when agents add value
- Large refactoring across multiple services
- Need multiple expert perspectives simultaneously
- Tasks that run while you do other work
- Complex multi-phase workflows
- But: How often do these scenarios occur?
- Most daily dev doesn't need agents
- Commands and Skills handle vast majority
- Agent overhead not worth it for simple tasks
- Coordination complexity can slow you down
- Start with simpler tools, add agents only if needed
- Keep workflow simple and effective
-->

---

# Creating and Configuring Agents

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
Agent Configuration:
────────────────────
Define in settings.json or conversation

{
  "agents": {
    "tester": {
      "role": "Test specialist",
      "tools": ["bash", "read", "write"],
      "model": "sonnet",
      "context": "Focus on Spock tests"
    },
    "architect": {
      "role": "Design specialist",
      "tools": ["read", "grep"],
      "model": "opus",
      "context": "DDD and SOLID principles"
    }
  }
}
```

</div>
<div>

```
Usage:
──────
> Use tester agent to create tests for Film
> Use architect agent to review design
```

</div>
</div>

<!--
Slide 44: Creating and Configuring Agents
- Agents configured in settings or ad-hoc
- Define role, available tools, model
- Context provides agent-specific instructions
- Tester agent: Focus on testing, can execute tests
- Architect agent: Focus on design, read-only
- Model selection per agent (Opus for architecture)
- Can create custom agents for your needs
- Configuration can be project-specific
- Or define globally in ~/.claude/settings.json
- Start with built-in agents, customize as needed
-->

---

# Agent Limitations

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
Challenges:
───────────
• Coordination overhead
  (Managing multiple agents complex)

• Context fragmentation
  (Each agent sees partial view)

• Inconsistency risk
  (Agents may contradict)

• Debugging difficulty
  (Which agent caused bug?)

• Higher token costs
  (Multiple agents = 2-3x cost)

• Setup complexity
  (Roles, permissions, workflows)
```
</div>
<div>

```
Better alternatives for most tasks:
───────────────────────────────────
• Skills for complexity
• Commands for simplicity
• Single-agent conversation
```

</div>
</div>

<!--
Slide 45: Agent Limitations
- Be realistic about agent limitations
- Coordination adds significant overhead
- Each agent only sees their domain (can miss big picture)
- Agents may make conflicting recommendations
- Bugs harder to trace with multiple agents
- Token costs multiply with agents
- Setup and maintenance more complex
- For most daily tasks, not worth the complexity
- Skills provide specialization without fragmentation
- Commands provide consistency without overhead
- Single conversation with good prompts often best
- Use agents sparingly, when truly needed
-->

---

# EXERCISE 4: Kafka Integration

**Task:** Event-driven architecture with Kafka

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Requirements:**
```
1. Add Kafka dependencies to build.gradle
2. Update docker-compose.yml (Kafka broker + UI)
3. Create FilmCreatedEvent record
4. Implement Kafka producer in FilmService
5. Create Kafka consumer
6. Keep REST endpoint working
7. Spock tests with Testcontainers Kafka
8. Follow CLAUDE.md conventions
```

</div>
<div>

**Success:**
- Kafka running in Docker
- Events published when Film created
- Consumer processes events
- All tests passing

</div>
</div>

<!--
Slide 46: EXERCISE 4 - Kafka Integration
- Comprehensive exercise using all learned concepts
- Event-driven architecture with Spring Boot
- Use good prompts to guide Claude
- Reference existing code patterns
- Testcontainers for integration tests
- This tests prompting, CLAUDE.md, and technical skills
- Take time to understand generated code
- Don't just accept - review and learn
- Ask questions if unclear
- Can use commands if you created them
- Realistic production scenario

PROMPT EXAMPLE:
"Implement Kafka event-driven architecture for Film service:

1. build.gradle: Add spring-kafka and testcontainers-kafka dependencies
2. docker-compose.yml: Add Kafka broker (port 9092) + Kafka UI (port 8080)
3. FilmCreatedEvent record (id, title, releaseYear, timestamp)
4. Kafka Producer in @FilmService: Publish FilmCreatedEvent after film.save()
5. FilmEventConsumer: @KafkaListener processing FilmCreatedEvent
6. application.yml: Kafka configuration (bootstrap-servers, topics)
7. FilmKafkaSpec: Spock + @Testcontainers + KafkaContainer integration tests
8. Keep existing REST endpoint @/api/films working

Follow CLAUDE.md conventions. Test: POST film, verify Kafka event, consumer processes it, all tests pass"
-->

---

# MCP (Model Context Protocol)

## Connect Claude to External Tools

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**What is MCP?**
```
- Protocol for connecting AI to tools/data
- Access databases, APIs, services
- Extends Claude's capabilities
- Natural language to actions
```

</div>
<div>

**Connects to:**
```
- Databases (PostgreSQL, MongoDB)
- Dev Tools (GitHub, GitLab, Jira)
- Monitoring (Sentry, DataDog)
- Infrastructure (AWS, Vercel)
- Data sources (Notion, Google Drive)
```

</div>
</div>

<!--
Slide 47: MCP (Model Context Protocol) - Connect Claude to External Tools
- MCP is different from Skills and Agents
- Protocol for external integrations
- Claude can query databases directly
- Access GitHub, Jira, monitoring tools
- Natural language interface to your tools
- Example: "What errors in last 24 hours?" queries Sentry
- "Top 10 rated films?" queries PostgreSQL
- Extends Claude beyond code generation
- Useful for data analysis and operations
- Optional - not required for development
-->

---

# MCP vs Skills vs Agents

```
┌──────────┬─────────────┬─────────────┬──────────┐
│ Aspect   │ MCP         │ Skills      │ Agents   │
├──────────┼─────────────┼─────────────┼──────────┤
│ Purpose  │ External    │ Internal    │ Autonomy │
│          │ tools/data  │ capabilities│          │
├──────────┼─────────────┼─────────────┼──────────┤
│ Examples │ GitHub,     │ pdf, docx,  │ Tester,  │
│          │ PostgreSQL, │ webapp-     │ Architect│
│          │ Sentry, AWS │ testing     │          │
├──────────┼─────────────┼─────────────┼──────────┤
│ Scope    │ Ecosystem   │ Task-       │ Role-    │
│          │ integration │ specific    │ specific │
├──────────┼─────────────┼─────────────┼──────────┤
│ Data     │ External    │ Internal    │ Context  │
│          │ (DB, API)   │ (processing)│ isolated │
└──────────┴─────────────┴─────────────┴──────────┘

All three can work together
```

<!--
Slide 48: MCP vs Skills vs Agents
- MCP, Skills, and Agents serve different purposes
- MCP: Connect to external systems
- Skills: Internal capabilities and workflows
- Agents: Autonomous roles and coordination
- Not either/or - they're complementary
- Example workflow: MCP accesses database, Skill processes data, Agent coordinates
- MCP for integration with existing tools
- Skills for reusable capabilities
- Agents for complex coordination
- Most Java devs need Commands + Skills, MCP optional, Agents rare
-->

---

# Java Developer Benefits of MCP

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
Database Queries:
─────────────────
> Find customers who haven't purchased
> in 90 days

[PostgreSQL MCP queries database]
Returns results + generates JPA method

Monitoring:
───────────
> What errors in last 24 hours?

```

</div>
<div>

```

[Sentry MCP fetches logs]
Shows stacktraces + suggestions

CI/CD:
──────
> Deploy film-service to staging

[GitHub Actions MCP triggers pipeline]
Build, test, deploy

Natural language → Infrastructure
```

</div>
</div>

<!--
Slide 49: Java Developer Benefits of MCP
- MCP adds value for specific workflows
- Database queries without writing SQL
- Monitoring integration for debugging
- CI/CD automation via natural language
- Generate repository methods from queries
- Analyze production errors easily
- Deploy without remembering commands
- But: Do you need this frequently?
- Most dev work is code, not ops
- MCP more valuable for ops/data tasks
- Add only if you use these features often
-->

---

# Popular MCP Servers

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
PostgreSQL
└─ Database queries and schema

GitHub
└─ Code management, PRs, issues

Sentry
└─ Error tracking and debugging

AWS
└─ Infrastructure management

Jira
└─ Project management
```

</div>
<div>

```
Installation:
─────────────
$ claude mcp add postgres
$ claude mcp add github

Then use naturally in conversations
```

</div>
</div>

<!--
Slide 50: Popular MCP Servers
- Popular MCP servers for Java development
- PostgreSQL for database work
- GitHub for repo management
- Sentry for production debugging
- AWS for infrastructure
- Jira for project tracking
- Simple installation via CLI
- Once installed, use naturally in conversation
- Claude knows when to use MCP
- No special syntax needed
- MCP list growing constantly
- Install what you need, ignore the rest
-->

---

# Do Java Devs Need MCP?

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
Commands + Skills Already Cover:
────────────────────────────────
• Code generation
• Refactoring
• Test creation
• Document processing
• Web testing
• Database migrations

MCP Adds Value For:
────────────────────
• Frequent prod DB querying
• Live monitoring integration
• Regular infrastructure inspection
• CI/CD automation workflows

```

</div>
<div>

```
Recommendation:
───────────────
Start: CLAUDE.md + Commands + Skills
      (Covers 90%+ of daily dev)

Add MCP if:
• You frequently query production
• Need monitoring integration
• Manage infrastructure regularly

Most Java devs: MCP optional for daily work
```

</div>
</div>

<!--
Slide 51: Do Java Devs Need MCP?
- Honest assessment of MCP value
- Commands and Skills handle most development
- Code generation, tests, refactoring covered
- MCP valuable for operations and data tasks
- Production database analysis
- Real-time monitoring and debugging
- Infrastructure management
- CI/CD automation
- But how often do you do these?
- Daily dev is mostly code, not ops
- Start with simpler tools
- Add MCP if you have specific need
- Don't add complexity without benefit
-->

---

# EXERCISE 5: Database Schema Migration

**Task:**  Add Rating system for films

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Requirements:**
```
1. Flyway migration (ratings table)
2. Rating JPA entity + validation (1-5 score)
3. @OneToMany relationship with Film
4. RatingRepository (Spring Data)
5. RatingService (business logic)
6. POST /api/films/{id}/ratings endpoint
7. Calculate average rating for Film
8. Spock + Testcontainers tests
```

</div>
<div>

**Success:**
- Working Rating system
- All tests passing
- Database migration applied

</div>
</div>

<!--
Slide 52: EXERCISE 5 - Database Schema Migration
- Database evolution exercise
- Practice Flyway migrations
- JPA relationships
- REST endpoint creation
- Test with Testcontainers
- Use prompts with context and patterns
- Reference existing Film code
- Think about validation (1-5 rating)
- Consider edge cases in tests
- This is realistic production scenario
- Good practice for schema changes

PROMPT EXAMPLE:
"Analyze @Film.java and create Rating system:

1. V4__create_ratings_table.sql: ratings table (id, film_id FK, score INT 1-5, review_text VARCHAR(500), created_at)
2. Rating entity: @ManyToOne Film, @Min(1) @Max(5) score validation, @NotBlank reviewText
3. RatingRepository extends JpaRepository<Rating, Long>
4. RatingService: save(), calculateAverageRating(filmId)
5. RatingController: POST /api/films/{filmId}/ratings, GET /api/films/{filmId}/average-rating
6. RatingDto record + RatingMapper
7. RatingServiceSpec + RatingControllerSpec with Spock + Testcontainers
8. Update Film entity with @OneToMany ratings relationship

Follow @Film.java patterns and CLAUDE.md. Test: POST rating, GET average, all validations work, tests pass"
-->

---

# EXERCISE 6: Spock Testing

**Task:** Comprehensive test suite

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**FilmServiceSpec:**
- CRUD operations
- Validation scenarios
- Error handling
- Data-driven tests with @Unroll
- Testcontainers PostgreSQL

</div>
<div>

**FilmRepositorySpec:**
```
- Custom queries
- Database constraints
- Relationship handling
```

</div>
</div>

<!--
Slide 53: EXERCISE 6 - Spock Testing
- Focus on testing excellence
- Comprehensive coverage
- Spock Given-When-Then pattern
- Data-driven testing with @Unroll
- Testcontainers for real database
- Test both service and repository layers
- Edge cases and error handling
- This builds testing muscle
- Good tests = confident refactoring
- Practice prompting for test generation
- Review generated tests carefully
- Understand what they're testing

PROMPT EXAMPLE:
"Create comprehensive Spock test suite for Film service:

FilmServiceSpec:
- Test CRUD: create(), findById(), findAll(), update(), delete()
- Test validation: @NotBlank title, @Min release year
- Test error handling: FilmNotFoundException, ValidationException
- @Unroll data-driven: multiple film scenarios (different years, titles)
- Testcontainers PostgreSQL for real DB integration
- Given-When-Then structure, NEVER use 'value == true'

FilmRepositorySpec:
- Test custom queries: findByTitleContaining(), findByReleaseYearBetween()
- Test database constraints: unique title, not null validations
- Test @OneToMany ratings relationship
- Use PollingConditions for async operations if any

Follow CLAUDE.md Spock conventions. Ensure >80% coverage, all tests green"
-->

---

# EXERCISE 7: Java 17+ Features

**Task:** Modernize Film service

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Apply:**
```
1. FilmDto → record
2. Sealed interface FilmStatus
3. Pattern matching in switch
4. Optional<Film> everywhere5. Stream API for collections
6. Text blocks for SQL/JSON
7. Immutability in DTOs
8. Update all Spock tests
```

</div>
<div>

**Success:**
- Data first design
- All tests passing
- No breaking changes to API

</div>
</div>

<!--
Slide 54: EXERCISE 7 - Java 17+ Features
- Refactoring exercise with modern Java
- Apply Java 25 features learned
- Records for immutable DTOs
- Sealed classes for type safety
- Pattern matching for clean code
- Optional instead of null
- Streams for collection processing
- Text blocks for readability
- Update tests to match changes
- This is realistic modernization task
- Practice safe refactoring
- Tests ensure no regression
- Good prompts guide Claude

PROMPT EXAMPLE:
"Modernize @Film.java and @FilmService.java with Java 25 features:

1. FilmDto: Convert class → record (immutable DTO)
2. FilmStatus enum → sealed interface with DRAFT, PUBLISHED, ARCHIVED implementations
3. FilmService methods: Use pattern matching in switch expressions for status handling
4. All return types: Optional<Film> instead of null returns
5. Collection processing: Replace loops with Stream API (.filter(), .map(), .collect())
6. SQL queries: Use text blocks for multi-line SQL
7. FilmDto constructor: Defensive copying for immutability (List.copyOf(), Map.copyOf())
8. Update FilmServiceSpec: All tests still passing with new patterns
9. Stream Gatherers (JEP 485) - use Gatherer for complex stream processing

Follow CLAUDE.md Java 25 conventions. Verify: No breaking API changes, all tests green, modern Java patterns throughout"
-->

---

# EXERCISE 8: Precise Prompting Challenge

**Task:** Film search endpoint - 3 approaches

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Round 1 (Vague):**
```
"Add search functionality"
```

**Round 2 (Basic):**
```
"REST endpoint to search
by title and genre"
```

</div>
<div>

**Round 3 (Precise):**
```
GET /api/films/search
• title (partial, case-insensitive)
• genre (exact), minYear, maxYear
• Spring Data JPA Specification
• Return FilmDto sorted by year desc
• Spock tests with @Unroll
• OpenAPI/Swagger annotations
```

**Compare:** Quality, coverage, time to production

</div>
</div>

<!--
Slide 55: EXERCISE 8 - Precise Prompting Challenge
- Final exercise demonstrates prompting mastery
- Same task, three different prompts
- Execute all three, compare results
- Round 1: Vague - see what Claude guesses
- Round 2: Better - some context provided
- Round 3: Precise - everything specified
- Dramatic quality difference
- Time to production-ready varies widely
- This proves value of good prompting
- Most important skill you'll learn today
- Quality of prompt = quality of output
- Discuss results as group
- Learn from differences
-->

---

# Git Worktrees

## Work on Multiple Branches Simultaneously

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

```
What are worktrees?
───────────────────
Separate working directories for different branches
No need to stash or switch branches

Create worktree:
────────────────
$ git worktree add ../feature-branch feature-name
$ git worktree add ../hotfix main

```

</div>
<div>

```
List worktrees:
───────────────
$ git worktree list
/path/to/main        abc1234 [main]
/path/to/feature     def5678 [feature-name]
/path/to/hotfix      ghi9012 [main]

Remove worktree:
────────────────
$ git worktree remove ../feature-branch
```

</div>
</div>

<!--
Slide 56: Git Worktrees - Work on Multiple Branches Simultaneously
- Git worktrees allow multiple checkouts of same repository
- Each worktree is separate directory with its own branch
- Perfect for: working on feature while fixing urgent bug
- No switching branches, no stashing changes
- Share same .git directory (efficient)

EXAMPLE WORKFLOW:
$ cd ~/project/main
$ git worktree add ../urgent-fix main
$ cd ../urgent-fix
# Fix bug, commit, push
$ cd ../main
$ git worktree remove ../urgent-fix
# Continue feature work without interruption

COMMON USE CASES:
- Feature development + production hotfix
- Code review in separate directory
- Testing different approaches simultaneously
- Building multiple branches for comparison

NOTE: All worktrees share same .git, commits visible everywhere
Clean up with 'git worktree prune' for deleted directories
-->

---

# Workshop Recap

**What We Learned:**

- Claude Code commands (Shift+Tab, rewind, /clear)
- Effective prompting techniques
- CLAUDE.md configuration
- Custom commands creation
- Skills vs Agents vs MCP
- Kafka integration (event-driven)
- Database migration with Flyway
- Spock + Testcontainers testing

<!--
Slide 57: Workshop Recap
- Review everything covered
- Four hours of intensive learning
- Theory and practice balanced
- Commands for efficiency
- Prompting as critical skill
- Configuration for consistency
- Understanding tool trade-offs
- Real production scenarios
- Modern Java best practices
- Testing excellence
- AI is a tool, you're the architect
- You make decisions, AI assists
-->

---

# Key Takeaways

```
1. Prompt quality matters most
   Context + Goal + Constraints + Patterns
2. Invest in CLAUDE.md
   1 hour setup saves days of corrections
3. Use right model for task
   Haiku → Sonnet → Opus
4. Custom commands for repetitive tasks
   Build your workflow library
5. Skills > Agents for most work
   Simpler is better
6. MCP optional, add only if needed
   Don't overcomplicate
7. Review all AI-generated code
   AI assists, you verify
8. /clear context regularly
   Manage tokens and costs
9. Iterate and improve prompts
   Gets better with practice
10. Stay current with AI tools
   Evolves rapidly
   
   
```

<!--
Slide 58: Key Takeaways
- Ten principles to remember
- Prompting is skill #1
- CLAUDE.md is foundation
- Model selection for cost
- Commands for efficiency
- Keep tools simple
- Don't add MCP unless needed
- Always review code
- Context management saves money
- Prompts improve with practice
- AI tools evolving fast, stay informed
- These principles apply beyond Claude Code
- Foundation for AI-assisted development
-->

---

# Next Steps

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">

<div>

**Keep Learning:**
- Use Claude Code daily
- Expand command library
- Experiment with Skills
- Refine CLAUDE.md

**Share Knowledge:**
- Teach teammates
- Share prompts and commands
- Contribute to community

</div>
<div>

**Measure Impact:**
- Track productivity gains
- Document time saved
- Compare code quality

**Resources:**
```
- https://code.claude.com/docs
- https://github.com/travisvn/awesome-claude-skills
- https://claudeskillshub.org
- https://github.com/Njengah/claude-code-cheat-sheet
```

</div>
</div>

<!--
Slide 59: Next Steps
- Continue practicing daily
- Build on workshop foundation
- Daily use builds proficiency
- Expand your personal library
- Share knowledge with team
- Help others get started
- Measure your improvements
- Track time saved
- Quality improvements
- Official docs for reference
- Community skills on GitHub
- Workshop materials in repository
- Stay connected for questions
-->

---

# Q&A

## Questions? Discussion?

<!--
Slide 60: Q&A
- Open floor for questions
- Any topic from workshop
- Practical concerns about adoption
- Technical deep dives
- Team workflows
- Enterprise considerations
- Real-world scenarios
- Share experiences
- Learn from each other
- Exchange contact info
- Repository has all materials
- Stay in touch for continued learning

**Topics:**
- Prompt engineering
- CLAUDE.md strategies
- Custom commands and skills
- Agent use cases
- Team adoption
- Security and compliance
- Cost optimization
- Integration with existing tools
- Advanced scenarios

**Thank you for participating**
-->

---

# Thank You

```
Repository:
https://github.com/urbontaitis/code-with-agent

Contains:
├─ All exercises
├─ Solutions
├─ CLAUDE.md template
├─ Custom commands examples
└─ Workshop slides
```

<!--
Slide 61: Thank You

-->
