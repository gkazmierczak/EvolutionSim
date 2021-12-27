package evo;

import classes.SimulationParams;
import gui.MapRenderer;
import gui.SimulationParamMenu;
import gui.SimulationRenderer;
import interfaces.IWorldMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EvolutionSimulator extends Application {
    Stage primaryStage;
    SimulationParams simulationParams;
    SimulationParamMenu simulationParamMenu=new SimulationParamMenu(this);
    LoopedWorldMap loopedWorldMap;
    SimulationEngine loopedWorldSimulationEngine;
    SimulationRenderer renderer;
//    MapRenderer loopedWorldRenderer;
    BoundedWorldMap boundedWorldMap;
    Thread engineThread1;
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Evolution Simulator");
        Scene scene = simulationParamMenu.getScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void updateUI(){
        Platform.runLater(()->{
            renderer.getNextFrame();
        });

    }
    public void startSimulation(){
        simulationParams=simulationParamMenu.getSimulationParams();
        System.out.println("got params");
        this.loopedWorldMap=new LoopedWorldMap(simulationParams.width, simulationParams.height,simulationParams.jungleRatio);
        System.out.println("got map1");
        this.boundedWorldMap=new BoundedWorldMap(simulationParams.width, simulationParams.height,simulationParams.jungleRatio);
        System.out.println("got map2");
        this.renderer=new SimulationRenderer(this.primaryStage,this.loopedWorldMap,this.boundedWorldMap);
        System.out.println("got renderer");
        this.loopedWorldSimulationEngine=new SimulationEngine(loopedWorldMap,simulationParams);
        this.loopedWorldSimulationEngine.setRenderer(renderer.getLoopedMapRenderer());
        loopedWorldSimulationEngine.init();
        loopedWorldSimulationEngine.setSimulator(this);
        loopedWorldMap.printAnimals();
        engineThread1=new Thread(loopedWorldSimulationEngine);
        engineThread1.start();
//        loopedWorldSimulationEngine.run();

//        System.out.println(this.loopedWorldMap.jungleWidth);
//        System.out.println(this.loopedWorldMap.jungleHeight);
//        this.boundedWorldMap=new BoundedWorldMap(simulationParams.width, simulationParams.height,simulationParams.jungleRatio);
    }
}
