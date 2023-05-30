package com.revature.app.services;

import com.revature.app.daos.OrderDAO;
import com.revature.app.daos.ProductDAO;
import com.revature.app.models.Order;
import com.revature.app.models.Product;
import lombok.AllArgsConstructor;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;

    // todo review with josh
    public List<Product> findByDepartmentId(String departmentId) {
        Optional<List<Product>> productsOpt = productDAO.findByDepartmentId(departmentId);

        if (productsOpt.isEmpty()) {
            return new ArrayList<>();
        }
        return productsOpt.get();
    }

    public List<Product> findByCategory(String name) {
        return this.productDAO.findByCategory(name);
    }

    public void updateOnHand (String productId, String user_id, String orderId, int quantity) { this.orderDAO.updateOnHand(productId, orderId, user_id, quantity);
    }

    public void addToOrder (Order order){ this.orderDAO.save(order); }

    public Optional<Order> retrieveOrder (String id) { return orderDAO.findByUserId(id); }

    public boolean inCartCheck(String pId, String user) {
        return this.orderDAO.cartCheck(pId, user);
    }
}
