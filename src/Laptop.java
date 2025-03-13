public class Laptop extends HardwareType{
    private int memorySize;
    private int capacitySize;
    private double screenSize;

    public Laptop(String category, String type, String ID, String brand, String CPU, double price, int memorySize,
                  int capacitySize, double screenSize) {
        super(category, type, ID, brand, CPU, price);
        this.memorySize = memorySize;
        this.capacitySize = capacitySize;
        this.screenSize = screenSize;
    }

    @Override
    public int getMemorySize() { return memorySize;}
    @Override
    public int getCapacitySize() { return capacitySize; }
    @Override
    public double getScreenSize() { return screenSize; }

    public void setMemorySize(int memorySize) { this.memorySize = memorySize; }

    public void setCapacitySize(int capacitySize) { this.capacitySize = capacitySize; }

    public void setScreenSize(double screenSize) { this.screenSize = screenSize; }
}
