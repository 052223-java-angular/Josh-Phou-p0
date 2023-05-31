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

    public Optional<Review> findByUserName(String productId, String userName) {
        return this.reviewDAO.findByUser(productId, userName);
    }

    public void createReview(Review review) {
        this.reviewDAO.save(review);
    }

    public String getProductId (String productName) {
        return this.reviewDAO.getProductId(productName);
    }
}
