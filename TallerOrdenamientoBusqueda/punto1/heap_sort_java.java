import java.io.*;
import java.util.*;

class HeapSortJava {

    // Para convertir un subárbol con raíz en el nodo i en un montón (heap)
    static void heapify(int[] arr, int n, int i) {
        int largest = i; // Inicializar el más grande como la raíz
        int l = 2 * i + 1; // izquierda
        int r = 2 * i + 2; // derecha

        // Si el hijo izquierdo es más grande que la raíz
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // Si el hijo derecho es más grande que el más grande hasta ahora
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // Si el más grande no es la raíz
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // Heapify recursivamente el subárbol afectado
            heapify(arr, n, largest);
        }
    }

    static void heapSort(int[] arr) {
        int n = arr.length;

        // Construir el montón (reorganizar el arreglo)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // Extraer elementos uno por uno del montón
        for (int i = n - 1; i > 0; i--) {
            // Mover la raíz actual al final
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Llamar a max heapify en el montón reducido
            heapify(arr, i, 0);
        }
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
                    heapSort(arr);
                    long endTime = System.currentTimeMillis();
                    // ----------------

                    long duration = (endTime - startTime);
                    
                    writer.println("Java," + "HeapSort," + n + "," + duration);
                    System.out.println("Procesado Java HeapSort con " + n + " elementos en " + duration + " ms");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el CSV: " + e.getMessage());
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