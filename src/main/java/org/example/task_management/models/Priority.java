package org.example.task_management.models;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static Priority fromString(String priority) {
        return switch (priority.toUpperCase()) {
            case "LOW" -> LOW;
            case "MEDIUM" -> MEDIUM;
            case "HIGH" -> HIGH;
            default -> throw new IllegalArgumentException("Unknown priority: " + priority);
        };
    }
}
