import java.util.ArrayList;

public class LocationManager {

    private final ArrayList<Location> locations = new ArrayList<>();
    private int nextId = 1;

    public LocationManager() {

        locations.addAll(DataManager.loadLocations());

        for (Location location : locations) {
            nextId = Math.max(nextId, location.getId() + 1);
        }
    }

    public void addLocation(String name) {

        if (name == null || name.trim().isEmpty()
                || name.contains("|")
                || name.contains("\n")
                || name.contains("\r")) {

            System.out.println("Invalid location name.");
            return;
        }

        name = name.trim();

        for (Location location : locations) {

            if (location.getName().equalsIgnoreCase(name)) {
                System.out.println("Location already exists.");
                return;
            }
        }

        Location location = new Location(nextId, name);

        locations.add(location);

        DataManager.saveLocations(locations);

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

        Location location = getLocationById(id);

        if (location != null) {
            System.out.println("\nLocation found:");
            location.displayLocation();
        } else {
            System.out.println("Location not found.");
        }
    }

    public Location getLocationById(int id) {

        for (Location location : locations) {

            if (location.getId() == id) {
                return location;
            }
        }

        return null;
    }

    public void deleteLocation(int id) {

        Location location = getLocationById(id);

        if (location == null) {
            System.out.println("Location not found.");
            return;
        }

        locations.remove(location);

        DataManager.saveLocations(locations);

        System.out.println("Location deleted successfully!");
    }

    public ArrayList<Location> getAllLocations() {
        return new ArrayList<>(locations);
    }
}