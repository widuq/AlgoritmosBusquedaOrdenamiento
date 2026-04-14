import time
import os
import sys

# Aumentar límite de recursión para 1M de elementos
sys.setrecursionlimit(2000000)

def merge(arr, left, mid, right):
    n1 = mid - left + 1
    n2 = right - mid

    # Crear arreglos temporales
    L = [0] * n1
    R = [0] * n2

    for i in range(n1):
        L[i] = arr[left + i]
    for j in range(n2):
        R[j] = arr[mid + 1 + j]
        
    i = j = 0
    k = left  

    while i < n1 and j < n2:
        if L[i] <= R[j]:
            arr[k] = L[i]
            i += 1
        else:
            arr[k] = R[j]
            j += 1
        k += 1

    while i < n1:
        arr[k] = L[i]
        i += 1
        k += 1

    while j < n2:
        arr[k] = R[j]
        j += 1
        k += 1

def mergeSort(arr, left, right):
    if left < right:
        mid = (left + right) // 2
        mergeSort(arr, left, mid)
        mergeSort(arr, mid + 1, right)
        merge(arr, left, mid, right)

def cargar_datos(ruta, n):
    try:
        with open(ruta, 'r') as f:
            # Leemos las líneas y las convertimos a enteros
            return [int(line.strip()) for line in f]
    except FileNotFoundError:
        print(f"No se encontró el archivo: {ruta}")
        return None

# --- CÓDIGO PRINCIPAL MODIFICADO ---
if __name__ == "__main__":
    tamanos = [10000, 100000, 1000000]
    ruta_base = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto1\\"
    nombre_csv = ruta_base + "resultados_merge_sort.csv"

    # 'a' para añadir al final del archivo existente
    with open(nombre_csv, 'a') as f:
        for n in tamanos:
            ruta_txt = f"{ruta_base}datos_{n}.txt"
            arr = cargar_datos(ruta_txt, n)

            if arr:
                start_time = time.perf_counter()
                mergeSort(arr, 0, len(arr) - 1)
                end_time = time.perf_counter()
                
                duracion_ms = (end_time - start_time) * 1000
                
                # Escribir: Lenguaje, Algoritmo, Tamaño, Tiempo
                f.write(f"Python,MergeSort,{n},{int(duracion_ms)}\n")
                print(f"Python - Procesado {n} en {int(duracion_ms)} ms")