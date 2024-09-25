package com.example.demo6;

public class Horario {
    private String hora;
    private boolean ocupado;

    public Horario(String hora, boolean ocupado){
        this.hora = hora;
        this.ocupado = ocupado;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
