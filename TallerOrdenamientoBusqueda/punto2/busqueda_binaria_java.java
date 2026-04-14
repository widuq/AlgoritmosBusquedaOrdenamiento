package punto2;

import java.io.*;
import java.util.*;

class BusquedaBinariaJava {

    // Función recursiva de búsqueda binaria
    static int binarySearch(int arr[], int low, int high, int x) {
        if (high >= low) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == x)
                return mid;

            if (arr[mid] > x)
                return binarySearch(arr, low, mid - 1, x);

            return binarySearch(arr, mid + 1, high, x);
        }
        return -1;
    }

    public static void main(String args[]) {
        int[] tamaños = {10000, 100000, 1000000};
        String rutaBase = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto2\\";
        String archivoSalida = rutaBase + "resultados_algoritmos_busqueda.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida, true))) {
            // Escribir cabecera si el archivo es nuevo
            File file = new File(archivoSalida);
            if (file.length() == 0) {
                writer.println("Lenguaje,Algoritmo,Tamano,Tiempo_ns");
            }

            for (int n : tamaños) {
                String nombreArchivo = rutaBase + "datos_" + n + ".txt";
                int[] arr = cargarYOrdenarDatos(nombreArchivo, n);

                if (arr != null) {
                    // Vamos a buscar el último elemento para el peor caso
                    int x = arr[n - 1]; 

                    // Usamos nanosegundos porque la búsqueda binaria es DEMASIADO rápida
                    long startTime = System.nanoTime();
                    int result = binarySearch(arr, 0, n - 1, x);
                    long endTime = System.nanoTime();

                    long duration = (endTime - startTime);
                    
                    writer.println("Java,BusquedaBinaria," + n + "," + duration);
                    System.out.println("Busqueda Binaria Java | n=" + n + 
                   ": " + duration + " ns | " +
                   "Valor encontrado: " + arr[result] + 
                   " (en el índice: " + result + ")");
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
            // ¡IMPORTANTE! Ordenar antes de la búsqueda binaria
            Arrays.sort(arr); 
            return arr;
        } catch (IOException e) {
            System.out.println("No se encontró el archivo: " + archivo);
            return null;
        }
    }
}