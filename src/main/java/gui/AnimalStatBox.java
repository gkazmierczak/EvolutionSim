package gui;

import evo.GenericWorldMap;
import javafx.scene.layout.VBox;

public class AnimalStatBox {
    private GenericWorldMap map;
    VBox vBox;
    public AnimalStatBox(GenericWorldMap map){
// TODO - Statystyki wybranego zwierzaka, wybieranie zwierzaka wsm też
        this.map=map;
        this.vBox=new VBox();

    }
}
