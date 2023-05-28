package com.revature.app.services;

import com.revature.app.daos.OrderDAO;
import com.revature.app.models.Order;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.System.out;

@AllArgsConstructor
public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final OrderDAO orderDAO;

    /* an enum of valid and available order statuses
    * */
    public enum ORDER_STATUS {
        PENDING, SHIPPING, COMPLETE
    }

    /* Find orders by the given userId
     *
     * @param userId the user_id of a user
     * @param status an enum indicating the status of an order
     * @return list of orders associated with user and matching the status
     * */
    public List<Order> findOrderByUserId(String userId, ORDER_STATUS status) {


        // retrieve the order by user id and current status
        logger.info("Finding the order for user_id {} with status {}", userId, status.ordinal());
        Optional<List<Order>> orderItems = orderDAO.findOrderByUserId(userId, String.valueOf(status.ordinal()));

        if (orderItems.isEmpty()) {
            logger.info("Unable to locate any orders for user_id: {}", userId);
            return new ArrayList<>();
        }

        return orderItems.get();
    }


    /* Updates the product order quantity or removes the product when quantity is less than or equal to zero
    *
    * @param increase whether to increase or decrease the order quantity
    * @param orderId the order_id associated to the order
    * @param productId the product_id of the product
    * @return 1 to indicate success,  0 to indicate failure
    * */
    public int updateProductOrderQuantity(boolean increase, String orderId, String productId) {

        // verify the order and product exists
        Optional<Order> order = orderDAO.findOrderByProductId(orderId, productId);

        if (order.isEmpty()) {
            logger.info("Unable to locate any orders for order_id: {} and product_id: {}", orderId, productId);
            return 0;
        }

        int newQuantity = increase ? toInt(order.get().getQuantity()) + 1 : toInt(order.get().getQuantity()) -1;
        if (newQuantity <= 0) {
            logger.info("Deleting product_id: {} from order having order_id: {} because quantity is 0", productId, orderId);
            // when the newQuantity is 0, delete the item from the order
            return orderDAO.deleteProductFromOrder(orderId, productId);
        }

        if (toInt(order.get().getProduct().getOnHand()) - toInt(order.get().getQuantity()) + 1 < 0) {
            logger.info("Inventory level for product_id: {} is too low, cannot update the order quantity.", productId);
            return 0;
        }

        logger.info("Updating the order quantity for product_id: {} belonging to order_id: {} ", productId, orderId);
        return orderDAO.updateQuantity(String.valueOf(newQuantity), orderId, productId);

    }


    /* Removes a product from the order
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * @return 1 to indicate success,  0 to indicate failure
     * */
    public int removeProductFromOrder(String orderId, String productId) {

        // verify the order and product exists
        Optional<Order> order = orderDAO.findOrderByProductId(orderId, productId);

        // when empty, return 0 to indicate update was not successful
        if (order.isEmpty()) {
            logger.info("Unable to remove product_id: {} for order of order_id: {} because it was not found.", productId, orderId);
            return 0;
        }

        logger.info("Deleting product_id: {} from order having order_id: {}", productId, orderId);
        return orderDAO.deleteProductFromOrder(orderId, productId);
    }


    /* Delete the orders records matching the order_id
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * @return 1 to indicate success,  0 to indicate failure
     * */
    public int deleteOrder(String orderId) {
        logger.info("Beginning to delete order having order_id: {}", orderId);
        return orderDAO.deleteByOrderId(orderId);
    }


    /* Completes the checkout process by Updating the status of the order to complete
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * */
    public void checkout(List<Order> orders) {

        // update the order status to complete and on hand quantity for all items
        logger.info("Checking out and updating the status of order having order_id: {}", orders.get(0).getOrderId());
        for (Order order : orders) {
            orderDAO.updateOrderStatus(
                    String.valueOf(ORDER_STATUS.COMPLETE.ordinal()),
                    order.getOrderId(),
                    order.getProductId(),
                    String.valueOf(toInt(order.getProduct().getOnHand()) - toInt(order.getQuantity())));
        }
    }


    /* Convert a String to Integer
    *
    * @param value the string to convert
    * @return an int
    * */
    private int toInt(String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }

}
