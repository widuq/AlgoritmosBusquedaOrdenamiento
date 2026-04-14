import java.io.*;
import java.util.*;

class RadixSortJava {

    // Función para obtener el valor máximo (para saber cuántos dígitos procesar)
    static int getMax(int arr[], int n) {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // Counting Sort basado en el dígito representado por exp (1, 10, 100, etc.)
    static void countSort(int arr[], int n, int exp) {
        int output[] = new int[n];
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    static void radixSort(int arr[], int n) {
        int m = getMax(arr, n);
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }

    public static void main(String args[]) {
        int[] tamaños = {10000, 100000, 1000000};
        String rutaBase = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\";
        String archivoSalida = rutaBase + "resultados_merge_sort.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida, true))) {
            
            for (int n : tamaños) {
                String nombreArchivo = rutaBase + "datos_" + n + ".txt";
                int[] arr = cargarDatos(nombreArchivo, n);

                if (arr != null) {
                    // --- MEDICIÓN ---
                    long startTime = System.currentTimeMillis();
                    radixSort(arr, arr.length);
                    long endTime = System.currentTimeMillis();
                    // ----------------

                    long duration = (endTime - startTime);
                    
                    writer.println("Java," + "RadixSort," + n + "," + duration);
                    System.out.println("Procesado Java RadixSort con " + n + " elementos en " + duration + " ms");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al manejar el CSV: " + e.getMessage());
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