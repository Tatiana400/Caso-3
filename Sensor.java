// PRODUCE LOS EVENTOS
public class Sensor extends Thread {
    private int id;
    private int baseEventos;
    private int numServidores; // ns
    private Buzon buzonEntrada;

    public Sensor(int id, int baseEventos, int numServidores, Buzon buzonEntrada) {
        this.id = id;
        this.baseEventos = baseEventos;
        this.numServidores = numServidores;
        this.buzonEntrada = buzonEntrada;
    }

    @Override
    public void run() {
        int totalEventos = baseEventos * id; // CADA SENSOR GENERA UN MÚLTIPLO DE BASEEVENTOS
        for (int i = 1; i <= totalEventos; i++) {
            int tipo = (int)(Math.random()*numServidores) + 1; // TIPO DE EVENTO ENTRE 1 Y NUMSERVIDORES
            Evento evento = new Evento(id, tipo, i);
            
            try {
                buzonEntrada.enviar(evento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Sensor " + id + " terminado");
    }
}
