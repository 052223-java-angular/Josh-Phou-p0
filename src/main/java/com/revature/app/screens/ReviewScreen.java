package com.revature.app.screens;

import com.revature.app.models.Review;
import com.revature.app.models.Session;
import com.revature.app.services.ReviewService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

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
                //System.out.println("[3] Edit a review");
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
                                System.out.println("Invalid entry please enter a rating between 1 and 5");
                                break rating;
                            }
                            if (rating >= 1 && rating <= 5) {
                                System.out.print("Enter comment: ");
                                String comment = scanner.nextLine();
                                createReview(input, rating, comment);
                            }else {
                                System.out.println("Invalid entry");
                                break rating;
                            }
                        }
                        break;
                    case "3":
                        rating = 0;
                        clearScreen();
                        System.out.print("Type the name of the product review to edit: ");
                        input = scanner.nextLine();
                        verify = verifyPurchase(input);
                        rating:
                        if(verify) {
                            System.out.print("Enter updated rating (1-5): ");

                            try {
                                rating = scanner.nextInt();
                            } catch (Exception e) {
                                System.out.println("Invalid entry please enter a rating between 1 and 5");
                                break rating;
                            }
                            if (rating >= 1 && rating <= 5) {
                                System.out.print("Enter updated comment: ");
                                String comment = scanner.nextLine();
                                editReview(input, rating, comment);
                            }else {
                                System.out.println("Invalid entry");
                                break rating;
                            }
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

        System.out.println("\n------------------------------------------");
        if( reviews == null){
            System.out.println("No reviews for " + pName);
        }

        for(int i = 0; i <reviews.size(); i++){
            System.out.println(pName
                    + " rating: "
                    + reviews.get(i).getRating()
                    + " Comment: "
                    + reviews.get(i).getComment());
        }

        System.out.println("\n------------------------------------------");
    }

    public void deleteReview(String name) {
        String id = session.getId();
        reviewService.deleteReview(name, id);
    }

    public void createReview(String input, int rating, String comment) {
        String id = createOrderUUID();
        String pId = reviewService.getPIdByName(input);

        Review newReview = new Review(id, comment, rating, session.getId(), pId);
        reviewService.createReview(newReview);
    }

    public boolean verifyPurchase (String name) {
        Optional<Review> checkReview = reviewService.findByUserName(name, session.getUsername());

        if( !checkReview.isEmpty()) {
            return true;
        }
        return false;
    }

    public void editReview(String name,int rating, String comment) {
        Optional<Review> oldReview = reviewService.findByUserName(name, session.getUsername());

        if (!oldReview.isEmpty()) {
            Review updateReview = oldReview.get();
            reviewService.updateReview(updateReview, comment);
        }
    }

    public String createOrderUUID() {
        String uuid= String.valueOf(UUID.randomUUID());
        return uuid;
    }

    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
