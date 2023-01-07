package com.example.demo1;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Window;

import java.io.IOException;

public class Navbar{
    final double WIDTH = Double.MAX_VALUE;
    final double HEIGHT = Double.MAX_VALUE;
    GridPane gridPane=new GridPane();
    Navbar(){
        Button zarzadzaj=new Button("Zarządzaj");
        zarzadzaj.setOnMouseClicked(event -> {
                zarzadzaj();

        });
        zarzadzaj.setMaxSize(WIDTH, HEIGHT);


        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(33.3);
//        columnConstraints.setPercentWidth(50);



        Button sprawdzObecnosc=new Button("Sprawdź obecność");
        sprawdzObecnosc.setPrefSize(WIDTH,HEIGHT);
        sprawdzObecnosc.setOnMouseClicked(event -> {
            sprawdzObecnosc();
        });
        Button wyswietlGrupy=new Button("Wyświetl Grupy");
        wyswietlGrupy.setPrefSize(WIDTH,HEIGHT);
        wyswietlGrupy.setOnMouseClicked(event -> {
            wyswietlGrupy();
        });
        gridPane.getChildren().addAll(zarzadzaj,sprawdzObecnosc,wyswietlGrupy);
//        gridPane.getChildren().addAll(zarzadzaj,sprawdzObecnosc);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);
//        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints);
        gridPane.getRowConstraints().addAll(rowConstraints);
        GridPane.setConstraints(zarzadzaj,0,0);
        GridPane.setConstraints(sprawdzObecnosc,1,0);
        GridPane.setConstraints(wyswietlGrupy,2,0);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setPrefSize(Control.USE_PREF_SIZE,Control.USE_PREF_SIZE);



    }

    private void wyswietlGrupy() {

        Start.scene.setRoot(new WyswietlGrupy().borderPane);
        Start.scene.getWindow().sizeToScene();
    }

    private void zarzadzaj() {
        Start.scene.setRoot(new ManageStudent().borderPane);
        Start.scene.getWindow().sizeToScene();
    }

    private void sprawdzObecnosc() {
        Start.scene.setRoot(new SprawdzObecnosci().borderPane);
        Start.scene.getWindow().sizeToScene();


    }
}
