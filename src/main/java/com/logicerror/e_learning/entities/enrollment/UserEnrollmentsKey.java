package com.logicerror.e_learning.entities.enrollment;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEnrollmentsKey {

    private Long userId;
    private Long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEnrollmentsKey that)) return false;
        return userId.equals(that.userId) && courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId);
    }
}
