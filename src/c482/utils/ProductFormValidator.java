package c482.utils;

import c482.models.Part;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProductFormValidator {
    
    private final TextField name;
    private final TextField inv;
    private final TextField price;
    private final TextField max;
    private final TextField min;
    private final List<Part> associatedParts;
    private final Label error;
    
    public ProductFormValidator(TextField name, TextField inv, TextField price,
            TextField max, TextField min, List<Part> associatedParts, Label error) {
        this.name = name;
        this.inv = inv;
        this.price = price;
        this.max = max;
        this.min = min;
        this.associatedParts = associatedParts;
        this.error = error;
    }
    
    public boolean validate() {
        error.setText("");
        
        boolean valid = FormValidator.requiredFieldIsValid(name, error, "Name is required.") &&
                FormValidator.requiredFieldIsValid(inv, error, "Inventory count is required.") &&
                FormValidator.textFieldIsInteger(inv, error, "Inventory count must be a number.") &&
                FormValidator.requiredFieldIsValid(price, error, "Price/Cost is required.") &&
                FormValidator.textFieldIsDollarAmount(price, error, "Price/Cost must be a number.") &&
                FormValidator.requiredFieldIsValid(max, error, "Max is required.") &&
                FormValidator.textFieldIsInteger(max, error, "Max must be a number.") &&
                FormValidator.requiredFieldIsValid(min, error, "Min is required.") &&
                FormValidator.textFieldIsInteger(min, error, "Min must be a number.");
        if (!valid) {
            return false;
        }
        
        int invCount = Integer.parseInt(inv.getText());
        double priceNum = Double.parseDouble(price.getText().replace("$", ""));
        int maxCount = Integer.parseInt(max.getText());
        int minCount = Integer.parseInt(min.getText());
        
        if (invCount < 0) {
            error.setText("Inventory count cannot be less than 0.");
            inv.requestFocus();
            return false;
        } else if (priceNum < 0) {
            error.setText("Price/Cost cannot be less than '0.00'.");
            price.requestFocus();
            return false;
        } else if (maxCount < 0) {
            error.setText("Max cannot be less than 0.");
            max.requestFocus();
            return false;
        } else if (minCount < 0) {
            error.setText("Min cannot be less than 0.");
            min.requestFocus();
            return false;
        } else if (maxCount < minCount) {
            error.setText("Max cannot be less than min.");
            max.requestFocus();
            return false;
        }  else if (invCount > maxCount) {
            error.setText("Inventory cannot be more than max.");
            inv.requestFocus();
            return false;
        } else if (invCount < minCount) {
            error.setText("Inventory cannot be less than min.");
            inv.requestFocus();
            return false;
        } else if (associatedParts.size() < 1) {
            error.setText("A product must contain at least one part.");
            return false;
        }
        
        double partsPrice = 0;
        for (Part p : associatedParts) {
            partsPrice += p.getPrice();
        }
        if (partsPrice > priceNum) {
            error.setText("The price of a product cannot be lower than the price of its parts.");
            price.requestFocus();
            return false;
        }
        
        return true;
    }
    
}
