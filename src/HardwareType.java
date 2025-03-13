public abstract class HardwareType {
    public String getMemorySize;
    private String category;
    private String type;
    private String ID;
    private String brand;
    private String CPU;
    private double price;

    public HardwareType(String category, String type, String ID, String brand, String CPU, double price) {
        this.category = category;
        this.type = type;
        this.ID = ID;
        this.brand = brand;
        this.CPU = CPU;
        this.price = price;
    }

    // Getters
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getID() { return ID; }
    public String getBrand() { return brand; }
    public String getCPU() { return CPU; }
    public double getPrice() { return price; }

    // Placeholder will override in subclasses
    public int getMemorySize() { return 0; }
    public int getCapacitySize() { return 0; }
    public double getScreenSize() { return 0; }

    // Setters
    public void setCategory(String category) { this.category = category; }
    public void setType(String type) { this.type = type; }
    public void setID(String ID) { this.ID = ID; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setCPU(String CPU) {this.CPU = CPU; }
    public void setPrice(double price) {this.price = price; }

    // Helper method to find the hardware item by its ID we get the ID from the table and then use this
    // to find the item that is associated with the ID that we found.
    public static HardwareType findHardwareByID(String id) {
        for(HardwareType h : ComputerUtils.getHardware()) {
            if(h.getID().equals(id)) {
                return h;
            }
        }
        return null;
    }
}
