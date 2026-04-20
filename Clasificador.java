// ENVIAR EVENTOS AL SERVIDOR CORRECTO

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Clasificador extends Thread {
    private Buzon clasificacion;
    private Buzon[] servidores;

    private static CyclicBarrier barrera; // PARA SINCRONIZAR EL FIN DE LOS CLASIFICADORES

    public Clasificador(Buzon clasificacion, Buzon[] servidores) {
        this.clasificacion = clasificacion;
        this.servidores = servidores;
    }

    public static void iniciarBarrera(int nc, Buzon[] servidores) {
        barrera = new CyclicBarrier(nc, new Runnable() {
            @Override
            public void run() {
                try {
                    for ( int i = 0; i < servidores.length; i++) {
                        servidores[i].enviar(Evento.crearFin()); // ENVIAR FIN A LOS SERVIDORES
                    }
                    System.out.println("Último clasificador envió FIN a servidores");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                }
            }
        });
    }

    @Override
    public void run() {
        try {
            boolean terminado = false;
            while (!terminado) {
                Evento evento = clasificacion.recibir();
                if (evento.isFin()) {
                    terminado = true;
                } else {
                    int destino = evento.getTipo() - 1;
                    servidores[destino].enviar(evento); // ENVIAR AL SERVIDOR CORRECTO
                }
            }
            System.out.println("Último clasificador liberó la barrera");
            barrera.await(); // ESPERA HASTA QUE TODOS LOS CLASIFICADORES LLEGUEN A LA BARRERA
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } catch (BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }

}