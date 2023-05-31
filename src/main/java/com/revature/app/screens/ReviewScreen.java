package com.revature.app.screens;

import com.revature.app.models.Review;
import com.revature.app.models.Session;
import com.revature.app.services.ReviewService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.security.spec.ECField;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@AllArgsConstructor
public class ReviewScreen implements IScreen {
    private final ReviewService reviewService;
    private final RouterService routerService;
    private Session session;

    @Override
    public void start(Scanner scanner) {
        String input = "";

        exit:
        {
            while (true) {
                clearScreen();
                System.out.println("Product Reviews\n");
                System.out.println("[1] View a product's reviews");
                System.out.println("[2] Leave a review");
                System.out.println("[x] Exit");


                System.out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()) {
                    case "1":
                        clearScreen();
                        System.out.print("Enter the name of a product to see reviews: ");
                        input = scanner.nextLine();
                        viewReview(input);
                        System.out.print("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    case "2":
                        int rating = 0;
                        clearScreen();
                        System.out.print("Type the name of the product you would like to review: ");
                        input = scanner.nextLine();
                        boolean verify = verifyPurchase(input);
                        rating:
                        if(verify) {
                            System.out.print("Enter updated rating (1-5): ");

                            try {
                                rating = scanner.nextInt();
                            } catch (Exception e) {
                                System.out.println("Invalid entry");
                                break rating;
                            }
                            if (rating >= 1 && rating <= 5) {
                                scanner.nextLine();
                                System.out.print("Enter comment: ");
                                String comment = scanner.nextLine();
                                try {
                                    createReview(input, rating, comment);
                                }catch(Exception e) {
                                    System.out.println("You have already reviewed this product");
                                    System.out.print("Press enter to continue...");
                                    scanner.nextLine();
                                }
                            }else {
                                System.out.println("Invalid entry");
                                break rating;
                            }
                        }else {
                            System.out.println("You have not purchased this product yet.");
                        }
                        break;
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

    /*-------------------------Methods--------------------------------*/

    public void viewReview(String pName) {

        List<Review> reviews = reviewService.findByName(pName);

        System.out.println("\n=========================================");
        if( reviews == null){
            System.out.println("No reviews for " + pName);
        }

        for(int i = 0; i <reviews.size(); i++){
            System.out.println(pName
                    + "\nrating: "
                    + reviews.get(i).getRating()
                    + "\nComment: "
                    + reviews.get(i).getComment());
            System.out.println("-----------------------");
        }

        System.out.println("=========================================");
    }

    public void createReview(String input, int rating, String comment) {
        String id = createUUID();
        String pId = reviewService.getProductId(input);

        Review newReview = new Review(id, comment, rating, session.getId(), pId);
        reviewService.createReview(newReview);
    }

    public boolean verifyPurchase (String productName) {
        String productId = getProductId(productName);
        Optional<Review> checkReview = reviewService.findByUserName(productId, session.getUsername());

        if( !checkReview.isEmpty()) {
            return false;
        }
        return true;
    }

    public String createUUID() {
        String uuid= String.valueOf(UUID.randomUUID());
        return uuid;
    }

    public String getProductId (String productName) {
        String productId = reviewService.getProductId(productName);
        return productId;
    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
