import classes.Animal;
import classes.Grass;
import classes.Vector2D;
import enums.MapDirection;
import enums.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void orientTest(){
        Animal animal=new Animal(new Vector2D(0,0), MapDirection.NORTH);
        animal.move(MoveDirection.LEFT);
        Assertions.assertEquals(animal.orient,MapDirection.WEST);
    }

    @Test
    public void procreateTest(){
        Grass.GenericWorldMap map=new Grass.GenericWorldMap(10,10,0.5);
        Animal animal1=new Animal(new Vector2D(0,0),map,100,5);
        Animal animal2=new Animal(new Vector2D(0,0),map,100,5);
        Animal child1 =animal1.procreate(animal2);
        Assertions.assertNotNull(child1);
        Assertions.assertEquals(animal1.energy,75);
        Assertions.assertEquals(animal2.energy,75);
        Assertions.assertEquals(child1.energy,50);
        Animal child2 =animal2.procreate(animal1);
        Assertions.assertNotNull(child2);
        Assertions.assertEquals(animal1.energy,57);
        Assertions.assertEquals(animal2.energy,57);
        Assertions.assertEquals(child2.energy,36);
        Assertions.assertNotEquals(child1,child2);
        Assertions.assertEquals(animal1.getChildrenCount(),2);
        Assertions.assertEquals(animal1.getChildrenCount(),animal2.getChildrenCount());
    }


}
