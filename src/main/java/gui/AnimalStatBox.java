package gui;

import classes.Animal;
import evo.GenericWorldMap;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AnimalStatBox {
    private GenericWorldMap map;
    private Animal trackedAnimal;
    private Label mapLabel;
    private Label genotypeLabel;
    private Label childrenLabel;
    private Label descendantLabel;
    private Label energyLabel;
    private Label birthEpochLabel;
    private Label deathEpochLabel;
    private ImageView imageView;
    private Image[] images;
    VBox vBox;
    public AnimalStatBox(GenericWorldMap map, Image[] images){
        this.map=map;
        this.trackedAnimal=map.getTrackedAnimal();
        birthEpochLabel=new Label();
        mapLabel=new Label();
        deathEpochLabel=new Label();
        childrenLabel=new Label();
        energyLabel=new Label();
        descendantLabel=new Label();
        genotypeLabel=new Label();
        this.imageView=new ImageView();
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);
        mapLabel.setText("Animal tracked on "+map.getMapType()+" map");
        this.images=images;
        this.vBox=new VBox(mapLabel,imageView,genotypeLabel,energyLabel,childrenLabel,descendantLabel,birthEpochLabel,deathEpochLabel);
        vBox.setAlignment(Pos.CENTER);

        updateStats();
    }
    public void updateStats(){
        this.trackedAnimal=map.getTrackedAnimal();
        if(this.trackedAnimal!=null){
            birthEpochLabel.setText("Birth epoch: "+String.valueOf(trackedAnimal.getBirthEpoch()));
            String genotype = Arrays.stream(trackedAnimal.getGenes().getGenotype()).mapToObj(String::valueOf).collect(Collectors.joining(""));
            genotypeLabel.setText(genotype);
            energyLabel.setText("Energy: "+String.valueOf(trackedAnimal.energy));
            childrenLabel.setText("Children count: "+String.valueOf(trackedAnimal.getTrackedChildrenCount()));
            descendantLabel.setText("Descendant count: " +String.valueOf(trackedAnimal.getTrackedDescendantCount()));
            if(trackedAnimal.isAlive()){
                this.imageView.setImage(this.images[trackedAnimal.orient.ordinal()]);
                deathEpochLabel.setText("Death epoch: ");

            }
            else{
                this.imageView.setImage(this.images[8]);
                deathEpochLabel.setText("Death epoch: "+String.valueOf(trackedAnimal.getDeathEpoch()));
            }
        }
        else{
            clearStats();
        }
    }
    public void clearStats(){
        birthEpochLabel.setText("Birth epoch: ");
        genotypeLabel.setText("");
        energyLabel.setText("Energy: ");
        childrenLabel.setText("Children count: ");
        descendantLabel.setText("Descendant count: ");
        deathEpochLabel.setText("Death epoch: ");
        imageView.setImage(null);
    }
}
