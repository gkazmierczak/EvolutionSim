package enums;

import classes.Vector2D;

import java.util.Arrays;

public enum MapDirection {
    NORTH,
    NORTHWEST,
    WEST,
    SOUTHWEST,
    SOUTH,
    SOUTHEAST,
    EAST,
    NORTHEAST;
    MapDirection[] directions = values();

    MapDirection rotate(int turn) {
        int i = Arrays.asList(directions).indexOf(this);
        return directions[(i + turn) % 8];
    }

    Vector2D toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2D(0, 1);
            case NORTHEAST -> new Vector2D(-1, 1);
            case NORTHWEST -> new Vector2D(1, 1);
            case SOUTH -> new Vector2D(0, -1);
            case SOUTHEAST -> new Vector2D(-1, -1);
            case SOUTHWEST -> new Vector2D(1, -1);
            case WEST -> new Vector2D(1, 0);
            case EAST -> new Vector2D(-1, 0);
        };
    }
}
