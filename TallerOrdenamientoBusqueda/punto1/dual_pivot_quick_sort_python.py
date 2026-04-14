import time
import sys

# Aumentar el límite de recursión para archivos muy grandes si es necesario
sys.setrecursionlimit(2000000)

def dual_pivot_quick_sort(arr, low, high):
    if low < high:
        # lp = left pivot, rp = right pivot
        lp, rp = partition(arr, low, high)
        
        dual_pivot_quick_sort(arr, low, lp - 1)
        dual_pivot_quick_sort(arr, lp + 1, rp - 1)
        dual_pivot_quick_sort(arr, rp + 1, high)

def partition(arr, low, high):
    if arr[low] > arr[high]:
        arr[low], arr[high] = arr[high], arr[low]
        
    j = k = low + 1
    g, p, q = high - 1, arr[low], arr[high]
    
    while k <= g:
        if arr[k] < p:
            arr[k], arr[j] = arr[j], arr[k]
            j += 1
        elif arr[k] >= q:
            while arr[g] > q and k < g:
                g -= 1
            arr[k], arr[g] = arr[g], arr[k]
            g -= 1
            if arr[k] < p:
                arr[k], arr[j] = arr[j], arr[k]
                j += 1
        k += 1
        
    j -= 1
    g += 1
    
    arr[low], arr[j] = arr[j], arr[low]
    arr[high], arr[g] = arr[g], arr[high]
    
    return j, g

def cargar_datos(ruta_archivo):
    try:
        with open(ruta_archivo, 'r') as f:
            contenido = f.read().split()
            return [int(x) for x in contenido]
    except FileNotFoundError:
        print(f"No se encontró el archivo: {ruta_archivo}")
        return None

def main():
    tamanos = [10000, 100000, 1000000]
    ruta_base = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\"
    archivo_csv = ruta_base + "resultados_merge_sort.csv"

    # Abrir en modo 'a' (append)
    with open(archivo_csv, 'a') as f_csv:
        for n in tamanos:
            nombre_archivo = f"{ruta_base}datos_{n}.txt"
            arr = cargar_datos(nombre_archivo)

            if arr is not None:
                # --- MEDICIÓN DEL ALGORITMO ---
                start_time = time.time()
                dual_pivot_quick_sort(arr, 0, len(arr) - 1)
                end_time = time.time()
                # ------------------------------

                duration_ms = int((end_time - start_time) * 1000)
                
                linea = f"Python,DualPivotQuickSort,{n},{duration_ms}\n"
                f_csv.write(linea)
                
                print(f"Procesado Python DualPivotQuickSort con {n} elementos en {duration_ms} ms")

if __name__ == "__main__":
    main()