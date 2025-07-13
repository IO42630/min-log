# min-log

## Purpose
Enhanced logging wrapper around java.util.logging with pretty formatting, thread names, and method names.

## Guidelines
- **Architecture**: Utility library with static methods
- **Dependencies**: min-bom for dependency management
- **Parent**: min-root (needs update to 21.0.0)
- **Key Class**: LogU - main utility class
- **Usage**: Use LogU.remake() to customize logging behavior

## Key Features
- Pretty console and file output formatting
- Thread name and method name display
- Multiple log types: START, END, PLAIN, SAVE, LOAD
- Customizable formatting patterns
- Automatic FileHandler and ConsoleHandler setup

## Code Patterns
```java
// Initialize logging
LogU.remake(logFileDir, "com.myproject", customFormat);

// Usage patterns
LogU.infoStart("Starting operation %s", operationName);
LogU.infoPlain("Processing data");
LogU.infoEnd("Completed operation");
LogU.save("Saved file %s", fileName);
LogU.load("Loaded config %s", configName);
```

## Maintenance
- **URGENT**: Update parent to min-root 21.0.0
- Keep formatter compatible with java.util.logging
- Thread filtering logic in THREADS_TO_IGNORE_BELOW_WARNING
- Maintain backwards compatibility for LogU API