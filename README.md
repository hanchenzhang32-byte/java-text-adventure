# Java Text Adventure

A command-line escape adventure built with Java and object-oriented programming. Explore ten rooms on a 5×5 map, collect items, unlock a door with a keycard and restore the engine to complete the mission.

## Features

- Six application classes: `Game`, `Room`, `Map`, `Position`, `Inventory` and `Score`.
- Text commands for movement, inspection, inventory, map display and puzzle actions.
- Room-specific interactions and progress flags.
- Scoring for movement and completed puzzles.
- Original coursework JUnit tests for the supporting classes.

## Run

Requires a JDK (`java` and `javac` on your PATH). Verified locally with JDK 25.

```sh
sh run.sh
```

Enter `help` to see the commands. Examples: `look`, `map`, `move south`, `inventory`, `score`, `quit`.

## Tests

```sh
sh test.sh
```

The included JUnit console launcher runs the original supporting-class tests. These tests do not cover every interactive game path. Build output is written to `out/` and excluded from version control.

## Project structure

- `src/org/uob/a1/`: application source.
- `test/org/uob/a1/`: original coursework tests.
- `lib/`: original test dependencies.
- `run.sh`, `test.sh`: portable shell entry points (invoke using `sh`).

## Provenance

Portfolio copy of my University of Birmingham Object Oriented Programming coursework. Application Java source is preserved from my university GitLab snapshot `43f89bc6a4fb48f4de111e3e01107b5e8c811902`. README, ignore rules and build/test wrappers were added or tidied for this portfolio. Compiled classes and notebook checkpoint copies are excluded. The coursework scaffolding and supplied tests are included as project context; they are not presented as wholly original work.
