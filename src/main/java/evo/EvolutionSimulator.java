package evo;

import classes.SimulationParams;
import enums.MapType;
import gui.SimulationParamMenu;
import gui.SimulationRenderer;
import interfaces.IButtonPressHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class EvolutionSimulator extends Application implements IButtonPressHandler {
    Stage primaryStage;
    SimulationParams simulationParams;
    SimulationParamMenu simulationParamMenu = new SimulationParamMenu(this);
    LoopedWorldMap loopedWorldMap;
    SimulationEngine loopedWorldSimulationEngine;
    SimulationRenderer renderer;
    //    MapRenderer loopedWorldRenderer;
    BoundedWorldMap boundedWorldMap;
    Thread loopedWorldSimulationThread;
    private SimulationEngine boundedWorldSimulationEngine;
    private Thread boundedWorldSimulationThread;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Evolution Simulator");
        Scene scene = simulationParamMenu.getScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public void updateUI() {
//        Platform.runLater(() -> {
//            renderer.getNextFrame();
//        });
//    }
    public void updateMapRender(MapType mapType){
        Platform.runLater(() -> {
            if(mapType==MapType.BOUNDED){
                this.renderer.getBoundedMapRenderer().getNextFrame(false);
            }
            else{
                this.renderer.getLoopedMapRenderer().getNextFrame(false);
            }

        });

    }

    public void initSimulation() {
        simulationParams = simulationParamMenu.getSimulationParams();
        System.out.println("got params");
        this.loopedWorldMap = new LoopedWorldMap(simulationParams.width, simulationParams.height, simulationParams.jungleRatio);
        System.out.println("got map1");
        this.boundedWorldMap = new BoundedWorldMap(simulationParams.width, simulationParams.height, simulationParams.jungleRatio);
        System.out.println("got map2");
        this.renderer = new SimulationRenderer(this.primaryStage, this.loopedWorldMap, this.boundedWorldMap, this);
        System.out.println("got renderer");
        this.loopedWorldSimulationEngine = new SimulationEngine(loopedWorldMap, simulationParams);
        this.loopedWorldSimulationEngine.setRenderer(renderer.getLoopedMapRenderer());
        loopedWorldSimulationEngine.init();
        loopedWorldSimulationEngine.setSimulator(this);
//        loopedWorldMap.printAnimals();
        loopedWorldSimulationThread = new Thread(loopedWorldSimulationEngine);
        this.boundedWorldSimulationEngine = new SimulationEngine(boundedWorldMap, simulationParams);
        this.boundedWorldSimulationEngine.setRenderer(renderer.getBoundedMapRenderer());
        boundedWorldSimulationEngine.init();
        boundedWorldSimulationEngine.setSimulator(this);
//        boundedWorldMap.printAnimals();
        boundedWorldSimulationThread = new Thread(boundedWorldSimulationEngine);
//        boundedWorldSimulationThread.start();
//        loopedWorldSimulationThread.start();
//        loopedWorldSimulationEngine.run();

//        System.out.println(this.loopedWorldMap.jungleWidth);
//        System.out.println(this.loopedWorldMap.jungleHeight);
//        this.boundedWorldMap=new BoundedWorldMap(simulationParams.width, simulationParams.height,simulationParams.jungleRatio);
    }

    public void simulationFinished(SimulationEngine engine) {
        renderer.simulationFinished(engine);
    }

    public void startSimulation(MapType mapType) {
        if (mapType == MapType.BOUNDED) {
            renderer.printMessageToLog("Starting simulation of BOUNDED world.\n");
            this.boundedWorldSimulationThread.start();
        } else if (mapType == MapType.LOOPED) {
            renderer.printMessageToLog("Starting simulation of LOOPED world.\n");
            this.loopedWorldSimulationThread.start();
        }
    }

    public void pauseSimulation(MapType mapType) {
        if (mapType == MapType.LOOPED) {
            renderer.printMessageToLog("Paused simulation of LOOPED world.\n");
           this.loopedWorldSimulationEngine.threadSuspended=true;
        } else {
            renderer.printMessageToLog("Paused simulation of BOUNDED world.\n");
            this.boundedWorldSimulationEngine.threadSuspended=true;
        }

    }

    public void resumeSimulation(MapType mapType) {
        if (mapType == MapType.LOOPED) {
            renderer.printMessageToLog("Resumed simulation of LOOPED world.\n");
            this.loopedWorldSimulationEngine.threadSuspended=false;
        } else {
            renderer.printMessageToLog("Resumed simulation of BOUNDED world.\n");
            this.boundedWorldSimulationEngine.threadSuspended=false;
        }

    }

    public void handleButtonPress(Button button, Object param) {
        if (param instanceof MapType) {
            if (Objects.equals(button.getText(), "Start")) {
                startSimulation((MapType) param);
                button.setText("Pause");
            } else if (Objects.equals(button.getText(), "Pause")) {
                pauseSimulation((MapType) param);
                button.setText("Resume");
            } else if (Objects.equals(button.getText(), "Resume")) {
                resumeSimulation((MapType) param);
                button.setText("Pause");
            }
        }
        else if (param instanceof String){
            if (param.equals("SAVE")){
                this.saveSimulationData();
            }
            else if (param.equals("HIGHLIGHT")){
                this.highlight();
            }
        }
    }
    public void printMessage(String message){
        this.renderer.printMessageToLog(message);
    }
    public void saveSimulationData(){
        if(loopedWorldSimulationEngine.threadSuspended||boundedWorldSimulationEngine.threadSuspended){
            if(loopedWorldSimulationEngine.threadSuspended){

            }
            if(boundedWorldSimulationEngine.threadSuspended){

            }
        }
        else{
            this.renderer.printMessageToLog("At least one simulation needs to be paused in order to save data.\n");
        }
    }
    public void highlight(){
        if(loopedWorldSimulationEngine.threadSuspended||boundedWorldSimulationEngine.threadSuspended){
            if(loopedWorldSimulationEngine.threadSuspended){
                renderer.highlightGenotype(MapType.LOOPED);
            }
            if(boundedWorldSimulationEngine.threadSuspended){
                renderer.highlightGenotype(MapType.BOUNDED);
            }
        }
        else{
            this.renderer.printMessageToLog("Simulation needs to be paused before highlighting.\n");
        }
    }
}
