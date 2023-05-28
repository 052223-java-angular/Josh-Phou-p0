package com.revature.app.screens;

import com.revature.app.models.Product;
import com.revature.app.models.Session;
import com.revature.app.services.ProductService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Scanner;

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

        exit:{
            while (true) {
                clearScreen();
                System.out.println(category + " Catalog\n");

                for (int i = 0; i < items.size(); i++) {
                    System.out.println("[" + (i+1) + "] " + items.get(i).getName() + " On hand: " + items.get(i).getOnHand());
                }

                System.out.println("[x] Return to menu");
                System.out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()) {

                    case "1":
                        System.out.println("How many would you like to purchase?");
                        choice = Integer.parseInt(scanner.nextLine());

                        if (choice <= items.get(1).getOnHand() && choice > 0){
                            int remains = items.get(1).getOnHand() - choice;
                            items.get(1).setOnHand(remains);

                            //TODO add to cart method status 0
                        }
                        else {
                            System.out.println("Invalid input");
                        }
                        break exit;
                    case "2":
                        System.out.println("How many would you like to purchase?");
                        choice = Integer.parseInt(scanner.nextLine());

                        if (choice <= items.get(2).getOnHand() && choice > 0){
                            int remains = items.get(2).getOnHand() - choice;
                            items.get(2).setOnHand(remains);
                        }
                        else {
                            System.out.println("Invalid input");
                        }
                        break exit;
                    case "3":
                        System.out.println("How many would you like to purchase?");
                        choice = Integer.parseInt(scanner.nextLine());

                        if (choice <= items.get(3).getOnHand() && choice > 0){
                            int remains = items.get(3).getOnHand() - choice;
                            items.get(3).setOnHand(remains);
                        }
                        else {
                            System.out.println("Invalid input");
                        }
                        break exit;
                    case "4":
                        System.out.println("How many would you like to purchase?");
                        choice = Integer.parseInt(scanner.nextLine());

                        if (choice <= items.get(4).getOnHand() && choice > 0){
                            int remains = items.get(4).getOnHand() - choice;
                            items.get(4).setOnHand(remains);
                        }
                        else {
                            System.out.println("Invalid input");
                        }
                        break exit;
                    case "x":
                        routerService.navigate("/browse", scanner);
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






    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}