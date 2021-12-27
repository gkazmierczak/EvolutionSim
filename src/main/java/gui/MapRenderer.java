package gui;

import classes.Animal;
import classes.Grass;
import classes.Vector2D;
import evo.GenericWorldMap;
import interfaces.IMapElement;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;

public class MapRenderer {
    GenericWorldMap map;
    int width;
    int height;
    VBox vBox;
    GridPane grid;
    GrassView grassView=new GrassView();
    public MapRenderer(GenericWorldMap map){
        this.map=map;
        this.width=map.width;
        this.height=map.height;
        this.vBox=new VBox();
    }
    public void init(){
        grid=new GridPane();
        vBox.getChildren().add(grid);
        this.grid.setPrefSize(500,500);
        getNextFrame();
    }
    public void getNextFrame(){
        this.grid.getChildren().clear();
        for(int i=0;i<width+1;i++){
            for(int j=0;j<height+1;j++){
                if(i==0 && j!=0){
                    Label l=new Label(String.valueOf(height-j));
                    l.setFont(Font.font(20));
                    this.grid.add(l,i,j,1,1);
                    this.grid.setHalignment(l, HPos.CENTER);

                }
                else if(i!=0 && j==0){
                    Label l=new Label(String.valueOf(i-1));
                    l.setFont(Font.font(20));
                    this.grid.add(l,i,j,1,1);
                    this.grid.setHalignment(l,HPos.CENTER);

                }
                else{
                    Parent parent=getElementViewAt(i-1,height-j);
                    if(parent!=null) {
                        grid.add(parent, i, j);
                    }
                }

            }
        }

    }
    public void updateWidth(Number newValue){
        this.vBox.setPrefWidth((double) newValue);
        this.grid.setPrefWidth((double) newValue);
        this.grid.getColumnConstraints().clear();
        for (int i = 0; i < width; i++) {
            this.grid.getColumnConstraints().add(new ColumnConstraints((double) newValue/(width+2)));
        }
    }
    public void updateHeight(Number newValue){
        this.vBox.setPrefHeight((double) newValue);
        this.grid.setPrefHeight((double) newValue);
        this.grid.getRowConstraints().clear();
        for (int i = 0; i < height; i++) {
            this.grid.getRowConstraints().add(new RowConstraints((double) newValue/(height+2)));
        }
    }

    public Parent getElementViewAt(int column, int row) {
        IMapElement mapElement=map.topObjectAt(new Vector2D(column,row));
        if(mapElement==null){
            return null;
        }
        else if (mapElement instanceof Grass){
            GrassView grassView=new GrassView();
            return grassView.vBox;
        }
        else{
            Animal animal=(Animal) mapElement;
            animal.updateView();
            return animal.getView();
        }

    }

    public VBox getCurrentView() {
        return this.vBox;
    }
}
