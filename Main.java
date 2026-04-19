import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // LEER CONFIGURACIÓN dDEL ARCHIVO config.txt
        int ni, baseEventos, nc, ns, tam1, tam2;
        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            String[] datos = br.readLine().trim().split("\\s+");

            ni = Integer.parseInt(datos[0]); // NÚMERO DE SENSORES
            baseEventos = Integer.parseInt(datos[1]); // BASE DE EVENTOS
            nc = Integer.parseInt(datos[2]); // NÚMERO DE CLASIFICADORES
            ns = Integer.parseInt(datos[3]); // NÚMERO DE SERVIDORES
            tam1 = Integer.parseInt(datos[4]); // CAPACIDAD DEL BUZÓN DE CLASIFICACIÓN
            tam2 = Integer.parseInt(datos[5]); // CAPACIDAD DE LOS BUZONES PARA CONSOLIDACIÓN

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error leyendo config.txt: " + e.getMessage());
            return;
        }

        int totalEventos = baseEventos * (ni * (ni + 1) / 2);

        // CREAR BUZONES: -1 PARA ILIMITADO, OTRO PARA LIMITADO
        Buzon entrada = new Buzon(-1);         
        Buzon alertas = new Buzon(-1);         
        Buzon clasificacion = new Buzon(tam1); 

        Buzon[] buzonesServidores = new Buzon[ns];
        for (int i = 0; i < ns; i++) {
            buzonesServidores[i] = new Buzon(tam2);
        }

        //PARA PROBAR LA SALIDA
        System.out.println("Sensores: " + ni);
        System.out.println("Base eventos: " + baseEventos);
        System.out.println("Clasificadores: " + nc);
        System.out.println("Servidores: " + ns);
        System.out.println("Tam1: " + tam1);
        System.out.println("Tam2: " + tam2);
        System.out.println("Total eventos: " + totalEventos);

        List<Thread> hilos = new ArrayList<>();

        for (int i = 1; i <= ni; i++) {
            Sensor s = new Sensor(i, baseEventos, ns, entrada);
            hilos.add(s);
        }

        Broker broker = new Broker(entrada, alertas, clasificacion, totalEventos);
        hilos.add(broker);

        Administrador admin = new Administrador(alertas, clasificacion, nc);
        hilos.add(admin);

        Clasificador.iniciarBarrera(nc, buzonesServidores);
        for (int i = 0; i < nc; i++) {
            Clasificador c = new Clasificador(clasificacion, buzonesServidores);
            hilos.add(c);
        }

        for (int i = 0; i < ns; i++) {
            Servidor serv = new Servidor(buzonesServidores[i]);
            hilos.add(serv);
        }

        for (Thread t : hilos) {
            t.start();
        }

        // ESPERA A QUE TERMINEN TODOS LOS HILOS
        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Sistema terminado correctamente. Todos los buzones vacíos.");
    }
}