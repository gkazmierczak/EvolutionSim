package interfaces;

import classes.Animal;
import classes.Grass;
import classes.Vector2D;

import java.util.List;

public interface IWorldMap {
    List<IMapElement> objectsAt(Vector2D position);
    void place(IMapElement element,Vector2D position);
    boolean canMoveTo(Vector2D position);
    Grass grassAt(Vector2D position);

    long getEpoch();
}
