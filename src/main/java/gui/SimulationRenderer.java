package gui;

import evo.BoundedWorldMap;
import evo.GenericWorldMap;
import evo.LoopedWorldMap;
import evo.SimulationEngine;
import interfaces.ButtonPressHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private OutputLog outputLog;
    private Stage primaryStage;
    private HBox mainBox;
    private VBox middleVBox;
    private ButtonPressHandler handler;
    public SimulationRenderer(Stage primaryStage, LoopedWorldMap loopedWorldMap, BoundedWorldMap boundedWorldMap, ButtonPressHandler handler){
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
        this.mainBox=new HBox();
        this.mainBox.getChildren().add(loopedMapRenderer.getCurrentView());
        middleVBox=new VBox();
        outputLog=new OutputLog();
        middleVBox.getChildren().add(outputLog.textArea);
        this.mainBox.getChildren().add(middleVBox);
        this.mainBox.getChildren().add(boundedMapRenderer.getCurrentView());
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
    public void getNextFrame(){
        this.loopedMapRenderer.getNextFrame();
        this.boundedMapRenderer.getNextFrame();
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
