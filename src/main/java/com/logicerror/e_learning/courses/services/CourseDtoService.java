package com.logicerror.e_learning.courses.services;

import com.logicerror.e_learning.courses.dtos.CourseDto;
import com.logicerror.e_learning.dto.*;
import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.courses.mappers.CourseMapper;
import com.logicerror.e_learning.services.utils.ResourcesURLService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseDtoService {
    
    private final CourseMapper courseMapper;
    private final ResourcesURLService resourcesURLService;

    public CourseDto convertToDto(ProjectionDTOMapper<CourseDto> projection) {
        if (projection == null) return null;
        CourseDto courseDto = projection.toDTO();
        courseDto.setImageUrl(resourcesURLService.buildResourceURL(courseDto.getImageUrl()));
        if(courseDto.getSections() != null) {
            courseDto.getSections().forEach(sectionDto -> {
                if(sectionDto.getVideos() != null) {
                    sectionDto.getVideos().forEach(videoDto -> {
                        videoDto.setUrl(resourcesURLService.buildResourceURL(videoDto.getUrl()));
                    });
                }
            });
        }
        return courseDto;
    }


    /**
     * Converts Course entity to CourseDto with all necessary transformations
     */
    public CourseDto convertToDto(Course course) {
        return courseMapper.courseToCourseDto(course);
    }
}

