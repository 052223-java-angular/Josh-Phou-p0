package com.revature.app.screens;

import com.revature.app.models.Review;
import com.revature.app.models.Session;
import com.revature.app.services.ReviewService;
import com.revature.app.services.RouterService;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
public class ReviewScreen implements IScreen {
    private final ReviewService reviewService;
    private final RouterService router;
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
                System.out.println("[3] Edit a review");
                System.out.println("[x] Exit");


                System.out.print("\nEnter: ");
                input = scanner.nextLine();

                switch (input.toLowerCase()) {
                    case "1":
                        System.out.print("Type the name of the product you would like to review: ");
                        input = scanner.nextLine();
                        viewReview(input);
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "x":
                        break exit;
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

    public String viewReview(String name) {
        String review = "";
        Review reviewObj = reviewService.findByName(name);

        review = reviewObj.getComment();

        return review;
    }

    public void deleteReview(String name) {
        String id = session.getId();
        reviewService.deleteReview(name, id);
    }

    public void editReview(String name, String comment) {
        Review oldReview = reviewService.findByName(name);

        if (!(oldReview == null)) {
            reviewService.updateReview(oldReview, comment);
        }

    }



    /* ------------------------ Helper methods ------------------------------*/

    // Clears the console screen.

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
