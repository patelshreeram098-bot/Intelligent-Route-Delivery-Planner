import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final Path LOCATIONS_FILE = Path.of("locations.txt");
    private static final Path ROUTES_FILE = Path.of("routes.txt");

    public static void saveLocations(List<Location> locations) {

        try {
            List<String> lines = new ArrayList<>();

            for (Location location : locations) {
                lines.add(location.getId() + "|" + location.getName());
            }

            Files.write(LOCATIONS_FILE, lines);

        } catch (IOException e) {
            System.out.println("Unable to save locations: " + e.getMessage());
        }
    }

    public static List<Location> loadLocations() {

        List<Location> locations = new ArrayList<>();

        if (!Files.exists(LOCATIONS_FILE)) {
            return locations;
        }

        try {
            for (String line : Files.readAllLines(LOCATIONS_FILE)) {

                String[] parts = line.split("\\|", 2);

                if (parts.length != 2) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];

                    if (id > 0 && !name.trim().isEmpty()) {
                        locations.add(new Location(id, name));
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid location record.");
                }
            }

        } catch (IOException e) {
            System.out.println("Unable to load locations: " + e.getMessage());
        }

        return locations;
    }

    public static void saveRoutes(List<Route> routes) {

        try {
            List<String> lines = new ArrayList<>();

            for (Route route : routes) {

                lines.add(
                        route.getSource().getId() + "|"
                        + route.getDestination().getId() + "|"
                        + route.getDistance()
                );
            }

            Files.write(ROUTES_FILE, lines);

        } catch (IOException e) {
            System.out.println("Unable to save routes: " + e.getMessage());
        }
    }

    public static List<Route> loadRoutes(LocationManager locationManager) {

        List<Route> routes = new ArrayList<>();

        if (!Files.exists(ROUTES_FILE)) {
            return routes;
        }

        try {
            for (String line : Files.readAllLines(ROUTES_FILE)) {

                String[] parts = line.split("\\|");

                if (parts.length != 3) {
                    continue;
                }

                try {
                    int sourceId = Integer.parseInt(parts[0]);
                    int destinationId = Integer.parseInt(parts[1]);
                    double distance = Double.parseDouble(parts[2]);

                    Location source = locationManager.getLocationById(sourceId);
                    Location destination = locationManager.getLocationById(destinationId);

                    if (source == null || destination == null
                            || sourceId == destinationId
                            || !Double.isFinite(distance)
                            || distance <= 0) {
                        continue;
                    }

                    boolean duplicate = false;

                    for (Route existing : routes) {

                        int existingSource = existing.getSource().getId();
                        int existingDestination = existing.getDestination().getId();

                        if ((existingSource == sourceId
                                && existingDestination == destinationId)
                                || (existingSource == destinationId
                                && existingDestination == sourceId)) {

                            duplicate = true;
                            break;
                        }
                    }

                    if (!duplicate) {
                        routes.add(new Route(source, destination, distance));
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid route record.");
                }
            }

        } catch (IOException e) {
            System.out.println("Unable to load routes: " + e.getMessage());
        }

        return routes;
    }
}