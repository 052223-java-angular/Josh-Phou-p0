package com.revature.app.services;

import com.revature.app.daos.*;

import com.revature.app.models.Session;
import com.revature.app.screens.*;

import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class RouterService {
    private Session session;

    public void navigate (String path, Scanner scanner)
    {
        switch (path){
            case "/home":
                new HomeScreen(this, session).start(scanner);
                break;
            case "/register":
                // Within the register screen, the user should return the main menu after completing the registration process
                new RegisterScreen(getUserService(), this).start(scanner);
                break;
            case "/login":
                // after a successful login, the user should navigate to the browse screen
                new LoginScreen(getUserService(), this, session).start(scanner);
                break;
            case "/storefront":
                new StoreFront(this, session).start(scanner);
                break;
            case "/products":
                new ProductScreen(this, session).start(scanner);
                break;
            case "/cart":
                break;
            case "/checkout":
                new CheckoutScreen(getOrderService(), this, session).start(scanner);
                break;
            case "/review":
                new ReviewScreen(getReviewService(),this,session).start(scanner);
                break;
            case "/orderHistory":
                break;
            default:
                break;
        }
    }

    public void productNavigate (Scanner scanner, String input)
    {
        new PurchaseScreen(getProductService(),this, session, input).start(scanner);
    }

    /*
     * ------------------------  Dependency injection helper methods ------------------------
     */

    private UserService getUserService() {
        return new UserService(new UserDAO(), getRoleService());
    }

    private RoleService getRoleService() {
        return new RoleService(new RoleDAO());
    }

    private OrderService getOrderService() {
        return new OrderService(new OrderDAO());
    }

    private ProductService getProductService() {
        return new ProductService(new ProductDAO());
    }
    private ReviewService getReviewService() { return new ReviewService(new ReviewDAO()); }
}
