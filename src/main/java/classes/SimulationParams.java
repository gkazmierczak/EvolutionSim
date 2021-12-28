package classes;

import enums.MapType;
import interfaces.IPositionObserver;
import interfaces.IWorldMap;

public class SimulationParams {
    public int width;
    public int height;
    public double jungleRatio;
    public int grassEnergy;
    public int animalStartEnergy;
    public int dailyEnergyCost;
    public int minProcreateEnergy;
    public int grassSpawnRate=1;
    public int startingAnimalCount;
    public int updateStepTime;
    public boolean isMagic;
    public SimulationParams(int width, int height, double jungleRatio, int grassEnergy, int animalStartEnergy, int dailyEnergyCost, int minProcreateEnergy, int startingAnimalCount, int updateStepTime,boolean isMagic) {
        this.width = width;
        this.height = height;
        this.jungleRatio=jungleRatio;
        this.grassEnergy=grassEnergy;
        this.animalStartEnergy=animalStartEnergy;
        this.dailyEnergyCost=dailyEnergyCost;
        this.minProcreateEnergy=minProcreateEnergy;
        this.startingAnimalCount=startingAnimalCount;
        this.updateStepTime=updateStepTime;
        this.isMagic=isMagic;
    }

    public SimulationParams() {
    }

    public void setAnimalStartEnergy(int animalStartEnergy) {
        this.animalStartEnergy = animalStartEnergy;
    }

    public void setDailyEnergyCost(int dailyEnergyCost) {
        this.dailyEnergyCost = dailyEnergyCost;
    }

    public void setGrassEnergy(int grassEnergy) {
        this.grassEnergy = grassEnergy;
    }

    public void setGrassSpawnRate(int grassSpawnRate) {
        this.grassSpawnRate = grassSpawnRate;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setJungleRatio(double jungleRatio) {
        this.jungleRatio = jungleRatio;
    }

    public void setMinProcreateEnergy(int minProcreateEnergy) {
        this.minProcreateEnergy = minProcreateEnergy;
    }

    public void setStartingAnimalCount(int startingAnimalCount) {
        this.startingAnimalCount = startingAnimalCount;
    }

    public void setUpdateStepTime(int updateStepTime) {
        this.updateStepTime = updateStepTime;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setMagic(boolean magic) {
        isMagic = magic;
    }

    public static class BoundedWorldMap extends Grass.GenericWorldMap implements IWorldMap, IPositionObserver {

        @Override
        public boolean canMoveTo(Vector2D position) {
            return isInBounds(position);
        }


        public BoundedWorldMap(int width, int height,double jungleRatio){
            super(width,height,jungleRatio);
            this.mapType= MapType.BOUNDED;
        }

    }

    public static class LoopedWorldMap extends Grass.GenericWorldMap implements IWorldMap, IPositionObserver {

        @Override
        public void positionChanged(Vector2D oldPosition, Vector2D newPosition, Animal animal) {
            if (isInBounds(newPosition)){
                super.positionChanged(oldPosition, newPosition, animal);
            }
            else{
                if(newPosition.x>=0 && newPosition.y>=0){
                    newPosition=new Vector2D(newPosition.x%width, newPosition.y%height );
                }
                else if(newPosition.x>=0) {
                    newPosition=new Vector2D(newPosition.x%width, height+newPosition.y );
                }
                else if(newPosition.y>=0) {
                    newPosition=new Vector2D(width+ newPosition.x, newPosition.y%height );
                }

                else{
                    newPosition=new Vector2D(width+ newPosition.x, height+newPosition.y );
                }
                super.positionChanged(oldPosition, newPosition, animal);
                animal.setPositionLooped(newPosition);
            }
        }

        public LoopedWorldMap(int width, int height,double jungleRatio) {
            super(width, height,jungleRatio);
            this.mapType= MapType.LOOPED;
        }
    }
}
