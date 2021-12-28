package gui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class TextInputBox extends Parent {
    private final HBox content= new HBox();
    private final TextField textField;
    public TextInputBox(String text, String defaultValue){
        content.setPrefSize(640,24);
        textField=new TextField(defaultValue);
        Label label = new Label(text);
        label.setFont(Font.font(14));
        textField.setFont(Font.font(14));
        content.getChildren().add(label);
        content.getChildren().add(textField);
        content.setSpacing(80);
        content.setAlignment(Pos.CENTER);
    }

    public HBox getContent() {
        return content;
    }
    public int getInputValueInt(){
        return Integer.parseInt(this.textField.getText());
    }
    public Double getInputValueDouble(){
        return Double.valueOf(this.textField.getText());
    }
}
