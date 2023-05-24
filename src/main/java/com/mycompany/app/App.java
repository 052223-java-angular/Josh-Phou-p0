package com.mycompany.app;

import com.mycompany.app.screens.HomeScreen;

import java.util.Scanner;

import static java.lang.System.out;

public class App {
  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);

    // temporary - replace with router
    new HomeScreen().start(scanner);

  }
}
