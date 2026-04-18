public class Servidor extends Thread {
    private Buzon buzon;

    public Servidor(Buzon buzon) {
        this.buzon = buzon;
    }

    @Override
    public void run() {
        try {
            boolean terminado = false;
            while (!terminado) {
                Evento evento = buzon.recibir(); // ESPERA PASIVA HASTA RECIBIR UN EVENTO
                if (evento.isFin()) {
                    terminado = true;
                } else {
                    int tiempo = (int)(Math.random()*901) + 100; // ENTRE 100 Y 1000 MS
                    Thread.sleep(tiempo); // SIMULA EL PROCESAMIENTO DEL EVENTO
                    Thread.yield(); // ESPERA SEMI-ACTIVA
                }
            }
            System.out.println("Servidor terminado");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}