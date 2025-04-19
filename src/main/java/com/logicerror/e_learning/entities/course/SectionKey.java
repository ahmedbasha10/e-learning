package com.logicerror.e_learning.entities.course;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SectionKey {

    @Column(name = "course_id")
    private Long courseId;
    @Column(name = "section_id")
    private Long sectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionKey that)) return false;
        return courseId.equals(that.courseId) && sectionId.equals(that.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, sectionId);
    }

}
