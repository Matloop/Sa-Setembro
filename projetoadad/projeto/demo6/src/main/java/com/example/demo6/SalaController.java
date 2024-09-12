package com.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.demo6.Main.getMap;

public class SalaController {

    @FXML
    private Text ocupacao1, ocupacao2, ocupacao3;

    @FXML
    private Button sala1Button, sala2Button, sala3Button;

    private List<Text> ocupacoes;
    private int sala;

    @FXML
    public void initialize() {
        ocupacoes = new ArrayList<>();
        ocupacoes.add(ocupacao1);
        ocupacoes.add(ocupacao2);
        ocupacoes.add(ocupacao3);
        System.out.println("SalaController inicializado");
    }

    @FXML
    public void trocarTelaHorarios(ActionEvent e) throws IOException {
        Button clickedButton = (Button) e.getSource();
        switch (clickedButton.getId()) {
            case "sala1Button":
                sala = 1;
                break;
            case "sala2Button":
                sala = 2;
                break;
            case "sala3Button":
                sala = 3;
                break;
            default:
                System.out.println("ID do botão não reconhecido: " + clickedButton.getId());
                return;
        }
        System.out.println("Sala selecionada: " + sala);

        atualizarOcupacaoSalas();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("horarios.fxml"));
        Parent root = loader.load();
        HorariosController horariosController = loader.getController();
        if (horariosController != null) {
            horariosController.setSala(sala);
            System.out.println("HorariosController configurado com sala " + sala);
        } else {
            System.out.println("ERRO: HorariosController não encontrado.");
        }

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Tela de horários carregada para sala " + sala);
    }

    public void atualizarOcupacaoSalas() {
        HashMap<Integer, List<Horario>> horarioSalas = Main.getMap();
        if (horarioSalas == null) {
            System.out.println("ERRO: Mapa de horários é nulo");
            return;
        }

        for (int i = 1; i <= 3; i++) {
            List<Horario> horarios = horarioSalas.get(i);
            double ocupacao = calcularOcupacao(horarios);
            ocupacoes.get(i-1).setText(String.format("Ocupação Sala %d: %.2f%%", i, ocupacao));
            System.out.println("Ocupação da Sala " + i + ": " + ocupacao + "%");
        }
    }

    private double calcularOcupacao(List<Horario> horarios) {
        if (horarios == null || horarios.isEmpty()) {
            System.out.println("AVISO: Lista de horários nula ou vazia");
            return 0;
        }

        long ocupados = horarios.stream().filter(Horario::isOcupado).count();
        return (ocupados / (double) horarios.size()) * 100;
    }

    public void onShown() {
        atualizarOcupacaoSalas();
        System.out.println("SalaController: onShown chamado");
    }
}