import java.util.*;

public class Restaurant extends Details {
    int orderNum;
    double total;
    ArrayList<Meal> mealsOrdered;
    Map<Integer, Meal> meals;

    public Restaurant(String name,  String contactNumber, String location,int orderNum,
                      Map<Integer, Meal> meals, ArrayList<Meal> mealsOrdered) {
        super(name, contactNumber,location);
        this.orderNum = orderNum;
        this.mealsOrdered = mealsOrdered;
        this.meals = meals;
    }

    public double getOrderTotal() {
        // add the quantity of the meal multiplied by the price of the meal to get the 'total'
        for (Meal m : mealsOrdered ) {
            total += m.quantity * m.price;
        }

        return total;
    }

    public void placeOrder() {
        Scanner in = new Scanner(System.in);
        viewRestaurantMeals();
        System.out.println("Enter the meal number of the meal you would like to order.\n");
        int mealNum = -1;

        while (true) {
            try {
                while (mealNum > meals.size() + 1 || mealNum < 0) {
                    mealNum = Integer.parseInt(in.nextLine());
                    if (mealNum + 1 > meals.size() + 1 || mealNum < 0) System.out.println("Invalid Meal Number! Please enter a valid meal number.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid meal number.");
            }
        }

        System.out.println("Please enter the quantity of " + meals.get(mealNum).name + ".");

        int quantity;

        while (true) {
            try {
                quantity = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid number.");
            }
        }

        for (Map.Entry<Integer, Meal> meal : meals.entrySet()) {
            if (meal.getKey().equals(mealNum)) {
                meal.getValue().quantity = quantity;
                mealsOrdered.add(meal.getValue());
            }
        }
    }

    // creates a 'Meal' object with the values entered within the parameters and adds it to 'meals'
    public void addRestaurantMeal(int mealNum, String meal, int quantity, double price) {
        meals.put(mealNum, new Meal(meal, quantity, price));
    }

    public void viewRestaurantMeals() {
        System.out.println("Menu:");
        for (Map.Entry<Integer ,Meal> entry : meals.entrySet())
            System.out.println("Meal " + entry.getKey() + ": " + entry.getValue().name + ", Price: " + entry.getValue().price);
    }

    public void viewOrderedMeals() {
        System.out.println("Current Ordered Meals:");
        for (Meal m : mealsOrdered) {
            System.out.println(m.name + " " + m.quantity + " " + m.price);
        }
    }
}