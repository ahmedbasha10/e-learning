package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.dto.UserDto;
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
    public void addServerHostToCourseResources(CourseDto course) {
        if (course.getImageUrl() != null) {
            String filePath = baseHost + File.separator + course.getImageUrl();
            course.setImageUrl(filePath.replace("\\", "/"));
        }
        
        if (course.getSections() != null) {
            course.getSections().forEach(this::addServerHostToVideoUrl);
        }
    }


    public void calculateEnrolledStudentsCount(CourseDto course) {
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

