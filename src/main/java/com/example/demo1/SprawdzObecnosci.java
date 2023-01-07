package com.example.demo1;

import com.common.DataPackage;
import com.common.Student;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SprawdzObecnosci {
    BorderPane borderPane = new BorderPane();

    SprawdzObecnosci() {
        Navbar navbar = new Navbar();
        navbar.gridPane.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(navbar.gridPane);
        ListView<Object> listView = new ListView<>();
        listView.setPrefSize(600, 500);
        try {
            Start.client.send(new DataPackage(DataPackage.Command.GET_ABSENCE_LIST, null));
            DataPackage dataPackage = Start.client.receive();
            for (String o : dataPackage.getData().keySet()) {
                Student student = (Student) dataPackage.getData().get(o);
                if (student.getGrupa() == null) {
                    listView.getItems().addAll(new StudentElement(student.getImie(), student.getNazwisko(),
                            student.getId(), 0, student.getObecnosc()));
                } else {
                    listView.getItems().addAll(new StudentElement(student.getImie(), student.getNazwisko(),
                            student.getId(), student.getGrupa(), student.getObecnosc()));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            MyPopup myPopup = new MyPopup("Błąd połączenia z serwerem");
            myPopup.show(Start.scene.getWindow());
            try {
                Start.client.close();
            } catch (IOException ignored) {
            }
            return;
        }
        borderPane.setBottom(listView);
        borderPane.setCenter(new TytulyObecnosc());
    }
}