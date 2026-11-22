import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        LocationManager locationManager = new LocationManager();
        RouteManager routeManager = new RouteManager(locationManager);

        try (Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                System.out.println("\n==================================");
                System.out.println(" INTELLIGENT ROUTE & DELIVERY PLANNER");
                System.out.println("==================================");

                System.out.println("1. Add Location");
                System.out.println("2. View All Locations");
                System.out.println("3. Search Location");
                System.out.println("4. Add Route");
                System.out.println("5. View All Routes");
                System.out.println("6. View Routes From Location");
                System.out.println("7. View Graph");
                System.out.println("8. Perform BFS");
                System.out.println("9. Find Shortest Route");
                System.out.println("10. Delete Location");
                System.out.println("11. Delete Route");
                System.out.println("12. Exit");

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

                        locationManager.addLocation(name);
                    }

                    case 2 -> locationManager.viewLocations();

                    case 3 -> {

                        System.out.print("Enter Location ID: ");

                        try {
                            int id = Integer.parseInt(scanner.nextLine());
                            locationManager.searchLocation(id);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid ID.");
                        }
                    }

                    case 4 -> {

                        try {
                            System.out.print("Enter source location ID: ");
                            int sourceId = Integer.parseInt(scanner.nextLine());

                            System.out.print("Enter destination location ID: ");
                            int destinationId = Integer.parseInt(scanner.nextLine());

                            System.out.print("Enter distance in km: ");
                            double distance = Double.parseDouble(scanner.nextLine());

                            Location source = locationManager.getLocationById(sourceId);
                            Location destination = locationManager.getLocationById(destinationId);

                            routeManager.addRoute(source, destination, distance);

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter valid numeric values.");
                        }
                    }

                    case 5 -> routeManager.viewRoutes();

                    case 6 -> {

                        System.out.print("Enter location ID: ");

                        try {
                            int id = Integer.parseInt(scanner.nextLine());

                            Location location = locationManager.getLocationById(id);

                            if (location == null) {
                                System.out.println("Location not found.");
                            } else {
                                routeManager.viewRoutesFrom(location);
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid ID.");
                        }
                    }

                    case 7 -> routeManager.viewGraph();

                    case 8 -> {

                        System.out.print("Enter starting location ID: ");

                        try {
                            int id = Integer.parseInt(scanner.nextLine());
                            routeManager.performBFS(id);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid ID.");
                        }
                    }

                    case 9 -> {

                        try {
                            System.out.print("Enter source location ID: ");
                            int sourceId = Integer.parseInt(scanner.nextLine());

                            System.out.print("Enter destination location ID: ");
                            int destinationId = Integer.parseInt(scanner.nextLine());

                            routeManager.findShortestRoute(sourceId, destinationId);

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter valid location IDs.");
                        }
                    }

                    case 10 -> {

                        System.out.print("Enter Location ID to delete: ");

                        try {
                            int id = Integer.parseInt(scanner.nextLine());

                            if (locationManager.getLocationById(id) == null) {
                                System.out.println("Location not found.");
                            } else {
                                routeManager.removeRoutesForLocation(id);
                                locationManager.deleteLocation(id);
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid ID.");
                        }
                    }

                    case 11 -> {

                        try {
                            System.out.print("Enter source location ID: ");
                            int sourceId = Integer.parseInt(scanner.nextLine());

                            System.out.print("Enter destination location ID: ");
                            int destinationId = Integer.parseInt(scanner.nextLine());

                            routeManager.deleteRoute(sourceId, destinationId);

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter valid location IDs.");
                        }
                    }

                    case 12 -> {
                        running = false;
                        System.out.println("Exiting application...");
                    }

                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }
}