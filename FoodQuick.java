import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodQuick {
    public static ArrayList<Driver> getAllDrivers(String fileName) {
        ArrayList<Driver> drivers = new ArrayList<>();
        // Read from drivers-info.txt
        try {
            File file = new File(fileName);
            Scanner scanDrivers = new Scanner(file);

            // reads 'driver.txt' line by line
            while (scanDrivers.hasNextLine()) {
                // each line is split by ', " which creates an array
                String[] driver = scanDrivers.nextLine().split(", ");
                // create a new 'Driver' object by indexing the 'driver' array
                Driver newDriver = new Driver(driver[0], driver[1], Integer.parseInt(driver[2]));
                // add 'newDriver' to 'drivers'
                drivers.add(newDriver);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return drivers;
    }
    public static ArrayList<String> getDriverLocations() {
        // contains all locations of drivers
        ArrayList<Driver> drivers = getAllDrivers("drivers-info.txt");
        ArrayList<String> driverLocations = new ArrayList<>();

        // if the current drivers location is not within 'driversLocations', it is added to the array
        for (Driver d : drivers) {
            if (!driverLocations.contains(d.location)) {
                driverLocations.add(d.location);
            }
        }

        return driverLocations;
    }

    public static boolean isDriverInCustomerLocation(String customerLocation) {
        ArrayList<String> driverLocations = getDriverLocations();

        return driverLocations.contains(customerLocation);
    }

    public static Integer getDriverWithMinLoad(String customerLocation) {
        ArrayList<Integer> totalDriverLoad = new ArrayList<>();
        ArrayList<Driver> drivers = getAllDrivers("drivers-info.txt");

        // iterate through 'drivers' using a for each loop
        for (Driver driver : drivers) {
            // if the driver and custer are in the same location, add the drivers load to 'totalDriverLoad' and add the
            // 'driver' to 'driversInLocation'
            if (customerLocation.equals(driver.location)) {
                totalDriverLoad.add(driver.load);
            }
        }

        return Collections.min(totalDriverLoad);
    }

    public static ArrayList<Driver> getDriversInLocation(String customerLocation) {
        ArrayList<Driver> driversInLocation = new ArrayList<>();
        ArrayList<Driver> drivers = getAllDrivers("drivers-info.txt");

        for (Driver driver : drivers) {
            // if the driver and custer are in the same location, add the drivers load to 'totalDriverLoad' and add the
            // 'driver' to 'driversInLocation'
            if (customerLocation.equals(driver.location)) {
                driversInLocation.add(driver);
            }
        }

        return driversInLocation;
    }

    public static String selectDriver(String customerLocation){
        ArrayList<Driver> driversInLocation = getDriversInLocation(customerLocation);
        Integer driverMinLoad = getDriverWithMinLoad(customerLocation);
        String selectedDriver = "";

        for (Driver driver : driversInLocation) {
            if (driver.load == driverMinLoad) {
                selectedDriver = driver.name;
            }
        }

        return selectedDriver;
    }
    public static void sendInvoiceInvalidLocation(String customerLocation) {
        boolean isDriverInLocation = FoodQuick.isDriverInCustomerLocation(customerLocation);

        if (!isDriverInLocation) {
            try {
                // write the following message to 'invoice.txt' and exit the program
                Formatter f = new Formatter("invoice.txt");
                f.format("%s", "Sorry! Our drivers are too far away from you to be able to deliver to your " +
                        "location.");
                f.close();

                System.exit(0);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void sendInvoiceValidLocation(Customer newCustomer, Restaurant restaurant) {
        try {
            Formatter f = new Formatter("invoice.txt");
            f.format("%s", "Order number: " + restaurant.orderNum + "\n");
            f.format("%s", "Customer name: " + newCustomer.name + "\n");
            f.format("%s", "Email address: " + newCustomer.emailAddress + "\n");
            f.format("%s", "Phone Number: " + newCustomer.contactNumber + "\n");
            f.format("%s", "Location: " + newCustomer.location + "\n\n");
            f.format("%s", "You have ordered the following from " + restaurant.name + " in "
                    + restaurant.location + ":\n\n");

            // Iterate through each meal and write them to the file
            // Using math.round to round of to 2 decimal places
            for (Meal m : restaurant.mealsOrdered) {
                f.format("%s", m.quantity + " x " + m.name
                        + " (R" + Math.round(m.price * 100) / 100.0 + ")\n");
            }

            if (newCustomer.specialInstructions.isEmpty()) {
                f.format("%s", """

                        Special instructions: No special Instructions given.

                        """);
            } else {
                f.format("%s", "\nSpecial instructions: " + newCustomer.specialInstructions + "\n\n");
            }

            f.format("%s", "Total: R" + Math.round(restaurant.getOrderTotal() * 100) / 100.0);

            f.format("%s", "\n\n" + selectDriver(newCustomer.location) + " is nearest to the restaurant, and so he will be delivering " +
                    "your order to you at:\n\n");
            String[] customerAddressFormat = newCustomer.address.split(", ");

            for (String customerAdd : customerAddressFormat) {
                f.format("%s", customerAdd + "\n");
            }

            f.format("%s",
                    "\n" + "If you need to contact the restaurant, their number is " + restaurant.contactNumber + ".");
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String validateCustomerName() {
        String customerName = "";
        Scanner in = new Scanner(System.in);

        while (customerName.isBlank() || customerName.length() < 3) {
            System.out.println("Please enter your name and surname:");
            customerName = in.nextLine();
            if (customerName.isBlank() || customerName.length() < 3) {
                System.out.println("This is a required field and it must be longer than 3 letters");
            }
        }

        return customerName;
    }

    public static String validateCustomerNumber() {
        Scanner in = new Scanner(System.in);
        long customerNum = 0;

        // The customer is prompted to enter their phone number
        // If a character other than numbers are entered, an error is thrown
        // The number can only be as long as 10
        while (true) {
            try {
                while (String.valueOf(customerNum).length() != 10) {
                    System.out.println("Please enter your phone number(no spaces):");
                    customerNum = Integer.parseInt(in.nextLine());

                    if (String.valueOf(customerNum).length() != 10) {
                        System.out.println("Length of number must be 10.");
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!");
            }
        }

        return formatPhoneNumber(customerNum);
    }

    public static String validateCustomerEmail() {
        Scanner in = new Scanner(System.in);
        String customerEmail = "";
        boolean isValidEmail = validateEmailAddress(customerEmail);

        // If the customer input does not match the pattern specified in the emailRegex, a message will display and
        // ask the customer to input a valid email address
        while (!isValidEmail) {
            System.out.println("Please enter your email address:");
            customerEmail = in.nextLine();

            isValidEmail = validateEmailAddress(customerEmail);

            if (!isValidEmail) {
                System.out.println("Invalid Email Address.");
            }
        }

        return customerEmail;
    }

    public static String validateCustomerLocation() {
        ArrayList<String> driverLocations = getDriverLocations();
        int counter = 1;
        int customerLocationNum = 0;
        Scanner in = new Scanner(System.in);

        for (String location : driverLocations) {
            System.out.println(counter + ". " + location);
            counter++;
        }

        System.out.println(counter + ". Location not listed");

        while (true) {
            try {
                while (customerLocationNum > driverLocations.size() + 1 || customerLocationNum <= 0) {
                    System.out.println("Enter the number that corresponds with your current location:");
                    customerLocationNum = Integer.parseInt(in.nextLine());
                    if (customerLocationNum > driverLocations.size() + 1 || customerLocationNum <= 0)
                        System.out.println("Invalid Location Number! Please enter a valid location number.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid number.");
            }
        }

        if (customerLocationNum == driverLocations.size() + 1) {
            return "";
        } else {
            return driverLocations.get(customerLocationNum - 1);
        }
    }

    public static String validateCustomerAddress() {
        String street = "";
        String suburb = "";
        Scanner in = new Scanner(System.in);

        while (street.isBlank() || street.length() < 3) {
            System.out.println("Enter your street number followed by street name:");
            street = in.nextLine();
            if (street.isBlank() || street.length() < 3) {
                System.out.println("Field cannot be blank and it must be longer than 3 letters");
            }
        }

        while (suburb.isBlank() || suburb.length() < 3) {
            System.out.println("Enter your suburb:");
            suburb = in.nextLine();
            if (suburb.isBlank() || suburb.length() < 3) {
                System.out.println("Field cannot be blank and it must be longer than 3 letters");
            }
        }

        return street + ", " + suburb;
    }

    // Helper Methods
    public static String formatPhoneNumber(long number) {
        // converts number into a string
        String contactNumber = Long.toString(number);
        // gets the first 3 numbers in 'contactNumber'
        String areaCode = contactNumber.substring(0, 3);
        // Starts indexing from the 4th iteration util the 7
        String prefix = contactNumber.substring(3, contactNumber.length() - 4);
        // Gets the last 4 numbers
        String lineNumber = contactNumber.substring(contactNumber.length() - 4);

        return areaCode + " " + prefix + " "  + lineNumber;
    }

    public static boolean validateEmailAddress(String emailAddress) {
        // Regular expression ensures that customer includes an '@' symbol in their input
        String emailRegex = "^(.+)@(.+)$";
        // Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(emailRegex);
        // Create instance of matcher
        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();
    }
}