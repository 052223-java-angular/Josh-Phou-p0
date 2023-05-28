package com.revature.app.screens;

import com.revature.app.models.Session;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class StoreFront implements IScreen {
    private final RouterService router;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String input = "";
        // todo -- if this is not required, do remove so that screen flow is not interrupted
//        scanner.nextLine();

        exit: {
            while (true) {
                clearScreen();
                System.out.println("Welcome to the -Company name- eStore\n");
                System.out.println("[1] Select a product category");
                System.out.println("[2] View cart");
                System.out.println("[3] View past orders");
                System.out.println("[4] Leave a product review");
                System.out.println("[5] Checkout - temporary option / placeholder");
                System.out.println("[x] Exit");

                // todo remove, just verifying user is being passed to the session object
//                out.format("Checking the user is stored in the session object/instance: %s \n\n", session.toString());

                System.out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()){
                    case "1":
                        router.navigate("/product", scanner);
                        break;
                    case"2":
                        router.navigate("/cart", scanner);
                        break;
                    case "3":
                        router.navigate("/orders", scanner);
                        break;
                    case "4":
                        router.navigate("/review", scanner);
                        break;
                    case "5":
                        router.navigate("/checkout", scanner);
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