package com.revature.app.services;

import com.revature.app.daos.ProductDAO;
import com.revature.app.models.Product;
import lombok.AllArgsConstructor;



import java.util.List;

@AllArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;

    public List<Product> findByCategory(String name) {
        return this.productDAO.findByCategory(name);
    }

    public void updateOnHand (String name, int i) {
        this.productDAO.updateOnHand(name,i);
    }

}
