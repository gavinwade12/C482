package c482.models;

public class InventoryManager {
    
    private static InventoryManager instance;
    public Inventory inventory;
    
    private InventoryManager() {
        inventory = new Inventory();
    }
    
    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }
    
}
