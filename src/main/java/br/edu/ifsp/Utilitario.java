package br.edu.ifsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public final class Utilitario {

    private Utilitario() {}

    public static List<String> carregarCandidatos(){
        Path path = Paths.get("src/main/resources/candidatos.csv");
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines().map(line -> line.replace("\"", "")).collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo. Abortando a execução.");
            throw new RuntimeException(e);
        }
    }

    public static String textoFixo(String texto, int tamanhoFixo){
        final int tamanhoInicial = texto.length();
        final int espacos = Math.max(0, tamanhoFixo - tamanhoInicial);
        return texto + " ".repeat(espacos);
    }
}
