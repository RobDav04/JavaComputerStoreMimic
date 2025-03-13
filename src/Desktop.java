public class Desktop extends HardwareType{
    private int memorySize;
    private int capacitySize;

    public Desktop(String category, String type, String ID, String brand, String CPU, double price, int memorySize,
                   int capacitySize) {
        super(category, type, ID, brand, CPU, price);
        this.memorySize = memorySize;
        this.capacitySize = capacitySize;
    }

    @Override
    public int getMemorySize() { return memorySize;}
    @Override
    public int getCapacitySize() { return capacitySize;}

    public void setMemorySize(int memorySize) { this.memorySize = memorySize; }

    public void setCapacitySize(int capacitySize) { this.capacitySize = capacitySize; }
}
