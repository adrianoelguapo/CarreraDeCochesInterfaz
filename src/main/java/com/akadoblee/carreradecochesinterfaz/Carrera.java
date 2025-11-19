package com.akadoblee.carreradecochesinterfaz;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carrera {

    @FXML
    private VBox carsContainer;

    @FXML
    private Button startButton;

    @FXML
    private ListView<String> resultsList;

    private final Map<String, ProgressBar> barras = new HashMap<>();
    private final Map<String, Label> etiquetasDistancia = new HashMap<>();
    private final List<String> clasificacion = new ArrayList<>();
    private final int DISTANCIA_TOTAL = 1500;

    @FXML
    private void onStartButton() {

        startButton.setDisable(true);
        resultsList.getItems().clear();
        carsContainer.getChildren().clear();

        synchronized (clasificacion) {

            clasificacion.clear();

        }

        Coche c1 = new Coche("Mazda RX-7", DISTANCIA_TOTAL, 205, this);
        Coche c2 = new Coche("Nissan Skyline GTR R34", DISTANCIA_TOTAL, 230, this);
        Coche c3 = new Coche("Toyota Supra MK4", DISTANCIA_TOTAL, 220, this);
        Coche c4 = new Coche("Acura NSX", DISTANCIA_TOTAL, 210, this);
        Coche c5 = new Coche("Subaru Impreza", DISTANCIA_TOTAL, 225, this);
        Coche c6 = new Coche("Honda S2000", DISTANCIA_TOTAL, 200, this);

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

        synchronized (barras) {

            barras.put(nombre, pb);

        }
        synchronized (etiquetasDistancia) {

            etiquetasDistancia.put(nombre, distLabel);

        }

    }

    public synchronized void actualizarProgreso(String nombre, int distanciaRecorrida) {

        javafx.application.Platform.runLater(() -> {

            ProgressBar pb;
            Label dl;

            synchronized (barras) {

                pb = barras.get(nombre);

            }

            synchronized (etiquetasDistancia) {

                dl = etiquetasDistancia.get(nombre);

            }

            if (pb != null && dl != null) {

                double progress = Math.min(1.0, (double) distanciaRecorrida / DISTANCIA_TOTAL);
                pb.setProgress(progress);
                dl.setText(distanciaRecorrida + " m");

            }

        });

    }

    public synchronized void registrarLlegada(String nombre) {

        javafx.application.Platform.runLater(() -> {

            synchronized (clasificacion) {

                clasificacion.add(nombre);

            }

            resultsList.getItems().clear();

            List<String> copiaClasificacion;

            synchronized (clasificacion) {

                copiaClasificacion = new ArrayList<>(clasificacion);

            }

            for (int i = 0; i < copiaClasificacion.size(); i++) {

                resultsList.getItems().add((i + 1) + ". " + copiaClasificacion.get(i));

            }

            if (copiaClasificacion.size() >= 6) {

                startButton.setDisable(false);

            }

        });

    }

    public synchronized List<String> getClasificacion() {

        return new ArrayList<>(clasificacion);

    }

}