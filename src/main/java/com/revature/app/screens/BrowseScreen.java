package com.revature.app.screens;

import com.revature.app.services.RouterService;
import com.revature.app.services.UserService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class BrowseScreen implements IScreen {
    private final RouterService router;

    @Override
    public void start(Scanner scanner) {
        String input = "";
        // todo -- if this is not required, do remove so that screen flow is not interrupted
//        scanner.nextLine();

        exit: {
            while (true) {
                clearScreen();
                System.out.println("Welcome to the product catalog\n");
                System.out.println("[1] Select a product category");
                System.out.println("[2] View cart");
                System.out.println("[3] View past orders");
                System.out.println("[4] Leave a review");
                System.out.println("[x] Exit");

                System.out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()){
                    case "1":
                        router.navigate("/Product", scanner);
                        break;
                    case"2":
                        // TODO: navigate to cart
                        break;
                    case "3":
                        // TODO navigate to past orders
                        break;
                    case "4":
                        // TODO navigate to review
                        break;
                    case "x":
                        break exit;
                    default:
                        clearScreen();
                        System.out.println("Invalid option selected");
                        System.out.print("Press enter to continue...");
                        scanner.nextLine();
                        break;
                }
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