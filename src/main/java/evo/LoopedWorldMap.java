package evo;

import classes.Animal;
import classes.Vector2D;
import interfaces.IMapElement;
import interfaces.IPositionObserver;
import interfaces.IWorldMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoopedWorldMap extends GenericWorldMap implements IWorldMap, IPositionObserver {
    public List<IMapElement> objectsAt(Vector2D position) {
        return super.objectsAt(position);
    }

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
    }
}
