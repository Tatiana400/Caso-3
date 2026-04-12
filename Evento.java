// PRESENTAR CADA EVENTO DEL SISTEMA

public class Evento {
    public int sensorId;
    public int tipo; // DEFINE A CUÁL SERVIDOR VA EL EVENTP
    public boolean fin; // PARA CONTROLAR CUANDO SE TERMINA
    public int secuencial; // NÚMERO DEL EVENTO

    public Evento(int sensorId, int tipo, int secuencial) {
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.fin = false;
        this.secuencial = secuencial;
    }

    public static Evento crearFin() {
        Evento evento = new Evento(0,0,0);
        evento.fin = true;
        return evento;
    }
}