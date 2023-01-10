package com.frontend;

import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class TytulyObecnosc extends GridPane {
    TytulyObecnosc() {
        Text textImie = new Text("Imie");
        Text textNazwisko = new Text("Nazwisko");
        Text textId = new Text("Id");
        Text textGrupa = new Text("Grupa");
        this.getChildren().addAll(textImie, textNazwisko, textId, textGrupa);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(20);
        this.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints, columnConstraints,
                columnConstraints);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().addAll(rowConstraints);
        GridPane.setConstraints(textImie, 1, 0);
        GridPane.setConstraints(textNazwisko, 2, 0);
        GridPane.setConstraints(textId, 0, 0);
        GridPane.setConstraints(textGrupa, 3, 0);
        this.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
    }
}