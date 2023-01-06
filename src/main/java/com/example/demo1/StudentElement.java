package com.example.demo1;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentElement extends GridPane {
    StudentElement(String imie, String nazwisko, String id, String grupa){
        Text textImie=new Text(imie);
        Text textNazwisko=new Text(nazwisko);
        Text textId=new Text(id);
        Text textGrupa=new Text(grupa);
        ChoiceBox<Object> choiceBox=new ChoiceBox<>();


        choiceBox.getItems().addAll("Obecny","Nieobecny","Spozniony");
        choiceBox.setValue("Obecny");
        choiceBox.setOnAction(event -> {
            System.out.println(choiceBox.getValue());
            DataPackage dataPackage = new DataPackage(DataPackage.Command.CHECK_ABSENCE, new HashMap<>(Map.of("Imie", imie, "Nazwisko", nazwisko, "ID_Studenta", id, "ID_Grupy", grupa, "Obecnosc", choiceBox.getValue())));
            try {
                Start.client.send(dataPackage);
                Start.client.receive();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        this.getChildren().addAll(textImie,textNazwisko,textId,textGrupa,choiceBox);

        ColumnConstraints columnConstraints=new ColumnConstraints();
        columnConstraints.setPercentWidth(20);
        this.getColumnConstraints().addAll(columnConstraints,columnConstraints,columnConstraints,columnConstraints,columnConstraints);
        RowConstraints rowConstraints=new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().addAll(rowConstraints);

        GridPane.setConstraints(textImie,1,0);
        GridPane.setConstraints(textNazwisko,2,0);
        GridPane.setConstraints(textId,0,0);
        GridPane.setConstraints(textGrupa,3,0);
        GridPane.setConstraints(choiceBox,4,0);
//        this.setMinHeight(100);

//        this.setPrefSize(Control.USE_PREF_SIZE,Control.USE_PREF_SIZE);
        this.setMinHeight(50);
    }
}
