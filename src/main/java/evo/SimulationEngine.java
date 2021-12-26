package evo;


import classes.SimulationParams;

import java.util.Timer;

public class SimulationEngine implements Runnable {
    private GenericWorldMap map;
    private SimulationParams simulationParams;
    private int updateDelay;
    private boolean isRunning=false;
    public SimulationEngine(GenericWorldMap map, SimulationParams simulationParams){
        this.map=map;
        this.simulationParams=simulationParams;
        this.updateDelay=simulationParams.updateStepTime;

    }
    public void run(){
        isRunning=true;
        while(isRunning){
            map.removeDeadAnimals();
            map.moveAnimals();
            map.feedAnimals();
            map.reproduceAnimals(simulationParams.minProcreateEnergy);
            map.spawnGrass(simulationParams.grassEnergy);
            try {
                Thread.sleep(updateDelay);
            }
            catch (InterruptedException e){
                System.out.println("yep");
            }
        }
    }
}
