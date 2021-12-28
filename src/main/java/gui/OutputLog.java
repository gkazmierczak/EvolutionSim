package gui;


import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class OutputLog {
    TextArea textArea;
    VBox vBox;
    public OutputLog(){
        this.textArea=new TextArea();
//        this.vBox=new VBox(textArea);
    }
    public void printMessage(String message){
        if(message!=null) {
            this.textArea.appendText(message);
//            this.textArea.
        }
    }
    public void updateWidth(double newValue){
        double unit=newValue/100;
        this.textArea.setPrefWidth(20*unit);
    }
    public void updateHeight(double newValue){
        double unit=newValue/100;
        this.textArea.setPrefHeight(36*unit);
    }
}

