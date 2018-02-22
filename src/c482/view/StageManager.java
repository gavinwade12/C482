package c482.view;

import c482.controllers.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
    
    private static StageManager instance;
    private final Stage stage;
    
    protected StageManager(Stage stage) {
        this.stage = stage;
    }
    
    public static StageManager getInstance() {
        return instance;
    }
    
    public static StageManager setStage(Stage stage) {
        if (instance == null) {
            instance = new StageManager(stage);
        }
        return instance;
    }
    
    public void switchScene(final FxmlView view) {
        switchScene(view, null);
    }
    
    public void switchScene(final FxmlView view, Object data) {
        Parent page = loadFxmlPage(view.getFxmlFile(), data);
        show(page, view.getTitle());
    }
    
    private void show(Parent page, String title) {
        Scene scene = prepareScene(page);
        
        stage.setTitle(title);
        stage.setScene(scene);
        
        stage.setMinHeight(page.minHeight(-1));
        stage.setMinWidth(page.minWidth(-1));
        stage.setMaxHeight(page.maxHeight(-1));
        stage.setMaxWidth(page.maxWidth(-1));
        
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }
    
    private Scene prepareScene(Parent page) {
        Scene scene = stage.getScene();
        
        if (scene == null) {
            scene = new Scene(page);
        }
        scene.setRoot(page);
        return scene;
    }
    
    private Parent loadFxmlPage(final String filePath, final Object data) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
        Parent page = null;
        try {
            page = loader.load();
        } catch (Exception e) {
            System.err.println("Unable to load page from path \"" + filePath + "\".");
            System.err.println(e.toString());
            System.exit(2);
        }
        loader.<BaseController>getController().setData(data);
        return page;
    }
    
}
