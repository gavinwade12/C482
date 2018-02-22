package c482.view;

import java.util.ResourceBundle;

public enum FxmlView {
    
    MAIN {
        @Override
        String getTitle() {
            return getStringFromResourceBulde("main.title");
        }
        
        @Override
        String getFxmlFile() {
            return "/c482/fxml/Main.fxml";
        }
    }, ADD_PART {
        @Override
        String getTitle() {
            return getStringFromResourceBulde("part.add.title");
        }
        
        @Override
        String getFxmlFile() {
            return "/c482/fxml/AddPart.fxml";
        }
    }, MODIFT_PART {
        @Override
        String getTitle() {
            return getStringFromResourceBulde("part.modify.title");
        }
        
        @Override
        String getFxmlFile() {
            return "/c482/fxml/ModifyPart.fxml";
        }
    }, ADD_PRODUCT {
        @Override
        String getTitle() {
            return getStringFromResourceBulde("product.add.title");
        }
        
        @Override
        String getFxmlFile() {
            return "/c482/fxml/AddProduct.fxml";
        }
    }, MODIFY_PRODUCT {
        @Override
        String getTitle() {
            return getStringFromResourceBulde("product.modify.title");
        }
        
        @Override
        String getFxmlFile() {
            return "/c482/fxml/ModifyProduct.fxml";
        }
    };
    
    abstract String getTitle();
    abstract String getFxmlFile();

    String getStringFromResourceBulde(String key) {
        return ResourceBundle.getBundle("c482.Bundle").getString(key);
    }
}
