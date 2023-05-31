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

        while (true) {
            clearScreen();
            out.println("Welcome to the -Company name- eStore " + session.getUsername() + "!");
            out.println("[1] Products");
            out.println("[2] View cart / orders");
            out.println("[3] Checkout");
            out.println("[4] Leave a product review");
            out.println("[x] Return to the login/register screen");
            // out.println("[q] Logout and exit");


            out.print("\nEnter: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()){
                case "1":
                    router.navigate("/products", scanner);
                    continue;
                case "2":
                    router.navigate("/orders", scanner);
                    continue;
                case "3":
                    router.navigate("/checkout", scanner);
                    continue;
                case "4":
                    router.navigate("/review", scanner);
                    continue;
                case "x":
                    session = new Session();
                    router.navigate("/home", scanner);
                    break;
                case "q":
                    out.println("Have a good day!");
                    return;
                default:
                    clearScreen();
                    out.println("Invalid option selected");
                    out.print("Press enter to continue...");
                    scanner.nextLine();
                    continue;

                }
            break;
        }

    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        out.print("\033[H\033[2J");
        out.flush();
    }
}