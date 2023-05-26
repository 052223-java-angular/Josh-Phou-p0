package com.revature.app.screens;

import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class p4Screen implements IScreen{
    private final RouterService router;

    @Override
    public void start(Scanner scanner) {
        String input = "";
        scanner.nextLine();

        while (true) {
            clearScreen();
            System.out.println("Product1 Catalog\n");
            System.out.println("[1] Product 1");
            System.out.println("[2] Product 2");
            System.out.println("[3] Product 3");
            System.out.println("[4] Product 4");
            System.out.println("[x] Return to menu");

            System.out.print("\nEnter: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "1":
                    // TODO choose product 1
                    break;
                case"2":
                    // TODO choose product 1
                    break;
                case "3":
                    // TODO choose product 1
                    break;
                case "4":
                    // TODO choose product 1
                    break;
                case "x":
                    // TODO return to BrowseScreen
                    break;
                default:
                    clearScreen();
                    System.out.println("Invalid option selected");
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
            }

        }
    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}