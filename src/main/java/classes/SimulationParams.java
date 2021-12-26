package classes;

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

    public SimulationParams(int width, int height, double jungleRatio, int grassEnergy, int animalStartEnergy, int dailyEnergyCost, int minProcreateEnergy, int startingAnimalCount, int updateStepTime) {
        this.width = width;
        this.height = height;
        this.jungleRatio=jungleRatio;
        this.grassEnergy=grassEnergy;
        this.animalStartEnergy=animalStartEnergy;
        this.dailyEnergyCost=dailyEnergyCost;
        this.minProcreateEnergy=minProcreateEnergy;
        this.startingAnimalCount=startingAnimalCount;
        this.updateStepTime=updateStepTime;
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
}
