package c482.models;

import c482.utils.SearchableList;

public abstract class Part implements SearchableList.NamedItem {
    
    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    
    public Part(String name, double price, int inStock, int min, int max) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
    
    public int getInStock() {
        return inStock;
    }
    
    public void setMin(int min) {
        this.min = min;
    }
    
    public int getMin() {
        return min;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    public int getMax() {
        return max;
    }
    
    public void setPartID(int partID) {
        this.partID = partID;
    }
    
    public int getPartID() {
        return partID;
    }
}
