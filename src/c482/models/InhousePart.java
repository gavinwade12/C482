package c482.models;

public class InhousePart extends Part {
    
    private int machineID;
    
    public InhousePart(String name, double price, int inStock, int min, int max,
            int machineID) {
        super(name, price, inStock, min, max);
        this.machineID = machineID;
    }
    
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    
    public int getMachineID() {
        return machineID;
    }
}
