package classes;

import enums.*;
import gui.AnimalView;
import interfaces.*;
import javafx.scene.Parent;

import java.util.ArrayList;

public class Animal implements IMapElement {
    public MapDirection orient;
    private Vector2D position;
    public int energy;
    private final IWorldMap map;
    private final Genes genes;
    private final int dailyEnergyCost;
    private int age = 0;
    private long birthEpoque;
    private long deathEpoque;
    private int childrenCount;
    private boolean isTracked=false;
    private Animal trackedAncestor=null;
    private int trackedChildrenCount;
    private int trackedDescendantCount;
    private final ArrayList<IPositionObserver> observers = new ArrayList<>();
    AnimalView view;

    public Vector2D getPosition() {
        return this.position;
    }

    public Animal(Vector2D initialPosition, IWorldMap map, int initialEnergy, int dailyEnergyCost) {
        this.position = initialPosition;
        this.map = map;
        this.energy = initialEnergy;
        this.orient = MapDirection.values()[(int) Math.floor(Math.random() * 8)];
        this.addObserver((IPositionObserver) map);
        this.genes = new Genes(32, 8);
        this.dailyEnergyCost = dailyEnergyCost;
        this.view=new AnimalView(this);
    }

    public Animal(Vector2D initialPosition, IWorldMap map, int initialEnergy, int dailyEnergyCost, Animal parent1, Animal parent2) {
        this.position = initialPosition;
        this.map = map;
        this.energy = initialEnergy;
        this.dailyEnergyCost = dailyEnergyCost;
        this.orient = MapDirection.values()[(int) Math.floor(Math.random() * 8)];
        this.addObserver((IPositionObserver) map);
        if (parent1.energy > parent2.energy) {
            this.genes = new Genes(parent1.getGenes(), parent2.getGenes(), (float) parent1.energy / (parent2.energy + parent1.energy));
        } else {
            this.genes = new Genes(parent2.getGenes(), parent1.getGenes(), (float) parent2.energy / (parent1.energy + parent2.energy));
        }
        this.birthEpoque=map.getEpoque();
        this.view=new AnimalView(this);
    }
    public void move(){
        this.age+=1;
        int move=genes.getMove();
//        System.out.println(move);
        if(move==0){
            this.moveForward();
        }
        else if (move==4){
            this.moveBackward();
        }
        else{
//            System.out.println(orient);
            this.orient=this.orient.rotate(move);
            this.decreaseEnergy(dailyEnergyCost);
//            System.out.println(orient);
        }
    }
    public void startTracking(){
        this.isTracked=true;
    }
    public void stopTracking(){
        this.isTracked=false;
        trackedChildrenCount=0;
        trackedDescendantCount =0;
    }
    public void move(MoveDirection direction) {
        this.age += 1;
        switch (direction) {
            case LEFT -> this.orient = this.orient.rotate(-2);
            case RIGHT -> this.orient = this.orient.rotate(2);
            case FORWARD -> this.moveForward();
            case BACKWARD -> this.moveBackward();
        }
    }

    private void moveForward() {
        Vector2D newPos = this.position.add(this.orient.toUnitVector());
        if (this.map.canMoveTo(newPos)) {
            this.setPosition(newPos);
            this.decreaseEnergy(dailyEnergyCost);
        }
    }

    private void moveBackward() {
        Vector2D newPos = this.position.subtract(this.orient.toUnitVector());
        if (this.map.canMoveTo(newPos)) {
            this.setPosition(newPos);
            this.decreaseEnergy(dailyEnergyCost);

        }
    }

    private void setPosition(Vector2D newPos) {
        Vector2D oldPos = this.position;
        this.position = newPos;
        this.positionChanged(oldPos, newPos);
    }

    public void setPositionLooped(Vector2D newPos) {
        this.position = newPos;
    }

    private void positionChanged(Vector2D oldPos, Vector2D newPos) {
        for (IPositionObserver observer : observers) {
            observer.positionChanged(oldPos, newPos, this);
        }
    }
    public void setDeathEpoque(long epoque){
        deathEpoque=epoque;
    }
    public Parent getView(){
        return this.view.vBox;
    }
    public int getChildrenCount(){
        return this.childrenCount;
    }
    public int getTrackedChildrenCount(){
        return this.trackedChildrenCount;
    }
    public int getTrackedDescendantCount(){
        return this.trackedDescendantCount;
    }
    public void addObserver(IPositionObserver observer) {
        this.observers.add(observer);
    }

    public Genes getGenes() {
        return genes;
    }
    public void increaseChildrenCount(){
        this.childrenCount+=1;
    }
    void removeObserver(IPositionObserver observer) {
        this.observers.remove(observer);
    }
    public int getLifespan(){
        return this.age;
    }
    public boolean isAlive() {
        return energy > 0;
    }

    public void increaseEnergy(int amount) {
        energy += amount;
    }
    public void updateView(){
        this.view.updatePosition();
    }
    public void decreaseEnergy(int amount) {
        energy -= amount;
    }
    public void setOrient(MapDirection mapDirection){
        this.orient=mapDirection;
    }
    public Animal procreate(Animal partner) {
        int childEnergy = partner.energy / 4 + energy / 4;
        decreaseEnergy(energy / 4);
        partner.decreaseEnergy(partner.energy / 4);
        Animal child=new Animal(position, map, childEnergy, dailyEnergyCost, this, partner);
        increaseChildrenCount();
        if(isTracked){
            trackedChildrenCount+=1;
            trackedDescendantCount +=1;
            child.trackedAncestor=this;
        }
        partner.increaseChildrenCount();
        if(partner.isTracked){
            partner.trackedChildrenCount+=1;
            partner.trackedDescendantCount +=1;
            child.trackedAncestor=partner;
        }
        if(trackedAncestor!=null){
            trackedAncestor.trackedDescendantCount +=1;
            child.trackedAncestor=trackedAncestor;
        }

        return child;
    }


}

