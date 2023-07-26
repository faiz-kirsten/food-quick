import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

    public static void sendInvoice() {}
}