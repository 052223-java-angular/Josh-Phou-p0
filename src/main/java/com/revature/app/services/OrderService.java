package com.revature.app.services;

import com.revature.app.daos.OrderDAO;
import com.revature.app.models.Order;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.revature.app.utils.FormatUtil.*;


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
     * @return list of orders associated with user and matching the status
     * */
    public List<Order> findOrderByUserId(String userId) {


        // retrieve the order by user id and current status
        logger.info("Finding the order for user_id {}", userId);
        Optional<List<Order>> orderItems = orderDAO.findOrderByUserId(userId);

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
        Optional<Order> order = orderDAO.findOrderByProductAndOrderId(orderId, productId);

        if (order.isEmpty()) {
            logger.info("Unable to locate any orders for order_id: {} and product_id: {}", orderId, productId);
            return 0;
        }

        // when order is found, determine whether increase or decrease can happen given current inventory levels
        int newQuantity = increase ? toInt(order.get().getQuantity()) + 1 : toInt(order.get().getQuantity()) -1;

        if (newQuantity <= 0) {
            logger.info("Deleting product_id: {} from order having order_id: {} because quantity is 0", productId, orderId);
            // update on_hand quantity by quantity in order

            int newOnHandQty =  toInt(order.get().getQuantity()) + toInt(order.get().getProduct().getOnHand());

            // when the newQuantity is 0, delete the item from the order
            return orderDAO.deleteProductFromOrder(String.valueOf(newOnHandQty), orderId, productId);
        }

        if (toInt(order.get().getProduct().getOnHand()) - toInt(order.get().getQuantity()) + 1 <= 0) {
            logger.info("Inventory level for product_id: {} is too low, cannot update the order quantity.", productId);
            return 0;
        }

        // when all checks pass, update the product on_hand quantity too
        int onHandChangeQty = newQuantity >  toInt(order.get().getQuantity()) ?
                toInt(order.get().getProduct().getOnHand()) - 1 : toInt(order.get().getProduct().getOnHand()) + 1;

        logger.info("Updating the order quantity for product_id: {} belonging to order_id: {} ", productId, orderId);
        return orderDAO.updateQuantity(String.valueOf(newQuantity), String.valueOf(onHandChangeQty), orderId, productId);

    }


    /* Removes a product from the order
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * @return 1 to indicate success,  0 to indicate failure
     * */
    public int removeProductFromOrder(String orderId, String productId) {

        // verify the order and product exists
        Optional<Order> order = orderDAO.findOrderByProductAndOrderId(orderId, productId);

        // when empty, return 0 to indicate update was not successful
        if (order.isEmpty()) {
            logger.info("Unable to remove product_id: {} for order of order_id: {} because it was not found.", productId, orderId);
            return 0;
        }

        // update on_hand quantity by quantity in order

        int newOnHandQty = toInt(order.get().getQuantity()) + toInt(order.get().getProduct().getOnHand());
        logger.info("Deleting product_id: {} from order having order_id: {}", productId, orderId);
        return orderDAO.deleteProductFromOrder(String.valueOf(newOnHandQty), orderId, productId);
    }


    /* Delete the orders records and updates the product on_hand quantity
     *
     * @param orders a list of orders to delete
     * @return 1 to indicate success,  0 to indicate failure
     * */
    public int deleteOrderUpdateOnHand(List<Order> orders) {
        logger.info("Beginning to delete order ");

        return orders.size() > 0 ? orderDAO.deleteByOrderIdAndProductId(orders) : 0;
    }


    /* Completes the checkout process by Updating the status of the order to complete
     *
     * @param orderId the order_id associated to the order
     * @param productId the product_id of the product
     * */
    public void checkout(List<Order> orders) {

        if (orders.size() > 0 ) {

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
    }


}
