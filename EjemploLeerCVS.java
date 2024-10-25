import java.io.*;
import java.util.*;

public class EjemploLeerCVS {
    public static void main(String[] args) {
        HashMap<String, Integer> wordCounts = leer();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una palabra para saber cuántas veces aparece en el texto: ");
        String palabra = scanner.nextLine().toLowerCase();
        if (wordCounts.containsKey(palabra)) {
            System.out.println("La palabra '" + palabra + "' aparece " + wordCounts.get(palabra) + " veces.");
        } else {
            System.out.println("La palabra '" + palabra + "' no aparece en el texto.");
        }
        scanner.close();
    }

    public static HashMap<String, Integer> leer() {
        HashMap<String, Integer> conteoPalabras = new HashMap<>();
        try {
            BufferedReader lector = new BufferedReader(new FileReader("custo.csv"));
            String linea;
            boolean isFirstLine = true;
            while ((linea = lector.readLine()) != null) {
                // Omitir la primera línea (cabecera)
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Reemplazar comillas y dividir por comas
                linea = linea.replace("\"", "");
                String[] fields = linea.split(",");

                for (String field : fields) {
                    // Dividir el campo en palabras utilizando caracteres no alfabéticos como separadores
                    String[] palabras = field.split("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+");
                    for (String palabra : palabras) {
                        palabra = palabra.toLowerCase();
                        if (palabra.length() > 0) {
                            conteoPalabras.put(palabra, conteoPalabras.getOrDefault(palabra, 0) + 1);
                        }
                    }
                }
            }
            lector.close();

            BufferedWriter escritor = new BufferedWriter(new FileWriter("frecuencias.csv"));
            for (Map.Entry<String, Integer> entry : conteoPalabras.entrySet()) {
                escritor.write(entry.getKey() + "," + entry.getValue());
                escritor.newLine();
            }
            escritor.close();

        } catch (IOException e) {
            System.err.println("Error al leer o escribir el archivo");
        }
        return conteoPalabras;
    }
}
