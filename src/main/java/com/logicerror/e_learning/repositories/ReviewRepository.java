package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.review.Review;
import com.logicerror.e_learning.entities.review.ReviewKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewKey> {
}
