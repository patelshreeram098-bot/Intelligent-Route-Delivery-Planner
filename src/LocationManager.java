import java.util.ArrayList;

public class LocationManager {

    private final ArrayList<Location> locations = new ArrayList<>();
    private int nextId = 1;

    public void addLocation(String name) {

        Location location = new Location(nextId, name);
        locations.add(location);

        System.out.println("Location added successfully!");
        System.out.println("Location ID: " + nextId);

        nextId++;
    }

    public void viewLocations() {

        if (locations.isEmpty()) {
            System.out.println("No locations available.");
            return;
        }

        System.out.println("\n===== ALL LOCATIONS =====");

        for (Location location : locations) {
            location.displayLocation();
            System.out.println("-------------------------");
        }
    }

    public void searchLocation(int id) {

        for (Location location : locations) {

            if (location.getId() == id) {
                System.out.println("\nLocation found:");
                location.displayLocation();
                return;
            }
        }

        System.out.println("Location not found.");
    }

    public Location getLocationById(int id) {

    for (Location location : locations) {

        if (location.getId() == id) {
            return location;
        }
    }

    return null;
    }
}