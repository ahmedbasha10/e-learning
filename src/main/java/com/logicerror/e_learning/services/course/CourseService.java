package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.course.CourseNotFoundException;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.mappers.UserMapper;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.course.operationhandlers.creation.CourseCreationChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.creation.CourseCreationContext;
import com.logicerror.e_learning.services.course.operationhandlers.delete.CourseDeleteChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.delete.CourseDeleteContext;
import com.logicerror.e_learning.services.course.operationhandlers.update.CourseUpdateChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.update.CourseUpdateContext;
import com.logicerror.e_learning.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final IUserService userService;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final CourseCreationChainBuilder courseOperationChainBuilder; // <CreateCourseRequest>
    private final CourseUpdateChainBuilder courseUpdateChainBuilder; // <UpdateCourseRequest>
    private final CourseDeleteChainBuilder courseDeleteChainBuilder; // <DeleteCourseRequest>
    @Value("${api.base-host}")
    private String baseHost;
    private final Logger logger = LoggerFactory.getLogger(CourseService.class);


    @Override
    public Course getCourseById(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        logger.debug("Fetching course with ID: {}", courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });
    }

    @Override
    public Course getCourseByTitle(String title) {
        Assert.notNull(title, "Course title must not be null");
        logger.debug("Fetching course with title: {}", title);
        return courseRepository.findByTitle(title)
                .orElseThrow(() -> {
                    logger.error("Course not found with title: {}", title);
                    return new CourseNotFoundException("Course not found with title: " + title);
                });
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching all courses, page: {}", pageable.getPageNumber());
        Page<Course> courses = courseRepository.findAll(pageable);
        logger.debug("Found {} courses", courses.getTotalElements());
        return courses;
    }

    @Override
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

    @Override
    public Page<Course> getCoursesByCategory(String category, Pageable pageable) {
        Assert.notNull(category, "Course category must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by category: {}, page: {}", category, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByCategory(category, pageable);
        logger.debug("Found {} courses in category: {}", courses.getTotalElements(), category);
        return courses;
    }

    @Override
    public Page<Course> getCoursesByLevel(String level, Pageable pageable) {
        Assert.notNull(level, "Course level must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by level: {}, page: {}", level, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByLevel(level, pageable);
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

    @Override
    public void calculateEnrolledStudentsCount(CourseDto course) {
        Assert.notNull(course, "Course must not be null");
        logger.debug("Fetching student count for course ID: {}", course.getId());
        int studentsCount = userEnrollmentsRepository.countStudentsByCourseId(course.getId());
        logger.debug("Found {} students for course ID: {}", studentsCount, course.getId());
        course.setStudentsCount(studentsCount);
    }

    @Override
    public void addServerHostToCourseResources(CourseDto course){
        String filePath = baseHost + File.separator + course.getImageUrl();
        course.setImageUrl(filePath.replace("\\", "/"));
        course.getSections().forEach(this::addServerHostToVideoUrl);
    }

    private void addServerHostToVideoUrl(SectionDto section) {
        section.getVideos()
                .forEach(video -> {
                    String videoFilePath = baseHost + File.separator + video.getUrl();
                    video.setUrl(videoFilePath.replace("\\", "/"));
                });
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER')")
    public Course createCourse(CreateCourseRequest request) throws AccessDeniedException {
        User user = userService.getAuthenticatedUser();
        OperationHandler<CourseCreationContext> courseOperationHandler = courseOperationChainBuilder.build();
        CourseCreationContext context = new CourseCreationContext(request, user);
        courseOperationHandler.handle(context);
        return context.getCourse();
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Course updateCourse(Long courseId, UpdateCourseRequest request) throws AccessDeniedException {
        User user = userService.getAuthenticatedUser();
        OperationHandler<CourseUpdateContext> courseOperationHandler = courseUpdateChainBuilder.build();
        CourseUpdateContext context = new CourseUpdateContext(courseId, request, user);
        courseOperationHandler.handle(context);
        return context.getUpdatedCourse();
    }

    @Override
    public void updateCourseDuration(Course course) {
        Assert.notNull(course, "Course must not be null");
        logger.debug("Updating duration for course with ID: {}", course.getId());
        Long totalDuration = sectionRepository.findAllByCourseId(course.getId())
                .stream()
                .mapToLong(Section::getDuration)
                .sum();
        course.setDuration(totalDuration.intValue());
        logger.debug("Updated duration for course ID: {} to {}", course.getId(), totalDuration);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteCourse(Long courseId) {
        User user = userService.getAuthenticatedUser();
        OperationHandler<CourseDeleteContext> courseOperationHandler = courseDeleteChainBuilder.build();
        CourseDeleteContext context = new CourseDeleteContext(courseId, user);
        courseOperationHandler.handle(context);
    }

    @Override
    public CourseDto convertToDto(Course course) {
        User user = teacherCoursesRepository.findByCourseId(course.getId()).getFirst().getUser();
        UserDto teacher = userMapper.userToUserDto(user);
        CourseDto courseDto = courseMapper.courseToCourseDto(course, teacher);
        addServerHostToCourseResources(courseDto);
        calculateEnrolledStudentsCount(courseDto);
        return courseDto;
    }
}
