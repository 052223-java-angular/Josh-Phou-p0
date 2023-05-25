package com.revature.app.services;

import com.revature.app.daos.RoleDAO;
import com.revature.app.daos.UserDAO;
import com.revature.app.screens.HomeScreen;
import com.revature.app.screens.LoginScreen;
import com.revature.app.screens.RegisterScreen;

import java.util.Scanner;

public class RouterService {

    public void navigate (String path, Scanner scanner)
    {
        switch (path){
            case "/home":
                new HomeScreen(this).start(scanner);
                break;
            case "/register":
                new RegisterScreen(getUserService()).start(scanner);
                break;
            case "/login":
                new LoginScreen(getUserService()).start(scanner);
                break;
            case "/browse":
                break;
            case "/checkout":
                break;
            case "/review":
                break;
            case "/orderHistory":
                break;
            default:
                break;
        }
    }

    private UserService getUserService() {
        return new UserService(new UserDAO(), getRoleService());
    }

    private RoleService getRoleService() {
        return new RoleService(new RoleDAO());
    }

}
