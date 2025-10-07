package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.*;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.mappers.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseDtoService {
    
    private final CourseMapper courseMapper;

    public CourseDto convertToDto(ProjectionDTOMapper<CourseDto> projection) {
        if (projection == null) return null;
        return projection.toDTO();
    }




    /**
     * Converts Course entity to CourseDto with all necessary transformations
     */
    public CourseDto convertToDto(Course course) {
        return courseMapper.courseToCourseDto(course);
    }
}

