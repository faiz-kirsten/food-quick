import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String customerName = FoodQuick.validateCustomerName();
        String customerNum = FoodQuick.validateCustomerNumber();
        String customerEmail = FoodQuick.validateCustomerEmail();
        String customerLocation = FoodQuick.validateCustomerLocation(); // Customer city
        FoodQuick.sendInvoiceInvalidLocation(customerLocation);
        String customerAddress = FoodQuick.validateCustomerAddress(); // split questions into street and suburb
        Customer newCustomer = new Customer(customerName, customerNum, customerLocation,
                customerAddress, customerEmail);
        // Restaurant
        Restaurant joeysPizza = new Restaurant("Joey's Pizza", FoodQuick.formatPhoneNumber(1987654321),
                customerLocation, 1234);

        // add meals using the addMeal method to 'meals' - the restaurant meals
        joeysPizza.addRestaurantMeal(1, "Pepperoni Pizza", 1, 78.00);
        joeysPizza.addRestaurantMeal(2, "Hawaiian Pizza", 1, 82.00);
        joeysPizza.addRestaurantMeal(3, "Chicken and Cheese Pizza", 1, 72.99);
        joeysPizza.addRestaurantMeal(4, "BBQ Chicken Pizza", 1, 90.00);
        joeysPizza.addRestaurantMeal(5, "Extra Cheese Pizza", 1, 90.00);
        joeysPizza.addRestaurantMeal(6, "Wonder Why Pizza", 1, 215.00);

        boolean isDriverInLocation = FoodQuick.isDriverInCustomerLocation(newCustomer.location);

        if (isDriverInLocation) {
            newCustomer.placeOrder(joeysPizza);
            FoodQuick.sendInvoiceValidLocation(newCustomer, joeysPizza);
        }
    }
}