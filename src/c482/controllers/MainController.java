package c482.controllers;

import c482.models.InventoryManager;
import c482.models.Part;
import c482.models.Product;
import c482.utils.PriceCellFactory;
import c482.utils.SearchableList;
import c482.view.FxmlView;
import c482.view.StageManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController extends BaseController implements Initializable {
    
    private final StageManager stageManager;
    private final InventoryManager inventoryManager;
    private final ObservableList<Part> parts = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private SearchableList searchableParts;
    private SearchableList searchableProducts;
    
    @FXML private TextField partsSearchInput;
    @FXML private TextField productsSearchInput;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, String> partPricePerUnitColumn;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Product, String> productPricePerUnitColumn;
    
    public MainController(StageManager stageManager, InventoryManager inventoryManager) {
        this.stageManager = stageManager;
        this.inventoryManager = inventoryManager;
        this.parts.setAll(this.inventoryManager.inventory.allParts);
        this.products.setAll(this.inventoryManager.inventory.products);
    }
    
    public MainController() {
        this(StageManager.getInstance(), InventoryManager.getInstance());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDColumn.setCellValueFactory(new PropertyValueFactory("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory("inStock"));
        partPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory("price"));
        partPricePerUnitColumn.setCellFactory(new PriceCellFactory<>());
        searchableParts = new SearchableList(parts);
        searchableParts.bind(partsTable);
        
        productIDColumn.setCellValueFactory(new PropertyValueFactory("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory("inStock"));
        productPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory("price"));
        productPricePerUnitColumn.setCellFactory(new PriceCellFactory<>());
        searchableProducts = new SearchableList(products);
        searchableProducts.bind(productsTable);
    }
    
    @Override
    public void setData(Object data) {
    }
    
    public void exit() {
        System.exit(0);
    }
    
    public void addPartButtonClicked() {
        stageManager.switchScene(FxmlView.ADD_PART);
    }
    
    public void modifyPartButtonClicked() {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        stageManager.switchScene(FxmlView.MODIFT_PART, part);
    }
    
    public void deletePartButtonClicked() {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete this part?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inventoryManager.inventory.deletePart(part);
        partsTable.getItems().remove(part);
        }
    }
    
    public void searchPartsButtonClicked() {
        searchableParts.filter(partsSearchInput.getText());
        partsTable.refresh();
    }
    
    public void addProductButtonClicked() {
        stageManager.switchScene(FxmlView.ADD_PRODUCT);
    }
    
    public void modifyProductButtonClicked() {
        final Product product = productsTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }
        stageManager.switchScene(FxmlView.MODIFY_PRODUCT, product);
    }
    
    public void deleteProductButtonClicked() {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }
        
        boolean hasParts = false;
        try {
            product.lookupAssociatedPart(0);
            hasParts = true;
        } catch (Exception e) {}
        
        if (hasParts) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Deletion Failed");
            alert.setHeaderText("The requested deletion could not be completed.");
            alert.setContentText("The product selected has associated parts and cannot be deleted.");
            alert.showAndWait();
            return;
        }
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete this product?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inventoryManager.inventory.removeProduct(
                productsTable.getSelectionModel().getFocusedIndex());
            productsTable.getItems().remove(product);
        }
    }
    
    public void searchProductsButtonClicked() {
        searchableProducts.filter(productsSearchInput.getText());
        productsTable.refresh();
    }
    
}
