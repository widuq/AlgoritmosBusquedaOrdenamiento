import time
import sys

# Aumentar el límite de recursión para evitar errores con 1,000,000 de elementos
sys.setrecursionlimit(2000000)

def heapify(arr, n, i):
    # Inicializar el más grande como la raíz
    largest = i
    l = 2 * i + 1
    r = 2 * i + 2

    # Si el hijo izquierdo es más grande que la raíz
    if l < n and arr[l] > arr[largest]:
        largest = l

    # Si el hijo derecho es más grande que el más grande hasta ahora
    if r < n and arr[r] > arr[largest]:
        largest = r

    # Si el más grande no es la raíz
    if largest != i:
        arr[i], arr[largest] = arr[largest], arr[i]
        # Heapify recursivamente el subárbol afectado
        heapify(arr, n, largest)

def heap_sort(arr):
    n = len(arr)

    # Construir el heap (reorganizar el arreglo)
    for i in range(n // 2 - 1, -1, -1):
        heapify(arr, n, i)

    # Extraer elementos uno por uno del heap
    for i in range(n - 1, 0, -1):
        # Mover la raíz actual al final
        arr[0], arr[i] = arr[i], arr[0]
        # Llamar a max heapify en el heap reducido
        heapify(arr, i, 0)

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
    
    # Ruta base según tu estructura
    ruta_base = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\"
    archivo_csv = ruta_base + "resultados_merge_sort.csv"

    # Abrir el CSV en modo 'a' (append)
    with open(archivo_csv, 'a') as f_csv:
        for n in tamanos:
            nombre_archivo = f"{ruta_base}datos_{n}.txt"
            arr = cargar_datos(nombre_archivo)

            if arr is not None:
                # --- MEDICIÓN DEL TIEMPO ---
                start_time = time.time()
                heap_sort(arr)
                end_time = time.time()
                # ---------------------------

                # Convertir a milisegundos
                duration_ms = int((end_time - start_time) * 1000)
                
                # Escribir resultado
                linea = f"Python,HeapSort,{n},{duration_ms}\n"
                f_csv.write(linea)
                
                print(f"Procesado Python HeapSort con {n} elementos en {duration_ms} ms")

if __name__ == "__main__":
    main()