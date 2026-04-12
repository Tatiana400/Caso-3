// FILTRAR EVENTOS: DEFINE SI ES NORMAL O ANÓMALO
public class Broker extends Thread {
    private Buzon entrada;
    private Buzon alertas;
    private Buzon clasificacion;
    private int totalEventos;

    public Broker(Buzon entrada, Buzon alertas, Buzon clasificacion, int totalEventos) {
        this.entrada = entrada;
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.totalEventos = totalEventos;
    }

    public void run() {
        // COMPLETAR
    }

}