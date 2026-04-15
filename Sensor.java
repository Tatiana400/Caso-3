// PRODUCE LOS EVENTOS
public class Sensor extends Thread {
    private int id;
    private int baseEventos;
    private int numSensores; // ns
    private Buzon buzonEntrada;

    public Sensor(int id, int baseEventos, int numSensores, Buzon buzonEntrada) {
        this.id = id;
        this.baseEventos = baseEventos;
        this.numSensores = numSensores;
        this.buzonEntrada = buzonEntrada;
    }

    public void generarEventos() {
        int totalEventos = baseEventos * id; // CADA SENSOR GENERA UN MÚLTIPLO DE BASEEVENTOS
        for (int i = 1; i <= totalEventos; i++) {
            int tipo = (int)(Math.random()*numSensores) + 1; // TIPO DE EVENTO ENTRE 1 Y NUMSENSORES
            Evento evento = new Evento(id, tipo, i);
            
            try {
                buzonEntrada.enviar(evento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        generarEventos();
    }
}
