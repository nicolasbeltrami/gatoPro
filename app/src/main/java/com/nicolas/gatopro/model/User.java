package com.nicolas.gatopro.model;

public class User {
    private String name;
    private int puntos;
    private int partidasJugadas;

    public User() {
    }

    public User(String name, int puntos, int partidasJugadas) {
        this.name = name;
        this.puntos = puntos;
        this.partidasJugadas = partidasJugadas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }
}
