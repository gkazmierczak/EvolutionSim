package gui;


import javafx.scene.control.TextArea;


public class OutputLog {
    TextArea textArea;

    public OutputLog() {
        this.textArea = new TextArea();
    }

    public void printMessage(String message) {
        if (message != null) {
            this.textArea.appendText(message);
        }
    }

    public void updateWidth(double newValue) {
        double unit = newValue / 100;
        this.textArea.setPrefWidth(20 * unit);
    }

    public void updateHeight(double newValue) {
        double unit = newValue / 100;
        this.textArea.setPrefHeight(36 * unit);
    }
}

