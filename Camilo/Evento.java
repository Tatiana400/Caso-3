package Camilo;

import java.util.Random;

public class Evento {
    static int ns = 1;
    private int[] id;
    private int seudo;
    private boolean fin;

    public Evento(int[] id, boolean fin) {
        this.id = id;
        this.fin = fin;
        Random r = new Random();
        this.seudo = r.nextInt(Evento.ns);
    }

    public int[] getId() {
        return id;
    }

    public int getSeudo() {
        return seudo;
    }
    
    public boolean isFin() {
        return fin;
    }

}
