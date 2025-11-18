package com.akadoblee.carreradecochesinterfaz;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class CarreraController implements Carrera.CarreraListener {

    @FXML
    private VBox carsContainer;

    @FXML
    private Button startButton;

    @FXML
    private ListView<String> resultsList;

    private final Map<String, ProgressBar> barras = new HashMap<>();
    private final Map<String, Label> etiquetasDistancia = new HashMap<>();
    private Carrera carrera;
    private final int DISTANCIA_TOTAL = 1500;

    @FXML
    private void onStartButton() {

        startButton.setDisable(true);
        resultsList.getItems().clear();
        carsContainer.getChildren().clear();

        carrera = new Carrera(5);
        carrera.addListener(this);

        Coche c1 = new Coche("Mazda RX-7", DISTANCIA_TOTAL, 205, carrera);
        Coche c2 = new Coche("Nissan Skyline GTR R34", DISTANCIA_TOTAL, 230, carrera);
        Coche c3 = new Coche("Toyota Supra MK4", DISTANCIA_TOTAL, 220, carrera);
        Coche c4 = new Coche("Acura NSX", DISTANCIA_TOTAL, 210, carrera);
        Coche c5 = new Coche("Subaru Impreza", DISTANCIA_TOTAL, 225, carrera);
        Coche c6 = new Coche("Honda S2000", DISTANCIA_TOTAL, 200, carrera);

        addCarUI(c1.getNombre());
        addCarUI(c2.getNombre());
        addCarUI(c3.getNombre());
        addCarUI(c4.getNombre());
        addCarUI(c5.getNombre());
        addCarUI(c6.getNombre());

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
        c6.start();

    }

    private void addCarUI(String nombre) {
        HBox row = new HBox(15);
        row.getStyleClass().add("car-row");
        row.getStyleClass().add("glass-pane");

        Label nameLabel = new Label(nombre);
        nameLabel.getStyleClass().add("car-name");

        ProgressBar pb = new ProgressBar(0);
        pb.setPrefWidth(600);

        Label distLabel = new Label("0 m");
        distLabel.getStyleClass().add("car-distance");

        row.getChildren().addAll(nameLabel, pb, distLabel);
        carsContainer.getChildren().add(row);

        barras.put(nombre, pb);
        etiquetasDistancia.put(nombre, distLabel);
    }

    @Override
    public void onProgreso(String nombre, int distanciaRecorrida) {

        ProgressBar pb = barras.get(nombre);
        Label dl = etiquetasDistancia.get(nombre);

        if (pb != null && dl != null) {

            double progress = Math.min(1.0, (double) distanciaRecorrida / DISTANCIA_TOTAL);
            pb.setProgress(progress);
            dl.setText(distanciaRecorrida + " m");

        }

    }

    @Override
    public void onLlegada(String nombre, java.util.List<String> clasificacionParcial) {

        resultsList.getItems().clear();

        for (int i = 0; i < clasificacionParcial.size(); i++) {

            resultsList.getItems().add((i + 1) + ". " + clasificacionParcial.get(i));

        }

        if (clasificacionParcial.size() >= 6) {
            startButton.setDisable(false);
        }

    }

}