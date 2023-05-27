package com.revature.app.daos;

import com.revature.app.models.Order;
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

public class OrderDAO implements ICrudDAO<Order> {

    @Override
    public void save(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Order findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Order> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }


    public Optional<List<Order>> findPendingOrderByUserId(String userId) {

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT a.*, b.id as product_id, b.name as product_name, b.price, b.on_hand, b.departments_id " +
                    "FROM ORDERS AS a " +
                    "INNER JOIN PRODUCTS AS b " +
                    "ON a.product_id = b.id " +
                    "WHERE user_id = ? " +
                    "AND status = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, userId);
                ps.setString(2, "0");

                try (ResultSet rs = ps.executeQuery()) {
                    List<Order> orderItemList = new ArrayList<>();

                    while (rs.next()) {
                        Product product = new Product(
                                rs.getString("product_id"),
                                rs.getString("product_name"),
                                rs.getString("price"),
                                rs.getString("on_hand"),
                                rs.getString("departments_id")
                        );
                        Order orderItem = new Order(
                                rs.getString("id"),
                                rs.getString("order_id"),
                                rs.getString("status"),
                                rs.getString("quantity"),
                                rs.getString("user_id"),
                                rs.getString("product_id"),
                                product
                        );
                        orderItemList.add(orderItem);
                    }

                    return Optional.of(orderItemList);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }

    }

}
