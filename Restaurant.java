import java.util.*;

public class Restaurant extends Details {
    int orderNum;
    double total;
    ArrayList<Meal> mealsOrdered = new ArrayList<>();
    Map<Integer, Meal> meals = new HashMap<>();

    public Restaurant(String name,  String contactNumber, String location,int orderNum) {
        super(name, contactNumber,location);
        this.orderNum = orderNum;

    }

    public double getOrderTotal() {
        // add the quantity of the meal multiplied by the price of the meal to get the 'total'
        for (Meal m : mealsOrdered ) {
            total += m.quantity * m.price;
        }

        return total;
    }

    public void addOrderedMeal() {
        Scanner in = new Scanner(System.in);
        viewRestaurantMeals();

        int mealNum = -1;

        while (true) {
            try {
                while (mealNum > meals.size() + 1 || mealNum <= 0) {
                    System.out.println("Enter the meal number of the meal you would like to order:");
                    mealNum = Integer.parseInt(in.nextLine());
                    if (mealNum + 1 > meals.size() + 1 || mealNum <= 0) System.out.println("Invalid Meal Number! Please enter a valid meal number.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid meal number.");
            }
        }

        Meal newMeal = meals.get(mealNum);

        int quantity = -1;

        while (true) {
            try {
                while (quantity <= 0) {
                    if (mealsOrdered.contains(newMeal)) {
                        System.out.println(newMeal.name + " has been added to cart already. ");
                        viewOrderedMeals();
                        System.out.println("Add more to quantity or just enter 0 to go back.");
                    }

                    System.out.println("Please enter the quantity of " + meals.get(mealNum).name + " or 0 to go back:");
                    quantity = Integer.parseInt(in.nextLine());

                    if (quantity == 0) break;

                    if (mealsOrdered.contains(newMeal)) {
                        mealsOrdered.get(mealsOrdered.indexOf(newMeal)).quantity += quantity;
                    } else {
                        newMeal.quantity = quantity;
                        mealsOrdered.add(newMeal);
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid number.");
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
            System.out.println("Meal " + entry.getKey() + " - " + entry.getValue().name + " - Price: " + entry.getValue().price);
        System.out.println();
    }

    public void viewOrderedMeals() {
        for (Meal m : mealsOrdered) {
            System.out.println(m.quantity + " x " + m.name + " (" + m.price + ")");
        }
    }
}