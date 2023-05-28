package com.revature.app.services;

import com.revature.app.daos.OrderDAO;
import com.revature.app.models.Order;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.out;

@AllArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public enum ORDER_STATUS {
        PENDING, SHIPPING, COMPLETE
    }

    /* Find orders by the given userId
    * */
    public List<Order> findOrderByUserId(String userId, ORDER_STATUS status) {

        // retrieve the order by user id and current status
        Optional<List<Order>> orderItems = orderDAO.findOrderByUserId(userId, String.valueOf(status.ordinal()));

        if (orderItems.isEmpty()) {
            return new ArrayList<>();
        }

        return orderItems.get();
    }

    /* Updates the product order quantity or removes the product when quantity is less than or equal to zero
    * */
    public void updateProductOrderQuantity(boolean increase, String orderId, String productId) {

        // verify the order and product exists
        Optional<Order> order = orderDAO.findOrderByProductId(orderId, productId);

        // when empty, return 0 to indicate update was not successful
        if (order.isEmpty()) {
            out.format("Product %s was not found for order %s, unable to increase product qty\n", productId, orderId);
        } else {

            // calculate the newQuantity
            int newQuantity = increase ? toInt(order.get().getQuantity()) + 1 : toInt(order.get().getQuantity()) -1;

            if (newQuantity <= 0) {
                // when the newQuantity is 0, delete the item from the order
                orderDAO.deleteProductFromOrder(orderId, productId);
            } else {
                // when inventory is negative, do not update
                if (toInt(order.get().getProduct().getOnHand()) - toInt(order.get().getQuantity()) + 1 < 0) {
                    out.format("%nUnable to increase quantity; not enough %s on hand%n", order.get().getProduct().getName());
                } else {
                    // when newQuantity is > 0, update the product quantity
                    orderDAO.updateQuantity(String.valueOf(newQuantity), orderId, productId);
                }
            }
        }

    }

    /* Removes a product from the order
    * */
    public void removeProductFromOrder(String orderId, String productId) {

        // verify the order and product exists
        Optional<Order> order = orderDAO.findOrderByProductId(orderId, productId);

        // when empty, return 0 to indicate update was not successful
        if (order.isEmpty()) {
            out.format("Product %s was not found for order %s, unable to remove\n", productId, orderId);
        } else {

            // when order is found, delete the product from the order
            orderDAO.deleteProductFromOrder(orderId, productId);
        }

    }

    /* Delete the orders records matching the order_id */
    public void deleteOrder(String orderId) {
        orderDAO.deleteByOrderId(orderId);
    }

    /* Update the status of the order to complete for checking out / purchase
    * */
    public void checkout(List<Order> orders) {

        // update the order status to complete and on hand quantity for all items
        for (Order order : orders) {
            orderDAO.updateOrderStatus(
                    String.valueOf(ORDER_STATUS.COMPLETE.ordinal()),
                    order.getOrderId(),
                    order.getProductId(),
                    String.valueOf(toInt(order.getProduct().getOnHand()) - toInt(order.getQuantity())));
        }
    }

    /* Convert a String to Integer */
    private int toInt(String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }

}
