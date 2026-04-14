import time
import os

def shaker_sort(a):
    n = len(a)
    swapped = True
    start = 0
    end = n - 1
    while (swapped == True):
        swapped = False

        # Fase de izquierda a derecha
        for i in range(start, end):
            if (a[i] > a[i + 1]):
                a[i], a[i + 1] = a[i + 1], a[i]
                swapped = True

        if (swapped == False):
            break

        swapped = False
        end = end - 1

        # Fase de derecha a izquierda
        for i in range(end - 1, start - 1, -1):
            if (a[i] > a[i + 1]):
                a[i], a[i + 1] = a[i + 1], a[i]
                swapped = True

        start = start + 1

def cargar_datos(ruta_archivo):
    try:
        with open(ruta_archivo, 'r') as f:
            # Lee los números separados por espacios o saltos de línea
            contenido = f.read().split()
            return [int(x) for x in contenido]
    except FileNotFoundError:
        print(f"No se encontró el archivo: {ruta_archivo}")
        return None

def main():
    tamanos = [10000, 100000, 1000000]
    
    # Ruta de los archivos (usando la misma de tu ejemplo)
    ruta_base = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\"
    archivo_csv = ruta_base + "resultados_merge_sort.csv"

    # Abrir el CSV en modo 'a' (append) para concatenar
    with open(archivo_csv, 'a') as f_csv:
        # Nota: No escribimos encabezado porque ya lo hizo el programa de Java
        
        for n in tamanos:
            nombre_archivo = f"{ruta_base}datos_{n}.txt"
            arr = cargar_datos(nombre_archivo)

            if arr is not None:
                # --- MEDICIÓN ÚNICAMENTE DEL ALGORITMO ---
                start_time = time.time()
                shaker_sort(arr)
                end_time = time.time()
                # -----------------------------------------

                # Convertimos la diferencia a milisegundos (ms)
                duration_ms = int((end_time - start_time) * 1000)
                
                # Formato: Lenguaje,Algoritmo,Tamano,Tiempo_ms
                linea = f"Python,ShakerSort,{n},{duration_ms}\n"
                f_csv.write(linea)
                
                print(f"Procesado Python Shaker Sort con {n} elementos en {duration_ms} ms")

if __name__ == "__main__":
    main()