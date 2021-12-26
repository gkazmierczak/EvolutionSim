import classes.Animal;
import classes.Vector2D;
import enums.MapDirection;
import enums.MoveDirection;
import evo.BoundedWorldMap;
import evo.LoopedWorldMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapTest {
    @Test
    public void LoopedMapPositionChangeTest() {
//      Test position looping on LoopedWorldMap
//      Place animals on map and move them
        LoopedWorldMap loopedWorldMap = new LoopedWorldMap(10, 10, 0.5);
        Animal animal = new Animal(new Vector2D(0, 0), loopedWorldMap, 999, 1);
        animal.setOrient(MapDirection.NORTH);
        loopedWorldMap.place(animal, animal.getPosition());
        Animal animal2 = new Animal(new Vector2D(9, 9), loopedWorldMap, 999, 1);
        animal2.setOrient(MapDirection.NORTHEAST);
        loopedWorldMap.place(animal2, animal2.getPosition());
//        Movement in bounds
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2D(0, 1), animal.getPosition());
//        X-looping
        animal.setOrient(MapDirection.WEST);
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2D(9, 1), animal.getPosition());
        animal.setOrient(MapDirection.SOUTH);
//        Y-looping
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2D(9, 9), animal.getPosition());
//        Diagonal looping
        animal2.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2D(0, 0), animal2.getPosition());
        animal2.move(MoveDirection.BACKWARD);
        Assertions.assertEquals(new Vector2D(9, 9), animal2.getPosition());
    }
    @Test
    public void BoundedMapPositionChangeTest() {
//      Test position changes on BoundedWorldMap
//      Place animals on map and move them
        BoundedWorldMap boundedWorldMap = new BoundedWorldMap(10, 10, 0.5);
        Animal animal = new Animal(new Vector2D(0, 0), boundedWorldMap, 999, 1);
        animal.setOrient(MapDirection.NORTH);
        boundedWorldMap.place(animal, animal.getPosition());
        Animal animal2 = new Animal(new Vector2D(9, 9), boundedWorldMap, 999, 1);
        animal2.setOrient(MapDirection.NORTHEAST);
        boundedWorldMap.place(animal2, animal2.getPosition());
//        Check in bounds
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2D(0, 1), animal.getPosition());
//        Check out of bounds
        animal2.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2D(9,9), animal2.getPosition());
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        Assertions.assertEquals(new Vector2D(0, 0), animal.getPosition());
    }
}
