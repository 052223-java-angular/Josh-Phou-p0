package com.revature.app;

import com.revature.app.daos.OrderDAO;
import com.revature.app.models.Order;
import com.revature.app.models.Product;
import com.revature.app.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    private OrderService orderService;

    private OrderService.ORDER_STATUS status;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        orderService = new OrderService(orderDAO);
    }

    @Test
    public void testFindOrderByUserId_WhenOrdersExist() {
        String userId = "123";
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("456", "0", "1", userId, "1", "123", new Product()));
        orders.add(new Order("789", "0", "1", userId, "1", "123", new Product()));

        when(orderDAO.findOrderByUserId(userId)).thenReturn(Optional.of(orders));

        List<Order> result = orderService.findOrderByUserId(userId);

        assertEquals(orders, result);
        verify(orderDAO, times(1)).findOrderByUserId(userId);
    }

    @Test
    public void testFindOrderByUserId_WhenNoOrdersExist() {
        String userId = "123";
        when(orderDAO.findOrderByUserId(userId)).thenReturn(Optional.empty());

        List<Order> result = orderService.findOrderByUserId(userId);

        assertEquals(new ArrayList<>(), result);
        verify(orderDAO, times(1)).findOrderByUserId(userId);
    }

    @Test
    public void testUpdateProductOrderQuantity_WhenOrderExistsAndIncreaseIsTrue() {
        String orderId = "123";
        String productId = "456";
        Order order = new Order();
        order.setQuantity("5");
        order.setProduct(new Product("456", "Test Product", "19.99", "10", "789"));

        when(orderDAO.findOrderByProductAndOrderId(orderId, productId)).thenReturn(Optional.of(order));
        when(orderDAO.updateQuantity(anyString(), anyString(), anyString())).thenReturn(1);

        int result = orderService.updateProductOrderQuantity(true, orderId, productId);

        assertEquals(1, result);
        verify(orderDAO, times(1)).findOrderByProductAndOrderId(orderId, productId);
        verify(orderDAO, times(1)).updateQuantity("6", orderId, productId);
    }

    @Test
    public void testUpdateProductOrderQuantity_WhenOrderExistsAndIncreaseIsFalse() {
        String orderId = "123";
        String productId = "456";
        Order order = new Order();
        order.setQuantity("5");
        order.setProduct(new Product("456", "Test Product", "19.99", "10", "789"));

        when(orderDAO.findOrderByProductAndOrderId(orderId, productId)).thenReturn(Optional.of(order));
        when(orderDAO.updateQuantity(anyString(), anyString(), anyString())).thenReturn(1);

        int result = orderService.updateProductOrderQuantity(false, orderId, productId);

        assertEquals(1, result);
        verify(orderDAO, times(1)).findOrderByProductAndOrderId(orderId, productId);
        verify(orderDAO, times(1)).updateQuantity("4", orderId, productId);
    }

    @Test
    public void testUpdateProductOrderQuantity_WhenOrderDoesNotExist() {
        String orderId = "123";
        String productId = "456";

        when(orderDAO.findOrderByProductAndOrderId(orderId, productId)).thenReturn(Optional.empty());

        int result = orderService.updateProductOrderQuantity(true, orderId, productId);

        assertEquals(0, result);
        verify(orderDAO, times(1)).findOrderByProductAndOrderId(orderId, productId);
        verify(orderDAO, never()).updateQuantity(anyString(), anyString(), anyString());
        verify(orderDAO, never()).deleteProductFromOrder(anyString(), anyString());
    }

    @Test
    public void testUpdateProductOrderQuantity_WhenNewQuantityIsZero() {
        String orderId = "123";
        String productId = "456";
        Order order = new Order();
        order.setQuantity("1");
        order.setProduct(new Product("456", "Test Product", "19.99", "10", "789"));

        when(orderDAO.findOrderByProductAndOrderId(orderId, productId)).thenReturn(Optional.of(order));
        when(orderDAO.deleteProductFromOrder(orderId, productId)).thenReturn(1);

        int result = orderService.updateProductOrderQuantity(false, orderId, productId);

        assertEquals(1, result);
        verify(orderDAO, times(1)).findOrderByProductAndOrderId(orderId, productId);
        verify(orderDAO, never()).updateQuantity(anyString(), anyString(), anyString());
        verify(orderDAO, times(1)).deleteProductFromOrder(orderId, productId);
    }

    @Test
    public void testUpdateProductOrderQuantity_WhenOnHandIsTooLow() {
        String orderId = "123";
        String productId = "456";
        Order order = new Order();
        order.setQuantity("1");
        order.setProduct(new Product("456", "Test Product", "19.99", "0", "789"));

        when(orderDAO.findOrderByProductAndOrderId(orderId, productId)).thenReturn(Optional.of(order));

        int result = orderService.updateProductOrderQuantity(true, orderId, productId);

        assertEquals(0, result);
        verify(orderDAO, times(1)).findOrderByProductAndOrderId(orderId, productId);
        verify(orderDAO, never()).updateQuantity(anyString(), anyString(), anyString());
        verify(orderDAO, never()).deleteProductFromOrder(anyString(), anyString());
    }

    @Test
    public void testRemoveProductFromOrder_WhenOrderAndProductExist() {
        String orderId = "123";
        String productId = "456";
        Order order = new Order();

        when(orderDAO.findOrderByProductAndOrderId(orderId, productId)).thenReturn(Optional.of(order));
        when(orderDAO.deleteProductFromOrder(orderId, productId)).thenReturn(1);

        int result = orderService.removeProductFromOrder(orderId, productId);

        assertEquals(1, result);
        verify(orderDAO, times(1)).findOrderByProductAndOrderId(orderId, productId);
        verify(orderDAO, times(1)).deleteProductFromOrder(orderId, productId);
    }

    @Test
    public void testRemoveProductFromOrder_WhenOrderOrProductDoesNotExist() {
        String orderId = "123";
        String productId = "456";

        when(orderDAO.findOrderByProductAndOrderId(orderId, productId)).thenReturn(Optional.empty());

        int result = orderService.removeProductFromOrder(orderId, productId);

        assertEquals(0, result);
        verify(orderDAO, times(1)).findOrderByProductAndOrderId(orderId, productId);
        verify(orderDAO, never()).deleteProductFromOrder(orderId, productId);
    }

    @Test
    public void testDeleteOrder_WhenOrderExists() {
        String orderId = "123";
        when(orderDAO.deleteByOrderId(orderId)).thenReturn(1);

        int result = orderService.deleteOrder(orderId);

        assertEquals(1, result);
        verify(orderDAO, times(1)).deleteByOrderId(orderId);
    }

    @Test
    public void testDeleteOrder_WhenOrderDoesNotExist() {
        String orderId = "123";
        when(orderDAO.deleteByOrderId(orderId)).thenReturn(0);

        int result = orderService.deleteOrder(orderId);

        assertEquals(0, result);
        verify(orderDAO, times(1)).deleteByOrderId(orderId);
    }

    @Test
    public void testCheckout_WhenOrdersExist() {
        String orderId = "123";
        String productId1 = "456";
        String productId2 = "789";
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(productId1);
        product2.setId(productId2);
        product1.setOnHand("8");
        product2.setOnHand("9");
        Order order1 = new Order("789", "2", "1", "111", productId1, orderId, product1);
        Order order2 = new Order("7891", "2", "1", "111", productId2, orderId, product2);
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        orderService.checkout(orders);

        verify(orderDAO, times(1)).updateOrderStatus("2", orderId, productId1, "7");
        verify(orderDAO, times(1)).updateOrderStatus("2", orderId, productId2, "8");
    }

    @Test
    public void testCheckout_WhenNoOrdersExist() {
        List<Order> orders = new ArrayList<>();

        orderService.checkout(orders);

        verify(orderDAO, never()).updateOrderStatus(anyString(), anyString(), anyString(), anyString());
    }

}
