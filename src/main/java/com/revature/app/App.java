package com.revature.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.app.services.RouterService;
import com.revature.app.utils.ConnectionFactory;

public class App {
  public static void main(String args[]) {
    System.out.println("Welcome to eCommerce App!");

    Scanner scan = new Scanner(System.in);
    RouterService router = new RouterService();
    router.navigate("/home",scan);
  }
}