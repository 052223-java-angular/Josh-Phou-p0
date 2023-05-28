package com.revature.app.daos;

import com.revature.app.models.Review;
import com.revature.app.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReviewDAO implements ICrudDAO<Review> {


    @Override
    public void save(Review obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO reviews (id, comment, rating, user_id, product_id) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set values for prepared statement parameters
                ps.setString(1, obj.getId());
                ps.setString(2, obj.getComment());
                ps.setInt(3, obj.getRating());
                ps.setString(4, obj.getUserId());
                ps.setString(5, obj.getProductId());

                // Execute the SQL statement
                ps.executeUpdate();
                }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
    }


    @Override
    public void update(Review review) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE reviews INNER JOIN products ON reviews.product_id=products.id SET review = ? WHERE product.name = ? AND user_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "product_name");
                ps.setString(2, "review");
                ps.setString(3, "user_id");

                ps.executeUpdate();

            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
    }

    public Review updateReview(Review review, String comment) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE reviews INNER JOIN products ON reviews.product_id=products.id SET review = ? WHERE product.name = ? AND user_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "product_name");
                ps.setString(2, comment);
                ps.setString(3, "user_id");

                ps.executeUpdate();

                return review;

            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
    }

    @Override
    public void delete(String id) {

    }

    public void deleteByName(String name, String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM reviews INNER JOIN products ON reviews.product_id=products.id WHERE product.name = ? AND user_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "product_name");
                ps.setString(2, "user_id");

                ps.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
    }

    @Override
    public Optional<Review> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public Review findByProductName (String name) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM reviews INNER JOIN products ON reviews.product_id=products.id WHERE product.name = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set the username parameter for the prepared statement
                ps.setString(1, name);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Create a new User object and populate it with data from the result set
                        Review review = new Review();
                        review.setId(rs.getString("id"));
                        review.setComment(rs.getString("comment"));
                        review.setRating(rs.getInt("rating"));
                        review.setUserId(rs.getString("user_id"));
                        review.setProductId(rs.getString("product_id"));
                        return review;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
        return null;
    }

    public  Optional<Review> findByUser (String name, String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM reviews INNER JOIN products ON reviews.product_id=products.id WHERE product.name = ? AND user_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set the username parameter for the prepared statement
                ps.setString(1, name);
                ps.setString(2, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Create a new User object and populate it with data from the result set
                        Review review = new Review();
                        review.setId(rs.getString("id"));
                        review.setComment(rs.getString("comment"));
                        review.setRating(rs.getInt("rating"));
                        review.setUserId(rs.getString("user_id"));
                        review.setProductId(rs.getString("product_id"));
                        return Optional.of(review);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
        return Optional.empty();
    }


    @Override
    public List<Review> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
