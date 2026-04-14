package punto2;

import java.io.*;
import java.util.*;

public class busqueda_saltos_java {

    public static int jumpSearch(int[] arr, int x) {
        int n = arr.length;
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;

        // Saltar bloques hasta encontrar uno que pueda contener x
        while (arr[Math.min(step, n) - 1] < x) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) return -1;
        }

        // Búsqueda lineal dentro del bloque identificado
        while (arr[prev] < x) {
            prev++;
            if (prev == Math.min(step, n)) return -1;
        }

        if (arr[prev] == x) return prev;
        return -1;
    }

    public static void main(String[] args) {
        int[] tamaños = {10000, 100000, 1000000};
        String rutaBase = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto2\\";
        String archivoSalida = rutaBase + "resultados_algoritmos_busqueda.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida, true))) {
            for (int n : tamaños) {
                String nombreArchivo = rutaBase + "datos_" + n + ".txt";
                int[] arr = cargarYOrdenarDatos(nombreArchivo, n);

                if (arr != null) {
                    int x = arr[n - 1]; // Peor caso (último elemento)

                    long startTime = System.nanoTime();
                    int result = jumpSearch(arr, x);
                    long endTime = System.nanoTime();

                    long duration = (endTime - startTime);
                    writer.println("Java,JumpSearch," + n + "," + duration);
                    
                    System.out.println("Jump Search (Java) n=" + n + ": " + duration + " ns | Valor: " + arr[result] + " (Index: " + result + ")");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int[] cargarYOrdenarDatos(String archivo, int n) {
        try {
            Scanner sc = new Scanner(new File(archivo));
            int[] arr = new int[n];
            int i = 0;
            while (sc.hasNextInt() && i < n) {
                arr[i++] = sc.nextInt();
            }
            sc.close();
            Arrays.sort(arr);
            return arr;
        } catch (IOException e) {
            return null;
        }
    }
}
