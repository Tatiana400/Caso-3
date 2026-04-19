package Camilo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Clasificador extends Thread {
    static int nc;
    static CyclicBarrier barrera;
    static Buzon[] buzonesConsolidacion;
    private Buzon buzonClasi;

    public Clasificador(Buzon buzonClasi) {
        this.buzonClasi = buzonClasi;
    }

    Runnable eventoFinal = new Runnable() {
        @Override
        public void run() {
            System.out.println("Todos los clasificadores han terminado. Enviando eventos de fin a los servidores.");
            for (Buzon buzon : buzonesConsolidacion) {
                Evento EventoFin = new Evento(new int[]{0, 0}, true);
                buzon.addEvento(EventoFin);
            }
        }  
    };

    public void setNc(int nc) {
        Clasificador.nc = nc;
        Clasificador.barrera = new CyclicBarrier(nc, eventoFinal);
    }

    public void setbuzonesConsolidacion(Buzon[] buzonesConsolidacion) {
        Clasificador.buzonesConsolidacion = buzonesConsolidacion;
    }

    @Override
    public void run() {
        while (true) {
            Evento evento = buzonClasi.getEvento();
            if (evento.isFin()) {
                break;
            }
            System.out.println("Clasificador procesó evento con id: " + evento.getId()[0] + "-" + evento.getId()[1]);
            int seudo = evento.getSeudo();
            Buzon buzonDestino = buzonesConsolidacion[seudo];
            buzonDestino.addEvento(evento);
        }
        
        try {
            barrera.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
    
}