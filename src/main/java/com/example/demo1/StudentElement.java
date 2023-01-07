package com.example.demo1;

import com.common.DataPackage;
import javafx.animation.PauseTransition;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentElement extends GridPane {
    StudentElement(String imie, String nazwisko, int id, int grupa, String obecnosc) {
        Text textImie = new Text(imie);
        Text textNazwisko = new Text(nazwisko);
        Text textId = new Text(String.valueOf(id));
        Text textGrupa = null;
        if (grupa == 0) {
            textGrupa = new Text("Brak");
        } else {
            textGrupa = new Text(String.valueOf(grupa));
        }
        ChoiceBox<Object> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Obecny", "Nieobecny", "Spozniony");
        choiceBox.setValue(obecnosc);
        choiceBox.setOnAction(event -> {
            System.out.println(choiceBox.getValue());
            DataPackage dataPackage = new DataPackage(DataPackage.Command.CHECK_ABSENCE,
                    new HashMap<>(Map.of("Imie", imie, "Nazwisko", nazwisko, "ID_Grupy", grupa,
                            "Obecnosc", choiceBox.getValue(), "ID_Studenta", id)));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie = Start.client.receive();
                if (powiadomienie.getCommand() == DataPackage.Command.SUCCESSFUL) {
                    MyPopup myPopup = new MyPopup("Zmieniono obecnosc");
                    myPopup.show(Start.scene.getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                } else {
                    MyPopup myPopup = new MyPopup("Nie udało się zmienić obecnosci");
                    myPopup.show(Start.scene.getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        this.getChildren().addAll(textImie, textNazwisko, textId, textGrupa, choiceBox);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(20);
        this.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints,
                columnConstraints, columnConstraints);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().addAll(rowConstraints);
        GridPane.setConstraints(textImie, 1, 0);
        GridPane.setConstraints(textNazwisko, 2, 0);
        GridPane.setConstraints(textId, 0, 0);
        GridPane.setConstraints(textGrupa, 3, 0);
        GridPane.setConstraints(choiceBox, 4, 0);
        this.setMinHeight(50);
    }
}