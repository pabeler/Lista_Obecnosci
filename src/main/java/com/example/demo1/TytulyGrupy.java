package com.example.demo1;

import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class TytulyGrupy extends GridPane {
    TytulyGrupy(){
        Text textID=new Text("ID");
        Text textNazwa=new Text("Nazwa Grupy");

        this.getChildren().addAll(textID,textNazwa);

        ColumnConstraints columnConstraints=new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        this.getColumnConstraints().addAll(columnConstraints,columnConstraints);
        RowConstraints rowConstraints=new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().addAll(rowConstraints);

        GridPane.setConstraints(textID,0,0);
        GridPane.setConstraints(textNazwa,1,0);


        this.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);}

}
