import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

    public void bfs(int startId) {

    if (!adjacencyList.containsKey(startId)) {
        System.out.println("Location not found in graph.");
        return;
    }

    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();

    queue.add(startId);
    visited.add(startId);

    System.out.println("\n===== BFS TRAVERSAL =====");

    while (!queue.isEmpty()) {

        int currentId = queue.poll();

        List<Route> routes = adjacencyList.get(currentId);

        Location currentLocation = routes.get(0).getSource();

        System.out.print(currentLocation.getName() + " -> ");

        for (Route route : routes) {

            int neighbourId = route.getDestination().getId();

            if (!visited.contains(neighbourId)) {

                visited.add(neighbourId);
                queue.add(neighbourId);
            }
        }
    }

    System.out.println("END");
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