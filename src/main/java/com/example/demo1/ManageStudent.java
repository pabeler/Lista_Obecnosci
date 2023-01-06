package com.example.demo1;

import com.common.DataPackage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
        TextField grupaDodaj=new TextField();
        grupaDodaj.setPromptText("ID Grupy");

        dodaj.setOnMouseClicked(event ->{
            DataPackage dataPackage = new DataPackage(DataPackage.Command.ADD_STUDENT, new HashMap<>(Map.of("Imie", imieDodaj.getText(), "Nazwisko", nazwiskoDodaj.getText(), "ID_Studenta", idDodaj.getText(), "ID_Grupy", grupaDodaj.getText())));
            try {
                Start.client.send(dataPackage);
                Start.client.receive();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });



        gridPane.add(dodaj,0,0);
        gridPane.add(idDodaj,1,0);
        gridPane.add(imieDodaj,2,0);
        gridPane.add(nazwiskoDodaj,3,0);
        gridPane.add(grupaDodaj,4,0);

        Button usun=new Button("Usuń Studenta");
        usun.setPrefWidth(WIDTH);
        TextField idUsun=new TextField();
        idUsun.setPromptText("ID Studenta");
        gridPane.add(usun,0,1);
        gridPane.add(idUsun,1,1);

        Button usunZGrupy=new Button("Usuń Studenta z grupy");
        usunZGrupy.setPrefWidth(WIDTH);
        TextField idUsunZGrupyStudent=new TextField();
        TextField idUsunZGrupyGrupa=new TextField();
        idUsunZGrupyStudent.setPromptText("ID Studenta");
        idUsunZGrupyGrupa.setPromptText("ID Grupy");
        gridPane.add(usunZGrupy,0,2);
        gridPane.add(idUsunZGrupyStudent,1,2);
        gridPane.add(idUsunZGrupyGrupa,2,2);

        Button dodajDoGrupy=new Button("Dodaj Studenta do grupy");
        dodajDoGrupy.setPrefWidth(WIDTH);
        TextField idDodajDoGrupy=new TextField();
        idDodajDoGrupy.setPromptText("ID Studenta");
        TextField grupaDodajDoGrupy=new TextField();
        grupaDodajDoGrupy.setPromptText("ID Grupy");
        gridPane.add(dodajDoGrupy,0,3);
        gridPane.add(idDodajDoGrupy,1,3);
        gridPane.add(grupaDodajDoGrupy,2,3);



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
//        navbar.gridPane.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(navbar.gridPane);

        Button dodajGrupa=new Button("Dodaj Grupe");
        dodajGrupa.setPrefWidth(WIDTH);
        TextField idDodajGrupa=new TextField();
        idDodajGrupa.setPromptText("ID Grupy");
        gridPane.add(dodajGrupa,0,5);
        gridPane.add(idDodajGrupa,1,5);

        Button usunGrupa=new Button("Usun Grupe");
        usunGrupa.setPrefWidth(WIDTH);
        TextField idUsunGrupa=new TextField();
        idUsunGrupa.setPromptText("ID Grupy");
        gridPane.add(usunGrupa,0,6);
        gridPane.add(idUsunGrupa,1,6);













    }

}