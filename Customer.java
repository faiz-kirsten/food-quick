import java.util.Scanner;

public class Customer extends Details {
        String address, emailAddress, specialInstructions;
        public Customer(String name,  String contactNumber, String location, String address, String emailAddress){
            super(name, contactNumber,location);
            this.address = address;
            this.emailAddress = emailAddress;
        }

        // Create to validate every input from the customer
        public void placeOrder(Restaurant restaurant) {
            Scanner in = new Scanner(System.in);

            restaurant.addOrderedMeal();

            int option = 0;

            while (true) {
                try {
                    while (option != -1) {
                        System.out.println("Enter any number(except -1) to place another order or enter -1 to continue.");
                        option = Integer.parseInt(in.nextLine());
                        if (option != -1 || restaurant.mealsOrdered.isEmpty()) {
                            if (restaurant.mealsOrdered.isEmpty()) {
                                System.out.println("Your cart is empty. Please place an order: ");
                                option = 0;
                            }
                            restaurant.addOrderedMeal();
                            System.out.println("Current Order: ");
                            restaurant.viewOrderedMeals();
                        }
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input. Please enter a valid number.");
                }
            }

            System.out.println("Any special instructions for the meals you ordered? Press enter to leave blank.");
            specialInstructions = in.nextLine();

        }
}