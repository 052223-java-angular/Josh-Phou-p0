package com.revature.app.daos;

import com.revature.app.models.Order;
import com.revature.app.models.Product;
import com.revature.app.services.OrderService;
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
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO orders (id, order_id, status, quantity, user_id, product_id) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, order.getId());
                ps.setString(2, order.getOrderId());
                ps.setString(3, order.getStatus());
                ps.setString(4, order.getQuantity());
                ps.setString(5, order.getUserId());
                ps.setString(6, order.getProductId());

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
    public void update(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /* Updates the product order quantity matching the order_id and product_id
    * */
    public void updateQuantity(String quantity, String orderId, String productId) {

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE ORDERS SET QUANTITY = ? WHERE order_id = ? and product_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, quantity);
                ps.setString(2, orderId);
                ps.setString(3, productId);

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

    /* Deletes an Order record by the tables id
    * */
    @Override
    public void delete(String id) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM ORDERS WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, id);

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

    /* Deletes all the records of an order by the order_id
    * */
    public void deleteByOrderId(String orderId) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM ORDERS WHERE order_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, orderId);
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

    /* Delete a product from the orders table that matches the order_id and product_id
    * */
    public void deleteProductFromOrder(String orderId, String productId) {

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM ORDERS WHERE order_id = ? and product_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, orderId);
                ps.setString(2, productId);

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

    /* Find the order and its associated products by the order_id
    * */
    @Override
    public Optional<Order> findById(String id) {

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT a.*, b.id as product_id, b.name as product_name, b.price, b.on_hand, b.departments_id " +
                    "FROM ORDERS AS a " +
                    "INNER JOIN PRODUCTS AS b " +
                    "ON a.product_id = b.id " +
                    "WHERE id = ? " +
                    "ORDER BY b.name";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(buildOrderInstance(rs));
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
    public List<Order> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    /* Find orders by the users id */
    public Optional<List<Order>> findOrderByUserId(String userId, OrderService.ORDER_STATUS status) {

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT a.*, b.id as product_id, b.name as product_name, b.price, b.on_hand, b.departments_id " +
                    "FROM ORDERS AS a " +
                    "INNER JOIN PRODUCTS AS b " +
                    "ON a.product_id = b.id " +
                    "WHERE user_id = ? " +
                    "AND status = ? " +
                    "ORDER BY b.name";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, userId);
                ps.setString(2, String.valueOf(status.ordinal()));

                try (ResultSet rs = ps.executeQuery()) {
                    List<Order> orderItemList = new ArrayList<>();

                    while (rs.next()) {
                        orderItemList.add(buildOrderInstance(rs));
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


    /* Retrieves an order by the product id
    * */
    public Optional<Order> findOrderByProductId(String orderId, String productId) {

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT a.*, b.id as product_id, b.name as product_name, b.price, b.on_hand, b.departments_id " +
                    "FROM ORDERS AS a " +
                    "INNER JOIN PRODUCTS AS b " +
                    "ON a.product_id = b.id " +
                    "WHERE a.product_id = ? " +
                    "AND a.order_id = ? " +
                    "ORDER BY b.name";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, productId);
                ps.setString(2, orderId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(buildOrderInstance(rs));
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

    public Optional<Order> findByUserId (String id) {
        Order order = new Order();
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM orders WHERE user_id = ? AND status = '0'";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, id);


                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        order.setId(rs.getString("id"));
                        order.setOrderId(rs.getString("order_id"));
                        order.setStatus(rs.getString("status"));
                        order.setQuantity(rs.getString("quantity"));
                        order.setProductId(rs.getString("product_id"));
                        order.setUserId(rs.getString("user_id"));
                        return Optional.of(order);
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


    /*
     * ------------------------  Helper methods ------------------------
     */

    /* Sets the fields of the Product and Order instance using the data from the ResultSet*/
    private Order buildOrderInstance(ResultSet rs) throws SQLException {
        Product product = new Product(
                rs.getString("product_id"),
                rs.getString("product_name"),
                rs.getString("price"),
                rs.getString("on_hand"),
                rs.getString("departments_id")
        );
        return new Order(
                rs.getString("id"),
                rs.getString("status"),
                rs.getString("quantity"),
                rs.getString("user_id"),
                rs.getString("product_id"),
                rs.getString("order_id"),
                product
        );
    }

    public boolean cartCheck(String pId, String user) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM orders WHERE user_id = ? AND user_id = ? AND status = '0'";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, pId);
                ps.setString(2, user);


                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return true;
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
        return false;
    }

    public void updateOnHand (String productId, String orderId, String userId, int quantity) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE products SET on_hand = ? WHERE id = ? AND user_id = ? AND status = '0'";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set the name parameter for the prepared statement
                ps.setInt(1, quantity);
                ps.setString(2, userId);
                ps.setString(2, productId);

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
}
