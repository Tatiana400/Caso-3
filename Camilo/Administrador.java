package Camilo;

import java.util.Random;

public class Administrador extends Thread {
    private Buzon buzonAlertas;
    private Buzon buzonClasi;
    static int nc;
    
    public Administrador(Buzon buzonAlertas, Buzon buzonClasi, int nc) {
        this.buzonAlertas = buzonAlertas;
        this.buzonClasi = buzonClasi;
        Administrador.nc = nc;
    }

    @Override
    public void run() {
        while (true) {
            while (buzonAlertas.getCantidadEventos() != 0) {
                while (buzonAlertas.getEnUso()) {
                    Thread.yield();
                }
            }
            buzonAlertas.usar();
            Evento eventoAlerta = buzonAlertas.getEvento();
            buzonAlertas.dejarUsar();
            
            if (eventoAlerta.isFin()) {
                break;
            } else {
                Random r = new Random();
                int ofensivo = r.nextInt(21);
                if (ofensivo%4 == 0) {
                    System.out.println("Evento inofensivo detectado con id: " + eventoAlerta.getId()[0] + "-" + eventoAlerta.getId()[1]);
                    while (buzonClasi.getEnUso()) {
                        Thread.yield();
                    }
                    buzonAlertas.usar();
                    buzonClasi.addEvento(eventoAlerta);
                    buzonClasi.dejarUsar();
                } else {
                    System.out.println("Evento ofensivo detectado con id: " + eventoAlerta.getId()[0] + "-" + eventoAlerta.getId()[1]);
                }
            }
        }

        while (buzonClasi.getEnUso()) {
            Thread.yield();
        }
        buzonAlertas.usar();
        for (int i = 0; i < nc; i++) {
            Evento EventoFin = new Evento(new int[]{0, 0}, true);
            buzonClasi.addEvento(EventoFin);
        }
        buzonClasi.dejarUsar();

    }
}
