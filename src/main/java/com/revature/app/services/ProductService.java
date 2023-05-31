package com.revature.app.services;

import com.revature.app.daos.OrderDAO;
import com.revature.app.daos.ProductDAO;
import com.revature.app.models.Order;
import com.revature.app.models.Product;
import lombok.AllArgsConstructor;



import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;

    public List<Product> findByCategory(String name) { return this.productDAO.findByCategory(name); }

    public void updateOnHand (String productId, String user_id, String quantity) { this.orderDAO.updateOnHand(productId, user_id, quantity); }

    public void addToOrder (Order order){ this.orderDAO.save(order); }

    public Optional<Order> retrieveOrder (String user_id) { return orderDAO.findByUserId(user_id); }

    public boolean inCartCheck(String pId, String user) { return this.orderDAO.cartCheck(pId, user); }

    public Optional<Product> findByName(String name) { return this.productDAO.findByName(name); }

    public List<Product> findByPriceRange(double min, double max) {
        List<Product> productList = productDAO.findByPriceRange(min,max);
        return productList;
    }
}
