package com.revature.app.screens;

import com.revature.app.services.RouterService;
import lombok.*;

import java.util.Scanner;

@AllArgsConstructor
public class ProductScreen implements IScreen {
    private final RouterService router;

    @Override
    public void start(Scanner scanner) {
        String input = "";
        scanner.nextLine();

        while (true) {
            clearScreen();
            System.out.println("Welcome to the product catalog\n");
            System.out.println("[1] Product category 1");
            System.out.println("[2] Product category 2");
            System.out.println("[3] Product category 3");
            System.out.println("[4] Product category 4");
            System.out.println("[x] Return to menu");

            System.out.print("\nEnter: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "1":
                    router.navigate("/product1", scanner);
                    break;
                case"2":
                    router.navigate("/product2", scanner);
                    break;
                case "3":
                    router.navigate("/product3", scanner);
                    break;
                case "4":
                    router.navigate("/product4", scanner);
                    break;
                case "x":
                    router.navigate("/browse", scanner);
                    break;
                default:
                    clearScreen();
                    System.out.println("Invalid option selected");
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
            }

            break;
        }
    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
