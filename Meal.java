// Creates a new meal object for the restaurant
public class Meal {
    int quantity;
    String name;
    double price;

    public Meal(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}