package com.revature.app.screens;

import com.revature.app.models.Department;
import com.revature.app.models.Product;
import com.revature.app.models.Session;
import com.revature.app.services.DepartmentService;
import com.revature.app.services.ProductService;
import com.revature.app.services.RouterService;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static com.revature.app.utils.FormatUtil.toInt;

@AllArgsConstructor
public class ProductScreen implements IScreen {
    private final ProductService productService;
    private final DepartmentService departmentService;
    private final RouterService router;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String input = "";
//        scanner.nextLine();
        // todo fetch department options from the db
        List<Department> departments = departmentService.findAll();

        // instantiate a list for storing product items, pass this list into the method
        List<Product> cart = new ArrayList<>();

        while (true) {
            clearScreen();
            System.out.println("Welcome to the product catalog\n");

            for (int i = 1; i < departments.size(); i++) {
                System.out.format("[%s] Product %s%n", i, departments.get(i-1).getName());
            }

//            System.out.println("[1] Product categoryA");
//            System.out.println("[2] Product categoryB");
//            System.out.println("[3] Product categoryC");
//            System.out.println("[4] Product categoryD");
            System.out.println("[x] Return to menu");

            System.out.print("\nEnter: ");
            input = scanner.nextLine();

            String caseOptions = "";
            for (int i = 0; i < departments.size(); i++) {
                caseOptions = caseOptions + ", " + i + 1;
            }

            if (caseOptions.contains(input)) {
                displayProducts(departments.get(toInt(input) - 1).getId(), cart, scanner);
                continue;
            } else {
                break;
            }


//            switch (input.toLowerCase()) {
//                case "1":
                    // todo review and redo - should fetch product data fpr cat_A
                    // use helper method
//                    router.productNavigate(scanner, "CategoryA");
                    // lees 1 b/c list index begins at 0
//                    displayProducts(departments.get(toInt(input) - 1).getId(), cart, scanner);
//                    continue;
//                    break;
//                case"2":
//                    router.productNavigate( scanner, "CategoryB");
//                    break;
//                case "3":
//                    router.productNavigate(scanner, "CategoryC");
//                    break;
//                case "4":
//                    router.productNavigate(scanner, "CategoryD");
//                    break;
//                case "x":
//                    router.navigate("/browse", scanner);
//                    break;
//                default:
//                    clearScreen();
//                    System.out.println("Invalid option selected");
//                    System.out.print("Press enter to continue...");
//                    scanner.nextLine();
//                    break;
//            }

            // add to db for breaking out this loop
            // todo persist cart contents here to db

//            break;
        }
    }

    private void displayProducts(String departmentId, List<Product> cart, Scanner scanner) {

        // fetch the products for the department id
        List<Product> products = productService.findByDepartmentId(departmentId);

        // print the products for the user to select
        System.out.println("Available products");
        int i = 1;
        for (Product product : products) {
            System.out.format("[%s] %s%n", i, product.toString());
            i++;
        }

        while (true) {
            System.out.println("\nWhat would you like to do next?");
            System.out.println("[1] Add product to cart");
            System.out.println("[2] Leave a review");
            System.out.println("[3] View my cart");
            System.out.println("[x] Go back");
            String choice = scanner.nextLine();
            // collect user choice, add to cart. e.g. add to cart, leave a review, back, etc

            switch (choice) {
                case "1":
                    Product product = products.get(0);
                    cart.add(new Product(product.getId(), product.getName(), product.getPrice(), product.getOnHand(), product.getDepartmentId()));
                    continue;
                case "2":
                    // todo will need review service or a method in productService to add a review
                    continue;
                case "3":
                    i = 1;
                    System.out.println("Your cart content");
                    for (Product p : cart) {
                        System.out.format("[%s] %s%n", i, p.toString());
                        i++;
                    }
                    continue;
                case "x":
                    break;
            }
            break;
        }

    }



    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
