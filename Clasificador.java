// ENVIAR EVENTOS AL SERVIDOR CORRECTO
public class Clasificador extends Thread {
    private Buzon clasificacion;
    private Buzon[] servidores;

    public static int activos; //COMPARTIDO POR TODOS LOS CLASIFICADORES

    public Clasificador(Buzon clasificacion, Buzon[] servidores) {
        this.clasificacion = clasificacion;
        this.servidores = servidores;
    }

    public static void setActivos(int n) {
        activos = n;
    }

    private synchronized void terminar(Buzon[] servidores) throws InterruptedException {
        activos--;

        if (activos == 0) {
            for (int i = 0; i < servidores.length; i++) {
                servidores[i].enviar(Evento.crearFin()); // ENVIAR FIN A LOS SERVIDORES
            }
            System.out.println("Último clasificador envió FIN a servidores");
        }
    }

    @Override
    public void run() {
        try {
            boolean terminado = false;
            while (!terminado) {
                Evento evento = clasificacion.recibir();
                if (evento.isFin()) {
                    terminar(servidores);
                    terminado = true;
                } else {
                    int destino = evento.getTipo() - 1;
                    servidores[destino].enviar(evento); // ENVIAR AL SERVIDOR CORRECTO
                }
            }
            System.out.println("Clasificador terminado");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}