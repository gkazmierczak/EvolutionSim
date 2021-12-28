package evo;


import classes.SimulationParams;
import enums.MapType;
import gui.MapRenderer;

public class SimulationEngine implements Runnable {
    private GenericWorldMap map;
    private SimulationParams simulationParams;
    private MapRenderer renderer;
    private int updateDelay;
    private boolean isRunning=false;
    EvolutionSimulator simulator;
    private boolean isMagic;
    public volatile boolean threadSuspended=false;
    int respawnsRemaining=3;

    public SimulationEngine(GenericWorldMap map, SimulationParams simulationParams){
        this.map=map;
        this.simulationParams=simulationParams;
        this.updateDelay=simulationParams.updateStepTime;
        this.isMagic=simulationParams.isMagic;
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
    public MapType getSimulatedMapType(){
        System.out.println(this.map.getMapType());
        return this.map.getMapType();
    }
    public void interrupt(){
        this.isRunning=false;
    }


    public void run(){
        isRunning=true;
//        simulator.updateUI();
        while(isRunning){

            map.removeDeadAnimals();
            map.moveAnimals();
            map.feedAnimals();
            map.reproduceAnimals(simulationParams.minProcreateEnergy);
            boolean spawned=map.spawnGrass(simulationParams.grassEnergy);
            if(isMagic){
                if(map.getAnimalCount()<=5 && respawnsRemaining>0){
                    map.respawnAnimals(5,simulationParams.animalStartEnergy);
                    this.simulator.printMessage("5 animals appeared on "+map.getMapType().toString()+" map\n");
                    respawnsRemaining-=1;
                }
            }
            if(!spawned){
                if(map.getAnimalCount()==0){
                    this.simulator.simulationFinished(this);
                    break;
                }
            }
//            map.printAnimals();
            this.simulator.updateMapRender(map.getMapType());
//            break;

            try {
                Thread.sleep(updateDelay);
                synchronized(this) {
                    while (threadSuspended)
                        wait(100);
                }
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
