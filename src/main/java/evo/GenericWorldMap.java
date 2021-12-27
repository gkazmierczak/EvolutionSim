package evo;

import classes.Animal;
import classes.Grass;
import classes.Vector2D;
import interfaces.IMapElement;
import interfaces.IPositionObserver;
import interfaces.IWorldMap;

import java.util.*;

public class GenericWorldMap implements IWorldMap, IPositionObserver {
    public int width;
    public int height;
    public int jungleWidth;
    public int jungleHeight;
    private int grassCount=0;
    private int animalCount=0;
    public int jungleEmptyFieldCount;
    public int steppeEmptyFieldCount;
    private long epoque=0;
    private final Random random = new Random();
    private final Map<Vector2D, List<IMapElement>> map = new HashMap<>();
    private final List<Grass> grassToEat= new ArrayList<>();
    private final List<Animal> livingAnimalsList = new ArrayList<>();
    private final List<Animal> allAnimalsList = new ArrayList<>();
    private final HashSet<Vector2D> reproductionLocationSet =new HashSet<>();

    public List<IMapElement> objectsAt(Vector2D position) {
        return map.get(position);
    }

    public void place(IMapElement element, Vector2D position) {
        addToMap(element, position);
        if (element instanceof Animal) {
            livingAnimalsList.add((Animal) element);
            allAnimalsList.add((Animal) element);
            animalCount+=1;
        }

    }

    private void addToMap(IMapElement element, Vector2D position) {
        if (this.map.get(position) != null) {
            this.map.get(position).add(element);
            if(inJungle(position)){
                if(map.get(position).size()==1){
                    System.out.println(jungleEmptyFieldCount + " " + map.get(position).size());
                    jungleEmptyFieldCount-=1;
                }
            }
            else{
                if(map.get(position).size()==1){
                    steppeEmptyFieldCount-=1;
                }
            }
        } else {
            if(inJungle(position)){
                jungleEmptyFieldCount-=1;
            }
            else{
                steppeEmptyFieldCount-=1;
            }
            ArrayList<IMapElement> arrayList = new ArrayList<>();
            arrayList.add(element);
            this.map.put(position, arrayList);
        }
    }

    public boolean canMoveTo(Vector2D position) {
        return true;
    }

    public Grass grassAt(Vector2D position) {
        for (IMapElement element : objectsAt(position)) {
            if (element instanceof Grass) {
                return (Grass) element;
            }
        }
        return null;
    }


    public boolean isOccupied(Vector2D position) {
        return !(this.map.get(position)==null || this.map.get(position).isEmpty());
    }

    public boolean inJungle(Vector2D position){
        return (position.x>=(width-jungleWidth)/2 && position.x<(width-jungleWidth)/2+jungleWidth && position.y>=(height-jungleHeight)/2 && position.y<(height-jungleHeight)/2+jungleHeight );
    }

    public void positionChanged(Vector2D oldPosition, Vector2D newPosition, Animal animal) {
        this.map.get(oldPosition).remove(animal);
        if (inJungle(oldPosition)) {
            if (this.map.get(oldPosition).size() == 0) {
                this.jungleEmptyFieldCount += 1;
            }
        }
        else{
            if (this.map.get(oldPosition).size() == 0) {
                this.steppeEmptyFieldCount+= 1;
            }
        }
//        if(this.map.get(newPosition)==null){
//            if(inJungle(newPosition)){
//                this.jungleEmptyFieldCount-=1;
//            }
//            else{
//                this.steppeEmptyFieldCount-=1;
//            }
//        }
//        else {
//            if (inJungle(newPosition)) {
//                if (this.map.get(newPosition).size() == 0) {
//                    this.jungleEmptyFieldCount -= 1;
//                }
//            } else {
//                if (this.map.get(newPosition).size() == 0) {
//                    this.steppeEmptyFieldCount -= 1;
//                }
//            }
//        }
        addToMap(animal, newPosition);

    }

    public boolean isInBounds(Vector2D position) {
        return position.x >= 0 && position.x < width && position.y >= 0 && position.y < height;
    }

    public void removeDeadAnimals() {
        epoque+=1;
        Iterator<Animal> iterator = livingAnimalsList.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (!animal.isAlive()) {
                animal.setDeathEpoque(epoque);
                this.map.get(animal.getPosition()).remove(animal);
                if(this.map.get(animal.getPosition()).size()==0){
                    if(inJungle(animal.getPosition())){
                        jungleEmptyFieldCount+=1;
                    }
                    else{
                        steppeEmptyFieldCount+=1;
                    }
                }
                animalCount-=1;
                iterator.remove();
            }
        }
    }

    private void spawnGrassInSteppe(int grassEnergy) {
        if(steppeEmptyFieldCount<=0){
            return;
        }
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Vector2D grassPosition = new Vector2D(x, y);
        while ((x > (width - jungleWidth) / 2 && x < ((width - jungleWidth) / 2 + jungleWidth) && y > (height - jungleHeight) / 2 && y < ((height - jungleHeight) / 2 + jungleHeight)) || isOccupied(grassPosition)) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            grassPosition = new Vector2D(x, y);
        }
        Grass grass = new Grass(grassPosition, grassEnergy);
        place(grass, grassPosition);
        grassCount += 1;
    }

    private void spawnGrassInJungle(int grassEnergy) {
        if(jungleEmptyFieldCount<=0){
            return;
        }
        if(jungleEmptyFieldCount>5) {
            int x = random.nextInt(jungleWidth) + (width - jungleWidth) / 2;
            int y = random.nextInt(jungleHeight) + (height - jungleHeight) / 2;
            Vector2D grassPosition = new Vector2D(x, y);
            while (isOccupied(grassPosition)) {
                x = random.nextInt(jungleWidth) + (width - jungleWidth) / 2;
                y = random.nextInt(jungleHeight) + (height - jungleHeight) / 2;
                grassPosition = new Vector2D(x, y);
            }
            Grass grass = new Grass(grassPosition, grassEnergy);
            place(grass, grassPosition);
            grassCount += 1;
//            jungleEmptyFieldCount -= 1;
        }
        else{
            for (int i = (width - jungleWidth) / 2; i < (jungleWidth) + (width - jungleWidth) / 2; i++) {
                for (int j = (height - jungleHeight) / 2; j < (jungleHeight) + (height - jungleHeight) / 2; j++) {
                    if(!isOccupied(new Vector2D(i,j))){
                        Vector2D grassPosition = new Vector2D(i, j);
                        Grass grass = new Grass(grassPosition, grassEnergy);
                        place(grass, grassPosition);
                        grassCount += 1;
//                        jungleEmptyFieldCount -= 1;
                        return;
                    }
                }

            }
        }
    }
    public int getAnimalCount(){
        return this.animalCount;
    }

    public boolean spawnGrass(int grassEnergy) {
        if(steppeEmptyFieldCount>0 || jungleEmptyFieldCount>0) {
            spawnGrassInSteppe(grassEnergy);
            spawnGrassInJungle(grassEnergy);
            return true;
        }
        return false;
    }

    public void moveAnimals() {
        grassToEat.clear();
        reproductionLocationSet.clear();
        for(Animal animal: livingAnimalsList){
            if (objectsAt(animal.getPosition()).size()<=2){
                reproductionLocationSet.remove(animal.getPosition());
            }
            animal.move();
            if(objectsAt(animal.getPosition()).size()>2){
                reproductionLocationSet.add(animal.getPosition());
            }
            Grass grass=grassAt(animal.getPosition());
            if(grass!=null && !grassToEat.contains(grass)) {
                grassToEat.add(grass);
            }


        }
    }

    public void feedAnimals() {
        int maxEnergy=0;
        int maxEnergyCount=1;
        for(Grass grass:grassToEat){
            ArrayList<Animal> animalsToFeed=new ArrayList<>();
            for(IMapElement elem:objectsAt(grass.getPosition())){
                if(elem instanceof Animal){
                    animalsToFeed.add((Animal) elem);
                    if(((Animal) elem).energy>maxEnergy){
                        maxEnergy=((Animal) elem).energy;
                        maxEnergyCount=1;
                    }
                    else if (((Animal) elem).energy==maxEnergy){
                        maxEnergyCount+=1;
                    }
                }
            }
            for(Animal animal:animalsToFeed){
                if(animal.energy==maxEnergy){
                    animal.increaseEnergy(grass.energyValue/maxEnergyCount);
                }
            }
            map.get(grass.getPosition()).remove(grass);
            grassCount-=1;
        }
    }

    public void reproduceAnimals(int requiredEnergy) {
        for(Vector2D position:reproductionLocationSet){
            if(objectsAt(position).size()>=2){
                Animal parent1=null;
                Animal parent2=null;
                for(IMapElement elem:objectsAt(position)){
                    if(elem instanceof Animal animal && ((Animal) elem).energy>requiredEnergy){
                        if(parent2==null){
                            parent2=(Animal) elem;
                        }
                        else if (parent1==null){
                            if (animal.energy>parent2.energy){
                                parent1=animal;
                            }
                            else{
                                parent1=parent2;
                                parent2=animal;
                            }
                        }
                        else{
                            if(animal.energy>parent1.energy){
                                parent2=parent1;
                                parent1=animal;
                            }
                            else if (animal.energy>parent2.energy){
                                parent2=animal;
                            }
                        }
                    }
                }
                if(parent1!=null){
                    Animal child=parent1.procreate(parent2);
                    place(child,child.getPosition());
                }
            }

        }
    }

    public int getGrassCount() {
        return grassCount;
    }
    public int[] getDominantGenotype(){
//        TODO
        int[] dominantGenotype=null;
        int count=0;
        for(Animal animal:livingAnimalsList){
            if(Arrays.equals(animal.getGenes().getGenotype(),dominantGenotype)){
                count+=1;
            }
            else {
                count-=1;
                if(count<=0){
                    dominantGenotype=animal.getGenes().getGenotype();
                    count=1;
                }
            }
        }
        return dominantGenotype;
    }
    public double getAverageDeadAnimalLifespan(){
        double totalLifespan=0;
        double deadAnimalCount=0;
        for(Animal animal:allAnimalsList) {
            if (!animal.isAlive()) {
                totalLifespan += animal.getLifespan();
                deadAnimalCount+=1;
            }
        }
        return (double) Math.round(totalLifespan*10/deadAnimalCount)/10;

    }

    public double getAverageChildrenCount(){
        double totalChildrenCount=0;
        for(Animal animal:livingAnimalsList){
            totalChildrenCount+=animal.getChildrenCount();
        }
        return (double) Math.round(totalChildrenCount*10/livingAnimalsList.size())/10;
    }

    public long getEpoque() {
        return epoque;
    }
    public void printAnimals(){
        for (Animal animal:livingAnimalsList) {
            System.out.println(animal.toString()+" "+animal.getPosition().toString()+" "+animal.orient + " "+animal.energy);
        }
    }

    public double getAverageEnergy(){
        double totalEnergy=0;
        for(Animal animal:livingAnimalsList){
            totalEnergy+=animal.energy;
        }
        return (double) Math.round(totalEnergy*10/ livingAnimalsList.size())/10;
    }
    public IMapElement topObjectAt(Vector2D position){
        List<IMapElement> mapElements=map.get(position);
        if(mapElements==null || mapElements.size()==0){
            return null;
        }
        else if (mapElements.size()==1){
            return mapElements.get(0);
        }
        else{
            IMapElement topObject=null;
            int maxEnergy=0;
            for(IMapElement mapElement:mapElements){
                if(mapElement instanceof Animal animal && animal.energy>maxEnergy){
                    topObject=animal;
                    maxEnergy=animal.energy;
                }
            }
            return topObject;
        }
    }
    public void spawnAnimals(int spawnedAnimalsCount, int baseEnergy, int energyCost){
        for (int i = 0; i < spawnedAnimalsCount ; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Vector2D spawnPosition = new Vector2D(x, y);
            while (isOccupied(spawnPosition)){
                x = random.nextInt(width);
                y = random.nextInt(height);
                spawnPosition = new Vector2D(x, y);
            }
            Animal animal=new Animal(spawnPosition,this,baseEnergy,energyCost);
            place(animal,spawnPosition);
        }
    }

    public GenericWorldMap(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        double parts;
        if (jungleRatio < 1) {
            parts = 1 + (1 / jungleRatio);
            this.jungleWidth = (int) Math.floor(width / parts);
            this.jungleHeight = (int) Math.floor(height / parts);
        } else {
            parts = 1 + jungleRatio;
            this.jungleWidth = (int) (Math.round(jungleRatio * width / parts));
            this.jungleHeight = (int) (Math.round(jungleRatio * height / parts));
        }
        this.jungleEmptyFieldCount=this.jungleHeight*this.jungleWidth;
        this.steppeEmptyFieldCount=width*height-jungleEmptyFieldCount;
        System.out.println(jungleEmptyFieldCount);
        System.out.println(steppeEmptyFieldCount);

    }

}
