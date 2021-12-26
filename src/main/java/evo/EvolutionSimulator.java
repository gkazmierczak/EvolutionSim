package evo;

import classes.SimulationParams;
import gui.SimulationParamMenu;
import interfaces.IWorldMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EvolutionSimulator extends Application {
    Stage primaryStage;
    SimulationParams simulationParams;
    SimulationParamMenu simulationParamMenu=new SimulationParamMenu(this);
    LoopedWorldMap loopedWorldMap;
    BoundedWorldMap boundedWorldMap;
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        Scene scene = simulationParamMenu.getScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void startSimulation(){
        simulationParams=simulationParamMenu.getSimulationParams();
        this.loopedWorldMap=new LoopedWorldMap(simulationParams.width, simulationParams.height,simulationParams.jungleRatio);
        System.out.println(this.loopedWorldMap.jungleWidth);
        System.out.println(this.loopedWorldMap.jungleHeight);
//        this.boundedWorldMap=new BoundedWorldMap(simulationParams.width, simulationParams.height,simulationParams.jungleRatio);
    }
}
