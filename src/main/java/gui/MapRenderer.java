package gui;

import classes.Animal;
import classes.Grass;
import classes.Vector2D;
import evo.GenericWorldMap;
import interfaces.ButtonPressHandler;
import interfaces.IMapElement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class MapRenderer {
    GenericWorldMap map;
    int width;
    int height;
    VBox vBox;
    GridPane grid;
    MapStatBox statBox;
    ButtonPressHandler handler;
    Image grassImage;
    Image[] animalImages;
    Background steppeBackgound;
    Background jungleBackground;

    public MapRenderer(GenericWorldMap map, ButtonPressHandler handler) {
        this.map = map;
        this.width = map.width;
        this.height = map.height;
        this.vBox = new VBox();
        this.jungleBackground = new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
        this.steppeBackgound = new Background(new BackgroundFill(Color.OLIVEDRAB, CornerRadii.EMPTY, Insets.EMPTY));
        this.handler = handler;
        this.animalImages = new Image[8];
        for (int i = 0; i < 8; i++) {
            try {
                animalImages[i] = new Image(new FileInputStream("src/main/resources/animal" + i + ".png"), 20, 20, true, true);
            } catch (FileNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
        try {
            grassImage = new Image(new FileInputStream("src/main/resources/plant.png"), 20, 20, true, true);
        } catch (FileNotFoundException e) {
            grassImage = null;
        }
    }

    public void init() {
        grid = new GridPane();
        vBox.getChildren().add(grid);
        this.statBox = new MapStatBox(map, handler);
        vBox.getChildren().add(statBox.grid);
        grid.setAlignment(Pos.CENTER);
        this.grid.setPrefSize(500, 500);
        getNextFrame();
    }

    public void getNextFrame() {
        this.grid.getChildren().clear();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Parent parent = getElementViewAt(i, height - j - 1);
                if (parent != null) {
                    grid.add(parent, i, j);
                    grid.setHalignment(parent, HPos.CENTER);
                }
            }
        }
        this.statBox.updateStats();

    }

    public void updateWidth(Number newValue) {
        double unit = (double) newValue / 100;
        this.vBox.setPrefWidth(37 * unit);
        this.grid.setPrefWidth(37 * unit);
        this.grid.getColumnConstraints().clear();
        for (int i = 0; i < width; i++) {
            this.grid.getColumnConstraints().add(new ColumnConstraints((37 * unit) / (width)));
        }
        this.statBox.updateWidth((Double) newValue);

    }

    public void updateHeight(Number newValue) {
        double unit = (double) newValue / 100;
        this.vBox.setPrefHeight((double) newValue);
        this.grid.setPrefHeight(50 * unit);
        this.grid.getRowConstraints().clear();
        for (int i = 0; i < height; i++) {
            this.grid.getRowConstraints().add(new RowConstraints((50 * unit) / (height)));
        }
        this.statBox.updateHeight((Double) newValue);
    }

    public Parent getElementViewAt(int column, int row) {
        IMapElement mapElement = map.topObjectAt(new Vector2D(column, row));
        if (mapElement == null) {
//            return null;
            FlowPane field = new FlowPane();
            if (map.inJungle(new Vector2D(column, row))) {
//                field.setBackground(jungleBackground);
                field.getStyleClass().add("jungle-field");
            } else {
//                field.setBackground(steppeBackgound);
                field.getStyleClass().add("steppe-field");

            }
            return field;
        } else if (mapElement instanceof Grass) {
            GrassView grassView = new GrassView(grassImage, map.inJungle(mapElement.getPosition()));
            return grassView.vBox;
        } else {
            Animal animal = (Animal) mapElement;
            AnimalView view = new AnimalView(animal, animalImages);
            if (map.inJungle(animal.getPosition())) {
                view.vBox.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                view.vBox.setBackground(new Background(new BackgroundFill(Color.OLIVEDRAB, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            return view.vBox;
        }

    }

    public VBox getCurrentView() {
        return this.vBox;
    }
}
