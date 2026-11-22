import java.util.ArrayList;

public class RouteManager {

    private final ArrayList<Route> routes = new ArrayList<>();
    private Graph graph = new Graph();

    public RouteManager(LocationManager locationManager) {

        routes.addAll(DataManager.loadRoutes(locationManager));

        rebuildGraph();
    }

    public void addRoute(Location source, Location destination, double distance) {

        if (source == null || destination == null) {
            System.out.println("Invalid source or destination.");
            return;
        }

        if (source.getId() == destination.getId()) {
            System.out.println("Source and destination cannot be the same.");
            return;
        }

        if (!Double.isFinite(distance) || distance <= 0) {
            System.out.println("Distance must be a positive number.");
            return;
        }

        for (Route existingRoute : routes) {

            int existingSource = existingRoute.getSource().getId();
            int existingDestination = existingRoute.getDestination().getId();

            if ((existingSource == source.getId()
                    && existingDestination == destination.getId())
                    || (existingSource == destination.getId()
                    && existingDestination == source.getId())) {

                System.out.println("Route already exists.");
                return;
            }
        }

        Route route = new Route(source, destination, distance);

        routes.add(route);
        graph.addRoute(route);

        DataManager.saveRoutes(routes);

        System.out.println("Route added successfully!");
    }

    public void viewRoutes() {

        if (routes.isEmpty()) {
            System.out.println("No routes available.");
            return;
        }

        System.out.println("\n===== ALL ROUTES =====");

        for (Route route : routes) {
            route.displayRoute();
        }
    }

    public void viewRoutesFrom(Location location) {

        boolean found = false;

        for (Route route : routes) {

            if (route.getSource().getId() == location.getId()
                    || route.getDestination().getId() == location.getId()) {

                route.displayRoute();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No routes found from this location.");
        }
    }

    public void deleteRoute(int sourceId, int destinationId) {

        boolean removed = routes.removeIf(route ->

                (route.getSource().getId() == sourceId
                        && route.getDestination().getId() == destinationId)
                || (route.getSource().getId() == destinationId
                        && route.getDestination().getId() == sourceId)
        );

        if (removed) {

            rebuildGraph();
            DataManager.saveRoutes(routes);

            System.out.println("Route deleted successfully!");

        } else {
            System.out.println("Route not found.");
        }
    }

    public void removeRoutesForLocation(int locationId) {

        boolean removed = routes.removeIf(route ->

                route.getSource().getId() == locationId
                || route.getDestination().getId() == locationId
        );

        if (removed) {
            rebuildGraph();
            DataManager.saveRoutes(routes);
            System.out.println("Connected routes deleted successfully.");
        }
    }

    private void rebuildGraph() {

        graph = new Graph();

        for (Route route : routes) {
            graph.addRoute(route);
        }
    }

    public void viewGraph() {
        graph.displayGraph();
    }

    public void performBFS(int locationId) {
        graph.bfs(locationId);
    }

    public void findShortestRoute(int sourceId, int destinationId) {
        graph.dijkstra(sourceId, destinationId);
    }
}