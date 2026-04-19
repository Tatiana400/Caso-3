package Camilo;

import java.util.Random;

public class Analizador extends Thread {
    private Buzon buzonEntrada;
    private Buzon buzonClasi;
    private Buzon buzonAlertas;
    private int numEventos;

    public Analizador(Buzon buzonEntrada, Buzon buzonClasi, Buzon buzonAlertas) {
        this.buzonEntrada = buzonEntrada;
        this.buzonClasi = buzonClasi;
        this.buzonAlertas = buzonAlertas;
    }

    public void numEventos (int eventosBase, int ni) {
        int numero = 0;
        for (int i = 1; i <= ni; i++) {
            numero += eventosBase * i;
        }
        this.numEventos = numero;
    }
    
    @Override
    public void run() {
        int procesados = 0;
        Random r = new Random();

        while (procesados < this.numEventos) {
            Evento evento = buzonEntrada.getEvento();
            procesados++;

            
            int anomalo = r.nextInt(201);
            if (anomalo%8 == 0) {
                System.out.println("Evento anómalo detectado con id: " + evento.getId()[0] + "-" + evento.getId()[1]);
                while (buzonAlertas.getEnUso()) {
                    Thread.yield();
                }
                buzonAlertas.usar();
                buzonAlertas.addEvento(evento);
                buzonAlertas.dejarUsar();
            } else {
                System.out.println("Evento normal con id: " + evento.getId()[0] + "-" + evento.getId()[1]);
                while (buzonClasi.getEnUso()) {
                    Thread.yield();
                }
                buzonAlertas.usar();
                buzonClasi.addEvento(evento);
                buzonClasi.dejarUsar();
            }
        }
        
        Evento eventoFin = new Evento(new int[]{0, 0}, true);
        System.out.println("Analizador ha procesado todos los eventos y ha enviado el evento de fin al administrador.");
        buzonAlertas.addEvento(eventoFin);
    }
}