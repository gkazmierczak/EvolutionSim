package evo;

import classes.Animal;
import classes.Genes;
import classes.Vector2D;
import enums.MapDirection;

import java.util.Arrays;

public class World {
    public static void main(String[] args){
//        Genes g=new Genes(32,8);
//        MapDirection n=MapDirection.NORTH;
//        System.out.println(Arrays.toString(g.getGenes()));
//        System.out.println(MapDirection.NORTH.rotate(2));\
        LoopedWorldMap map=new LoopedWorldMap(10,10,0.5);
        System.out.println(map.jungleHeight);
        System.out.println(map.jungleWidth);
//        Animal animal=new Animal(new Vector2D(0,0),map,50,10);
//        map.place(animal,animal.getPosition());
//        System.out.println(map.inJungle(new Vector2D(2,3)));
//        System.out.println(map.inJungle(new Vector2D(4,4)));
//        System.out.println(map.inJungle(new Vector2D(4,5)));
//        System.out.println(map.inJungle(new Vector2D(4,6)));
//        System.out.println(map.inJungle(new Vector2D(4,9)));

    }
}
