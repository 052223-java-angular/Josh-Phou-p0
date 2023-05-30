package com.revature.app.services;

import com.revature.app.daos.OrderDAO;
import com.revature.app.daos.RoleDAO;
import com.revature.app.daos.UserDAO;

import com.revature.app.models.Session;
import com.revature.app.screens.*;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

@AllArgsConstructor
public class RouterService {

    private static final Logger logger = LogManager.getLogger(RouterService.class);
    private Session session;

    public void navigate (String path, Scanner scanner)
    {
        switch (path){
            case "/home":
                logger.info("Instantiating HomeScreen and injecting in RouterService");
                new HomeScreen(this).start(scanner);
                break;
            case "/register":
                logger.info("Instantiating RegisterScreen and injecting in UserService and RouterService");
                new RegisterScreen(getUserService(), this).start(scanner);
                break;
            case "/login":
                // after a successful login, the user should navigate to the browse screen
                logger.info("Instantiating LoginScreen and injecting in UserService, RouterService and the session");
                new LoginScreen(getUserService(), this, session).start(scanner);
                break;
            case "/storefront":
                logger.info("Instantiating StoreFront and injecting in RouterService and session");
                new StoreFront(this, session).start(scanner);
                break;
            case "/products":
                logger.info("Instantiating ProductScreen and injecting in RouterService");
                new ProductScreen(this).start(scanner);
                break;
            case "/cart":
                break;
            case "/orders":
                logger.info("Instantiating OrderHistoryScreen and injecting in OrderService, RouterService and session");
                new OrderHistoryScreen(getOrderService(), this, session).start(scanner);
                break;
            case "/checkout":
                logger.info("Instantiating CheckoutScreen and injecting in OrderService, RouterService and session");
                new CheckoutScreen(getOrderService(), this, session).start(scanner);
                break;
            case "/review":
                break;
            default:
                break;
        }
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
}
