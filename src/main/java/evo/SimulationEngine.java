package evo;


import classes.SimulationParams;
import gui.MapRenderer;
import gui.SimulationRenderer;
import javafx.application.Platform;

import java.util.Timer;

public class SimulationEngine implements Runnable {
    private GenericWorldMap map;
    private SimulationParams simulationParams;
    private MapRenderer renderer;
    private int updateDelay;
    private boolean isRunning=false;
    EvolutionSimulator simulator;
    public SimulationEngine(GenericWorldMap map, SimulationParams simulationParams){
        this.map=map;
        this.simulationParams=simulationParams;
        this.updateDelay=simulationParams.updateStepTime;
    }

    public void setRenderer(MapRenderer renderer) {
        this.renderer = renderer;
    }

    public void setSimulator(EvolutionSimulator simulator) {
        this.simulator = simulator;
    }

    public void init(){
        map.spawnAnimals(simulationParams.startingAnimalCount, simulationParams.animalStartEnergy, simulationParams.dailyEnergyCost);
    }


    public void run(){
        isRunning=true;
        while(isRunning){
            map.removeDeadAnimals();
            map.moveAnimals();
            map.feedAnimals();
            map.reproduceAnimals(simulationParams.minProcreateEnergy);
            boolean spawned=map.spawnGrass(simulationParams.grassEnergy);
            if(!spawned){
                if(map.getAnimalCount()==0){
                    System.out.println("broke");
                    System.out.println(map.getGrassCount());
                    System.out.println(map.jungleEmptyFieldCount);
                    System.out.println(map.steppeEmptyFieldCount);
                    break;
                }
            }
            map.printAnimals();
            this.simulator.updateUI();
//            break;

            try {
                Thread.sleep(updateDelay);
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
