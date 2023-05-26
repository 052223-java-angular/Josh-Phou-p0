package com.revature.app.screens;

import com.revature.app.models.Session;
import com.revature.app.models.User;
import com.revature.app.services.RouterService;
import com.revature.app.services.UserService;
import lombok.AllArgsConstructor;

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
                out.println("Logging in...");

                username = getUsername(scanner);
                password = getPassword(scanner);

                User user = userService.login(username, password);

                if (user == null) {
                    out.println("\nUsername or password was invalid.");
                    out.println("Press any key to try again (x to cancel)...");
                    String option = scanner.nextLine();

                    // when key x, return the user to the home screen
                    if (option.equalsIgnoreCase("x")) {
                        clearScreen();
                        routerService.navigate("/home", scanner);
                    } 

                    // else return top of loop and try again
                    continue;

                }

                // found the user; store user the session object and then route to the browse screen
                session.setSession(user);
                routerService.navigate("/browse", scanner);

                break exit;
            }

        }

    }

    /*
     * --------------------  HELPER METHODS ------------------------------
     * */

    public String getUsername(Scanner scanner) {

        out.println("\nEnter a username; ");
        return scanner.nextLine();
    }

    public String getPassword(Scanner scanner) {

        out.println("\nEnter your password: ");
        return scanner.nextLine();
    }

    private void clearScreen() {
        out.print("\033[H\033[2J");
        out.flush();
    }

}
