package c482.models;

public class OutsourcedPart extends Part {
    
    private String companyName;
    
    public OutsourcedPart(String name, double price, int inStock, int min,
            int max, String companyName) {
        super(name, price, inStock, min, max);
        this.companyName = companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getCompanyName() {
        return companyName;
    }
}
