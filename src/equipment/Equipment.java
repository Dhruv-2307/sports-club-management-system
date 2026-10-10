package equipment;

public class Equipment {
    private int id;
    private String name;
    private int totalQuantity;
    private int availableQuantity;
    private String location;

    public Equipment(int id, String name, int totalQuantity,
                     int availableQuantity, String location) {
        this.id = id;
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getLocation() {
        return location;
    }
}