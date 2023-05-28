package com.revature.app.daos;

import com.revature.app.models.Order;
import com.revature.app.models.Product;
import com.revature.app.utils.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements ICrudDAO<Order> {

    private static final Logger logger = LogManager.getLogger(OrderDAO.class);

    @Override
    public void save(Order order) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }


    @Override
    public void update(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }


    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }


    /* Find the order by its id
     *
     * @param id the id of the order record
     * @return an optional containing the order
     * */
    @Override
    public Optional<Order> findById(String id) {
        logger.info("Entering into findById(String id)");

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


    /* Update the status of the order
     *
     * @param status the ordinal string value of the order status
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * @param remainingOfHand the remaining quantity on hand
     * */
    public void updateOrderStatus(String status, String orderId, String productId, String remainingOnHand) {
        logger.info("Entering into updateOrderStatus(String status, String orderId, String productId, String remainingOnHand)");

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            // update the status of the order
            String sql = "UPDATE ORDERS SET STATUS = ? " +
                    "WHERE order_id = ? " +
                    "AND product_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, status);
                ps.setString(2, orderId);
                ps.setString(3, productId);

                ps.executeUpdate();
            }

            // update on_hand quantity in product
            sql = "UPDATE PRODUCTS SET ON_HAND = ? WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, remainingOnHand);
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


    /* Updates the product order quantity matching the order_id and product_id
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * @return 1 to indicate success,  0 to indicate failure
     * */
    public int updateQuantity(String quantity, String orderId, String productId) {
        logger.info("updateQuantity(String quantity, String orderId, String productId)");

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE ORDERS SET QUANTITY = ? WHERE order_id = ? and product_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, quantity);
                ps.setString(2, orderId);
                ps.setString(3, productId);

                return ps.executeUpdate();

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
     *
     * @param id the id of the order record
     * */
    @Override
    public void delete(String id) {
        logger.info("Entering into delete(String id)");

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
     *
     * @param orderId the order_id associated to the order
     * @return 1 to indicate success,  0 to indicate failure
     * */
    public int deleteByOrderId(String orderId) {
        logger.info("Entering into deleteByOrderId(String orderId)");

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM ORDERS WHERE order_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, orderId);
                return ps.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }

    }


    /* Delete a product from the order that matches the order_id and product_id
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * @return 1 to indicate success,  0 to indicate failure
     * */
    public int deleteProductFromOrder(String orderId, String productId) {
        logger.info("Entering into deleteProductFromOrder(String orderId, String productId)");

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM ORDERS WHERE order_id = ? and product_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, orderId);
                ps.setString(2, productId);

                return ps.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
    }


    /* Find orders by the users id
    *
     * @param userId the id of the user
     * @param status the ordinal string value of the order status
     * @return an optional containing a list of products / items in the order
     * */
    public Optional<List<Order>> findOrderByUserId(String userId, String status) {
        logger.info("Entering into findOrderByUserId(String userId, String status) ");

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
                ps.setString(2, status);

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


    /* Retrieves a product of an order by the order id and product id
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * @return an optional containing the product
     * */
    public Optional<Order> findOrderByProductAndOrderId(String orderId, String productId) {
        logger.info("findOrderByProductAndOrderId(String orderId, String productId)");

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


    /*
     * ------------------------  Helper methods ------------------------
     */


    /* Sets the fields of the Product and Order instance using the data from the ResultSet
    * */
    private Order buildOrderInstance(ResultSet rs) throws SQLException {
        logger.info("Entering into buildOrderInstance(ResultSet rs)");

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

}
