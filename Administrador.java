public class Administrador extends Thread {
    private Buzon alertas;
    private Buzon clasificacion;
    private int nc; // NÚMERO DE CLASIFICADORES

    public Administrador(Buzon alertas, Buzon clasificacion, int nc) {
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.nc = nc;
    }

    @Override
    public void run() {
        try {
            boolean terminado = false;
            while (!terminado) {
                Evento evento = alertas.recibir();

                if (evento.isFin()) {
                    terminado = true;
                } else {
                    int r = (int)(Math.random()*21); // NÚMERO ENTRE 0 Y 20
                    if (r % 4 == 0) { // NORMAL: MÚLTIPLO DE 4
                        while (clasificacion.estaLleno()) {
                            Thread.yield(); // ESPERA SEMI-ACTIVA SI EL BUZÓN DE CLASIFICACIÓN ESTÁ LLENO
                        }
                        clasificacion.enviar(evento);
                    }
                }
            }

            // ENVIAR FIN A LOS CLASIFICADORES
            for (int i = 0; i < nc; i++) {
                clasificacion.enviar(Evento.crearFin());
            }
            System.out.println("Administrador terminado");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
