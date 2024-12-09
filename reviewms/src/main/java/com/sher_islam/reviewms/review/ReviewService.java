package com.sher_islam.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean addReview(Review review,Long companyId);
    Review getReview(Long reviewId);
    boolean updateReview(Long reviewId,Review review);

    boolean deleteReview(Long reviewId);
}
