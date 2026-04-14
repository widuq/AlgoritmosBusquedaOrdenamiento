import time
import sys

# Aumentamos el límite de recursión por seguridad
sys.setrecursionlimit(2000000)

def ternarySearch(arr, l, r, x):
    if (r >= l):
        # Calculamos los dos puntos medios que dividen el arreglo en 3
        mid1 = l + (r - l) // 3
        mid2 = r - (r - l) // 3

        # Caso 1: El elemento está en uno de los puntos medios
        if arr[mid1] == x:
            return mid1
        if arr[mid2] == x:
            return mid2

        # Caso 2: El elemento está en el primer tercio
        if x < arr[mid1]:
            return ternarySearch(arr, l, mid1 - 1, x)
        
        # Caso 3: El elemento está en el tercer tercio
        elif x > arr[mid2]:
            return ternarySearch(arr, mid2 + 1, r, x)
        
        # Caso 4: El elemento está en el tercio central
        else:
            return ternarySearch(arr, mid1 + 1, mid2 - 1, x)
            
    return -1

def cargar_y_ordenar(ruta_archivo):
    try:
        with open(ruta_archivo, 'r') as f:
            datos = f.read().split()
            arr = [int(num) for num in datos]
            arr.sort() # Requisito para búsqueda ternaria
            return arr
    except FileNotFoundError:
        print(f"No se encontró el archivo: {ruta_archivo}")
        return None

def main():
    tamanos = [10000, 100000, 1000000]
    ruta_base = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto2\\"
    archivo_csv = ruta_base + "resultados_algoritmos_busqueda.csv"

    # Modo 'a' para agregar datos al CSV existente
    with open(archivo_csv, 'a') as f_csv:
        for n in tamanos:
            nombre_archivo = f"{ruta_base}datos_{n}.txt"
            arr = cargar_y_ordenar(nombre_archivo)

            if arr is not None:
                # Buscamos el último elemento (peor caso)
                x = arr[-1]

                # Medición en nanosegundos
                start_time = time.time_ns()
                result = ternarySearch(arr, 0, len(arr) - 1, x)
                end_time = time.time_ns()

                duration_ns = end_time - start_time
                
                # Guardar en CSV
                f_csv.write(f"Python,BusquedaTernaria,{n},{duration_ns}\n")
                
                # Imprimir resultados
                if result != -1:
                    print(f"Python Busqueda Ternaria n={n}: {duration_ns} ns | Valor: {arr[result]} (Index: {result})")
                else:
                    print(f"Python Busqueda Ternaria n={n}: No encontrado")

if __name__ == "__main__":
    main()