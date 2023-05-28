package com.revature.app.screens;

import com.revature.app.models.Session;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static java.lang.System.out;

@AllArgsConstructor
public class StoreFront implements IScreen {
    private static final Logger logger = LogManager.getLogger(StoreFront.class);

    private final RouterService router;

    private Session session;

    @Override
    public void start(Scanner scanner) {
        String input = "";

        exit: {
            while (true) {
                clearScreen();
                out.println("Welcome to the -Company name- eStore\n");
                out.println("[1] Select a product category");
//                out.println("[2] View cart");
                out.println("[3] View cart / orders");
                out.println("[4] Leave a product review");
                out.println("[5] Checkout - temporary option / placeholder");
                out.println("[x] Exit");

                out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()){
                    case "1":
                        router.navigate("/product", scanner);
                        break;
//                    case"2":
//                        router.navigate("/cart", scanner);
//                        break;
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
                        out.println("Invalid option selected");
                        out.print("Press enter to continue...");
                        scanner.nextLine();
                        break;
                }
            }
        }
    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        out.print("\033[H\033[2J");
        out.flush();
    }
}