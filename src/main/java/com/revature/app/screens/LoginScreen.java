package com.revature.app.screens;

import com.revature.app.models.Session;
import com.revature.app.models.User;
import com.revature.app.services.RouterService;
import com.revature.app.services.UserService;
import com.revature.app.utils.custom_exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.out;

@AllArgsConstructor
public class LoginScreen implements IScreen {
    private final UserService userService;
    private final RouterService routerService;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String username = "";
        String password = "";

        exit: {

            while (true) {
                clearScreen();
                out.println("Welcome to login screen!");

                username = getUsername(scanner);
                password = getPassword(scanner);

                User user = userService.login(username, password);
                session.setSession(user);
                routerService.navigate("/browse", scanner);

                break exit;
            }

        }

    }

    public String getUsername(Scanner scanner) {

        out.println("\nEnter a username");
        return scanner.nextLine();
    }

    public String getPassword(Scanner scanner) {
        out.println("\nEnter a password: ");
        return scanner.nextLine();
    }

    private void clearScreen() {
        out.print("\033[H\033[2J");
        out.flush();
    }

}
