// ENVIAR EVENTOS AL SERVIDOR CORRECTO
public class Clasificador extends Thread {
    private Buzon clasificacion;
    private Buzon[] servidores;

    public static int activos;

    public Clasificador(Buzon clasificacion, Buzon[] servidores) {
        this.clasificacion = clasificacion;
        this.servidores = servidores;
    }

    public void run() {
        // COMPLETAR
    }

    // TAL VEZ V UN METODO SYNCHRONIZED TAMBIÉN, NOSE?
}