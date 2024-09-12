package com.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {
    private static HashMap<Integer, List<Horario>> salaHorarios = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        inicializarHorarios();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("salas.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Reserva de Salas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void inicializarHorarios() {
        for (int sala = 1; sala <= 6; sala++) {
            List<Horario> horarios = new ArrayList<>();
            horarios.add(new Horario("17:30", false));
            horarios.add(new Horario("18:30", false));
            horarios.add(new Horario("19:30", false));
            horarios.add(new Horario("20:30", false));
            horarios.add(new Horario("21:30", false));
            horarios.add(new Horario("22:30", false));
            salaHorarios.put(sala, horarios);
        }
    }

    public static HashMap<Integer, List<Horario>> getMap() {
        return salaHorarios;
    }
}