import math
import time

def jumpSearch(arr, x, n):
    # Tamaño del salto: raíz cuadrada de n
    step = math.sqrt(n)
    
    # Encontrar el bloque donde está el elemento
    prev = 0
    while arr[int(min(step, n)-1)] < x:
        prev = step
        step += math.sqrt(n)
        if prev >= n:
            return -1
    
    # Búsqueda lineal dentro del bloque
    while arr[int(prev)] < x:
        prev += 1
        
        # Si llegamos al siguiente bloque o al final, no está
        if prev == min(step, n):
            return -1
    
    # Si lo encontramos, retornamos el índice
    if arr[int(prev)] == x:
        return int(prev)
    
    return -1

def cargar_y_ordenar(ruta_archivo):
    try:
        with open(ruta_archivo, 'r') as f:
            datos = f.read().split()
            arr = [int(num) for num in datos]
            arr.sort()  # Vital para Jump Search
            return arr
    except FileNotFoundError:
        print(f"No se encontró el archivo: {ruta_archivo}")
        return None

def main():
    tamanos = [10000, 100000, 1000000]
    ruta_base = "D:\\01Uniquindio\\20261\\analisisAlgoritmos\\TallerOrdenamientoBusqueda\\punto2\\"
    archivo_csv = ruta_base + "resultados_algoritmos_busqueda.csv"

    with open(archivo_csv, 'a') as f_csv:
        for n in tamanos:
            nombre_archivo = f"{ruta_base}datos_{n}.txt"
            arr = cargar_y_ordenar(nombre_archivo)

            if arr is not None:
                # Buscamos el último elemento (peor caso para Jump Search)
                x = arr[-1]

                start_time = time.time_ns()
                result = jumpSearch(arr, x, len(arr))
                end_time = time.time_ns()

                duration_ns = end_time - start_time
                
                f_csv.write(f"Python,JumpSearch,{n},{duration_ns}\n")
                
                if result != -1:
                    print(f"Python Jump Search n={n}: {duration_ns} ns | Valor: {arr[result]} (Index: {result})")
                else:
                    print(f"Python Jump Search n={n}: No encontrado")

if __name__ == "__main__":
    main()