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
public class VideoKey {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "video_id")
    private Long videoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof VideoKey that)) return false;
        return courseId.equals(that.courseId) && sectionId.equals(that.sectionId) && videoId.equals(that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, sectionId, videoId);
    }
}
