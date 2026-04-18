// PRESENTAR CADA EVENTO DEL SISTEMA
public class Evento {
    private int sensorId;
    private int tipo; // DEFINE A CUÁL SERVIDOR VA EL EVENTP
    private boolean fin; // PARA CONTROLAR CUANDO SE TERMINA
    private int secuencial; // NÚMERO DEL EVENTO

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

    public int getSensorId() {
        return sensorId;
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isFin() {
        return fin;
    }

    public int getSecuencial() {
        return secuencial;
    }
}