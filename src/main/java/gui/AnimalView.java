package gui;

import classes.Animal;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AnimalView extends Parent {
    public VBox vBox = new VBox();

    public AnimalView(Animal animal, Image[] images,double width,double height) {
        Image image = images[animal.orient.ordinal()];
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        vBox.getChildren().add(imageView);
        vBox.setAlignment(Pos.CENTER);
    }

}
