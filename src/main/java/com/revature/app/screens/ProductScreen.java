package com.revature.app.screens;

import com.revature.app.models.Order;
import com.revature.app.models.Product;
import com.revature.app.models.Session;
import com.revature.app.services.ProductService;
import com.revature.app.services.RouterService;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@AllArgsConstructor
public class ProductScreen implements IScreen {
    private final ProductService productService;
    private final RouterService router;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String input;
        int quantity;

        while (true) {
            clearScreen();
            System.out.println("Welcome to the product catalog\n");
            System.out.println("[1] Choose a product category");
            System.out.println("[2] Search by product name");
            System.out.println("[3] Search for a product by price");
            System.out.println("[x] Return to menu");

            System.out.print("\nEnter: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "1":
                    clearScreen();
                    //Select category prompt
                    System.out.println("Please select a category: ");
                    input = categoryLoader(scanner);
                    //create product list
                    List<Product> items = getProducts(input);
                    //display list and select
                    clearScreen();
                    String index = getIndex(items, scanner);
                    String pId = getPId(items, index);
                    quantity = getQuantity(items, index, scanner);
                    //check if there is a cart fetch/yes create/no
                    Order order = getOrder();
                    System.out.println(order);
                    //check if item is in cart
                    boolean check = productInCart(pId);
                    //in cart/update quantity not/create entry
                    if (check) {
                        quantityUpdate(pId, quantity);
                    }else {
                        createOrder(order, Integer.toString(quantity), pId);
                        addToOrder(order);
                    }
                    //return to selection screen
                    break;
                case"2":
                    System.out.println("\nEnter a product name: ");
                    String name = scanner.nextLine();
                    getProductByName(name);
                    System.out.println("\nEnter quantity to add to cart or [x] to go back.");
                    input = scanner.nextLine();
                    addToCart(name, input);
                    break;
                case "3":
                    System.out.println("Enter min product price");
                    input = scanner.nextLine();
                    System.out.println("Enter max product price");
                    input = scanner.nextLine();
                    break;
                case "x":
                    router.navigate("/storefront", scanner);
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

    /*------------------------------Methods----------------------------------*/

    public List<Product> getProducts(String name) {
        List<Product> items = this.productService.findByCategory(name);
        return items;
    }

    public String categoryLoader(Scanner scanner) {
        System.out.println("\n[1] Product CategoryA");
        System.out.println("[2] Product CategoryB");
        System.out.println("[3] Product CategoryC");
        System.out.println("[4] Product CategoryD");

        System.out.print("\nEnter: ");
        while(true) {
            switch (scanner.nextLine().toLowerCase()) {
                case "1":
                    return "CategoryA";
                case "2":
                    return "CategoryB";
                case "3":
                    return "CategoryC";
                case "4":
                    return "CategoryD";
                default:
                    clearScreen();
                    System.out.println("Invalid option selected");
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
            }
        }
    }

    public String getIndex(List<Product> items, Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("Select a product:\n");
            for (int i = 0; i < items.size(); i++) {
                System.out.println("[" + (i + 1) + "] "
                        + items.get(i).getName() + " for $"
                        + items.get(i).getPrice() + " On hand: "
                        + items.get(i).getOnHand());
            }
            System.out.println("\nEnter: ");

            while (true) {
                String input = scanner.nextLine();
                switch (input.toLowerCase()) {
                    case "1", "2", "3", "4":
                        return input;
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

    public String getPId(List<Product> items, String i) {
           int index = Integer.parseInt(i) - 1 ;
           return items.get(index).getId();
    }

    public int getQuantity (List<Product> items, String input, Scanner scanner) {
        int quantity = 0;
        while(true) {
            switch (input) {
                case "1", "2", "3", "4":
                    int num = Integer.parseInt(input) - 1;
                    System.out.println(items.get(num).getName() + " for " + items.get(num).getPrice());
                    System.out.println("\nAmount to purchase: ");

                    try {
                        quantity = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid option selected-----quantity");
                        System.out.print("Press enter to continue...");
                        scanner.nextLine();
                    }
                    if (quantity <= Integer.parseInt(items.get(num).getOnHand()) && quantity > 0) {
                        return quantity;
                    }
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

    public Order getOrder() {
        Optional<Order> currentOrder = productService.retrieveOrder(session.getId());
        if (currentOrder.isEmpty()) {
            Order newOrder = new Order();
            newOrder.setOrderId(createOrderUUID());
            return newOrder;
        }else{
            //unbox optional
            Order fetchOrder = currentOrder.get();
            return fetchOrder;
        }
    }

    public boolean productInCart (String pId) {
        boolean check;
        check = this.productService.inCartCheck(pId, session.getId());

        if(check) {
            return true;
        }
        return false;
    }

    public String createOrderUUID() {
        String uuid= String.valueOf(UUID.randomUUID());
        return uuid;
    }

    public void quantityUpdate (String productId, int quantity) {
        String quant = String.valueOf(quantity);
        productService.updateOnHand(productId,session.getId(),quant);
    }

    public void createOrder(Order order, String quantity, String pId ) {
        order.setId(createOrderUUID());
        order.setStatus("0");
        order.setQuantity(quantity);
        order.setUserId(session.getId());
        order.setProductId(pId);
    }

    public void addToOrder (Order order) {
        this.productService.addToOrder(order);
    }

    public void getProductByName(String name) {
        Optional<Product> search = this.productService.findByName(name);
        if ( search.isEmpty() ) {
            System.out.println("Product does not exist in database");
        }
        Product product = search.get();
        System.out.println(product.getName() + " costs " + product.getPrice() + " and there are [" + product.getOnHand() + "] currently on hand");
    }

    public void addToCart(String name, String quantity) {
        Optional<Product> getProduct = this.productService.findByName(name);
        Order order = getOrder();
        Product product = getProduct.get();
        if (Integer.parseInt(product.getOnHand())>= Integer.parseInt(quantity) && Integer.parseInt(quantity) > 0) {
            boolean check = productInCart(product.getId());
            if (check) {
                quantityUpdate(product.getId(), Integer.parseInt(quantity));
            }else {
                createOrder(order, quantity, product.getId());
                addToOrder(order);
            }
        }else {
            System.out.println("Invalid entry");
        }
    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
