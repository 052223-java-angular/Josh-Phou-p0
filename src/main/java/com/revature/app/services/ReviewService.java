package com.revature.app.services;

import com.revature.app.daos.ReviewDAO;
import com.revature.app.models.Review;
import lombok.AllArgsConstructor;



import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class ReviewService {
    private final ReviewDAO reviewDAO;

    public List<Review> findByName(String name) {
        return this.reviewDAO.findByProductName(name);
    }

    public void deleteReview(String name, String id) {
        this.reviewDAO.deleteByName(name, id);
    }

    public void updateReview(Review review, String comment) {
        this.reviewDAO.updateReview(review, comment);
    }

    public Optional<Review> findByUserName(String productName, String userName) {
        return this.reviewDAO.findByUser(productName, userName);
    }

    public void createReview(Review review) {
        this.reviewDAO.save(review);
    }

    public String getPIdByName (String name) {
        String productName = this.reviewDAO.getPIdByName(name);
        return productName;
    }
}
