import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ComputerUtils {

    // We have an array list to store the details from the txt file.
    private static final ArrayList<HardwareType> hardware = new ArrayList<>();

    // Similar to assignment 2 we use a scanner to get the details from the txt file and store them into the array list
    public static void readDataFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("computers.txt")));
            String item;
            try {
                while (scanner.hasNext()) {
                    item = scanner.nextLine();
                    String[] parts = item.split(","); // We use a very simple regex to split each part of the string
                    String category = parts[0].trim();
                    String type = parts[1].trim();
                    String ID = parts[2].trim();
                    String brand = parts[3].trim();
                    String CPU = parts[4].trim();
                    System.out.println(ID);

                    if(category.equals("Desktop PC")) {
                        int memorySize = Integer.parseInt(parts[5].trim());
                        int capacitySize = Integer.parseInt(parts[6].trim());
                        double price = Double.parseDouble(parts[7].trim());
                        Desktop desktop = new Desktop(category, type, ID, brand, CPU, price, memorySize, capacitySize);
                        hardware.add(desktop);
                    } else if (category.equals("Laptop")) {
                        int memorySize = Integer.parseInt(parts[5].trim());
                        int capacitySize = Integer.parseInt(parts[6].trim());
                        double screenSize = Double.parseDouble(parts[7].trim());
                        double price = Double.parseDouble(parts[8].trim());
                        Laptop laptop = new Laptop(category, type, ID, brand, CPU, price, memorySize, capacitySize,
                                screenSize);
                        hardware.add(laptop);
                    } else if (category.equals("Tablet")) {
                        double screenSize = Double.parseDouble(parts[5].trim());
                        double price = Double.parseDouble(parts[6].trim());
                        Tablet tablet = new Tablet(category, type, ID, brand, CPU, price, screenSize);
                        hardware.add(tablet);
                    }
                }
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: File could not load!");
            e.printStackTrace();
            System.exit(0);
        }

    }

    public static ArrayList<HardwareType> getHardware() { return hardware; }

    // We have a boolean here called includeAll. There are some scenario where we don't want to include the all option
    // For example when the user is adding, deleting, updating a product. We want them to select a specific catogery
    // for the item. But in the table we still want the option to have all.
    public static Set<String> getUniqueCategories(boolean includeAll) {
        Set<String> categories = new HashSet<>();
        if(includeAll) { categories.add("All"); }
        for (HardwareType h : hardware) {
            categories.add(h.getCategory());
        }
        return categories;
    }

    // Here in this function we are getting the unique types based off what the current category is. We pass it the
    // category and the same boolean to add all as an option or not. Then we do a loop and check if the type matches the
    // selected category then return the types.
    public static Set<String> getUniqueTypes(String category, boolean includeAll) {
        Set<String> types = new HashSet<>();
        if(includeAll) { types.add("All"); }
        for (HardwareType h : hardware) {
            if (category.equals("All") || h.getCategory().equals(category)) {
                types.add(h.getType());
            }
        }
        return types;
    }

}