package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ICourseService {
    Course getCourseById(Long courseId);
    Course getCourseByTitle(String title);
    Page<Course> getAllCourses(Pageable pageable);
    Page<Course> getAllCoursesWithStudentsCount(Pageable pageable);
    Page<Course> getCoursesByCategory(String category, Pageable pageable);
    Page<Course> getCoursesByLevel(String level, Pageable pageable);
    Page<Course> getCoursesByAuthenticatedTeacher(Pageable pageable);
    Page<Section> getCourseSections(Long courseId, Pageable pageable);
    void calculateEnrolledStudentsCount(CourseDto course);
    void addServerHostToCourseResources(CourseDto course);


    Course createCourse(CreateCourseRequest createCourseRequest, MultipartFile thumbnail);

    Course updateCourse(Long courseId, UpdateCourseRequest Course);
    void updateCourseDuration(Course course);

    void deleteCourse(Long courseId);

    CourseDto convertToDto(Course course);


}
