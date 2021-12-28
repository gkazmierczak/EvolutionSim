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
    public GrassView(Image image,boolean inJungle,double width,double height){
        if(image!=null){
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            vBox.getChildren().add(imageView);
            vBox.setAlignment(Pos.CENTER);
            if(inJungle){
                vBox.getStyleClass().add("jungle-field");
            }
            else{
                vBox.getStyleClass().add("steppe-field");
            }
        }
        else{
            vBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
}
