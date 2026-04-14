import java.io.*;
import java.util.*;

class MergeSortJava {

    static void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) { arr[k] = L[i]; i++; }
            else { arr[k] = R[j]; j++; }
            k++;
        }
        while (i < n1) { arr[k] = L[i]; i++; k++; }
        while (j < n2) { arr[k] = R[j]; j++; k++; }
    }

    static void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public static void main(String args[]) {
        int[] tamaños = {10000, 100000, 1000000};
        
        // Ruta de los archivos
        String rutaBase = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\";
        
        try (PrintWriter writer = new PrintWriter(new File(rutaBase+"resultados_merge_sort.csv"))) {
            writer.println("Lenguaje,Algoritmo,Tamano,Tiempo_ms"); // Encabezado CSV

            for (int n : tamaños) {
                
                String nombreArchivo = rutaBase + "datos_" + n + ".txt";
                int[] arr = cargarDatos(nombreArchivo, n);

                if (arr != null) {
                    // --- MEDICIÓN ÚNICAMENTE DEL ALGORITMO ---
                    long startTime = System.currentTimeMillis();
                    mergeSort(arr, 0, arr.length - 1);
                    long endTime = System.currentTimeMillis();
                    // -----------------------------------------

                    long duration = (endTime - startTime);
                    
                    writer.println("Java," + "MergeSort," + n + "," + duration);
                    System.out.println("Procesado " + n + " en " + duration + " ms");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al crear el CSV: " + e.getMessage());
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