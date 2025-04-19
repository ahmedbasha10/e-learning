package com.logicerror.e_learning.entities.review;

import com.logicerror.e_learning.entities.course.SectionKey;
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
public class ReviewKey {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "course_id")
    private Long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewKey that)) return false;
        return courseId.equals(that.courseId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, userId);
    }
}
