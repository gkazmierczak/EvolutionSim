package gui;

import evo.GenericWorldMap;
import interfaces.IButtonPressHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class MapStatBox {
    GridPane grid;
    private final GenericWorldMap map;
    private final Label epochLabel;
    private final Label dominantGenotypeLabel;
    private final LineChart<Number, Number> entityCountChart;
    private final LineChart<Number, Number> avgLifespanChart;
    private final LineChart<Number, Number> avgChildrenChart;
    private final LineChart<Number, Number> avgEnergyChart;
    private final Button simulationControlBtn;
    private final XYChart.Series<Number, Number> animalCounts = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> grassCounts = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgLifespans = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgChildrenCounts = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgEnergyLevels = new XYChart.Series<>();
    private final List<String[]> simulationData = new LinkedList<>();

    public MapStatBox(GenericWorldMap map, IButtonPressHandler handler) {
        this.map = map;
        this.grid = new GridPane();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Epoch");
        yAxis.setLabel("Count");
        xAxis.setForceZeroInRange(false);
        this.animalCounts.setName("Animals");
        this.grassCounts.setName("Grass");
        this.entityCountChart = new LineChart<>(xAxis, yAxis);
        entityCountChart.getData().add(animalCounts);
        entityCountChart.getData().add(grassCounts);
        entityCountChart.setCreateSymbols(false);
        entityCountChart.setLegendVisible(true);
        entityCountChart.setLegendSide(Side.RIGHT);
        final NumberAxis lifespanXAxis = new NumberAxis();
        final NumberAxis lifespanYAxis = new NumberAxis();
        lifespanXAxis.setLabel("Epoch");
        lifespanYAxis.setLabel("Avg lifespan");
        lifespanXAxis.setForceZeroInRange(false);
        lifespanYAxis.setForceZeroInRange(false);
        this.avgLifespans.setName("Lifespan");
        this.avgLifespanChart = new LineChart<>(lifespanXAxis, lifespanYAxis);
        avgLifespanChart.setCreateSymbols(false);
        avgLifespanChart.getData().add(avgLifespans);
        avgLifespanChart.setLegendVisible(false);
        final NumberAxis childrenXAxis = new NumberAxis();
        final NumberAxis childrenYAxis = new NumberAxis();
        childrenXAxis.setLabel("Epoch");
        childrenYAxis.setLabel("Avg children");
        childrenXAxis.setForceZeroInRange(false);
        childrenYAxis.setForceZeroInRange(false);
        this.avgChildrenCounts.setName("Children count");
        this.avgChildrenChart = new LineChart<>(childrenXAxis, childrenYAxis);
        avgChildrenChart.setCreateSymbols(false);
        avgChildrenChart.setLegendVisible(false);
        avgChildrenChart.getData().add(avgChildrenCounts);
        final NumberAxis energyXAxis = new NumberAxis();
        final NumberAxis energyYAxis = new NumberAxis();
        energyXAxis.setLabel("Epoch");
        energyYAxis.setLabel("Avg energy");
        energyXAxis.setForceZeroInRange(false);
        energyYAxis.setForceZeroInRange(false);
        this.avgEnergyLevels.setName("Energy levels");
        this.avgEnergyChart = new LineChart<>(energyXAxis, energyYAxis);
        avgEnergyChart.getData().add(avgEnergyLevels);
        avgEnergyChart.setCreateSymbols(false);
        avgEnergyChart.setLegendVisible(false);
        this.epochLabel = new Label();
        this.dominantGenotypeLabel = new Label();
        HBox hBox = new HBox();
        hBox.getChildren().add(epochLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        this.simulationControlBtn = new Button("Start");
        simulationControlBtn.setOnAction((event -> handler.handleButtonPress(simulationControlBtn, this.map.getMapType())));
        hBox.getChildren().add(simulationControlBtn);
        hBox.getChildren().add(dominantGenotypeLabel);
        epochLabel.setFont(Font.font(16));
        this.grid.add(hBox, 0, 0, 2, 1);
        this.grid.add(entityCountChart, 1, 1);
        this.grid.add(avgEnergyChart, 0, 2);
        this.grid.add(avgChildrenChart, 0, 1);
        this.grid.add(avgLifespanChart, 1, 2);

//        updateStats();
    }

    public void updateWidth(double newValue) {
        double unit = newValue / 100;
        this.grid.setPrefWidth(40 * unit);
        this.grid.getColumnConstraints().clear();
        this.grid.getColumnConstraints().add(new ColumnConstraints(18 * unit));
        this.grid.getColumnConstraints().add(new ColumnConstraints(22 * unit));
    }

    public void updateHeight(double newValue) {
        double unit = newValue / 100;
        this.grid.setPrefHeight(50 * unit);
        this.grid.getRowConstraints().clear();
        this.grid.getRowConstraints().add(new RowConstraints(3 * unit));
    }

    public void updateStats() {
        if (animalCounts.getData().size() > 250) {
            animalCounts.getData().remove(0);
            grassCounts.getData().remove(0);
            avgEnergyLevels.getData().remove(0);
            avgLifespans.getData().remove(0);
            avgChildrenCounts.getData().remove(0);
            NumberAxis xAxis = (NumberAxis) entityCountChart.getXAxis();
            xAxis.setLowerBound(animalCounts.getData().get(0).getXValue().doubleValue());
            xAxis = (NumberAxis) avgChildrenChart.getXAxis();
            xAxis.setLowerBound(avgChildrenCounts.getData().get(0).getXValue().doubleValue());
            xAxis = (NumberAxis) avgLifespanChart.getXAxis();
            xAxis.setLowerBound(avgLifespans.getData().get(0).getXValue().doubleValue());
            xAxis = (NumberAxis) avgEnergyChart.getXAxis();
            xAxis.setLowerBound(avgEnergyLevels.getData().get(0).getXValue().doubleValue());
        }
        this.animalCounts.getData().add(new XYChart.Data<>(map.getEpoch(), map.getAnimalCount()));
        this.grassCounts.getData().add(new XYChart.Data<>(map.getEpoch(), map.getGrassCount()));
        this.avgLifespans.getData().add(new XYChart.Data<>(map.getEpoch(), map.getAverageDeadAnimalLifespan()));
        this.avgEnergyLevels.getData().add(new XYChart.Data<>(map.getEpoch(), map.getAverageEnergy()));
        this.avgChildrenCounts.getData().add(new XYChart.Data<>(map.getEpoch(), map.getAverageChildrenCount()));
        this.epochLabel.setText("EPOCH: " + map.getEpoch());
        int[] genotype = map.getDominantGenotype();
        this.simulationData.add(new String[]{String.valueOf(map.getAnimalCount()), String.valueOf(map.getGrassCount()), String.valueOf(map.getAverageEnergy()), String.valueOf(map.getAverageDeadAnimalLifespan()), String.valueOf(map.getAverageChildrenCount())});
        if (genotype != null) {
            String result = Arrays.stream(genotype).mapToObj(String::valueOf).collect(Collectors.joining(""));
            this.dominantGenotypeLabel.setText("Dominant genotype: " + result);

        } else {
            this.dominantGenotypeLabel.setText("");
        }
    }

    public List<String[]> getSimulationData() {
        return this.simulationData;
    }
}

