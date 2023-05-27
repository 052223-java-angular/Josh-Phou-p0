package com.revature.app.screens;

import com.revature.app.models.Session;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

import static java.lang.System.out;

@AllArgsConstructor
public class CheckoutScreen implements IScreen {

    private final RouterService routerService;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String userSelection = "";

        exit: {

            while (true) {

                clearScreen();
                out.println("Welcome to the checkout screen! \n" + session.toString());
                out.println("\nWhat would you like to do?");
                out.println("[1] View order");
                out.println("[x] Return to store front\n");

                userSelection = scanner.nextLine();

                switch (userSelection) {
                    case "1":
                        // todo implement service methods for getting orders from db
                        out.println("The contents of the order ...");
                        break;
                    case "2":
                        // todo - possibly add option to delete order
                        break;
                    case "x":
                        // return the user back to the storefront
                        routerService.navigate("/storefront", scanner);
                        break;
                    default:

                }


                break exit;
            }
        }


    }

    /* clears the console / terminal screen */
    private void clearScreen() {

        // part \033[H moves cursor to top left corner
        // part \033[2J clears screen from cursor to end of screen
        out.print("\033[H\033[2J");

        // resets the cursor position to the top of the screen
        out.flush();
    }

}
