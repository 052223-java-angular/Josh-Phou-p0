package com.mycompany.app.screens;

import java.util.Scanner;

import static java.lang.System.out;

public class HomeScreen implements IScreen {

    @Override
    public void start(Scanner scanner) {
        String input = "";

        exit: {
            while (true) {

                out.println("Welcome to e-shop!");
                out.println("\n[1] Login");
                out.println("[2] Register");
                out.println("[3] Browse");
                out.println("[x] Exit");

                out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()) {
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "x":
                        break;
                    default:
                        clearScreen();
                        out.println("Invalid option");
                        out.print("\nPress enter to continue...");
                        scanner.nextLine();
                        break;
                }

                break exit;
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
