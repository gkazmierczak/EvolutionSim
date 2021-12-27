package gui;

import classes.Animal;
import classes.Vector2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AnimalView extends Parent {
    public VBox vBox=new VBox();
    private Animal animal;
    Label position;
    public AnimalView(Animal animal){
        this.animal=animal;
        try {
            Image image = new Image(new FileInputStream("src/main/resources/animal1.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            vBox.getChildren().add(imageView);
        }
        catch (FileNotFoundException e) {
            vBox.setBackground(new Background(new BackgroundFill(Color.SADDLEBROWN, new CornerRadii(50), Insets.EMPTY)));
        }
        position=new Label(animal.getPosition().toString());
        vBox.getChildren().add(position);
        vBox.setAlignment(Pos.CENTER);
    }
    public void updatePosition(){
        vBox.getChildren().remove(1);
        position=new Label(this.animal.getPosition().toString());
        vBox.getChildren().add(position);
    }

}
