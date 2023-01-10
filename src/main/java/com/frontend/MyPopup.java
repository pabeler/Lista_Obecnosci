package com.frontend;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

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

