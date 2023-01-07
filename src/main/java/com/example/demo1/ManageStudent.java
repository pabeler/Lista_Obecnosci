package com.example.demo1;

import com.common.DataPackage;
import javafx.animation.PauseTransition;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ManageStudent {
    public final BorderPane borderPane=new BorderPane();
    final double WIDTH = 150;


    public ManageStudent(){
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
        Navbar navbar=new Navbar();
        GridPane gridPane = new GridPane();









        Button dodaj=new Button("Dodaj Studenta");
        dodaj.setPrefWidth(WIDTH);
        TextField imieDodaj=new TextField();
        imieDodaj.setPromptText("Imie");
        TextField nazwiskoDodaj=new TextField();
        nazwiskoDodaj.setPromptText("Nazwisko");
        TextField idDodaj=new TextField();
        idDodaj.setPromptText("ID Studenta");

        dodaj.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.ADD_STUDENT, new HashMap<>(Map.of(
                    "Imie", imieDodaj.getText(),
                    "Nazwisko", nazwiskoDodaj.getText())));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie=Start.client.receive();
                if(powiadomienie.getCommand()==DataPackage.Command.SUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Dodano studenta");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();

                }
                else if (powiadomienie.getCommand()==DataPackage.Command.UNSUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Nie dodano studenta");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else {
                    MyPopup myPopup=new MyPopup("Nie można połączyć z serwerem");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });



        gridPane.add(dodaj,0,0);
        gridPane.add(imieDodaj,1,0);
        gridPane.add(nazwiskoDodaj,2,0);

        Button usun=new Button("Usuń Studenta");
        usun.setPrefWidth(WIDTH);

        TextField idUsun=new TextField();
        idUsun.setPromptText("ID Studenta");
        gridPane.add(usun,0,1);
        gridPane.add(idUsun,1,1);
        usun.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.DELETE_STUDENT, new HashMap<>(Map.of("ID_Studenta", Integer.valueOf(idUsun.getText()))));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie=Start.client.receive();
                if (powiadomienie.getCommand()==DataPackage.Command.SUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Usunięto studenta");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else if (powiadomienie.getCommand()==DataPackage.Command.UNSUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Nie usunięto studenta");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else {
                    MyPopup myPopup = new MyPopup("Nie można połączyć z serwerem");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button usunZGrupy=new Button("Usuń Studenta z grupy");

        usunZGrupy.setPrefWidth(WIDTH);
        TextField idUsunZGrupyStudent=new TextField();
        idUsunZGrupyStudent.setPromptText("ID Studenta");
        gridPane.add(usunZGrupy,0,2);
        gridPane.add(idUsunZGrupyStudent,1,2);
        usunZGrupy.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.REMOVE_STUDENT_FROM_GROUP, new HashMap<>(Map.of("ID_Studenta", Integer.valueOf(idUsunZGrupyStudent.getText()))));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie=Start.client.receive();
                if (powiadomienie.getCommand()==DataPackage.Command.SUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Usunięto studenta z grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else if (powiadomienie.getCommand()==DataPackage.Command.UNSUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Nie usunięto studenta z grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else {
                    MyPopup myPopup = new MyPopup("Nie można połączyć z serwerem");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button dodajDoGrupy=new Button("Dodaj Studenta do grupy");
        dodajDoGrupy.setPrefWidth(WIDTH);
        TextField idDodajDoGrupy=new TextField();
        idDodajDoGrupy.setPromptText("ID Studenta");
        TextField grupaDodajDoGrupy=new TextField();
        grupaDodajDoGrupy.setPromptText("ID Grupy");
        gridPane.add(dodajDoGrupy,0,3);
        gridPane.add(idDodajDoGrupy,1,3);
        gridPane.add(grupaDodajDoGrupy,2,3);
        dodajDoGrupy.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.ADD_STUDENT_TO_GROUP, new HashMap<>(Map.of("ID_Studenta", Integer.valueOf(idDodajDoGrupy.getText()), "ID_Grupy", Integer.valueOf(grupaDodajDoGrupy.getText()))));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie=Start.client.receive();
                if (powiadomienie.getCommand()==DataPackage.Command.SUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Dodano studenta do grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else if (powiadomienie.getCommand()==DataPackage.Command.UNSUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Nie dodano studenta do grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else {
                    MyPopup myPopup = new MyPopup("Nie można połączyć z serwerem");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });



        Button dodajTerminGrupy=new Button("Dodaj termin grupy");
        dodajTerminGrupy.setPrefWidth(WIDTH);
        TextField idDodajTerminGrupy=new TextField();
        idDodajTerminGrupy.setPromptText("ID Grupy");
        TextField dataDodajTerminGrupy=new TextField();
        dataDodajTerminGrupy.setPromptText("Data");
        gridPane.add(dodajTerminGrupy,0,4);
        gridPane.add(idDodajTerminGrupy,1,4);
        gridPane.add(dataDodajTerminGrupy,2,4);
        borderPane.setCenter(gridPane);
        borderPane.setTop(navbar.gridPane);
        dodajTerminGrupy.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.ADD_DEADLINE, new HashMap<>(Map.of("ID_Grupy", Integer.valueOf(idDodajTerminGrupy.getText()), "Data", dataDodajTerminGrupy.getText())));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie=Start.client.receive();
                if (powiadomienie.getCommand()==DataPackage.Command.SUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Dodano termin grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else if (powiadomienie.getCommand()==DataPackage.Command.UNSUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Nie dodano terminu grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else {
                    MyPopup myPopup = new MyPopup("Nie można połączyć z serwerem");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button dodajGrupa=new Button("Dodaj Grupe");
        dodajGrupa.setPrefWidth(WIDTH);
        TextField nazwaDodajGrupa=new TextField();
        nazwaDodajGrupa.setPromptText("Nazwa Grupy");
        gridPane.add(dodajGrupa,0,5);
        gridPane.add(nazwaDodajGrupa,1,5);
        dodajGrupa.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.ADD_GROUP, new HashMap<>(Map.of("Nazwa", nazwaDodajGrupa.getText())));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie=Start.client.receive();
                if (powiadomienie.getCommand()==DataPackage.Command.SUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Dodano grupe");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else if (powiadomienie.getCommand()==DataPackage.Command.UNSUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Nie dodano grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else {
                    MyPopup myPopup = new MyPopup("Nie można połączyć z serwerem");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button usunGrupa=new Button("Usun Grupe");
        usunGrupa.setPrefWidth(WIDTH);
        TextField idUsunGrupa=new TextField();
        idUsunGrupa.setPromptText("ID Grupy");
        gridPane.add(usunGrupa,0,6);
        gridPane.add(idUsunGrupa,1,6);
        usunGrupa.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.DELETE_GROUP, new HashMap<>(Map.of("ID_Grupy", Integer.valueOf(idUsunGrupa.getText()))));
            try {
                Start.client.send(dataPackage);
                DataPackage powiadomienie=Start.client.receive();
                if (powiadomienie.getCommand()==DataPackage.Command.SUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Usunieto grupe");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else if (powiadomienie.getCommand()==DataPackage.Command.UNSUCCESSFUL){
                    MyPopup myPopup=new MyPopup("Nie usunieto grupy");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( a -> myPopup.hide() );
                    delay.play();
                }
                else {
                    MyPopup myPopup = new MyPopup("Nie można połączyć z serwerem");
                    myPopup.show(borderPane.getScene().getWindow());
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(a -> myPopup.hide());
                    delay.play();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });













    }

}