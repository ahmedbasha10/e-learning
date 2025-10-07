package com.logicerror.e_learning.courses.services;

import com.logicerror.e_learning.courses.constants.CourseLevel;
import com.logicerror.e_learning.courses.exceptions.CourseNotFoundException;
import com.logicerror.e_learning.courses.projections.CourseDetailsProjectionDTOMapper;
import com.logicerror.e_learning.courses.projections.CourseListProjectionDTOMapper;
import com.logicerror.e_learning.courses.projections.CoursePreviewProjectionDTOMapper;
import com.logicerror.e_learning.courses.repositories.CourseRepository;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class DefaultCourseQueryService implements CourseQueryService {

    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final Logger logger = LoggerFactory.getLogger(DefaultCourseQueryService.class);

    @Override
    public CourseDetailsProjectionDTOMapper getCourseById(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        logger.debug("Fetching course with ID: {}", courseId);

        return courseRepository.findCourseById(courseId)
                .orElseThrow(() -> throwCourseNotFoundException(courseId));
    }

    @Override
    public CoursePreviewProjectionDTOMapper getCoursePreviewById(Long courseId) {
        return courseRepository.findCoursePreviewById(courseId)
                .orElseThrow(() -> throwCourseNotFoundException(courseId));
    }

    @Override
    public Page<CourseListProjectionDTOMapper> getAllCourses(Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching all courses, page: {}", pageable.getPageNumber());
        Page<CourseListProjectionDTOMapper> courses = courseRepository.findAllCourses(pageable);
        logger.debug("Found {} courses", courses.getTotalElements());
        return courses;
    }

    @Override
    public Page<CourseListProjectionDTOMapper> getCoursesByCategory(String category, Pageable pageable) {
        Assert.notNull(category, "Course category must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by category: {}, page: {}", category, pageable.getPageNumber());
        Page<CourseListProjectionDTOMapper> courses = courseRepository.findByCategory(category, pageable);
        logger.debug("Found {} courses in category: {}", courses.getTotalElements(), category);
        return courses;
    }

    @Override
    public Page<CourseListProjectionDTOMapper> getCoursesByLevel(CourseLevel level, Pageable pageable) {
        Assert.notNull(level, "Course level must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by level: {}, page: {}", level, pageable.getPageNumber());
        Page<CourseListProjectionDTOMapper> courses = courseRepository.findByLevel(level, pageable);
        logger.debug("Found {} courses with level: {}", courses.getTotalElements(), level);
        return courses;
    }

    @Override
    public Page<Section> getCourseSections(Long courseId, Pageable pageable) {
        Assert.notNull(courseId, "Course ID must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching sections for course ID: {}, page: {}", courseId, pageable.getPageNumber());
        return sectionRepository.findAllByCourseId(courseId, pageable);
    }


    private CourseNotFoundException throwCourseNotFoundException(Long courseId) {
        logger.error("Course not found with ID: {}", courseId);
        return new CourseNotFoundException("Course not found with id: " + courseId);
    }
}
