package com.example.demo1;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class WyswietlGrupy {
    BorderPane borderPane=new BorderPane();
    WyswietlGrupy(){
        Navbar navbar=new Navbar();

        navbar.gridPane.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(navbar.gridPane);
        ListView<Object> listView=new ListView<>();
        //change listview cell height
//        listView.getItems().addAll(new TytulyObecnosc());

//        listView.prefHeightProperty().bind(listView.fixedCellSizeProperty().multiply(20));

//        listView.getItems().addAll(new TytulyObecnosc());
        listView.getItems().addAll(new StudentElement("Mariusz","Janyszek",1,"1"));
        listView.getItems().addAll(new StudentElement("Test","Test",2,"1"));

//        borderPane.setCenter(new TytulyObecnosc());
        borderPane.setBottom(listView);
        borderPane.setCenter(new TytulyObecnosc());
    }


}
