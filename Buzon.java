// PARA COMPARTIR LOS EVENTOS ENTRE THREADS

import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private Queue<Evento> eventos;
    private int capacidad;

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.eventos = new LinkedList<>();
    }

    // REVISAR BIEN
    public synchronized void enviar(Evento evento) throws InterruptedException {
        while (eventos.size() >= capacidad) {
            wait();
        }
        eventos.add(evento);
        notifyAll();
    }

    // REVISAR BIEN
    public synchronized Evento recibir() throws InterruptedException {
        while (eventos.isEmpty()) {
            wait();
        }
        Evento evento = eventos.poll();
        notifyAll();
        return evento;
    }
}