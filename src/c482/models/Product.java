package c482.models;

import c482.utils.SearchableList;
import java.util.ArrayList;
import java.util.List;

public class Product implements SearchableList.NamedItem {
    
    private final ArrayList<Part> associatedParts = new ArrayList<>();
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    
    public Product() {
    }
    
    public Product(String name, double price, int inStock, int min, int max, 
            List<Part> associatedParts) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.associatedParts.addAll(associatedParts);
    }
    
    public void addAssociatedPart(Part associatedPart) {
        associatedParts.add(associatedPart);
    }
    
    public boolean removeAssociatedPart(int index) {
        return associatedParts.remove(index) != null;
    }
    
    public Part lookupAssociatedPart(int index) {
        return associatedParts.get(index);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
