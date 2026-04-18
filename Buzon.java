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

    public synchronized void enviar(Evento evento) throws InterruptedException {
        if (capacidad > 0) {
            while (eventos.size() >= capacidad) {
                wait(); // ESPERA PASIVA
            }
        }
        eventos.add(evento);
        System.out.println("Enviado evento " + evento.getSecuencial());
        notifyAll();
    }

    public synchronized Evento recibir() throws InterruptedException {
        while (eventos.isEmpty()) {
            wait(); // ESPERA PASIVA
        }
        Evento evento = eventos.remove(); // ELIMINA EL MÁS ANTIGUO
        System.out.println("Recibido evento " + evento.getSecuencial());        
        notifyAll();
        return evento;
    }
}