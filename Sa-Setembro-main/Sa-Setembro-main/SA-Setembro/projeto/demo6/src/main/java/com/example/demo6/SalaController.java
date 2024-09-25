package com.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.demo6.Main.getMap;


public class SalaController {

    @FXML
    private Text ocupacao1, ocupacao2, ocupacao3, salasMaisOcupadas;
    @FXML
    private Button sala1Button, sala2Button, sala3Button;
    @FXML
    private Arc arc1,arc2,arc3;
    private ArrayList<Arc> arcs;
    private List<Text> ocupacoes;
    private int sala;
    private ArrayList<Double> listaOcupacao;

    @FXML
    public void initialize() {
        ocupacoes = new ArrayList<>();
        arcs = new ArrayList<>();
        arcs.add(arc1);
        arcs.add(arc2);
        arcs.add(arc3);
        ocupacoes.add(ocupacao1);
        ocupacoes.add(ocupacao2);
        ocupacoes.add(ocupacao3);
        System.out.println("SalaController inicializado");

        for(Arc arc : arcs){
            arc.setVisible(false);
        }
    }

    @FXML
    public void trocarTelaHorarios(ActionEvent e) throws IOException {
        identificarSalas(e);
        atualizarOcupacaoSalas();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("horarios.fxml"));
        Parent root = loader.load();

        HorariosController horariosController = loader.getController();

        horariosController.mostrarSala(sala);

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

    public void  identificarSalas(ActionEvent e){
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
    }

    public void atualizarOcupacaoSalas() {
        HashMap<Integer, List<Horario>> horarioSalas = Main.getMap();
        if (horarioSalas == null) {
            System.out.println("ERRO: Mapa de horários é nulo");
            return;
        }

         listaOcupacao = new ArrayList<Double>();
        for (int i = 1; i <= 3; i++) {
            List<Horario> horarios = horarioSalas.get(i);
            double ocupacao = calcularOcupacao(horarios);
            ocupacoes.get(i-1).setText(String.format("Ocupação Sala %d: %.2f%%", i, ocupacao));
            listaOcupacao.add(ocupacao);
            System.out.println("Ocupação da Sala " + i + ": " + ocupacao + "%");
            atualizarGraficoPorcentagem(ocupacao, i);
        }

        salaMaisOcupada();
    }

    public void salaMaisOcupada(){
        Double salaOcupacao1 = listaOcupacao.get(0);
        Double salaOcupacao2 = listaOcupacao.get(1);
        Double salaOcupacao3 = listaOcupacao.get(2);

        ArrayList<String> salasMaisOcupadasLista = new ArrayList<>();
        //encontra a ocupação máxima
        double maxOcupacao = Math.max(salaOcupacao1,Math.max(salaOcupacao2,salaOcupacao3));
        //verifica se a ocupação maxima é uma dessas salas ou mais
        if(salaOcupacao1.equals(maxOcupacao)){
            salasMaisOcupadasLista.add("Sala 1");
        }
        if(salaOcupacao2.equals(maxOcupacao)){
            salasMaisOcupadasLista.add("Sala 2");
        }
        if(salaOcupacao3.equals(maxOcupacao)){
            salasMaisOcupadasLista.add("Sala 3");
        }

        if(salasMaisOcupadasLista.size() == 1){
            salasMaisOcupadas.setText("Sala mais ocupada: " + salasMaisOcupadasLista.get(0));
        } else {
            salasMaisOcupadas.setText("Salas mais ocupadas: " + String.join(", ", salasMaisOcupadasLista));
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

    public void atualizarGraficoPorcentagem(double porcentagem, int sala) {
        Arc arc = arcs.get(sala - 1);

        porcentagem = Math.max(0, Math.min(100, porcentagem));

        double centerX = arc.getCenterX();
        double centerY = arc.getCenterY();

        double radiusX = arc.getRadiusX();
        double radiusY = arc.getRadiusY();

        arc.setStartAngle(0); // Start from the top
        arc.setLength(porcentagem * 3.6); // Convert percentage to degrees (positive)
        if(porcentagem == 100){
            arc.setLength(359.9);
        }
        arc.setType(ArcType.ROUND);

        if(porcentagem > 0){
            arc.setVisible(true);
        }

    }
}