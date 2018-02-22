package c482;

import c482.models.InhousePart;
import c482.models.InventoryManager;
import c482.models.OutsourcedPart;
import c482.models.Part;
import c482.models.Product;
import c482.view.FxmlView;
import c482.view.StageManager;
import java.util.Arrays;
import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {
    
    protected StageManager stageManager;
    protected InventoryManager inventoryManager;
    
    @Override
    public void start(Stage primaryStage) {
        inventoryManager = InventoryManager.getInstance();
        seedData();
        stageManager = StageManager.setStage(primaryStage);
        displayInitialScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    protected void displayInitialScene() {
        stageManager.switchScene(FxmlView.MAIN);
    }
    
    protected void seedData() {
        Part bolt = new InhousePart("Bolt", 0.75, 250, 1, 500, 41235);
        inventoryManager.inventory.addPart(bolt);
        Part screw = new InhousePart("Screw", 0.60, 300, 1, 750, 84730);
        inventoryManager.inventory.addPart(screw);
        Part nail = new InhousePart("Nail", 0.45, 500, 1, 1000, 71628);
        inventoryManager.inventory.addPart(nail);
        Part hammer = new OutsourcedPart("Hammer", 10, 8, 1, 20, "The Hammer Co.");
        inventoryManager.inventory.addPart(hammer);
        Part screwDriver = new OutsourcedPart("Screw Driver", 7.5, 12, 1, 20, "Driven, LLC.");
        inventoryManager.inventory.addPart(screwDriver);
        Part wrench = new OutsourcedPart("Wrench", 12.8, 4, 1, 15, "Wrenchaholics Inc.");
        inventoryManager.inventory.addPart(wrench);
        
        inventoryManager.inventory.addProduct(new Product("Toolbox", 35, 2, 1, 10, 
                Arrays.asList(hammer, screwDriver, wrench)));
    }
    
}
