package com.frontend;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    static Scene scene;
    static Client client;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(new ManageStudent().borderPane, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        primaryStage.setScene(scene);
        primaryStage.show();
        try {
            client = new Client();
        } catch (IOException e) {
            HBox hBox = new HBox();
            hBox.setPrefSize(400, 100);
            hBox.setAlignment(Pos.CENTER);
            Text text = new Text("Nie można połączyć z serwerem");
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            hBox.getChildren().add(text);
            scene = new Scene(hBox, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
            primaryStage.setScene(scene);
        }
        primaryStage.setOnCloseRequest(event -> {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

