package com.revature.app;

import java.util.Scanner;

import com.revature.app.models.Session;
import com.revature.app.services.RouterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
  private static final Logger logger = LogManager.getLogger(App.class);

  public static void main(String args[]) {
    logger.info("------------------ START APPLICATION -----------------------------");
    System.out.println("Welcome to eCommerce App!");

    Scanner scan = new Scanner(System.in);
    RouterService router = new RouterService(new Session());

    router.navigate("/home", scan);

    logger.info("------------------ END APPLICATION -----------------------------");
    scan.close();

  }
}