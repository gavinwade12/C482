package c482.controllers;

import c482.models.InhousePart;
import c482.models.InventoryManager;
import c482.models.OutsourcedPart;
import c482.models.Part;
import c482.utils.PartFormValidator;
import c482.view.FxmlView;
import c482.view.StageManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class AddPartController extends BaseController implements Initializable {
    
    private StageManager stageManager;
    private InventoryManager inventoryManager;
    
    @FXML private RadioButton inhouse;
    @FXML private RadioButton outsourced;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private Label machineIDLabel;
    @FXML private TextField machineID;
    @FXML private Label companyNameLabel;
    @FXML private TextField companyName;
    @FXML private Label error;
    
    public AddPartController(StageManager stageManager, InventoryManager inventoryManager) {
        this.stageManager = stageManager;
        this.inventoryManager = inventoryManager;
    }
    
    public AddPartController() {
        this(StageManager.getInstance(), InventoryManager.getInstance());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final ToggleGroup group = new ToggleGroup();
        inhouse.setToggleGroup(group);
        outsourced.setToggleGroup(group);
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (group.getSelectedToggle().equals(inhouse)) {
                inhouseButtonClicked();
            } else {
                outsourcedButtonClicked();
            }
        });
    }    
    
    @Override
    public void setData(Object data) {
    }
    
    private void inhouseButtonClicked() {
        companyNameLabel.setVisible(false);
        companyName.setVisible(false);
        
        machineID.setText(null);
        machineIDLabel.setVisible(true);
        machineID.setVisible(true);
    }
    
    private void outsourcedButtonClicked() {
        machineIDLabel.setVisible(false);
        machineID.setVisible(false);
        
        companyName.setText(null);
        companyNameLabel.setVisible(true);
        companyName.setVisible(true);
    }
    
    public void saveButtonClicked() {
        PartFormValidator v = new PartFormValidator(name, inv, price, max, min,
                inhouse.isSelected(), machineID, companyName, error);
        if (v.validate()) {
            addPart();
            stageManager.switchScene(FxmlView.MAIN);
        }
    }
    
    public void cancelButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to leave without saving?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            stageManager.switchScene(FxmlView.MAIN);
        }
    }
    
    private void addPart() {
        int invCount = Integer.parseInt(inv.getText());
        double priceNum = Double.parseDouble(price.getText().replace("$", ""));
        int maxCount = Integer.parseInt(max.getText());
        int minCount = Integer.parseInt(min.getText());
        
        Part part;
        if (inhouse.isSelected()) {
            part = new InhousePart(name.getText(), priceNum,invCount, minCount,
                    maxCount, Integer.parseInt(machineID.getText()));
        } else {
            part = new OutsourcedPart(name.getText(), priceNum, 
                invCount, minCount, maxCount, companyName.getText());
        }
        inventoryManager.inventory.addPart(part);
    }
    
}
