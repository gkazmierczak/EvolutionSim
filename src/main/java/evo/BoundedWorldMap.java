package evo;

import classes.Vector2D;
import enums.MapType;
import interfaces.IPositionObserver;
import interfaces.IWorldMap;

public class BoundedWorldMap extends GenericWorldMap implements IWorldMap, IPositionObserver {

    @Override
    public boolean canMoveTo(Vector2D position) {
        return isInBounds(position);
    }


    public BoundedWorldMap(int width, int height,double jungleRatio){
        super(width,height,jungleRatio);
        this.mapType= MapType.BOUNDED;
    }

}
