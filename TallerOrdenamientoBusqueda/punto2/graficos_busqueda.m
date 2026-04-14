% Leer el CSV de búsqueda
tabla = readtable('resultados_algoritmos_busqueda.csv');

% Definir la ruta de salida para el punto 2
outputDir = 'D:\01Uniquindio\20261\analisisAlgoritmos\TallerOrdenamientoBusqueda\punto2\imagenes_resultados';

if ~exist(outputDir, 'dir')
    mkdir(outputDir);
end

% Definir algoritmos de búsqueda y sus complejidades
algoritmos = {'BusquedaBinaria', 'BusquedaTernaria', 'JumpSearch'};
% Complejidades: Binaria log2(n), Ternaria log3(n), Jump sqrt(n)
complejidades = {'O(\log n)', 'O(\log n)', 'O(\sqrt{n})'};

for k = 1:length(algoritmos)
    nombre_alg = algoritmos{k};
    complejidad_teorica = complejidades{k};
    
    % Filtrado de datos
    datos_actual = tabla(strcmp(tabla.Algoritmo, nombre_alg), :);
    java_data = datos_actual(strcmp(datos_actual.Lenguaje, 'Java'), :);
    python_data = datos_actual(strcmp(datos_actual.Lenguaje, 'Python'), :);
    
    % Nota: Aquí usamos Tiempo_ns
    data_plot = [java_data.Tiempo_ns, python_data.Tiempo_ns];
    
    % Crear figura invisible
    fig = figure('Color', 'w', 'Visible', 'off'); 
    b = bar(data_plot);
    
    % --- CONFIGURACIÓN DE ESCALA Y EJES ---
    set(gca, 'YScale', 'log'); 
    set(gca, 'XTickLabel', {'10K', '100K', '1M'});
    
    xlabel('Cantidad de Elementos ($n$)', 'Interpreter', 'latex', 'FontSize', 12);
    ylabel('Tiempo (ns) - Escala Logarítmica', 'FontSize', 11);
    
    grid on;
    
    % --- TÍTULO ---
    % Formatear nombre para el título (ej. BusquedaBinaria -> Búsqueda Binaria)
    titulo_display = regexprep(nombre_alg, '([A-Z])', ' $1'); 
    title_str = sprintf('\\textbf{%s - } $\\mathbf{%s}$', titulo_display, complejidad_teorica);
    title(title_str, 'Interpreter', 'latex', 'FontSize', 13);
    
    % Ajuste de límites (Multiplicamos por 40 para dar espacio a los números en ns)
    ylim([100, max(data_plot(:)) * 40]); 
    legend({'Java', 'Python'}, 'Location', 'northwest');
    
    % --- ETIQUETAS DE VALOR ---
    for i = 1:size(data_plot, 2)
        xtips = b(i).XEndPoints;
        ytips = b(i).YEndPoints;
        for j = 1:length(ytips)
            text(xtips(j), ytips(j) * 1.25, string(ytips(j)), ...
                'HorizontalAlignment', 'center', ...
                'VerticalAlignment', 'bottom', ...
                'FontWeight', 'bold', 'FontSize', 8);
        end
    end
    
    % --- EXPORTACIÓN ---
    nombre_archivo = ['resultados_', lower(nombre_alg), '.png'];
    ruta_completa = fullfile(outputDir, nombre_archivo);
    
    exportgraphics(gca, ruta_completa, 'Resolution', 300);
    close(fig);
    
    fprintf('Gráfico de búsqueda exportado: %s\n', ruta_completa);
end

disp('--- Punto 2 completado exitosamente ---');