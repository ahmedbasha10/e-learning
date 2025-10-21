package com.logicerror.e_learning.courses.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CourseLevel {
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");

    private final String level;

    CourseLevel(String level) {
        this.level = level;
    }

    @JsonCreator
    public static CourseLevel fromValue(String value) {
        for (CourseLevel level : CourseLevel.values()) {
            if (level.level.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown level: " + value);
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return level;
    }
}
