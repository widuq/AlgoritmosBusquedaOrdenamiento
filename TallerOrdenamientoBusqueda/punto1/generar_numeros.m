%Porgrama para generar numero aleatorios de 8 digitos
% Definición de las cantidades de elementos
cantidades = [10000, 100000, 1000000];

% Rango para números de 8 dígitos
limiteInferior = 10000000;
limiteSuperior = 99999999;

for n = cantidades
    % Generar vector de números aleatorios
    datos = randi([limiteInferior, limiteSuperior], n, 1);
    
    % Crear el nombre del archivo dinámicamente
    nombreArchivo = sprintf('datos_%d.txt', n);
    
    % Abrir archivo para escritura
    fileID = fopen(nombreArchivo, 'w');
    
    % Escribir los datos (un número por línea)
    fprintf(fileID, '%d\n', datos);
    
    % Cerrar el archivo
    fclose(fileID);
    
    fprintf('Archivo "%s" generado con éxito.\n', nombreArchivo);
end