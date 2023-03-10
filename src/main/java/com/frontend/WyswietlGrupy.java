package com.frontend;

import com.common.DataPackage;
import com.common.Grupa;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Class that represents the window that allows to display groups.
 */
public class WyswietlGrupy {
    BorderPane borderPane = new BorderPane();
    /**
     * Constructor that creates the window.
     */
    WyswietlGrupy() {
        Navbar navbar = new Navbar();
        navbar.gridPane.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(navbar.gridPane);
        ListView<Object> listView = new ListView<>();
        listView.setPrefSize(600, 500);
        try {
            Start.client.send(new DataPackage(DataPackage.Command.GET_GROUP_LIST, null));
            DataPackage dataPackage = Start.client.receive();
            for (String o : dataPackage.getMap().keySet()) {
                Grupa grupa = (Grupa) dataPackage.getMap().get(o);
                if (grupa.getTermin() == null) {
                    listView.getItems().addAll(new GrupaElement(grupa.getNazwa(), grupa.getId(), null));
                } else {
                    listView.getItems().addAll(new GrupaElement(grupa.getNazwa(), grupa.getId(), grupa.getTermin()
                            .toString()));
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
        borderPane.setCenter(new TytulyGrupy());
    }
}