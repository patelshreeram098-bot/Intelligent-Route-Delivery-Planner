import java.util.ArrayList;

public class RouteManager {

    private final ArrayList<Route> routes = new ArrayList<>();
    private final Graph graph = new Graph();
    public void addRoute(Location source, Location destination, double distance) {

        Route route = new Route(source, destination, distance);

        routes.add(route);
        graph.addRoute(route);
        System.out.println("Route added successfully!");
    }
    
    public void viewGraph() {
    graph.displayGraph();
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

            if (route.getSource().getId() == location.getId()) {
                route.displayRoute();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No routes found from this location.");
        }
    }
}