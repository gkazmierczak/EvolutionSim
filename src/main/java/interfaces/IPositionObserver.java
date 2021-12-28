package interfaces;

import classes.Animal;
import classes.Vector2D;

public interface IPositionObserver {
    void positionChanged(Vector2D oldPosition, Vector2D newPosition, Animal animal);
}
