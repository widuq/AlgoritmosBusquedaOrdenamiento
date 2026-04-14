import time
import os

def counting_sort(arr, exp1):
    n = len(arr)
    output = [0] * n
    count = [0] * 10

    # Almacenar el conteo de ocurrencias
    for i in range(0, n):
        index = arr[i] // exp1
        count[index % 10] += 1

    # Cambiar count[i] para que contenga la posición real
    for i in range(1, 10):
        count[i] += count[i - 1]

    # Construir el arreglo de salida
    i = n - 1
    while i >= 0:
        index = arr[i] // exp1
        output[count[index % 10] - 1] = arr[i]
        count[index % 10] -= 1
        i -= 1

    # Copiar al arreglo original
    for i in range(0, len(arr)):
        arr[i] = output[i]

def radix_sort(arr):
    if not arr:
        return
    
    max1 = max(arr)
    exp = 1
    while max1 // exp > 0:
        counting_sort(arr, exp)
        exp *= 10

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

    # Modo 'a' para no borrar lo anterior
    with open(archivo_csv, 'a') as f_csv:
        for n in tamanos:
            nombre_archivo = f"{ruta_base}datos_{n}.txt"
            arr = cargar_datos(nombre_archivo)

            if arr is not None:
                # --- MEDICIÓN ---
                start_time = time.time()
                radix_sort(arr)
                end_time = time.time()
                # ----------------

                duration_ms = int((end_time - start_time) * 1000)
                
                linea = f"Python,RadixSort,{n},{duration_ms}\n"
                f_csv.write(linea)
                
                print(f"Procesado Python RadixSort con {n} elementos en {duration_ms} ms")

if __name__ == "__main__":
    main()