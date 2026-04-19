package Camilo;

import java.util.Random;

public class Servidor extends Thread {
    private Buzon buzon;

    public Servidor (Buzon buzon) {
        this.buzon = buzon;
    }

    @Override
    public void run() {
        while(true) {
            Evento evento = buzon.getEvento();
            if (evento.isFin()) {
                break;
            }
            Random r = new Random();
            try {
                Thread.sleep(r.nextInt(901) + 100);
                System.out.println("Servidor procesó evento con id: " + evento.getId()[0] + "-" + evento.getId()[1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
