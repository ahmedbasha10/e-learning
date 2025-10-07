package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.constants.CourseLevel;
import com.logicerror.e_learning.dto.CourseDetailsProjectionDTOMapper;
import com.logicerror.e_learning.dto.CourseListProjectionDTOMapper;
import com.logicerror.e_learning.dto.PreviewProjectionDTOMapper;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.exceptions.course.CourseNotFoundException;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
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
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    private final Logger logger = LoggerFactory.getLogger(DefaultCourseQueryService.class);

    public CourseDetailsProjectionDTOMapper getCourseById(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        logger.debug("Fetching course with ID: {}", courseId);

        return courseRepository.findCourseById(courseId)
                .orElseThrow(() -> throwCourseNotFoundException(courseId));
    }

    @Override
    public PreviewProjectionDTOMapper getCoursePreviewById(Long courseId) {
        return courseRepository.findCoursePreviewById(courseId)
                .orElseThrow(() -> throwCourseNotFoundException(courseId));
    }

    private CourseNotFoundException throwCourseNotFoundException(Long courseId) {
        logger.error("Course not found with ID: {}", courseId);
        return new CourseNotFoundException("Course not found with id: " + courseId);
    }


    public Page<CourseListProjectionDTOMapper> getAllCourses(Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching all courses, page: {}", pageable.getPageNumber());
        Page<CourseListProjectionDTOMapper> courses = courseRepository.findAllCourses(pageable);
        logger.debug("Found {} courses", courses.getTotalElements());
        return courses;
    }


    public Page<CourseListProjectionDTOMapper> getCoursesByCategory(String category, Pageable pageable) {
        Assert.notNull(category, "Course category must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by category: {}, page: {}", category, pageable.getPageNumber());
        Page<CourseListProjectionDTOMapper> courses = courseRepository.findByCategory(category, pageable);
        logger.debug("Found {} courses in category: {}", courses.getTotalElements(), category);
        return courses;
    }

    public Page<CourseListProjectionDTOMapper> getCoursesByLevel(CourseLevel level, Pageable pageable) {
        Assert.notNull(level, "Course level must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by level: {}, page: {}", level, pageable.getPageNumber());
        Page<CourseListProjectionDTOMapper> courses = courseRepository.findByLevel(level, pageable);
        logger.debug("Found {} courses with level: {}", courses.getTotalElements(), level);
        return courses;
    }


    public Page<Section> getCourseSections(Long courseId, Pageable pageable) {
        Assert.notNull(courseId, "Course ID must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching sections for course ID: {}, page: {}", courseId, pageable.getPageNumber());
        return sectionRepository.findAllByCourseId(courseId, pageable);
    }

    public int getEnrolledStudentsCount(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        logger.debug("Fetching student count for course ID: {}", courseId);
        int studentsCount = userEnrollmentsRepository.countStudentsByCourseId(courseId);
        logger.debug("Found {} students for course ID: {}", studentsCount, courseId);
        return studentsCount;
    }

    @Override
    public boolean courseExists(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        return courseRepository.existsById(courseId);
    }

    @Override
    public boolean courseExistsByTitle(String title) {
        Assert.notNull(title, "Course title must not be null");
        return courseRepository.existsByTitle(title);
    }
}
