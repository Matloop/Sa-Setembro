package com.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    public static HashMap<Integer, ArrayList<Horario>> horarioSalas;

    @Override
    public void start(Stage stage) {
        // Initialize horarioSalas
        horarioSalas = new HashMap<>();

        // Define objetos horários em uma lista
        ArrayList<Horario> horarios1 = new ArrayList<>();
        horarios1.add(new Horario("3:00", false));
        horarios1.add(new Horario("15:00", false));

        ArrayList<Horario> horarios2 = new ArrayList<>();
        horarios2.add(new Horario("17:30", false));
        horarios2.add(new Horario("15:00", false));

        ArrayList<Horario> horarios3 = new ArrayList<>();
        horarios3.add(new Horario("17:30", false));
        horarios3.add(new Horario("15:00", false));

        ArrayList<Horario> horarios4 = new ArrayList<>();
        horarios3.add(new Horario("17:30", false));
        horarios3.add(new Horario("15:00", false));

        ArrayList<Horario> horarios5 = new ArrayList<>();
        horarios3.add(new Horario("17:30", false));
        horarios3.add(new Horario("15:00", false));

        ArrayList<Horario> horarios6 = new ArrayList<>();
        horarios3.add(new Horario("17:30", false));
        horarios3.add(new Horario("15:00", false));

        // Coloca as listas de horárias no HashMap
        horarioSalas.put(1, horarios1);
        horarioSalas.put(2, horarios2);
        horarioSalas.put(3, horarios3);
        horarioSalas.put(4,horarios4);
        horarioSalas.put(5,horarios5);
        horarioSalas.put(6,horarios6);

        // Faz o load do arquivo FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("salas.fxml"));
            Parent root = loader.load();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("Controle de horários");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception or provide user feedback
        }
    }
    //iniciar programa
    public static void main(String[] args) {
        launch();
    }
    //retornar HashMap do horário das salas
    public static HashMap<Integer, ArrayList<Horario>> getMap() {
        return horarioSalas;
    }
}
