package punto2;

import java.io.*;
import java.util.*;

class BusquedaTernariaJava {

    // Función de búsqueda ternaria para encontrar un valor x
    static int ternarySearch(int arr[], int low, int high, int x) {
        if (high >= low) {
            // Dividimos el rango en tres partes
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            // Si x está en alguno de los medios
            if (arr[mid1] == x) return mid1;
            if (arr[mid2] == x) return mid2;

            // Decidimos en qué tercio buscar
            if (x < arr[mid1]) {
                // x está en el primer tercio
                return ternarySearch(arr, low, mid1 - 1, x);
            } else if (x > arr[mid2]) {
                // x está en el tercer tercio
                return ternarySearch(arr, mid2 + 1, high, x);
            } else {
                // x está en el segundo tercio (el del medio)
                return ternarySearch(arr, mid1 + 1, mid2 - 1, x);
            }
        }
        return -1;
    }

    public static void main(String args[]) {
        int[] tamaños = {10000, 100000, 1000000};
        String rutaBase = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto2\\";
        String archivoSalida = rutaBase + "resultados_algoritmos_busqueda.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida, true))) {
            
            for (int n : tamaños) {
                String nombreArchivo = rutaBase + "datos_" + n + ".txt";
                int[] arr = cargarYOrdenarDatos(nombreArchivo, n);

                if (arr != null) {
                    // Buscamos el último elemento (peor caso)
                    int x = arr[n - 1]; 

                    long startTime = System.nanoTime();
                    int result = ternarySearch(arr, 0, n - 1, x);
                    long endTime = System.nanoTime();

                    long duration = (endTime - startTime);
                    
                    writer.println("Java,BusquedaTernaria," + n + "," + duration);
                    
                    if (result != -1) {
                        System.out.println("Busqueda Ternaria (Java) n=" + n + ": " + duration + " ns | Valor: " + arr[result] + " (Index: " + result + ")");
                    }
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
            System.out.println("No se encontró el archivo: " + archivo);
            return null;
        }
    }
}
