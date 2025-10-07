package com.logicerror.e_learning.courses.services.operationhandlers.creation;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.courses.requests.CreateCourseRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Data
public class CourseCreationContext {
    private final CreateCourseRequest request;
    private final MultipartFile thumbnail;
    private final User user;
    private Course course;
}
