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
                        List<Order> orderItems = orderService.findOrderByUserId(session.getId(), OrderService.ORDER_STATUS.PENDING);

                        if (orderItems.size() > 0) {
                            out.println("The items in the order ...");
                            displayOrderItems(orderItems);
                            displayOrderTotal(orderItems);

                            // prompt for action, checkout, modify or clear order
                            out.println("\nWhat would you like to do next?");
                            out.println("[1] Update quantity");
                            out.println("[2] Remove product");
                            out.println("[3] Delete order");
                            out.println("[4] Checkout");
                            out.println("[5} Return to store front");

                            userSelection = scanner.nextLine();
                            switch (userSelection) {
                                case "1":
                                    clearScreen();
                                    updateQuantity(scanner, orderItems);
                                    break;
                                case "2":
                                    clearScreen();
                                    removeProductFromOrder(scanner, orderItems);
                                    break;
                                case "3":
                                    clearScreen();
                                    deleteOrder(scanner, orderItems);
                                    break;
                                case "4":
                                    clearScreen();
                                    checkout(scanner, orderItems, session.getUsername());
                                    break;
                                case "5":
                                    routerService.navigate("/storefront", scanner);
                                    break;
                                default:
                            }

                        } else {
                            clearScreen();
                            out.println("\nYou have no orders, press any key to return to the store front");
                            scanner.nextLine();
                            routerService.navigate("/storefront", scanner);
                            break;
                        }

                        continue;
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

    /*
     * ------------------------  Menu Option Helper methods ------------------------
     */

    /* Updates the order quantity of a product
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
        out.println("[x] Go back");
        String quantityOpt = scanner.nextLine();

        switch (quantityOpt) {
            case "1":
                // when qty greater than 1, increase
                orderService.updateProductOrderQuantity(
                        true,
                        orderItems.get(toInt(itemToUpdate) - 1).getOrderId(),
                        orderItems.get(toInt(itemToUpdate) - 1).getProductId());
                break;
            case "2":
                // when qty equal or less than zero, confirm delete
                orderService.updateProductOrderQuantity(
                        false,
                        orderItems.get(toInt(itemToUpdate) - 1).getOrderId(),
                        orderItems.get(toInt(itemToUpdate) - 1).getProductId());
                break;
            case "x":
                break;
            default:
                clearScreen();
                out.println("Invalid option - press any key to return to checkout");
                break;
        }


    }

    /* Removes a product from the order
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
            orderService.removeProductFromOrder(
                    orderItems.get(toInt(itemToRemove)-1).getOrderId(),
                    orderItems.get(toInt(itemToRemove)-1).getProductId());
        }

    }

    /* Deletes the entire order
    * */
    private void deleteOrder(Scanner scanner, List<Order> orderItems) {
        out.println("\nDelete your order?");
        out.println("Press any key to confirm (x to cancel)");
        String opt = scanner.nextLine();

        // when selected option is not "x" and less than products in orderItems
        if (!opt.equalsIgnoreCase("x")) {
            // remove the product from the order
            orderService.deleteOrder(orderItems.get(0).getOrderId());
        }

    }

    /* Checkout and process the order
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
     * ------------------------  Helper methods ------------------------
     */


    /* Display the formatted order contents of the list
    *
    * */
    private void displayOrderItems(List<Order> orderItems) {

        // Display the items in the order
        out.format("Order #: %s%n", orderItems.get(0).getOrderId());
        AtomicInteger itemNum = new AtomicInteger(1);
        orderItems.forEach(item -> {
            out.format("Item [%s]: \tProduct#: %s \tName: %s \tOrder qty: %s \tPrice: %s \tOn Hand: %s%n",
                    itemNum.getAndIncrement(),
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    item.getProduct().getOnHand());
        });

    }

    /* Sums up and displays the order total
    *  */
    private void displayOrderTotal(List<Order> orderItems) {
        double total = 0.00;
        for (Order item : orderItems) {
            total += toDouble(item.getProduct().getPrice()) * toDouble(item.getQuantity());
        }
        out.format("Order Total: $%s%n", total);
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
