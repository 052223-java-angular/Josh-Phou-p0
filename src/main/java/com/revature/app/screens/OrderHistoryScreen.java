package com.revature.app.screens;

import com.revature.app.models.Order;
import com.revature.app.models.Session;
import com.revature.app.services.OrderService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

@AllArgsConstructor
public class OrderHistoryScreen implements IScreen {
    private static final Logger logger = LogManager.getLogger(OrderHistoryScreen.class);

    private final OrderService orderService;

    private final RouterService routerService;

    private Session session;

    @Override
    public void start(Scanner scanner) {

        while (true) {

            out.println("What would you like to do " + session.getUsername() + "?");
            out.println("[1] View current cart");
            out.println("[2] View completed orders");
            out.println("[x] or press enter to go back to the storefront");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    viewCurrentOrder(scanner);
                    continue;
                }
                case "2" -> {
                    viewCompletedOrders(scanner);
                    continue;
                } default -> {
                    routerService.navigate("/storefront", scanner);
                }
            }
            break;
        }
    }

    /*
     * ------------------------  Screen helper methods ------------------------
     */

    /* Fetches orders that are currently pending / open and displays them
    *
    * @param scanner scans for user input
    * */
    private void viewCurrentOrder(Scanner scanner) {
        logger.info("Entering into viewCurrentOrder(Scanner scanner)");

        List<Order> orderItems = orderService.findOrderByUserId(session.getId());

        // filter results to only include products having a status value of "0" i.e. open / pending
        orderItems = orderItems.stream()
                .filter(item -> item.getStatus().equalsIgnoreCase("0"))
                .toList();

        if (orderItems.size() > 0) {

            clearScreen();
            displayOrderItems(orderItems);
            displayOrderTotal(orderItems);
        } else {
            out.println("Unable to find a current cart");
        }
        out.println("Press enter key to continue...");
        scanner.nextLine();
    }

    /* Fetches orders that are currently complete / closed and displays them
     *
     * @param scanner scans for user input
     * */
    private void viewCompletedOrders(Scanner scanner) {
        logger.info("Entering into viewCompletedOrders(Scanner scanner)");

        List<Order> orderItems = orderService.findOrderByUserId(session.getId());

        if (orderItems.size() > 0) {
            clearScreen();

            // filter results to only include products having a status value of "2", i.e. complete
            orderItems = orderItems.stream()
                    .filter(item -> item.getStatus().equalsIgnoreCase("2"))
                    .toList();

            displayOrderItems(orderItems);
            displayOrderTotal(orderItems);
        } else {
            out.println("Unable to find any orders");
        }
        out.println("Press enter key to continue...");
        scanner.nextLine();
    }

    /*
     * ------------------------  Display helper methods ------------------------
     */


    /* Displays a formatted contents of the items / products in the list
     *
     * @param orderItems a list of items in an order
     *
     * */
    private void displayOrderItems(List<Order> orderItems) {
        logger.info("Entering displayOrderItems(List<Order> orderItems)");

        // Display the items in the order
        orderItems.stream()
                .sorted(Comparator.comparing(Order::getOrderId))
                .forEach(item -> {
                    out.format("OrderId: %s: \tProduct #: %s \tName: %s \tOrder qty: %s \tPrice \\pc: $ %s \tTotal Cost $ %s%n",
                    item.getOrderId(),
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    toDouble(item.getProduct().getPrice()) * toDouble(item.getQuantity()));
        });

    }

    /* Sums up the product price and order quantity and displays the value as the order total
     *
     * @param orderItems a list of items in an order
     *
     * */
    private void displayOrderTotal(List<Order> orderItems) {
        logger.info("Entering displayOrderTotal(List<Order> orderItems)");

        double total = orderItems.stream()
                .mapToDouble(e -> toDouble(e.getQuantity()) * toDouble(e.getProduct().getPrice()))
                .sum();

        out.format("Total spent: $ %s%n", total);
    }

    /* Converts String value to double
     * */
    private Double toDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            out.println(e.getLocalizedMessage());
        }
        return 0.00;
    }

    /* Converts String value to int
     * */
    private int toInt(String value) throws NumberFormatException {
        return Integer.parseInt(value);
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
