package com.logicerror.e_learning.courses.services.operationhandlers.creation;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.courses.exceptions.CourseCreationFailedException;
import com.logicerror.e_learning.courses.mappers.CourseMapper;
import com.logicerror.e_learning.courses.repositories.CourseRepository;
import com.logicerror.e_learning.courses.requests.CreateCourseRequest;
import com.logicerror.e_learning.courses.services.CourseThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCreationHandler extends BaseCourseCreationHandler {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CourseThumbnailService courseThumbnailService;


    
    @Override
    protected void processRequest(CourseCreationContext context) {
        CreateCourseRequest request = context.getRequest();

        logger.info("Creating new course with title: {}", request.getTitle());

        Course savedCourse = createCourse(context, request);

        checkCourseCreationState(savedCourse);

        context.setCourse(savedCourse);

        logger.info("Successfully created course with ID: {}", savedCourse.getId());
    }

    private Course createCourse(CourseCreationContext context, CreateCourseRequest request) {
        Course course = courseMapper.createCourseRequestToCourse(request);
        courseThumbnailService.setCourseThumbnail(course, context.getThumbnail());
        course.setDuration(0);
        return courseRepository.save(course);
    }

    private void checkCourseCreationState(Course savedCourse) {
        if (savedCourse == null || savedCourse.getId() == null) {
            logger.error("Course creation failed: could not generate ID");
            throw new CourseCreationFailedException("Failed to create course");
        }
    }



}
