package com.revature.app.services;

import com.revature.app.screens.HomeScreen;

import java.util.Scanner;

public class RouterService {

    public void navigate (String path, Scanner scan)
    {
        switch (path){
            case "/home":
                new HomeScreen().start(scan);
                break;
            case "/login":
                break;
            case "register":
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
}
