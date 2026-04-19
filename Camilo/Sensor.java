package Camilo;

public class Sensor extends Thread {
    private int id;
    static int eventosBase;
    private Buzon buzon;

    public Sensor (int id, Buzon buzon) {
        this.id = id;
        this.buzon = buzon;
    }

    @Override
    public void run() {
        for (int i=0; i < (Sensor.eventosBase*id); i++) {
            int[] identificadorEvento = {id, i+1};
            Evento evento = new Evento(identificadorEvento, false);
            while (buzon.getEnUso()) {
                Thread.yield();
            }
            buzon.usar();
            buzon.addEvento(evento);
            buzon.dejarUsar();
        }
    }
}
