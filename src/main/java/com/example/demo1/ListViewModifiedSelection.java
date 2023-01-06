package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ListViewModifiedSelection extends Application {
    @Override
    public void start(Stage primaryStage) {
        ListView<String> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        for (int i = 1 ; i <= 25 ; i++) {
            listView.getItems().add("Item "+i);
        }

        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (cell.isEmpty()) {
                    return ;
                }

                int index = cell.getIndex() ;
                if (listView.getSelectionModel().getSelectedIndices().contains(index)) {
                    listView.getSelectionModel().clearSelection(index);
                } else {
                    listView.getSelectionModel().select(index);
                }

                listView.requestFocus();

                e.consume();
            });

            return cell ;
        });

        primaryStage.setScene(new Scene(listView, 250, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
