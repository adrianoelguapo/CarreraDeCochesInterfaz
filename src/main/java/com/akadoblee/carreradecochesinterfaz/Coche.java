package com.akadoblee.carreradecochesinterfaz;

public class Coche extends Thread {

    private String nombre;
    private int distanciaRecorrida;
    private int distanciaTotal;
    private int velocidadMaxima;
    private Carrera carrera;

    public Coche(String nombre, int distanciaTotal, int velocidadMaxima, Carrera carrera) {

        this.nombre = nombre;
        this.distanciaRecorrida = 0;
        this.distanciaTotal = distanciaTotal;
        this.velocidadMaxima = velocidadMaxima;
        this.carrera = carrera;

    }

    @Override
    public void run() {

        System.out.println(nombre + " ha empezado la carrera!");

        while (distanciaRecorrida < distanciaTotal) {

            int avance = (int)(Math.random() * velocidadMaxima) + 1;
            distanciaRecorrida += avance;

            if (distanciaRecorrida > distanciaTotal) {

                distanciaRecorrida = distanciaTotal;

            }

            carrera.actualizarProgreso(nombre, distanciaRecorrida);

            try {

                Thread.sleep(800);

            } catch (InterruptedException e) {

                e.printStackTrace();
                break;

            }

        }

        System.out.println(nombre + " ha llegado a la meta!");
        carrera.registrarLlegada(nombre);

    }

    public String getNombre() {

        return nombre;

    }

    public int getDistanciaRecorrida() {

        return distanciaRecorrida;

    }

    public int getDistanciaTotal() {

        return distanciaTotal;

    }

}