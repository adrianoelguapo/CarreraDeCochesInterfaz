package com.akadoblee.carreradecochesinterfaz;

import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;

public class Carrera {

    private final List<String> clasificacion = new ArrayList<>();
    private final int totalCoches;
    private final List<CarreraListener> listeners = new ArrayList<>();

    public Carrera(int totalCoches) {

        this.totalCoches = totalCoches;

    }

    public synchronized void registrarLlegada(String nombre) {

        clasificacion.add(nombre);

        for (CarreraListener l : listeners) {

            String n = nombre;
            List<String> copia = new ArrayList<>(clasificacion);
            Platform.runLater(() -> l.onLlegada(n, copia));

        }

    }

    public synchronized void registrarProgreso(String nombre, int distancia) {

        for (CarreraListener l : listeners) {

            String n = nombre;
            int d = distancia;
            Platform.runLater(() -> l.onProgreso(n, d));

        }

    }

    public synchronized void addListener(CarreraListener listener) {

        listeners.add(listener);

    }

    public synchronized void removeListener(CarreraListener listener) {

        listeners.remove(listener);

    }

    public synchronized List<String> getClasificacion() {

        return new ArrayList<>(clasificacion);

    }

    public interface CarreraListener {

        void onProgreso(String nombre, int distanciaRecorrida);
        void onLlegada(String nombre, List<String> clasificacionParcial);

    }

}