package com.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HorariosController {
    //declaração de váriaveis
    @FXML
    private Text horario1, horario2,horario3, horario4,horario5, horario6;
    @FXML
    private Rectangle reservado1, reservado2,reservado3, reservado4,reservado5, reservado6;
    @FXML
    private Button botaoReservar1, botaoReservar2,botaoReservar3, botaoReservar4,botaoReservar5, botaoReservar6;

    private List<Text> horarioTextos;
    private List<Rectangle> reservadoRectangles;
    private List<Button> botaoReservarList;

    public int sala;
    //inicializar arraylist e variáveis FXML
    @FXML
    public void initialize() {
        horarioTextos = new ArrayList<>();
        reservadoRectangles = new ArrayList<>();
        botaoReservarList = new ArrayList<>();

        horarioTextos.add(horario1);
        horarioTextos.add(horario2);
        horarioTextos.add(horario3);
        horarioTextos.add(horario4);
        horarioTextos.add(horario5);
        horarioTextos.add(horario6);

        reservadoRectangles.add(reservado1);
        reservadoRectangles.add(reservado2);
        reservadoRectangles.add(reservado3);
        reservadoRectangles.add(reservado4);
        reservadoRectangles.add(reservado5);
        reservadoRectangles.add(reservado6);

        botaoReservarList.add(botaoReservar1);
        botaoReservarList.add(botaoReservar2);
        botaoReservarList.add(botaoReservar3);
        botaoReservarList.add(botaoReservar4);
        botaoReservarList.add(botaoReservar5);
        botaoReservarList.add(botaoReservar6);
    }

    public void trocarTelasSalas(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("salas.fxml"));
        Parent root = loader.load();


        // Get the controller after loading the FXML
        SalaController salaController = loader.getController();

        // Call the onShown method of SalaController
        salaController.onShown();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setSala(int sala) {
        this.sala = sala;

        List<Horario> horarios = Main.getMap().get(sala);

        if (horarios != null && !horarios.isEmpty()) {
            for (int i = 0; i < horarioTextos.size() && i < horarios.size(); i++) {
                horarioTextos.get(i).setText(horarios.get(i).getHora());
                atualizarCor(i, horarios.get(i).isOcupado());
            }
        } else {
            for (int i = 0; i < horarioTextos.size(); i++) {
                horarioTextos.get(i).setText("Nenhum horário disponível");
                atualizarCor(i, false);
            }
        }
    }

    @FXML
    public void botaoReservar1Pressionado(ActionEvent e) {
        reservarHorario(0);
    }

    @FXML
    public void botaoReservar2Pressionado(ActionEvent e) {
        reservarHorario(1);
    }
    public void botaoReservar3Pressionado(ActionEvent e) {
        reservarHorario(2);
    }
    public void botaoReservar4Pressionado(ActionEvent e) {
        reservarHorario(3);
    }
    public void botaoReservar5Pressionado(ActionEvent e) {
        reservarHorario(4);
    }public void botaoReservar6Pressionado(ActionEvent e) {
        reservarHorario(5);
    }

    private void reservarHorario(int index) {
        List<Horario> horarios = Main.getMap().get(sala);

        if (horarios != null && index < horarios.size()) {
            boolean ocupadoAtual = horarios.get(index).isOcupado();
            horarios.get(index).setOcupado(!ocupadoAtual);

            atualizarCor(index, !ocupadoAtual);
        } else {
            System.out.println("Erro: Horário inválido ou lista de horários não encontrada.");
        }
    }

    private void atualizarCor(int index, boolean ocupado) {
        if (index < reservadoRectangles.size()) {
            if (ocupado) {
                reservadoRectangles.get(index).setFill(Color.GREEN);
            } else {
                reservadoRectangles.get(index).setFill(Color.RED);
            }
        }
    }
}
