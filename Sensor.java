// PRODUCE LOS EVENTOS
public class Sensor extends Thread {
    private int id;
    private int baseEventos;
    private int numSensores;
    private Buzon buzonEntrada;

    public Sensor(int id, int baseEventos, int numSensores, Buzon buzonEntrada) {
        this.id = id;
        this.baseEventos = baseEventos;
        this.numSensores = numSensores;
        this.buzonEntrada = buzonEntrada;
    }

    public void run() {
        // COMPLETAR
    }
}
