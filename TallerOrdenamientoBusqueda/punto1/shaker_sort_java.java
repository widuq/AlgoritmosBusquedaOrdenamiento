import java.io.*;
import java.util.*;

class ShakerSortJava {

    // Implementación de Shaker Sort (Cocktail Sort)
    static void shakerSort(int a[]) {
        boolean swapped = true;
        int start = 0;
        int end = a.length;

        while (swapped) {
            // Reiniciar bandera al entrar
            swapped = false;

            // Fase 1: De izquierda a derecha (como Bubble Sort)
            for (int i = start; i < end - 1; ++i) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                    swapped = true;
                }
            }

            // Si no hubo cambios, el arreglo ya está ordenado
            if (!swapped) break;

            swapped = false;

            // Reducir el punto final porque el mayor ya está en su lugar
            end = end - 1;

            // Fase 2: De derecha a izquierda
            for (int i = end - 1; i >= start; i--) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                    swapped = true;
                }
            }

            // Aumentar el punto de inicio porque el menor ya está en su lugar
            start = start + 1;
        }
    }

    public static void main(String args[]) {
        // Tamaños definidos en tu taller
        int[] tamaños = {10000, 100000, 1000000};
        
        // Ruta de los archivos (ajustada a tu estructura)
        String rutaBase = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\";
        String archivoSalida = rutaBase + "resultados_merge_sort.csv";

        // Usamos FileWriter con el parámetro 'true' para hacer APPEND (concatenar)
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida, true))) {
            
            // Nota: No imprimimos el encabezado de nuevo para no ensuciar el CSV si ya existe
            
            for (int n : tamaños) {
                String nombreArchivo = rutaBase + "datos_" + n + ".txt";
                int[] arr = cargarDatos(nombreArchivo, n);

                if (arr != null) {
                    // --- MEDICIÓN ÚNICAMENTE DEL ALGORITMO ---
                    long startTime = System.currentTimeMillis();
                    shakerSort(arr);
                    long endTime = System.currentTimeMillis();
                    // -----------------------------------------

                    long duration = (endTime - startTime);
                    
                    // Guardamos con el nombre "ShakerSort"
                    writer.println("Java," + "ShakerSort," + n + "," + duration);
                    System.out.println("Procesado Shaker Sort con " + n + " elementos en " + duration + " ms");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el CSV: " + e.getMessage());
        }
    }

    private static int[] cargarDatos(String archivo, int n) {
        try {
            Scanner sc = new Scanner(new File(archivo));
            int[] arr = new int[n];
            int i = 0;
            while (sc.hasNextInt() && i < n) {
                arr[i++] = sc.nextInt();
            }
            sc.close();
            return arr;
        } catch (IOException e) {
            System.out.println("No se encontró el archivo: " + archivo);
            return null;
        }
    }
}