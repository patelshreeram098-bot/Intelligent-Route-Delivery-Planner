import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private final Map<Integer, List<Route>> adjacencyList = new LinkedHashMap<>();

    public void addRoute(Route route) {

        Location source = route.getSource();
        Location destination = route.getDestination();

        adjacencyList
                .computeIfAbsent(source.getId(), k -> new ArrayList<>())
                .add(route);

        Route reverseRoute = new Route(
                destination,
                source,
                route.getDistance()
        );

        adjacencyList
                .computeIfAbsent(destination.getId(), k -> new ArrayList<>())
                .add(reverseRoute);
    }

    public void displayGraph() {

        if (adjacencyList.isEmpty()) {
            System.out.println("Graph is empty.");
            return;
        }

        System.out.println("\n===== GRAPH ADJACENCY LIST =====");

        for (List<Route> routes : adjacencyList.values()) {

            Location location = routes.get(0).getSource();

            System.out.print(location.getName() + " -> ");

            for (Route route : routes) {
                System.out.print(
                        route.getDestination().getName()
                        + " (" + route.getDistance() + " km), "
                );
            }

            System.out.println();
        }
    }
}