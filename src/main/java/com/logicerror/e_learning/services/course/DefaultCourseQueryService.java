package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import com.logicerror.e_learning.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCourseQueryService implements CourseQueryService {

    private final IUserService userService;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    private final Logger logger = LoggerFactory.getLogger(DefaultCourseQueryService.class);

    public Course getCourseById(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        logger.debug("Fetching course with ID: {}", courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new com.logicerror.e_learning.exceptions.course.CourseNotFoundException("Course not found with id: " + courseId);
                });
    }

    public Course getCourseByTitle(String title) {
        Assert.notNull(title, "Course title must not be null");
        logger.debug("Fetching course with title: {}", title);
        return courseRepository.findByTitle(title)
                .orElseThrow(() -> {
                    logger.error("Course not found with title: {}", title);
                    return new com.logicerror.e_learning.exceptions.course.CourseNotFoundException("Course not found with title: " + title);
                });
    }

    public Page<Course> getAllCourses(Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching all courses, page: {}", pageable.getPageNumber());
        Page<Course> courses = courseRepository.findAll(pageable);
        logger.debug("Found {} courses", courses.getTotalElements());
        return courses;
    }

    //TODO: Optimize to avoid N+1 query problem
    public Page<Course> getAllCoursesWithStudentsCount(Pageable pageable) {
        logger.debug("Fetching all courses with students count, page: {}", pageable.getPageNumber());
        Assert.notNull(pageable, "Pageable must not be null");
        Page<Course> courses = courseRepository.findAll(pageable);

        List<Long> courseIds = courses.stream()
                .map(Course::getId)
                .toList();

        logger.debug("Found {} courses, fetching students count", courses.getTotalElements());
        Map<Long, Integer> studentsCountMap = courseRepository.findCoursesWithStudentsCount(courseIds)
                .stream()
                .collect(Collectors.toMap(
                        result -> ((Course) result[0]).getId(),
                        result -> ((Long) result[1]).intValue()
                ));

        courses.forEach(course -> {
            course.setStudentsCount(studentsCountMap.getOrDefault(course.getId(), 0));
        });

        logger.debug("Found {} courses with students count", courses.getTotalElements());
        return courses;
    }

    public Page<Course> getCoursesByCategory(String category, Pageable pageable) {
        Assert.notNull(category, "Course category must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by category: {}, page: {}", category, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByCategory(category, pageable);
        logger.debug("Found {} courses in category: {}", courses.getTotalElements(), category);
        return courses;
    }

    public Page<Course> getCoursesByLevel(String level, Pageable pageable) {
        Assert.notNull(level, "Course level must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by level: {}, page: {}", level, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByLevel(level, pageable);
        logger.debug("Found {} courses with level: {}", courses.getTotalElements(), level);
        return courses;
    }

    public Page<Course> getCoursesByAuthenticatedTeacher(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        Assert.notNull(user, "Authenticated user must not be null");
        if(!user.isTeacher()) {
            logger.error("User {} is not a teacher", user.getUsername());
            throw new AccessDeniedException("User is not a teacher");
        }
        logger.debug("Fetching courses for authenticated teacher: {}, page: {}", user.getUsername(), pageable.getPageNumber());

        return teacherCoursesRepository.findTeacherCoursesByTeacherId(user.getId(), pageable);
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
