package com.revature.app.daos;

import com.revature.app.models.Product;
import com.revature.app.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO implements ICrudDAO<Product> {


    @Override
    public void save(Product product) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Product product) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Optional<Product> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public List<Product> findByCategory(String name) {
       List<Product> items = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM products INNER JOIN departments ON products.departments_id=departments.id WHERE departments.name = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set the name parameter for the prepared statement
                ps.setString(1,  name);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // Create a new product object and populate it with data from the result set
                        Product product = new Product();
                        product.setId(rs.getString("id"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getDouble("price"));
                        product.setOnHand(rs.getString("on_hand"));
                        product.setDepartmentId(rs.getString("departments_id"));
                        items.add(product);
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
        return items;
    }

    @Override
    public List<Product> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public Optional<Product> findByName (String name) {
        Product product = new Product();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM products WHERE name = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set the name parameter for the prepared statement
                ps.setString(1,  name);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        product.setId(rs.getString("id"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getDouble("price"));
                        product.setOnHand(rs.getString("on_hand"));
                        product.setDepartmentId(rs.getString("departments_id"));
                        return Optional.of(product);
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

    public  List<Product> findByPriceRange(double min, double max) {
        List<Product> productList = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM products WHERE price >= ? AND price <=? ORDER BY price";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set the name parameter for the prepared statement
                ps.setDouble(1,  min);
                ps.setDouble(2,  max);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Product product = new Product();
                        product.setId(rs.getString("id"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getDouble("price"));
                        product.setOnHand(rs.getString("on_hand"));
                        product.setDepartmentId(rs.getString("departments_id"));
                        productList.add(product);
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
        return productList;
    }

}

