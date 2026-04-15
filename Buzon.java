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
        if (capacidad > 0) {
            while (eventos.size() >= capacidad) {
                wait(); // ESPERA PASIVA
            }
        }
        eventos.add(evento);
        notifyAll();
    }

    // REVISAR BIEN
    public synchronized Evento recibir() throws InterruptedException {
        while (eventos.isEmpty()) {
            wait(); // ESPERA PASIVA
        }
        Evento evento = eventos.remove(); // ELIMINA EL MÁS ANTIGUO
        notifyAll();
        return evento;
    }
}