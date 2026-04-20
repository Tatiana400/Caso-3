// FILTRAR EVENTOS: DEFINE SI ES NORMAL O ANÓMALO
public class Broker extends Thread {
    private Buzon entrada; // LOS REVISA
    private Buzon alertas; // PARA LOS ANÓMALOS
    private Buzon clasificacion; // PARA LOS NORMALES
    private int totalEventos;

    public Broker(Buzon entrada, Buzon alertas, Buzon clasificacion, int totalEventos) {
        this.entrada = entrada;
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run() {
        for (int i = 0; i < totalEventos; i++) {
            try {
                Evento evento = entrada.recibir();
                int r = (int)(Math.random()*201); // NÚMERO ENTRE 0 Y 200

                if (r % 8 == 0) { // ANÓMALO: MÚLTIPLO DE 8
                    while (alertas.estaLleno()) {
                        Thread.yield(); // ESPERA SEMI-ACTIVA SI EL BUZÓN DE ALERTAS ESTÁ LLENO
                    }
                    alertas.enviar(evento);
                } else { // NORMAL
                    while (clasificacion.estaLleno()) {
                        Thread.yield(); // ESPERA SEMI-ACTIVA SI EL BUZÓN DE CLASIFICACIÓN ESTÁ LLENO
                    }
                    clasificacion.enviar(evento);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    
        // PARA MANEJAR EL FIN DE LOS EVENTOS
        try {
            alertas.enviar(Evento.crearFin()); 
            System.out.println("Broker terminado");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}