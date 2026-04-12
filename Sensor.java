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
        for (int i = 1; i <= baseEventos*id; i++) {
            int tipo = (int)(Math.random()*numSensores) + 1; // TIPO DE EVENTO ENTRE 1 Y NUMSENSORES
            Evento evento = new Evento(tipo, id, i);
            
            try {
                buzonEntrada.enviar(evento);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
