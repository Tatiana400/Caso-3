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
        for (int i = 0; i < totalEventos; i++) {
            try {
                Evento evento = entrada.recibir();
                int r = (int)(Math.random()*201); // NÚMERO ENTRE 0 Y 200

                if (r % 8 == 0) { // ANÓMALO: MÚLTIPLO DE 8
                    alertas.enviar(evento);
                } else { // NORMAL
                    clasificacion.enviar(evento);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        // PARA MANEJAR EL FIN DE LOS EVENTOS
        try {
            alertas.enviar(Evento.crearFin()); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}