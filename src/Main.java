import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        LocationManager manager = new LocationManager();

        try (Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                System.out.println("\n==================================");
                System.out.println(" INTELLIGENT ROUTE & DELIVERY PLANNER");
                System.out.println("==================================");

                System.out.println("1. Add Location");
                System.out.println("2. View All Locations");
                System.out.println("3. Search Location");
                System.out.println("4. Exit");

                System.out.print("Enter your choice: ");

                int choice;

                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    continue;
                }

                switch (choice) {

                    case 1 -> {
                        System.out.print("Enter location name: ");
                        String name = scanner.nextLine().trim();

                        if (name.isEmpty()) {
                            System.out.println("Location name cannot be empty.");
                        } else {
                            manager.addLocation(name);
                        }
                    }

                    case 2 -> manager.viewLocations();

                    case 3 -> {
                        System.out.print("Enter Location ID: ");

                        try {
                            int id = Integer.parseInt(scanner.nextLine());
                            manager.searchLocation(id);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid ID.");
                        }
                    }

                    case 4 -> {
                        running = false;
                        System.out.println("Exiting application...");
                    }

                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }
}