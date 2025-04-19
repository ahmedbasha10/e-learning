package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.enrollment.UserEnrollments;
import com.logicerror.e_learning.entities.enrollment.UserEnrollmentsKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrollmentsRepository extends JpaRepository<UserEnrollments, UserEnrollmentsKey> {
    // Custom query methods can be defined here if needed
}
