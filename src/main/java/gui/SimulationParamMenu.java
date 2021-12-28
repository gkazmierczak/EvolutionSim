package gui;

import classes.SimulationParams;
import evo.EvolutionSimulator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SimulationParamMenu {
    SimulationParams simulationParams = new SimulationParams();
    TextInputBox widthBox = new TextInputBox("Map width:", "10");
    TextInputBox heightBox = new TextInputBox("Map height:", "10");
    TextInputBox jungleRatioBox = new TextInputBox("Jungle to steppe ratio:", "0.5");
    TextInputBox animalsBox = new TextInputBox("Spawned animals:", "10");
    TextInputBox dailyEnergyBox = new TextInputBox("Daily energy cost:", "1");
    TextInputBox animalEnergyBox = new TextInputBox("Animal starting energy:", "50");
    TextInputBox grassEnergyBox = new TextInputBox("Grass energy:", "20");
    final ToggleGroup modeGroup = new ToggleGroup();
    RadioButton rbNormal = new RadioButton("Normal mode");
    RadioButton rbMagic = new RadioButton("Magic mode");
    TextInputBox updateBox = new TextInputBox("Time between simulation steps (ms):", "30");
    Scene scene;
    EvolutionSimulator evolutionSimulator;

    public SimulationParamMenu(EvolutionSimulator evolutionSimulator) {
        VBox vBox = new VBox();
        vBox.setPrefSize(640, 480);
        vBox.setAlignment(Pos.CENTER);
        Label mapSettingsLabel = new Label("Map settings");
        rbNormal.setToggleGroup(modeGroup);
        rbNormal.setSelected(true);
        rbMagic.setToggleGroup(modeGroup);
        mapSettingsLabel.setFont(Font.font(18));
        vBox.getChildren().add(mapSettingsLabel);
        HBox hBox = new HBox(rbNormal, rbMagic);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(widthBox.getContent());
        vBox.getChildren().add(heightBox.getContent());
        vBox.getChildren().add(jungleRatioBox.getContent());
        vBox.getChildren().add(hBox);
        Label simSettingsLabel = new Label("Simulation settings");
        simSettingsLabel.setFont(Font.font(18));
        vBox.getChildren().add(simSettingsLabel);
        vBox.getChildren().add(animalsBox.getContent());
        vBox.getChildren().add(dailyEnergyBox.getContent());
        vBox.getChildren().add(animalEnergyBox.getContent());
        vBox.getChildren().add(grassEnergyBox.getContent());
        vBox.getChildren().add(updateBox.getContent());
        Button startBtn = new Button("Start Simulation");
        startBtn.setFont(Font.font(20));
        startBtn.setOnAction((event -> {
            this.saveSimulationParams();
            this.evolutionSimulator.initSimulation();
        }));
        vBox.getChildren().add(startBtn);
        vBox.setSpacing(10);
        this.scene = new Scene(vBox);
        this.evolutionSimulator = evolutionSimulator;

    }

    public SimulationParams getSimulationParams() {
        return this.simulationParams;
    }

    public void saveSimulationParams() {
        this.simulationParams = new SimulationParams(widthBox.getInputValueInt(), heightBox.getInputValueInt(), jungleRatioBox.getInputValueDouble(), grassEnergyBox.getInputValueInt(), animalEnergyBox.getInputValueInt(), dailyEnergyBox.getInputValueInt(), animalEnergyBox.getInputValueInt() / 2, animalsBox.getInputValueInt(), updateBox.getInputValueInt(), rbMagic.isSelected());
    }

    public Scene getScene() {
        return this.scene;
    }
}
