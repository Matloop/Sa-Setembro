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
        // Inicializar a lista de ocupações
        ocupacoes = new ArrayList<>();
        ocupacoes.add(ocupacao1);
        ocupacoes.add(ocupacao2);
        ocupacoes.add(ocupacao3);
    }
    //trocar
    @FXML
    public void trocarTelaHorarios(ActionEvent e) throws IOException {
        //Verifica qual botão foi criado, para definir variável sala
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
                System.out.println("ID do botão não reconhecido.");
                return;
        }

        // Atualiza a ocupação após a troca de cena
        atualizarOcupacaoSalas();


        //fazer load arquivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("horarios.fxml"));
        Parent root = loader.load();
        HorariosController horariosController = loader.getController();
        //verificar se o controller existe
        if (horariosController != null) {
            horariosController.setSala(sala);
        } else {
            System.out.println("HorariosController não encontrado.");
        }


        //iniciar a cena
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void atualizarOcupacaoSalas() {
        HashMap<Integer, ArrayList<Horario>> horarioSalas = Main.getMap();

        // Verificar ocupação da sala 1
        double ocupacaoSala1 = calcularOcupacao(horarioSalas.get(1));
        ocupacao1.setText("Ocupação Sala 1: " + ocupacaoSala1 + "%");

        // Verificar ocupação da sala 2
        double ocupacaoSala2 = calcularOcupacao(horarioSalas.get(2));
        ocupacao2.setText("Ocupação Sala 2: " + ocupacaoSala2 + "%");

        // Verificar ocupação da sala 3
        double ocupacaoSala3 = calcularOcupacao(horarioSalas.get(3));
        ocupacao3.setText("Ocupação Sala 3: " + ocupacaoSala3 + "%");
    }

    private double calcularOcupacao(ArrayList<Horario> horarios) {
        if (horarios == null || horarios.isEmpty()) {
            return 0;
        }

        long ocupados = horarios.stream().filter(Horario::isOcupado).count();
        return (ocupados / (double) horarios.size()) * 100;
    }

    public void onShown() {
        atualizarOcupacaoSalas();
    }


}
