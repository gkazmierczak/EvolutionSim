package gui;

import classes.Animal;
import classes.Grass;
import classes.Vector2D;
import interfaces.IButtonPressHandler;
import interfaces.IMapElement;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class MapRenderer {
    Grass.GenericWorldMap map;
    int width;
    int height;
    VBox vBox;
    GridPane grid;
    MapStatBox mapStatBox;
    AnimalStatBox animalStatBox;
    IButtonPressHandler handler;
    Image grassImage;
    Image[] animalImages;
    int[] highlightedGenotype;


    public MapRenderer(Grass.GenericWorldMap map, IButtonPressHandler handler) {
        this.map = map;
        this.width = map.width;
        this.height = map.height;
        this.vBox = new VBox();
        this.handler = handler;
        this.animalImages = new Image[9];
        for (int i = 0; i < 8; i++) {
            try {
                animalImages[i] = new Image(new FileInputStream("src/main/resources/animal" + i + ".png"), 32, 32, true, true);
            } catch (FileNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
        try {
            animalImages[8] = new Image(new FileInputStream("src/main/resources/deadAnimal.png"), 64, 64, true, true);
            grassImage = new Image(new FileInputStream("src/main/resources/plant.png"), 32, 32, true, true);
        } catch (FileNotFoundException e) {
            grassImage = null;
        }
    }

    public void init() {
        grid = new GridPane();
        vBox.getChildren().add(grid);
        this.mapStatBox = new MapStatBox(map, handler);
        vBox.getChildren().add(mapStatBox.grid);
        grid.setAlignment(Pos.CENTER);
        animalStatBox = new AnimalStatBox(this.map, animalImages);
        getNextFrame(false);
    }

    public AnimalStatBox getAnimalStatBox() {
        return this.animalStatBox;
    }

    public void getNextFrame(boolean highlight) {
        this.grid.getChildren().clear();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Parent parent = getElementViewAt(i, height - j - 1, highlight);
                if (parent != null) {
                    grid.add(parent, i, j);
                    GridPane.setHalignment(parent, HPos.CENTER);
                }
            }
        }
        this.mapStatBox.updateStats();
        this.animalStatBox.updateStats();

    }

    public void updateWidth(Number newValue) {
        double unit = (double) newValue / 100;
        this.vBox.setPrefWidth(40 * unit);
        this.grid.setPrefWidth(40 * unit);
        this.grid.getColumnConstraints().clear();
        for (int i = 0; i < width; i++) {
            this.grid.getColumnConstraints().add(new ColumnConstraints((40 * unit) / (width)));
        }
        this.mapStatBox.updateWidth((Double) newValue);

    }

    public void updateHeight(Number newValue) {
        double unit = (double) newValue / 100;
        this.vBox.setPrefHeight((double) newValue);
        this.grid.setPrefHeight(50 * unit);
        this.grid.getRowConstraints().clear();
        for (int i = 0; i < height; i++) {
            this.grid.getRowConstraints().add(new RowConstraints((50 * unit) / (height)));
        }
        this.mapStatBox.updateHeight((Double) newValue);
    }

    public Parent getElementViewAt(int column, int row, boolean highlight) {
        IMapElement mapElement = map.topObjectAt(new Vector2D(column, row));
        double imageWidth = (this.grid.getWidth()/width) *0.8;
        double imageHeight = (this.grid.getHeight()/height) *0.8;
        if (mapElement == null) {
            FlowPane field = new FlowPane();
            if (map.inJungle(new Vector2D(column, row))) {
                field.getStyleClass().add("jungle-field");
            } else {
                field.getStyleClass().add("steppe-field");
            }
            return field;
        } else if (mapElement instanceof Grass) {

            GrassView grassView = new GrassView(grassImage, map.inJungle(mapElement.getPosition()),imageWidth,imageHeight);
            return grassView.vBox;
        } else {
            Animal animal = (Animal) mapElement;
            AnimalView view = new AnimalView(animal, animalImages,imageWidth,imageHeight);
            view.vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                animal.toggleSelection();
                this.animalStatBox.updateStats();
            });
            if (map.inJungle(animal.getPosition())) {
                view.vBox.getStyleClass().add("jungle-field");
            } else {
                view.vBox.getStyleClass().add("steppe-field");
            }
            if (highlight) {
                if (Arrays.equals(animal.getGenes().getGenotype(), highlightedGenotype)) {
                    view.vBox.getStyleClass().add("highlighted-field");
                }
            }
            return view.vBox;
        }
    }

    public List<String[]> getSimulationData() {
        return this.mapStatBox.getSimulationData();
    }

    public void highlightGenotype() {
        this.highlightedGenotype = map.getDominantGenotype();
        getNextFrame(true);
    }

    public VBox getCurrentView() {
        return this.vBox;
    }
}
