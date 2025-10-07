package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.*;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.mappers.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseDtoService {
    
    private final CourseMapper courseMapper;

    public CourseDto convertToDto(CourseDetailsProjection projection) {
        if (projection == null) return null;

        UserDto teacher = UserDto.builder()
                .id(projection.getTeachers().iterator().next().getId())
                .firstName(projection.getTeachers().iterator().next().getFirstName())
                .lastName(projection.getTeachers().iterator().next().getLastName())
                .build();

        Set<SectionDto> sectionDTOs = buildSectionDTOs(projection);

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
                .sections(sectionDTOs)
                .build();

    }

    private Set<SectionDto> buildSectionDTOs(CourseDetailsProjection projection) {
        Set<SectionDto> sectionDTOs = new HashSet<>();
        for(SectionProjection section : projection.getSections()) {
            SectionDto sectionDto = SectionDto.builder()
                    .id(section.getId())
                    .title(section.getTitle())
                    .order(section.getOrder())
                    .duration(section.getDuration())
                    .build();

            sectionDto.getVideos().addAll(buildVideoDTOs(section));
            sectionDTOs.add(sectionDto);
        }
        return sectionDTOs;
    }

    private Set<VideoDto> buildVideoDTOs(SectionProjection section) {
        Set<VideoDto> videoDTOs = new HashSet<>();
        for(VideoProjection video : section.getVideos()) {
            VideoDto videoDto = VideoDto.builder()
                    .id(video.getId())
                    .title(video.getTitle())
                    .url(video.getUrl())
                    .duration(video.getDuration())
                    .build();
            videoDTOs.add(videoDto);
        }
        return videoDTOs;
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
                .description(projection.getDescription())
                .category(projection.getCategory())
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

