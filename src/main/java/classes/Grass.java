package classes;

import gui.GrassView;
import interfaces.IMapElement;

public class Grass implements IMapElement {
    private final Vector2D position;
    public final int energyValue;
    public Vector2D getPosition() {
        return this.position;
    }
    GrassView view;
    public Grass(Vector2D position,int energyValue){
        this.position=position;
        this.energyValue=energyValue;
        view=new GrassView();
    }
}
