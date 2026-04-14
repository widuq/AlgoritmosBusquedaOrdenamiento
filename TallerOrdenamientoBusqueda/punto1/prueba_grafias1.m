% Leer el CSV único
tabla = readtable('resultados_merge_sort.csv');

% Definir la ruta de salida (Asegúrate de que la carpeta exista)
outputDir = 'D:\01Uniquindio\20261\analisisAlgoritmos\TallerOrdenamientoBusqueda\punto1\imagenes_resultados';

% Si la carpeta no existe, MATLAB la crea por ti
if ~exist(outputDir, 'dir')
    mkdir(outputDir);
end

% Definir los algoritmos y sus complejidades
algoritmos = {'MergeSort', 'ShakerSort', 'DualPivotQuickSort', 'HeapSort', 'RadixSort'};
complejidades = {'O(n \log n)', 'O(n^2)', 'O(n \log n)', 'O(n \log n)', 'O(nk)'};

for k = 1:length(algoritmos)
    nombre_alg = algoritmos{k};
    complejidad_teorica = complejidades{k};
    
    % Filtrado de datos
    datos_actual = tabla(strcmp(tabla.Algoritmo, nombre_alg), :);
    java_data = datos_actual(strcmp(datos_actual.Lenguaje, 'Java'), :);
    python_data = datos_actual(strcmp(datos_actual.Lenguaje, 'Python'), :);
    data_plot = [java_data.Tiempo_ms, python_data.Tiempo_ms];
    
    % Crear figura (Invisible para mayor rapidez)
    fig = figure('Color', 'w', 'Visible', 'off'); 
    b = bar(data_plot);
    
    % --- CONFIGURACIÓN DE ESCALA Y EJES ---
    set(gca, 'YScale', 'log'); 
    set(gca, 'XTickLabel', {'10K', '100K', '1M'});
    
    % Etiqueta del eje X (Identificación de n)
    xlabel('Cantidad de Elementos ($n$)', 'Interpreter', 'latex', 'FontSize', 12);
    ylabel('Tiempo (ms) - Escala Logarítmica', 'FontSize', 11);
    
    grid on;
    
    % --- TÍTULO ---
    titulo_display = strrep(nombre_alg, 'DualPivotQuickSort', 'Dual-Pivot Quick Sort');
    title_str = sprintf('\\textbf{%s - } $\\mathbf{%s}$', titulo_display, complejidad_teorica);
    title(title_str, 'Interpreter', 'latex', 'FontSize', 13);
    
    % Ajuste de límites para evitar solapamiento
    ylim([1, max(data_plot(:)) * 25]); 
    legend({'Java', 'Python'}, 'Location', 'northwest');
    
    % --- ETIQUETAS DE VALOR ---
    for i = 1:size(data_plot, 2)
        xtips = b(i).XEndPoints;
        ytips = b(i).YEndPoints;
        for j = 1:length(ytips)
            % Multiplicación por 1.15 para posicionar texto en escala log
            text(xtips(j), ytips(j) * 1.15, string(ytips(j)), ...
                'HorizontalAlignment', 'center', ...
                'VerticalAlignment', 'bottom', ...
                'FontWeight', 'bold', 'FontSize', 9);
        end
    end
    
    % --- EXPORTACIÓN A RUTA ESPECÍFICA ---
    nombre_archivo = ['resultados_', lower(nombre_alg), '.png'];
    ruta_completa = fullfile(outputDir, nombre_archivo);
    
    exportgraphics(gca, ruta_completa, 'Resolution', 300);
    close(fig);
    
    fprintf('Gráfico exportado en: %s\n', ruta_completa);
end

disp('--- Proceso terminado: Todas las imágenes guardadas en la carpeta de la Uniquindio ---');