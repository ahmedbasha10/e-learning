package com.logicerror.e_learning.services.authorization;

import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseAuthorizationService {
    private final TeacherCoursesRepository teacherCoursesRepository;

    public boolean isCourseOwner(Long courseId, Long userId) {
        return teacherCoursesRepository.existsById(
                TeacherCoursesKey.builder()
                        .courseId(courseId)
                        .userId(userId)
                        .build()
        );
    }

    public boolean hasAccessToCreateCourse(User user) {
        if (user == null) {
            log.error("User must be authenticated to create a course");
            return false;
        }

        if (!user.isTeacher()) {
            log.error("User is not authorized to create a course");
            return false;
        }

        log.debug("User {} has permission to create a course", user.getUsername());
        return true;
    }

    public boolean hasAccessToModifyCourse(Long courseId, User user) {
        if (user == null) {
            log.error("User must be authenticated to modify a course with ID: {}", courseId);
            return false;
        }

        if (!user.isTeacher() && !user.isAdmin()) {
            log.error("User is not authorized to modify a course with ID: {}", courseId);
            return false;
        }

        boolean isOwner = isCourseOwner(courseId, user.getId());

        if (!isOwner) {
            log.error("User is not the owner of the course with ID: {}", courseId);
            return false;
        }

        log.debug("User {} has permission to modify the course with ID: {}", user.getUsername(), courseId);
        return true;
    }
}
