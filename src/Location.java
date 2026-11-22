public class Location {

    private final int id;
    private final String name;

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void displayLocation() {
        System.out.println("Location ID: " + id);
        System.out.println("Location Name: " + name);
    }
}