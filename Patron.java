// this class represents a library patron with ID, name, address, and overdue fine.

public class Patron {
    // Instance variables
    private String id;
    private String name;
    private String address;
    private double overdueFine;

    //Constructor
    public Patron(String id, String name, String address, double overdueFine) {
        this.id = id;
        this.name = name;
        this.address = address;
        setOverdueFine(overdueFine);
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getOverdueFine() { return overdueFine; }

    public void setOverdueFine(double fine) {
        if (fine < 0.00) {
            this.overdueFine = 0.00;
        } else if (fine > 250.00) {
            this.overdueFine = 250.00;
        } else {
            this.overdueFine = fine;
        }
    }
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Address: " + address + " | Fine: $" + String.format("%.2f", overdueFine);
    }
}