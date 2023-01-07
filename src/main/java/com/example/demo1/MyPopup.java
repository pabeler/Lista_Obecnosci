package com.example.demo1;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

public class MyPopup extends Popup {
    public MyPopup(String text) {
        super();
        HBox hBox = new HBox();
        hBox.setPrefSize(200, 75);
        hBox.setStyle("-fx-background-color: #ffffff");
        hBox.setAlignment(Pos.CENTER);
        Text text1 = new Text(text);
        hBox.getChildren().add(text1);
        this.getContent().add(hBox);


    }
}

