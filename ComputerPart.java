package computerbuilder;


public class ComputerPart {
    private String name;
    private String description;
    private double price;
    private String imagePath;


    public ComputerPart(String name, String description, double price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }


    public ComputerPart(String name, double price) {
        this(name, "No description", price, "default.png");
    }


    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }


    public String getPartInfo() {
        return String.format("%s: $%.2f", name, price);
    }


    public void displayPartInfo() {
        System.out.printf("Part: %s | Price: $%.2f%n", name, price);
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f", name, price);
    }
}