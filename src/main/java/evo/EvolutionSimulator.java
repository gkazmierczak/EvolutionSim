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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class EvolutionSimulator extends Application implements IButtonPressHandler {
    Stage primaryStage;
    SimulationParams simulationParams;
    SimulationParamMenu simulationParamMenu = new SimulationParamMenu(this);
    LoopedWorldMap loopedWorldMap;
    SimulationEngine loopedWorldSimulationEngine;
    SimulationRenderer renderer;
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
        this.loopedWorldMap = new LoopedWorldMap(simulationParams.width, simulationParams.height, simulationParams.jungleRatio);
        this.boundedWorldMap = new BoundedWorldMap(simulationParams.width, simulationParams.height, simulationParams.jungleRatio);
        this.renderer = new SimulationRenderer(this.primaryStage, this.loopedWorldMap, this.boundedWorldMap, this);
        this.loopedWorldSimulationEngine = new SimulationEngine(loopedWorldMap, simulationParams);
        this.loopedWorldSimulationEngine.setRenderer(renderer.getLoopedMapRenderer());
        loopedWorldSimulationEngine.init();
        loopedWorldSimulationEngine.setSimulator(this);
        loopedWorldSimulationThread = new Thread(loopedWorldSimulationEngine);
        this.boundedWorldSimulationEngine = new SimulationEngine(boundedWorldMap, simulationParams);
        this.boundedWorldSimulationEngine.setRenderer(renderer.getBoundedMapRenderer());
        boundedWorldSimulationEngine.init();
        boundedWorldSimulationEngine.setSimulator(this);
        boundedWorldSimulationThread = new Thread(boundedWorldSimulationEngine);
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
        primaryStage.setOnCloseRequest(e -> {
            loopedWorldSimulationEngine.interrupt();
            boundedWorldSimulationEngine.interrupt();
            Platform.exit();
        });
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
                List<String[]> simulationData = this.renderer.getLoopedMapRenderer().getSimulationData();
                saveDataAsCSV(simulationData);
            }
            if(boundedWorldSimulationEngine.threadSuspended){
                List<String[]> simulationData = this.renderer.getBoundedMapRenderer().getSimulationData();
                saveDataAsCSV(simulationData);
            }
        }
        else{
            this.renderer.printMessageToLog("At least one simulation needs to be paused in order to save data.\n");
        }
    }

    private void saveDataAsCSV(List<String[]> simulationData) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save data");
        fileChooser.setInitialFileName("simulationData.csv");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage=new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                PrintWriter pw = new PrintWriter(file);
                pw.println("Animal count,Grass count,Average energy,Average lifespan,Avergage children count");
                simulationData.stream()
                        .map(this::convertToCSV)
                        .forEach(pw::println);
                pw.println(this.convertToCSV(calculateAverages(simulationData)));
                pw.close();
                printMessage("Simulation data saved.\n");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private String[] calculateAverages(List<String[]> data){
        double[] totals=new double[5];
        data.forEach((entry)->{
            for (int i = 0; i < 5; i++) {
                totals[i]+=Double.parseDouble(entry[i]);
            }
        });
        String[] averages=new String[5];
        for (int i = 0; i < 5; i++) {
            averages[i]=String.valueOf(totals[i]/data.size());
        }
        return averages;
    }
    private String convertToCSV(String[] data) {
        return String.join(",", data);
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
