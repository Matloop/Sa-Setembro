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
    @FXML
    private Text horario1, horario2, horario3, horario4, horario5, horario6, salaAtual;
    @FXML
    private Rectangle reservado1, reservado2, reservado3, reservado4, reservado5, reservado6;
    @FXML
    private Button botaoReservar1, botaoReservar2, botaoReservar3, botaoReservar4, botaoReservar5, botaoReservar6;

    private List<Text> horarioTextos;
    private List<Rectangle> reservadoRectangles;
    private List<Button> botaoReservarList;

    public int sala;

    @FXML
    public void initialize() {
        horarioTextos = new ArrayList<>(List.of(horario1, horario2, horario3, horario4, horario5, horario6));
        reservadoRectangles = new ArrayList<>(List.of(reservado1, reservado2, reservado3, reservado4, reservado5, reservado6));
        botaoReservarList = new ArrayList<>(List.of(botaoReservar1, botaoReservar2, botaoReservar3, botaoReservar4, botaoReservar5, botaoReservar6));

        System.out.println("HorariosController inicializado.");
        System.out.println("Número de horários: " + horarioTextos.size());
        System.out.println("Número de retângulos: " + reservadoRectangles.size());
        System.out.println("Número de botões: " + botaoReservarList.size());
    }

    public void trocarTelasSalas(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("salas.fxml"));
        Parent root = loader.load();

        SalaController salaController = loader.getController();
        salaController.onShown();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setSala(int sala) {
        this.sala = sala;
        atualizarInterface();
    }

    private void atualizarInterface() {
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
    public void botaoReservar2Pressionado(ActionEvent e) { reservarHorario(1); }
    @FXML
    public void botaoReservar3Pressionado(ActionEvent e) { reservarHorario(2); }
    @FXML
    public void botaoReservar4Pressionado(ActionEvent e) { reservarHorario(3); }
    @FXML
    public void botaoReservar5Pressionado(ActionEvent e) { reservarHorario(4); }
    @FXML
    public void botaoReservar6Pressionado(ActionEvent e) { reservarHorario(5); }

    private void reservarHorario(int index) {
        List<Horario> horarios = Main.getMap().get(sala);

        if (horarios != null && index < horarios.size()) {
            Horario horario = horarios.get(index);
            boolean novoEstado = !horario.isOcupado();
            horario.setOcupado(novoEstado);

            // Atualiza o HashMap em Main
            Main.getMap().put(sala, new ArrayList<>(horarios));

            atualizarInterface();

            System.out.println("Horário " + index + " atualizado. Ocupado: " + novoEstado);
        } else {
            System.out.println("Erro: Horário inválido ou lista de horários não encontrada.");
        }
    }

    private void atualizarCor(int index, boolean ocupado) {
        if (index < reservadoRectangles.size()) {
            Rectangle rect = reservadoRectangles.get(index);
            Color novaCor = ocupado ? Color.GREEN : Color.RED;
            rect.setFill(novaCor);
        } else {
            System.out.println("Erro: Índice de retângulo inválido.");
        }
    }

    public void mostrarSala(int sala ){
        salaAtual.setText("Sala: " + sala);
    }
}