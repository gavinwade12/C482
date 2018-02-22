package c482.utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PartFormValidator {
    
    private TextField name;
    private TextField inv;
    private TextField price;
    private TextField max;
    private TextField min;
    private TextField machineID;
    private TextField companyName;
    private Label error;
    private boolean inhouse;
    
    public PartFormValidator(TextField name, TextField inv, TextField price,
            TextField max, TextField min, boolean inhouse, TextField machineID,
            TextField companyName, Label error) {
        this.name = name;
        this.inv = inv;
        this.price = price;
        this.max = max;
        this.min = min;
        this.machineID = machineID;
        this.companyName = companyName;
        this.error = error;
        this.inhouse = inhouse;
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
        } else if (invCount > maxCount) {
            error.setText("Inventory cannot be more than max.");
            inv.requestFocus();
            return false;
        } else if (invCount < minCount) {
            error.setText("Inventory cannot be less than min.");
            inv.requestFocus();
            return false;
        }
        
        if (inhouse) {
            return FormValidator.requiredFieldIsValid(machineID, error, "Machine ID is required.") &&
                   FormValidator.textFieldIsInteger(machineID, error, "Machine ID must be a number.");
        }
        return FormValidator.requiredFieldIsValid(companyName, error, "Company Name is required.");
    }
    
}
