package com.revature.app.screens;

import com.revature.app.models.User;
import com.revature.app.services.UserService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

import static java.lang.System.out;

@AllArgsConstructor
public class RegisterScreen implements IScreen {
    private final UserService userService;

    @Override
    public void start(Scanner scanner) {
        String username = "";
        String password = "";

        exit: {
            while (true) {
                clearScreen();
                out.println("Welcome to the register screen!");

                // get username
                username = getUsername(scanner);

                if (username.equals("x")) {
                    break exit;
                }

                // get password
                password = getPassword(scanner);

                if (password.equals("x")) {
                    break exit;
                }

                // confirm user's info
                clearScreen();
                out.println("Please confirm your credentials:");
                out.println("\nUsername: " + username);
                out.println("Password: " + password);
                out.print("\nEnter (y/n): ");

                switch (scanner.nextLine()) {
                    case "y":
                        User createdUser = userService.register(username, password);
//                        session.setSession(createdUser);
//                        router.navigate("/menu", scanner);
                        break exit;
                    case "n":
                        clearScreen();
                        out.println("Restarting process...");
                        out.print("\nPress enter to continue...");
                        scanner.nextLine();
                        break;
                    default:
                        clearScreen();
                        out.println("Invalid option!");
                        out.print("\nPress enter to continue...");
                        scanner.nextLine();
                        break;
                }

            }
        }

    }

    public String getUsername(Scanner scanner) {
        String username = "";

        while (true) {
            out.print("\nEnter a username (x to cancel): ");
            username = scanner.nextLine();

            if (username.equalsIgnoreCase("x")) {
                return "x";
            }

            if (!userService.isValidUsername(username)) {
                clearScreen();
                out.println("Username needs to be 8-20 characters long.");
                out.print("\nPress enter to continue...");
                scanner.nextLine();
                continue;
            }

            if (!userService.isUniqueUsername(username)) {
                clearScreen();
                out.println("Username is not unique!");
                out.print("\nPress enter to continue...");
                scanner.nextLine();
                continue;
            }

            break;
        }

        return username;
    }

    public String getPassword(Scanner scan) {
        String password = "";
        String confirmPassword = "";

        while (true) {
            out.print("\nEnter a password (x to cancel): ");
            password = scan.nextLine();

            if (password.equalsIgnoreCase("x")) {
                return "x";
            }

            if (!userService.isValidPassword(password)) {
                clearScreen();
                out.println("Password needs to be minimum 8 characters, at least 1 letter and 1 number");
                out.print("\nPress enter to continue...");
                scan.nextLine();
                continue;
            }

            out.print("\nPlease confirm password (x to cancel): ");
            confirmPassword = scan.nextLine();

            if (confirmPassword.equalsIgnoreCase("x")) {
                return "x";
            }

            if (!userService.isSamePassword(password, confirmPassword)) {
                clearScreen();
                out.println("Passwords do not match");
                out.print("\nPress enter to continue...");
                scan.nextLine();
                continue;
            }

            break;
        }

        return password;
    }


    private void clearScreen() {
        out.print("\033[H\033[2J");
        out.flush();
    }

}