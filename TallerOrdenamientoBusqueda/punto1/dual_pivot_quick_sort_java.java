import java.io.*;
import java.util.*;

class DualPivotQuickSortJava {

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void dualPivotQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            int[] piv = partition(arr, low, high);
            dualPivotQuickSort(arr, low, piv[0] - 1);
            dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
            dualPivotQuickSort(arr, piv[1] + 1, high);
        }
    }

    static int[] partition(int[] arr, int low, int high) {
        if (arr[low] > arr[high])
            swap(arr, low, high);

        int j = low + 1;
        int g = high - 1, k = low + 1,
            p = arr[low], q = arr[high];

        while (k <= g) {
            if (arr[k] < p) {
                swap(arr, k, j);
                j++;
            } else if (arr[k] >= q) {
                while (arr[g] > q && k < g)
                    g--;
                swap(arr, k, g);
                g--;
                if (arr[k] < p) {
                    swap(arr, k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;
        swap(arr, low, j);
        swap(arr, high, g);
        return new int[] { j, g };
    }

    public static void main(String args[]) {
        int[] tamaños = {10000, 100000, 1000000};
        String rutaBase = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\";
        String archivoSalida = rutaBase + "resultados_merge_sort.csv";

        // 'true' para concatenar (append)
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida, true))) {
            
            for (int n : tamaños) {
                String nombreArchivo = rutaBase + "datos_" + n + ".txt";
                int[] arr = cargarDatos(nombreArchivo, n);

                if (arr != null) {
                    long startTime = System.currentTimeMillis();
                    dualPivotQuickSort(arr, 0, arr.length - 1);
                    long endTime = System.currentTimeMillis();

                    long duration = (endTime - startTime);
                    
                    writer.println("Java," + "DualPivotQuickSort," + n + "," + duration);
                    System.out.println("Procesado DualPivotQuickSort con " + n + " en " + duration + " ms");
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