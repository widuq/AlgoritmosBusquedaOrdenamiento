import time
import sys

# Aumentamos el límite de recursión por si el árbol de búsqueda es profundo
sys.setrecursionlimit(2000000)

def binarySearch(arr, low, high, x):
    if high >= low:
        mid = low + (high - low) // 2

        if arr[mid] == x:
            return mid
        elif arr[mid] > x:
            return binarySearch(arr, low, mid - 1, x)
        else:
            return binarySearch(arr, mid + 1, high, x)
    else:
        return -1

def cargar_y_ordenar(ruta_archivo):
    try:
        with open(ruta_archivo, 'r') as f:
            # Leemos y convertimos a lista de enteros
            datos = f.read().split()
            arr = [int(num) for num in datos]
            # La búsqueda binaria requiere que el arreglo esté ordenado
            arr.sort() 
            return arr
    except FileNotFoundError:
        print(f"No se encontró el archivo: {ruta_archivo}")
        return None

def main():
    tamanos = [10000, 100000, 1000000]
    ruta_base = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto2\\"
    archivo_csv = ruta_base + "resultados_algoritmos_busqueda.csv"

    # Abrimos en modo 'a' (append) para añadir debajo de lo de Java
    with open(archivo_csv, 'a') as f_csv:
        for n in tamanos:
            nombre_archivo = f"{ruta_base}datos_{n}.txt"
            arr = cargar_y_ordenar(nombre_archivo)

            if arr is not None:
                # Buscamos el último elemento (peor caso)
                x = arr[-1]

                # Medición en nanosegundos
                start_time = time.time_ns()
                result = binarySearch(arr, 0, len(arr) - 1, x)
                end_time = time.time_ns()

                duration_ns = end_time - start_time
                
                # Escribir en CSV
                f_csv.write(f"Python,BusquedaBinaria,{n},{duration_ns}\n")
                
                # Imprimir el VALOR del número de 8 dígitos usando el índice result
                if result != -1:
                    print(f"Python Busqueda Binaria n={n}: {duration_ns} ns | Valor encontrado: {arr[result]} (Index: {result})")
                else:
                    print(f"Python Busqueda Binaria n={n}: No encontrado")

if __name__ == "__main__":
    main()