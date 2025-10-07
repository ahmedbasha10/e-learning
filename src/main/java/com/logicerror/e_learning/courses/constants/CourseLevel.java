package com.logicerror.e_learning.courses.constants;

public enum CourseLevel {
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");

    private final String level;

    CourseLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return level;
    }
}
