package gui;

import classes.SimulationParams;
import enums.MapType;
import evo.SimulationEngine;
import interfaces.IButtonPressHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SimulationRenderer {
    private MapRenderer loopedMapRenderer;
    private MapRenderer boundedMapRenderer;
    private final SimulationParams.LoopedWorldMap loopedWorldMap;
    private final SimulationParams.BoundedWorldMap boundedWorldMap;
    private OutputLog outputLog;
    private final Stage primaryStage;
    private Button saveBtn;
    private Button highlightBtn;
    private final IButtonPressHandler handler;
    public SimulationRenderer(Stage primaryStage, SimulationParams.LoopedWorldMap loopedWorldMap, SimulationParams.BoundedWorldMap boundedWorldMap, IButtonPressHandler handler){
        this.primaryStage=primaryStage;
        this.loopedWorldMap=loopedWorldMap;
        this.boundedWorldMap=boundedWorldMap;
        this.handler=handler;
        this.outputLog=new OutputLog();
        init();
    }
    public void init(){
        this.loopedMapRenderer=new MapRenderer(loopedWorldMap,handler);
        loopedMapRenderer.init();
        this.boundedMapRenderer=new MapRenderer(boundedWorldMap,handler);
        boundedMapRenderer.init();
        HBox mainBox = new HBox();
        mainBox.getChildren().add(loopedMapRenderer.getCurrentView());
        VBox middleBox = new VBox();
        outputLog=new OutputLog();
        highlightBtn=new Button("Highlight");
        highlightBtn.setOnAction((event)-> handler.handleButtonPress(highlightBtn,"HIGHLIGHT"));
        saveBtn=new Button("Save CSV");
        saveBtn.setOnAction((event)-> handler.handleButtonPress(saveBtn,"SAVE"));
        middleBox.getChildren().add(loopedMapRenderer.getAnimalStatBox().vBox);
        middleBox.getChildren().add(highlightBtn);
        middleBox.getChildren().add(saveBtn);
        middleBox.getChildren().add(boundedMapRenderer.getAnimalStatBox().vBox);
        middleBox.getChildren().add(outputLog.textArea);
        mainBox.getChildren().add(middleBox);
        mainBox.getChildren().add(boundedMapRenderer.getCurrentView());
        this.primaryStage.getScene().setRoot(mainBox);
        this.primaryStage.getScene().getStylesheets().add("stylesheet.css");
        this.primaryStage.setMinWidth(1280);
        this.primaryStage.setMinHeight(720);
        this.primaryStage.setWidth(1280.0);
        this.primaryStage.setHeight(720.0);
        this.loopedMapRenderer.updateWidth(1280.0);
        this.loopedMapRenderer.updateHeight(720.0);
        updateMiddleVBoxWidth(1280.0);
        updateMiddleVBoxHeight(720.0);
        middleBox.setAlignment(Pos.CENTER);
        this.boundedMapRenderer.updateHeight(720.0);
        this.boundedMapRenderer.updateWidth(1280.0);
        this.primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.loopedMapRenderer.updateWidth(newValue);
            this.updateMiddleVBoxWidth((double) newValue);
            this.boundedMapRenderer.updateWidth(newValue);
        });
        this.primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            this.loopedMapRenderer.updateHeight(newValue);
            this.updateMiddleVBoxHeight((double) newValue);
            this.boundedMapRenderer.updateHeight(newValue);
        });

    }
    public void highlightGenotype(MapType mapType){
        if(mapType==MapType.LOOPED){
            this.loopedMapRenderer.highlightGenotype();
        }
        else{
            this.boundedMapRenderer.highlightGenotype();
        }
    }
    public MapRenderer getBoundedMapRenderer() {
        return boundedMapRenderer;
    }

    public MapRenderer getLoopedMapRenderer() {
        return loopedMapRenderer;
    }
    public void simulationFinished(SimulationEngine engine){
        outputLog.printMessage("Simulation of "+engine.getSimulatedMapType()+" world has finished.\n");
    }
    public void printMessageToLog(String message){
        this.outputLog.printMessage(message);
    }
    private void updateMiddleVBoxWidth(double newValue){
        this.outputLog.updateWidth(newValue);
    }
    private void updateMiddleVBoxHeight(double newValue){
        this.outputLog.updateHeight(newValue);
    }

}
