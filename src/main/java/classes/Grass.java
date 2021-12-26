package classes;

import interfaces.IMapElement;

public class Grass implements IMapElement {
    private final Vector2D position;
    public final int energyValue;
    public Vector2D getPosition() {
        return this.position;
    }
    public Grass(Vector2D position,int energyValue){
        this.position=position;
        this.energyValue=energyValue;
    }
}
