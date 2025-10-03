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
    private final UserMapper userMapper;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    
    @Value("${api.base-host}")
    private String baseHost;


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
        UserDto teacher = getCourseTeacher(course.getId());
        CourseDto courseDto = courseMapper.courseToCourseDto(course, teacher);
        
        // Apply transformations
        //TODO: Check if we can optimize this
        addServerHostToCourseResources(courseDto);
        calculateEnrolledStudentsCount(courseDto);
        
        return courseDto;
    }

    /**
     * Adds server host to course image URL and video URLs
     */
    private void addServerHostToCourseResources(CourseDto course) {
        if (course.getImageUrl() != null) {
            String filePath = baseHost + File.separator + course.getImageUrl();
            course.setImageUrl(filePath.replace("\\", "/"));
        }
        
        if (course.getSections() != null) {
            course.getSections().forEach(this::addServerHostToVideoUrl);
        }
    }


    private void calculateEnrolledStudentsCount(CourseDto course) {
        int studentsCount = userEnrollmentsRepository.countStudentsByCourseId(course.getId());
        course.setStudentsCount(studentsCount);
    }

    private UserDto getCourseTeacher(Long courseId) {
        return teacherCoursesRepository.findByCourseId(courseId)
                .stream()
                .findFirst()
                .map(teacherCourse -> userMapper.userToUserDto(teacherCourse.getUser()))
                .orElse(null);
    }

    private void addServerHostToVideoUrl(SectionDto section) {
        if (section.getVideos() != null) {
            section.getVideos()
                    .forEach(video -> {
                        if (video.getUrl() != null) {
                            String videoFilePath = baseHost + File.separator + video.getUrl();
                            video.setUrl(videoFilePath.replace("\\", "/"));
                        }
                    });
        }
    }
}

