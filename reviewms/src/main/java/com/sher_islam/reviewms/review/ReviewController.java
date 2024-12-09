package com.sher_islam.reviewms.review;

import com.sher_islam.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,
                            ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer=reviewMessageProducer;
    }
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){

        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody Review review,
                                            @RequestParam Long companyId){
        boolean reviewAdded=reviewService.addReview(review,companyId);
        if(reviewAdded){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review added successfully",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getreview(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReview(
                reviewId),HttpStatus.OK);
    }
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review updatedReview){
        boolean isReviewUpdated= reviewService.updateReview(reviewId,updatedReview);
        if(isReviewUpdated){
            return new ResponseEntity<>("Review updated successfully"
                    ,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Review not updated"
                    ,HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isDeleted= reviewService.deleteReview(reviewId);
        if(isDeleted){
            return new ResponseEntity<>("Review deleted successfully"
                    ,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Review not deleted"
                    ,HttpStatus.NOT_FOUND);
        }


    }
    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId){
    List<Review> reviewList =reviewService.getAllReviews(companyId);
    return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }


}
