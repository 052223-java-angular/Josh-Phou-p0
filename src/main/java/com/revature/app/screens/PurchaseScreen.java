package com.revature.app.screens;

import com.revature.app.models.Order;
import com.revature.app.models.Product;
import com.revature.app.models.Session;
import com.revature.app.services.ProductService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@AllArgsConstructor
public class PurchaseScreen implements IScreen {
    private final ProductService productService;
    private final RouterService routerService;
    private Session session;
    String category;

    @Override
    public void start(Scanner scanner) {
        String input = "";
        scanner.nextLine();
        int choice = 0;
        List<Product> items = getProducts(category);

        Order order = new Order();
        Optional<Order> getOrder = productService.retrieveOrder(session.getId());

        //if pending order exist get order if not make new
        if (getOrder.isEmpty()) {
            order.setOrderId(createOrderUUID());
        }else{
            //unbox optional
            order = getOrder.get();
        }

        exit:{
            while (true) {
                clearScreen();
                System.out.println(category + " Catalog\n");

                for (int i = 0; i < items.size(); i++) {
                    onHandUpdater(items, i, order.getQuantity());
                    System.out.println("[" + (i+1) + "] "
                            + items.get(i).getName() + " for $"
                            + items.get(i).getPrice() + " On hand: "
                            + items.get(i).getOnHand() );
                }

                System.out.println("[x] Return to menu");
                System.out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()) {

                    case "1", "2", "3", "4":
                        //parse input -1 for array index
                        int num = Integer.parseInt(input) - 1;
                        System.out.println(items.get(num).getName() + " for " + items.get(num).getPrice());
                        System.out.println("How many would you like to purchase?");
                        //try block for non-int inputs
                        try{
                            choice = scanner.nextInt();
                        }
                        catch (NumberFormatException n){
                            System.out.println("Invalid input");
                            break exit;
                        }

                        //check if sufficient on hand
                        if (choice <= items.get(num).getOnHand() && choice > 0){
                            int remains = items.get(num).getOnHand() - choice;
                            items.get(num).setOnHand(remains);

                            //check if already in cart
                            boolean inCart = isInCart(items.get(num).getId());
                            if(inCart) {
                                //if in cart change quantity
                                quantityUpdate(items.get(num).getId(), order.getOrderId(), choice);
                            }else{
                                //if not in cart create new order
                                order.setId(createOrderUUID());
                                order.setStatus("0");
                                order.setQuantity(Integer.toString(choice));
                                order.setUserId(session.getId());
                                order.setProductId(items.get(num).getId());

                                addToOrder(order);
                            }


                        }
                        else {
                            System.out.println("Invalid input");
                        }
                        break exit;
                    case "x":
                        routerService.navigate("/storefront", scanner);
                        break;
                    default:
                        clearScreen();
                        System.out.println("Invalid option selected");
                        System.out.print("Press enter to continue...");
                        scanner.nextLine();
                        break;
                }
            }
        }
    }

    /*--------------------------Methods-------------------------------------*/

    public List<Product> getProducts(String name) {
        List<Product> items = this.productService.findByCategory(name);
        return items;
    }

    public boolean isInCart (String id) {
        boolean check = productService.inCartCheck(id, session.getId());

        return check;
    }

    public Optional<Order> getOrder(String id) {
        Optional<Order> currentOrder = productService.retrieveOrder(id);
        if (currentOrder.isEmpty())
        {
            return Optional.empty();
        }
        return currentOrder;
    }

    public String createOrderUUID() {
        String uuid= String.valueOf(UUID.randomUUID());
        return uuid;
    }

    public void onHandUpdater(List<Product> items, int i, String ordered) {
        int onHand = items.get(i).getOnHand();
        int inOrder = Integer.parseInt(ordered);

        onHand -= inOrder;

        items.get(i).setOnHand(onHand);
    }

    public void addToOrder (Order order) {

        productService.addToOrder(order);
    }

    public void quantityUpdate (String productId, String orderId, int quantity) {
        productService.updateOnHand(productId,orderId,session.getId(),quantity);
    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}