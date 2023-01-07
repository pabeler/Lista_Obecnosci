package com.example.demo1;

import com.common.DataPackage;
import com.common.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SprawdzObecnosci {
    BorderPane borderPane=new BorderPane();
    SprawdzObecnosci() {

        Navbar navbar=new Navbar();

        navbar.gridPane.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(navbar.gridPane);
        ListView<Object> listView=new ListView<>();
        //change listview cell height
//        listView.getItems().addAll(new TytulyObecnosc());

//        listView.prefHeightProperty().bind(listView.fixedCellSizeProperty().multiply(20));

//        listView.getItems().addAll(new TytulyObecnosc());
        try {
            Start.client.send(new DataPackage(DataPackage.Command.GET_ABSENCE_LIST,null));
            DataPackage dataPackage=Start.client.receive();
            for(String o:dataPackage.getData().keySet()){
                Student student =(Student) dataPackage.getData().get(o);
                listView.getItems().addAll(new StudentElement(student.getImie(),student.getNazwisko(),student.getId(),student.getGrupa(),student.getObecnosc()));

            }
        } catch (IOException|ClassNotFoundException e) {
            Popup popup=new Popup();
            popup.getContent().add(new Text("Nie można połączyć z serwerem"));
        }


//        borderPane.setCenter(new TytulyObecnosc());
        borderPane.setBottom(listView);
        borderPane.setCenter(new TytulyObecnosc());

    }
}
