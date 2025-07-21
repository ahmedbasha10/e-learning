package com.logicerror.e_learning.services.authorization;

import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
}
