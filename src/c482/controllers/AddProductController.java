package c482.controllers;

import c482.models.InventoryManager;
import c482.models.Part;
import c482.models.Product;
import c482.utils.PriceCellFactory;
import c482.utils.ProductFormValidator;
import c482.utils.SearchableList;
import c482.view.FxmlView;
import c482.view.StageManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddProductController extends BaseController implements Initializable {
    
    private StageManager stageManager;
    private InventoryManager inventoryManager;
    private final ObservableList<Part> availableParts = FXCollections.observableArrayList();
    private final ObservableList<Part> addedParts = FXCollections.observableArrayList();
    private SearchableList searchableAvailableParts;
    
    @FXML private TextField searchInput;
    @FXML private TextField productName;
    @FXML private TextField productInventory;
    @FXML private TextField productPrice;
    @FXML private TextField productMax;
    @FXML private TextField productMin;
    @FXML private Label error;
    @FXML private TableView<Part> availablePartsTable;
    @FXML private TableColumn<Part, Integer> availablePartsIDColumn;
    @FXML private TableColumn<Part, String> availablePartsNameColumn;
    @FXML private TableColumn<Part, Integer> availablePartsInventoryLevelColumn;
    @FXML private TableColumn<Part, String> availablePartsPricePerUnitColumn;
    @FXML private TableView<Part> addedPartsTable;
    @FXML private TableColumn<Part, Integer> addedPartsIDColumn;
    @FXML private TableColumn<Part, String> addedPartsNameColumn;
    @FXML private TableColumn<Part, Integer> addedPartsInventoryLevelColumn;
    @FXML private TableColumn<Part, String> addedPartsPricePerUnitColumn;
    
    public AddProductController(StageManager stageManager, InventoryManager inventoryManager) {
        this.stageManager = stageManager;
        this.inventoryManager = inventoryManager;
        this.availableParts.setAll(inventoryManager.inventory.allParts);
    }
    
    public AddProductController() {
        this(StageManager.getInstance(), InventoryManager.getInstance());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PropertyValueFactory idColumnFactory = new PropertyValueFactory("partID");
        PropertyValueFactory nameColumnFactory = new PropertyValueFactory("name");
        PropertyValueFactory inventoryLevelColumnFactory = new PropertyValueFactory("inStock");
        PropertyValueFactory priceColumnFactory = new PropertyValueFactory("price");
        
        availablePartsIDColumn.setCellValueFactory(idColumnFactory);
        availablePartsNameColumn.setCellValueFactory(nameColumnFactory);
        availablePartsInventoryLevelColumn.setCellValueFactory(inventoryLevelColumnFactory);
        availablePartsPricePerUnitColumn.setCellValueFactory(priceColumnFactory);
        availablePartsPricePerUnitColumn.setCellFactory(new PriceCellFactory<>());
        searchableAvailableParts = new SearchableList(availableParts);
        searchableAvailableParts.bind(availablePartsTable);
        
        addedPartsIDColumn.setCellValueFactory(idColumnFactory);
        addedPartsNameColumn.setCellValueFactory(nameColumnFactory);
        addedPartsInventoryLevelColumn.setCellValueFactory(inventoryLevelColumnFactory);
        addedPartsPricePerUnitColumn.setCellValueFactory(priceColumnFactory);
        addedPartsPricePerUnitColumn.setCellFactory(new PriceCellFactory<>());
        Bindings.bindContent(addedPartsTable.getItems(), addedParts);
    }

    @Override
    public void setData(Object data) {
    }
    
    public void searchButtonClicked() {
        searchableAvailableParts.filter(searchInput.getText());
        availablePartsTable.refresh();
    }
    
    public void addButtonClicked() {
        Part part = availablePartsTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        addedParts.add(part);
        availableParts.remove(part);
    }
    
    public void deleteButtonClicked() {
        Part part = addedPartsTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to remove this part from the product?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            addedParts.remove(part);
            availableParts.add(part);
        }
    }
    
    public void saveButtonClicked() {
        ProductFormValidator v = new ProductFormValidator(productName, 
                productInventory, productPrice, productMax, productMin, addedParts, error);
        if (v.validate()) {
            addProduct();
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
    
    private void addProduct() {
        String name = productName.getText();
        int inv = Integer.parseInt(productInventory.getText());
        double price = Double.parseDouble(productPrice.getText().replace("$", ""));
        int max = Integer.parseInt(productMax.getText());
        int min = Integer.parseInt(productMin.getText());
        Product product = new Product(name, price, inv, min, max, addedParts);
        inventoryManager.inventory.addProduct(product);
    }
    
}
