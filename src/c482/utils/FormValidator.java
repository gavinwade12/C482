package c482.utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormValidator {
    
    public static boolean requiredFieldIsValid(TextField textField) {
        return !StringUtils.isEmpty(textField.getText());
    }
    
    public static boolean requiredFieldIsValid(TextField textField, 
            Label errorLabel, String errorMessage) {
        if (!requiredFieldIsValid(textField)) {
            errorLabel.setText(errorMessage);
            textField.requestFocus();
            return false;
        }
        return true;
    }
    
    public static boolean textFieldIsInteger(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean textFieldIsInteger(TextField textField, 
            Label errorLabel, String errorMessage) {
        if (!textFieldIsInteger(textField)) {
            textField.requestFocus();
            errorLabel.setText(errorMessage);
            return false;
        }
        return true;
    }
    
    public static boolean textFieldIsDollarAmount(TextField textField) {
        String text = textField.getText();
        if (text != null) {
            text = text.replace("$", "");
        }
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean textFieldIsDollarAmount(TextField textField, 
            Label errorLabel, String errorMessage) {
        if (!textFieldIsDollarAmount(textField)) {
            errorLabel.setText(errorMessage);
            textField.requestFocus();
            return false;
        }
        return true;
    }
    
}
