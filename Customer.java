import java.util.Scanner;

public class Customer extends Details {
        String address, emailAddress, specialInstructions;
        public Customer(String name,  String contactNumber, String location, String address, String emailAddress, String specialInstructions){
            super(name, contactNumber,location);
            this.address = address;
            this.emailAddress = emailAddress;
            this.specialInstructions = specialInstructions;
        }

        // Create to validate every input from the customer
        public void placeOrder(Restaurant restaurant) {
            Scanner in = new Scanner(System.in);

            restaurant.receiveOrder();

            int option = 0;

            while (option != -1) {
                System.out.println("Enter 1 to place another order or enter -1 to continue.");
                option = in.nextInt();
                if (option == 1) {
                    restaurant.receiveOrder();
                }
            }
        }
}