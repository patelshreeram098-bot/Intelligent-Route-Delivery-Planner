public class Route {

    private final Location source;
    private final Location destination;
    private final double distance;

    public Route(Location source, Location destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

    public void displayRoute() {
        System.out.println(
                source.getName() + " -> " +
                destination.getName() +
                " | Distance: " + distance + " km"
        );
    }
}