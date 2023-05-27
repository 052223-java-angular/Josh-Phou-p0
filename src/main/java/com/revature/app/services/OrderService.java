package com.revature.app.services;

import com.revature.app.daos.OrderDAO;
import com.revature.app.models.Order;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public List<Order> findAllOrdersByUserId(String userId) {

        Optional<List<Order>> orderItems = orderDAO.findPendingOrderByUserId(userId);

        if (orderItems.isEmpty()) {
            return new ArrayList<>();
        }

        return orderItems.get();

    }
}
