package c482.models;

import java.util.ArrayList;

public class Inventory {
    
    private int partIDCounter = 1;
    private int productIDCounter = 1;
    
    public ArrayList<Product> products = new ArrayList<>();
    public ArrayList<Part> allParts = new ArrayList<>();
    
    public void addProduct(Product product) {
        addProduct(product, true);
    }
    
    public void addProduct(Product product, boolean setID) {
        if (setID) {
            product.setProductID(productIDCounter);
            productIDCounter++;
        }
        products.add(product);
    }
    
    public boolean removeProduct(int index) {
        return products.remove(index) != null;
    }
    
    public Product lookupProduct(int index) {
        return products.get(index);
    }
    
    public void updateProduct(int index) {
        
    }
    
    public void addPart(Part part) {
        addPart(part, true);
    }
    
    public void addPart(Part part, boolean setID) {
        if (setID) {
            part.setPartID(partIDCounter);
            partIDCounter++;
        }
        allParts.add(part);
    }
    
    public boolean deletePart(Part part) {
        return allParts.remove(part);
    }
    
    public Part lookupPart(int index) {
        return allParts.get(index);
    }
    
    public void updatePart(int index) {
        
    }
    
}
