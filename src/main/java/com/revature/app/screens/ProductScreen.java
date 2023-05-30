package com.revature.app.screens;

import com.revature.app.models.Session;
import com.revature.app.services.RouterService;
import lombok.*;

import java.util.Scanner;

@AllArgsConstructor
public class ProductScreen implements IScreen {
    private final RouterService router;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String input = "";
        scanner.nextLine();

        while (true) {
            clearScreen();
            System.out.println("Welcome to the product catalog\n");
            System.out.println("[1] Product categoryA");
            System.out.println("[2] Product categoryB");
            System.out.println("[3] Product categoryC");
            System.out.println("[4] Product categoryD");
            System.out.println("[x] Return to menu");

            System.out.print("\nEnter: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "1":
                    router.productNavigate(scanner, "CategoryA");
                    break;
                case"2":
                    router.productNavigate( scanner, "CategoryB");
                    break;
                case "3":
                    router.productNavigate(scanner, "CategoryC");
                    break;
                case "4":
                    router.productNavigate(scanner, "CategoryD");
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
