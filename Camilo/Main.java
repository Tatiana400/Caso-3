package Camilo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ingrese la ruta del archivo de eventos: ");
        String path = br.readLine();
        File file = new File(path);
        Scanner sc = new Scanner(file);
        String datos = sc.nextLine();
        sc.close();

        // Leer datos del archivo
        String[] datosChar = datos.split(" ");
        int ni = Integer.parseInt(datosChar[0]);
        int eventosBase = Integer.parseInt(datosChar[1]);
        int nc = Integer.parseInt(datosChar[2]);
        int ns = Integer.parseInt(datosChar[3]);
        int tam1 = Integer.parseInt(datosChar[4]);
        int tam2 = Integer.parseInt(datosChar[5]);

        // Configurar clases
        Evento.ns = ns;
        Sensor.eventosBase = eventosBase;

        // Crear buzones
        Buzon buzonEntrada = new Buzon(Integer.MAX_VALUE);
        Buzon buzonAlertas = new Buzon(Integer.MAX_VALUE);
        Buzon buzonClasificacion = new Buzon(tam1);
        Buzon[] buzonesConsolidacion = new Buzon[ns];
        for (int i = 0; i < ns; i++) {
            Buzon buzonConsolidacion = new Buzon(tam2);
            buzonesConsolidacion[i] = buzonConsolidacion;
        }


        // Crear actores
        Sensor[] sensores = new Sensor[ni];
        for (int i = 0; i < ni; i++) {
            sensores[i] = new Sensor(i+1, buzonEntrada);
        }

        Analizador analizador = new Analizador(buzonEntrada, buzonClasificacion, buzonAlertas);
        analizador.numEventos(eventosBase, ni);

        Administrador administrador = new Administrador(buzonAlertas, buzonClasificacion, nc);

        Clasificador[] clasificadores = new Clasificador[nc];
        for (int i = 0; i < nc; i++) {
            clasificadores[i] = new Clasificador(buzonClasificacion);
        }
        clasificadores[0].setNc(nc); // Para cyclic barrier
        clasificadores[0].setbuzonesConsolidacion(buzonesConsolidacion); // Para los mensajes finales

        Servidor[] servidores = new Servidor[ns];
        for (int i = 0; i < ns; i++) {
            servidores[i] = new Servidor(buzonesConsolidacion[i]);
        }

        //Ejecutar hilos
        for (Sensor s : sensores) s.start();
        analizador.start();
        administrador.start();
        for (Clasificador c : clasificadores) c.start();
        for (Servidor serv : servidores) serv.start();

        // Join para que no se termine el main antes de que terminen los hilos
        for (Sensor s : sensores) s.join();
        analizador.join();
        administrador.join();
        for (Clasificador c : clasificadores) c.join();
        for (Servidor serv : servidores) serv.join();

        System.out.println("Sistema terminado correctamente.");
    }
}
