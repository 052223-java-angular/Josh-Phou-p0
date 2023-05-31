package com.revature.app.screens;

import com.revature.app.models.Session;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import static java.lang.System.out;

@AllArgsConstructor
public class HomeScreen implements IScreen {

    private static final Logger logger = LogManager.getLogger(HomeScreen.class);

    private final RouterService routerService;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        logger.info("Entering home screen");

        String input = "";

        while (true) {

            out.println("\n[1] Register");
            out.println("[2] Login");
            out.println("[x] Exit");

            out.print("\nEnter: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "1":
                    routerService.navigate("/register", scanner);
                    break;
                case "2":
                    routerService.navigate("/login", scanner);
                    break;
                case "x":
                    out.println("Have a good day!");
                    System.exit(0);
                    return;
                default:
                    clearScreen();
                    out.println("Invalid option");
                    out.print("\nPress enter to continue...");
                    scanner.nextLine();
                    break;
            }

        }

    }

    /*
    * ------------------------  Helper methods ------------------------
    */

    /* clears the console / terminal screen */
    private void clearScreen() {

        // part \033[H moves cursor to top left corner
        // part \033[2J clears screen from cursor to end of screen
        out.print("\033[H\033[2J");

        // resets the cursor position to the top of the screen
        out.flush();
    }


}