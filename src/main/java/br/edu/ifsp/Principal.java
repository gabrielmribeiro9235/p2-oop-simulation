package br.edu.ifsp;

public class Principal {
    public static void main(String[] args) {
        Utilitario.carregarCandidatos().forEach(System.out::println);
    }
}
