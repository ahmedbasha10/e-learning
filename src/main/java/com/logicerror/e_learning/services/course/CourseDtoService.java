package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.*;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.mappers.UserMapper;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Service responsible for Course DTO operations and transformations
 * Handles URL manipulation and DTO conversion logic
 */
@Service
@RequiredArgsConstructor
public class CourseDtoService {
    
    private final CourseMapper courseMapper;

    public CourseDto convertToDto(CourseDetailsProjection projection) {
        if (projection == null) return null;

        UserDto teacher = UserDto.builder()
                .firstName(projection.getTeacherFirstName())
                .lastName(projection.getTeacherLastName())
                .id(projection.getTeacherId())
                .build();

        return CourseDto.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .description(projection.getDescription())
                .imageUrl(projection.getImageUrl())
                .level(projection.getLevel())
                .price(projection.getPrice())
                .duration(projection.getDuration())
                .teacher(teacher)
                .studentsCount(projection.getStudentsCount())
                .category(projection.getCategory())
                .sections(projection.getSections())
                .build();

    }

    public CourseDto convertToDto(CourseListProjection projection) {
        if (projection == null) return null;

        UserDto teacher = UserDto.builder()
                .firstName(projection.getTeacherFirstName())
                .lastName(projection.getTeacherLastName())
                .build();

        return CourseDto.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .imageUrl(projection.getImageUrl())
                .level(projection.getLevel())
                .price(projection.getPrice())
                .duration(projection.getDuration())
                .teacher(teacher)
                .build();
    }

    /**
     * Converts Course entity to CourseDto with all necessary transformations
     */
    public CourseDto convertToDto(Course course) {
        return courseMapper.courseToCourseDto(course);
    }
}

