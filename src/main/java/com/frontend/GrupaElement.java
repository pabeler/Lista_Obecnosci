package com.frontend;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class GrupaElement extends GridPane {
    GrupaElement(String nazwa, int id, String termin) {
        Text nazwaGrupy = new Text(nazwa);
        Text idGrupy = new Text(String.valueOf(id));
        Text terminGrupy;
        if (termin == null) {
            terminGrupy = new Text("Brak");
            this.getChildren().addAll(idGrupy, nazwaGrupy, terminGrupy);
        } else {
            terminGrupy = new Text(termin);
            this.getChildren().addAll(idGrupy, nazwaGrupy, terminGrupy);
        }
        ChoiceBox<Object> choiceBox = new ChoiceBox<>();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(33.3);
        this.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().addAll(rowConstraints);
        GridPane.setConstraints(idGrupy, 0, 0);
        GridPane.setConstraints(nazwaGrupy, 1, 0);
        GridPane.setConstraints(terminGrupy, 2, 0);
        this.setMinHeight(50);
    }
}