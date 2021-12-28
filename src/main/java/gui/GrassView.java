package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GrassView extends Parent {

    VBox vBox=new VBox();
    public GrassView(Image image,boolean inJungle){
        if(image!=null){
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            vBox.getChildren().add(imageView);
            vBox.setAlignment(Pos.CENTER);
            if(inJungle){
                vBox.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else{
                vBox.setBackground(new Background(new BackgroundFill(Color.OLIVEDRAB, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        else{
            vBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
}
