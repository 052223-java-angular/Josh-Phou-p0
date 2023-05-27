package com.revature.app.screens;

import com.revature.app.models.Order;
import com.revature.app.models.Session;
import com.revature.app.services.OrderService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

@AllArgsConstructor
public class CheckoutScreen implements IScreen {

    private final OrderService orderService;
    private final RouterService routerService;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String userSelection = "";

        exit: {

            while (true) {

                clearScreen();
                out.println("Welcome to the checkout screen! \n" + session.getUsername());
                out.println("\nWhat would you like to do?");
                out.println("[1] View order");
                out.println("[x] Return to store front\n");

                userSelection = scanner.nextLine();

                switch (userSelection) {
                    case "1":
                        out.println("The contents of the order ...");
                        List<Order> orderItems = orderService.findAllOrdersByUserId(session.getId());

                        // Display the items in the order
                        out.format("Order#: %s%n", orderItems.get(0).getOrderId());
                        AtomicInteger itemNum = new AtomicInteger(1);
                        orderItems.forEach(item -> {
                            out.format("Item [%s]: \tProduct#: %s \tName: %s \tPrice: %s \tOn Hand: %s%n",
                                    itemNum.getAndIncrement(),
                                    item.getProduct().getId(),
                                    item.getProduct().getName(),
                                    item.getProduct().getPrice(),
                                    item.getProduct().getOnHand());
                        });

                        // prompt for action, checkout, modify or clear order



                        scanner.nextLine();

                        continue;
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
