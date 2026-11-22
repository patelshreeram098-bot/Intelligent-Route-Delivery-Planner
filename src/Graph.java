import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph {

    private final Map<Integer, List<Route>> adjacencyList =
            new LinkedHashMap<>();

    private static class NodeDistance implements Comparable<NodeDistance> {

        int locationId;
        double distance;

        NodeDistance(int locationId, double distance) {
            this.locationId = locationId;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }

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

    public void dijkstra(int startId, int destinationId) {

        if (!adjacencyList.containsKey(startId)
                || !adjacencyList.containsKey(destinationId)) {

            System.out.println("Source or destination not found in graph.");
            return;
        }

        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();

        PriorityQueue<NodeDistance> queue = new PriorityQueue<>();

        for (int id : adjacencyList.keySet()) {
            distances.put(id, Double.POSITIVE_INFINITY);
        }

        distances.put(startId, 0.0);
        queue.add(new NodeDistance(startId, 0.0));

        while (!queue.isEmpty()) {

            NodeDistance current = queue.poll();

            int currentId = current.locationId;

            if (current.distance > distances.get(currentId)) {
                continue;
            }

            if (currentId == destinationId) {
                break;
            }

            for (Route route : adjacencyList.get(currentId)) {

                int neighbourId = route.getDestination().getId();

                double newDistance =
                        distances.get(currentId) + route.getDistance();

                if (newDistance < distances.get(neighbourId)) {

                    distances.put(neighbourId, newDistance);
                    previous.put(neighbourId, currentId);

                    queue.add(new NodeDistance(neighbourId, newDistance));
                }
            }
        }

        if (distances.get(destinationId) == Double.POSITIVE_INFINITY) {
            System.out.println("No route available between these locations.");
            return;
        }

        List<Integer> path = new ArrayList<>();

        int currentId = destinationId;

        while (currentId != startId) {

            path.add(currentId);
            currentId = previous.get(currentId);
        }

        path.add(startId);
        Collections.reverse(path);

        Map<Integer, Location> locationMap = new HashMap<>();

        for (List<Route> routes : adjacencyList.values()) {
            for (Route route : routes) {
                locationMap.put(
                        route.getSource().getId(),
                        route.getSource()
                );
                locationMap.put(
                        route.getDestination().getId(),
                        route.getDestination()
                );
            }
        }

        System.out.println("\n===== SHORTEST ROUTE =====");

        for (int i = 0; i < path.size(); i++) {

            Location location = locationMap.get(path.get(i));

            System.out.print(location.getName());

            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }

        System.out.println();
        System.out.println("Total Distance: "
                + distances.get(destinationId) + " km");
    }
}