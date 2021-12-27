package gui;

import evo.BoundedWorldMap;
import evo.GenericWorldMap;
import evo.LoopedWorldMap;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimulationRenderer {
    private MapRenderer loopedMapRenderer;
    private MapRenderer boundedMapRenderer;
    private LoopedWorldMap loopedWorldMap;
    private BoundedWorldMap boundedWorldMap;
    private Stage primaryStage;
    private HBox mainBox;
    public SimulationRenderer(Stage primaryStage, LoopedWorldMap loopedWorldMap, BoundedWorldMap boundedWorldMap){
        this.primaryStage=primaryStage;
        this.loopedWorldMap=loopedWorldMap;
        this.boundedWorldMap=boundedWorldMap;
        init();
    }
    public void init(){
        this.loopedMapRenderer=new MapRenderer(loopedWorldMap);
        loopedMapRenderer.init();
        this.boundedMapRenderer=new MapRenderer(boundedWorldMap);
        boundedMapRenderer.init();
        this.mainBox=new HBox();
        this.mainBox.getChildren().add(loopedMapRenderer.getCurrentView());
        this.primaryStage.getScene().setRoot(mainBox);
        this.primaryStage.setWidth(500);
        this.primaryStage.setHeight(500);
        this.primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {this.loopedMapRenderer.updateWidth(newValue);});
        this.primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {this.loopedMapRenderer.updateHeight(newValue);});

    }
    public void getNextFrame(){
        this.loopedMapRenderer.getNextFrame();
        System.out.println("next frame");

//        primaryStage.widthProperty();

    }
    public MapRenderer getBoundedMapRenderer() {
        return boundedMapRenderer;
    }

    public MapRenderer getLoopedMapRenderer() {
        return loopedMapRenderer;
    }
}
