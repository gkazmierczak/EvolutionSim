package evo;

import classes.Vector2D;
import interfaces.IMapElement;
import interfaces.IPositionObserver;
import interfaces.IWorldMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoundedWorldMap extends GenericWorldMap implements IWorldMap, IPositionObserver {
    private Map<Vector2D, List<IMapElement>> map=new HashMap<>();
    public List<IMapElement> objectsAt(Vector2D position) {
        return map.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return isInBounds(position);
    }


    public BoundedWorldMap(int width, int height,double jungleRatio){
        super(width,height,jungleRatio);
    }

}
