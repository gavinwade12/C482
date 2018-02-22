package c482.controllers;

import c482.models.InhousePart;
import c482.models.InventoryManager;
import c482.models.OutsourcedPart;
import c482.models.Part;
import c482.utils.PartFormValidator;
import c482.view.FxmlView;
import c482.view.StageManager;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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

public class ModifyPartController extends BaseController implements Initializable {
    
    private StageManager stageManager;
    private InventoryManager inventoryManager;
    private Part part;
    private final DecimalFormat priceFormat = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    
    @FXML private RadioButton inhouse;
    @FXML private RadioButton outsourced;
    @FXML private TextField partID;
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
    
    public ModifyPartController(StageManager stageManager, InventoryManager inventoryManager) {
        this.stageManager = stageManager;
        this.inventoryManager = inventoryManager;
    }
    
    public ModifyPartController() {
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
        if (data instanceof InhousePart) {
            setPart((InhousePart)data);
            part = (Part)data;
        } else if (data instanceof OutsourcedPart) {
            setPart((OutsourcedPart)data);
            part = (Part)data;
        }
    }
    
    private void setBasePart(Part part) {
        partID.setText(String.valueOf(part.getPartID()));
        name.setText(part.getName());
        inv.setText(String.valueOf(part.getInStock()));
        price.setText("$" + priceFormat.format(part.getPrice()));
        max.setText(String.valueOf(part.getMax()));
        min.setText(String.valueOf(part.getMin()));
    }
    
    private void setPart(InhousePart part) {
        setBasePart(part);
        machineID.setText(String.valueOf(part.getMachineID()));
        inhouse.selectedProperty().set(true);
    }
    
    private void setPart(OutsourcedPart part) {
        setBasePart(part);
        companyName.setText(part.getCompanyName());
        outsourced.selectedProperty().set(true);
    }

    private void inhouseButtonClicked() {
        companyNameLabel.setVisible(false);
        companyName.setVisible(false);
        
        machineIDLabel.setVisible(true);
        machineID.setVisible(true);
    }
    
    private void outsourcedButtonClicked() {
        machineIDLabel.setVisible(false);
        machineID.setVisible(false);
        
        companyNameLabel.setVisible(true);
        companyName.setVisible(true);
    }
    
    public void saveButtonClicked() {
        PartFormValidator v = new PartFormValidator(name, inv, price, max, min,
                inhouse.isSelected(), machineID, companyName, error);
        if (v.validate()) {
            updatePart();
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
    
    private void updatePart() {
        inventoryManager.inventory.deletePart(part);
        
        int invCount = Integer.parseInt(inv.getText());
        double priceNum = Double.parseDouble(price.getText().replace("$", ""));
        int maxCount = Integer.parseInt(max.getText());
        int minCount = Integer.parseInt(min.getText());
        
        Part newPart;
        if (inhouse.isSelected()) {
            newPart = new InhousePart(name.getText(), priceNum, invCount,
                    minCount, maxCount, Integer.parseInt(machineID.getText()));
        } else {
            newPart = new OutsourcedPart(name.getText(), priceNum, 
                invCount, minCount, maxCount, companyName.getText());
        }
        newPart.setPartID(part.getPartID());
        inventoryManager.inventory.addPart(newPart, false);
    }
    
}
