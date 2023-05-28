package com.revature.app.services;

import com.revature.app.daos.ReviewDAO;
import com.revature.app.models.Review;
import lombok.AllArgsConstructor;



import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class ReviewService {
    private final ReviewDAO reviewDAO;

    public Review findByName(String name) {
        return this.reviewDAO.findByProductName(name);
    }

    public void deleteReview(String name, String id) {
        this.reviewDAO.deleteByName(name, id);
    }

    public String updateReview(Review review, String comment) {
        this.reviewDAO.updateReview(review, comment);
        return review.getComment();
    }
}
