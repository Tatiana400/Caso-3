package Camilo;

import java.util.LinkedList;

public class Buzon {
    private int capacidad;
    private LinkedList<Evento> eventos;
    private boolean enUso;

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.eventos = new LinkedList<>();
        this.enUso = false;
    }

    public synchronized int getCapacidad() {
        return capacidad;
    }

    public synchronized int getCantidadEventos() {
        return eventos.size();
    }

    // Métodos para espera semiactiva
    public synchronized boolean getEnUso() {
        return enUso;
    }

    public synchronized void usar() {
        enUso = true;
    }

    public synchronized void dejarUsar() {
        enUso = false;
    }

    public synchronized Evento getEventoSemiActivo() {
        Evento evento = eventos.removeFirst();
        notify();
        return evento;
    }

    public synchronized void addEventoSemiActivo(Evento evento) {
        eventos.addLast(evento);
        notify();
    }

    // Métodos para espera pasiva
    public synchronized Evento getEvento() {
        while (eventos.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Evento evento = eventos.removeFirst();
        notify();
        return evento;
    }

    public synchronized void addEvento(Evento evento) {
        while (eventos.size() >= capacidad) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eventos.addLast(evento);
        notify();
    }
    
}