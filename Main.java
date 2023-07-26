import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String customerName = "Faiz Kirsten";
        String customerNum = "012 345 6789";
        String customerEmail = "fake123@gmail.co.za";
        String customerAddress = "13 MacDonnell str, Ridgeway";
        String customerLocation = "Johannesburg";
        String customerInstructions = "Extra thick base for each pizza ordered";
        Map<Integer, Meal> meals = new HashMap<>();
        ArrayList<Meal>  orderedMeals = new ArrayList<>();

        Customer newCustomer = new Customer(customerName, customerNum, customerLocation,
                customerAddress, customerEmail, customerInstructions);
        // Restaurant
        Restaurant joeysPizza = new Restaurant("Joey's Pizza", "1987654321",
                customerLocation, 1234, meals,  orderedMeals);

        // add meals using the addMeal method to 'meals' - the restaurant meals
        joeysPizza.addRestaurantMeal(1, "Pepperoni Pizza", 1, 78.00);
        joeysPizza.addRestaurantMeal(2, "Hawaiian Pizza", 1, 82.00);
        joeysPizza.addRestaurantMeal(3, "Chicken and Cheese Pizza", 1, 72.99);
        joeysPizza.addRestaurantMeal(4, "BBQ Chicken Pizza", 1, 90.00);
        joeysPizza.addRestaurantMeal(5, "Extra Cheese Pizza", 1, 90.00);

//        System.out.println(FoodQuick.selectDriver(customerLocation));

        // Customer placing orders
        joeysPizza.placeOrder();
        joeysPizza.placeOrder();
        joeysPizza.placeOrder();
//        joeysPizza.viewRestaurantMeals();
        joeysPizza.viewOrderedMeals();

        System.out.println(joeysPizza.getOrderTotal());
    }
}