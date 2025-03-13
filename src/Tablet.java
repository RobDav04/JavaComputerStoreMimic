public class Tablet extends HardwareType{
    private double screenSize;

    public Tablet(String category, String type, String ID, String brand, String CPU, double price, double screenSize) {
        super(category, type, ID, brand, CPU, price);
        this.screenSize = screenSize;
    }

    @Override
    public double getScreenSize() { return screenSize; }

    public void setScreenSize(double screenSize) { this.screenSize = screenSize; }
}
