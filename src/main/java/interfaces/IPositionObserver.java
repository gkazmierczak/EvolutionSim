package interfaces;

import classes.Animal;
import classes.Vector2D;

public interface IPositionObserver {
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition, Animal animal);
}
