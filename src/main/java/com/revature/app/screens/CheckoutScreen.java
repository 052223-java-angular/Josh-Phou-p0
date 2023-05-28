package com.revature.app.screens;

import com.revature.app.models.Order;
import com.revature.app.models.Session;
import com.revature.app.services.OrderService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

@AllArgsConstructor
public class CheckoutScreen implements IScreen {
    private static final Logger logger = LogManager.getLogger(CheckoutScreen.class);
    private final OrderService orderService;
    private final RouterService routerService;
    private Session session;

    @Override
    public void start(Scanner scanner) {

        while (true) {
            logger.info("Entering CheckoutScreen start(Scanner scanner) method.");

            clearScreen();
            out.println("Welcome to the checkout screen! \n" + session.getUsername());
            out.println("\nWhat would you like to do?");
            out.println("[1] View order");
            out.println("[x] Return to store front\n");

            logger.info("Collecting input from the user.");
            String userSelection = scanner.nextLine();

            if (userSelection.equalsIgnoreCase("1")) {
                logger.info("Case 1 - view order: calling userService method findOrderByUserId().");

                List<Order> orderItems = orderService.findOrderByUserId(session.getId(), OrderService.ORDER_STATUS.PENDING);

                if (orderItems.size() <= 0) {
                    logger.info("User has no open orders, routing user back to storefront.");
                    clearScreen();
                    out.println("\nYou have no orders, press any key to return to the store front");
                    scanner.nextLine();
                    routerService.navigate("/storefront", scanner);
                    break;
                }

                logger.info("Order list > 0, displaying order items and total.");

                out.println("The items in the order ...");
                displayOrderItems(orderItems);
                displayOrderTotal(orderItems);

                // prompt for action, checkout, modify or clear order
                out.println("\nWhat would you like to do next?");
                out.println("[1] Update quantity");
                out.println("[2] Remove product");
                out.println("[3] Delete order");
                out.println("[4] Checkout");
                out.println("Press any other key to go back to the store front");

                logger.info("Prompting for user input on what to do next with order items.");
                userSelection = scanner.nextLine();
                switch (userSelection) {
                    case "1":
                        logger.info("User selected case 1: begin process of updating product quantity.");
                        clearScreen();
                        updateQuantity(scanner, orderItems);
                        break;
                    case "2":
                        logger.info("User selected case 2: begin process of removing product from order.");
                        clearScreen();
                        removeProductFromOrder(scanner, orderItems);
                        break;
                    case "3":
                        logger.info("User selected case 3: begin process of deleting an order.");
                        clearScreen();
                        deleteOrder(scanner, orderItems);
                        break;
                    case "4":
                        logger.info("User selected case 4: begin process of checking out.");
                        clearScreen();
                        checkout(scanner, orderItems, session.getUsername());
                        break;
                    default:
                        logger.info("User selected case 5: routing user to storefront.");
                        routerService.navigate("/storefront", scanner);
                        break;
                }

            } else {
                // return the user back to the storefront
                routerService.navigate("/storefront", scanner);
                break;
            }

        }

    }

    /*
     * ------------------------  Menu Option Helper methods ------------------------
     */


    /* Updates the product quantity for an order
    *
    * @param scanner scans for user input
    * @param orderItems a list of items in an order
    *
    * */
    private void updateQuantity(Scanner scanner, List<Order> orderItems) {

        out.println("\nWhich item would you like to update?");
        AtomicInteger itemNum = new AtomicInteger(1);
        orderItems.forEach(item -> {
            out.format("[%s]: \t%s%n",
                    itemNum.getAndIncrement(),
                    item.getProduct().getName());
        });
        String itemToUpdate = scanner.nextLine();
        out.println("[1] Increase +");
        out.println("[2] Decrease -");
        out.println("Any key to go back");
        String quantityOpt = scanner.nextLine();

        switch (quantityOpt) {
            case "1":
                // when qty greater than 1, increase
                int result = orderService.updateProductOrderQuantity(
                        true,
                        orderItems.get(toInt(itemToUpdate) - 1).getOrderId(),
                        orderItems.get(toInt(itemToUpdate) - 1).getProductId());
                if (result >= 1) {
                    out.format("%nIncreased product order quantity for %s by 1.%n", orderItems.get(toInt(itemToUpdate) - 1).getProduct().getName());
                } else {
                    out.format("Product %s was not found for order %s, unable to increase product qty%n",
                            orderItems.get(toInt(itemToUpdate) - 1).getOrderId(),
                            orderItems.get(toInt(itemToUpdate) - 1).getProductId());
                }
                break;
            case "2":
                // when qty equal or less than zero, confirm delete
                result = orderService.updateProductOrderQuantity(
                        false,
                        orderItems.get(toInt(itemToUpdate) - 1).getOrderId(),
                        orderItems.get(toInt(itemToUpdate) - 1).getProductId());
                if (result >= 1) {
                    out.format("%nDecreased product order quantity for %s by 1.%n", orderItems.get(toInt(itemToUpdate) - 1).getProduct().getName());
                } else {
                    out.format("%nUnable to increase quantity; not enough %s on hand%n",
                            orderItems.get(toInt(itemToUpdate) - 1).getProduct().getName());
                }
                break;
            default:
                clearScreen();
                out.println("Invalid option - press any key to return to checkout");
                break;
        }


    }


    /* Removes a product from an order
    *
    * @param scanner scans for user input
    * @param orderItems a list of items in an order
    *
    * */
    private void removeProductFromOrder(Scanner scanner, List<Order> orderItems) {

        out.println("\nWhich item would you like to remove?");
        AtomicInteger itemNum = new AtomicInteger(1);
        orderItems.forEach(item -> {
            out.format("[%s]: \t%s%n",
                    itemNum.getAndIncrement(),
                    item.getProduct().getName());
        });
        out.println("[x] Go back");
        String itemToRemove = scanner.nextLine();

        // when selected option is not "x" and less than products in orderItems
        if (!itemToRemove.equalsIgnoreCase("x") &&
                toInt(itemToRemove) - 1 < orderItems.size()) {

            // remove the product from the order
            int result = orderService.removeProductFromOrder(
                    orderItems.get(toInt(itemToRemove)-1).getOrderId(),
                    orderItems.get(toInt(itemToRemove)-1).getProductId());

            if (result == 0) {
                out.format("Product %s was not found for order %s, unable to remove%n",
                        orderItems.get(toInt(itemToRemove)-1).getOrderId(),
                        orderItems.get(toInt(itemToRemove)-1).getProductId());
            }
        }

    }


    /* Deletes the entire order
     *
     * @param scanner scans for user input
     * @param orderItems a list of items in an order
     *
     * */
    private void deleteOrder(Scanner scanner, List<Order> orderItems) {
        out.println("\nDelete your order?");
        out.println("Press any key to confirm (x to cancel)");
        String opt = scanner.nextLine();

        // when selected option is not "x" and less than products in orderItems
        if (!opt.equalsIgnoreCase("x")) {
            // remove the product from the order
            int result = orderService.deleteOrder(orderItems.get(0).getOrderId());
            if (result == 0) {
                out.format("%nUnable to delete the order having order_id %s because it was not found.",
                        orderItems.get(0).getOrderId());
            }
        }

    }


    /* Checkout, process and change the status of an order
     *
     * @param scanner scans for user input
     * @param orderItems a list of items in an order
     * @param username the current users username
     *
     * */
    private void checkout(Scanner scanner, List<Order> orderItems, String username) {
        out.println("\nChecking out...");
        out.println("Press any key to continue (x to cancel)");
        String opt = scanner.nextLine();

        // when selected option is not "x" and less than products in orderItems
        if (!opt.equalsIgnoreCase("x")) {

            // todo extra feature -> add shipping, payment and new tables to store the address data
            // collect payment info, address, name
            // checkout and persist order by changing order status
            // checkout and change order status to complete
            orderService.checkout(orderItems);
            out.println("Checkout complete - thank you for your order " + username);
            out.println("Press any key to continue...");
            scanner.nextLine();
        }

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

        // Display the items in the order
        out.format("Order #: %s%n", orderItems.get(0).getOrderId());
        AtomicInteger itemNum = new AtomicInteger(1);
        orderItems.stream()
                .sorted((a, b) -> a.getOrderId().compareTo(b.getOrderId()))
                .forEach(item -> {
                    out.format("Item [%s]: \tOrder #: %s \tProduct #: %s \tName: %s \tOrder qty: %s \tPrice \\pc: $ %s \tTotal Cost $ %s \tOn Hand: %s%n",
                    itemNum.getAndIncrement(),
                    item.getOrderId(),
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    String.valueOf(toDouble(item.getProduct().getPrice()) * toDouble(item.getQuantity())),
                    item.getProduct().getOnHand());
        });

    }


    /* Sums up the product price and order quantity and displays the value as the order total
     *
     * @param orderItems a list of items in an order
     *
     * */
    private void displayOrderTotal(List<Order> orderItems) {
        double total = 0.00;
        for (Order item : orderItems) {
            total += toDouble(item.getProduct().getPrice()) * toDouble(item.getQuantity());
        }
        out.format("Order Total: $ %s%n", total);
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
