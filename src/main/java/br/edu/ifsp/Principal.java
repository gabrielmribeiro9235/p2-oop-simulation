package br.edu.ifsp;

import br.edu.ifsp.election.Urna;
import br.edu.ifsp.election.UrnaImpl;

public class Principal {
    public static void main(String[] args) {
        final int ELEITORES = 2;

        Urna urna = new UrnaImpl();

        System.out.println("\n".repeat(10));

        for (int i = 0; i < ELEITORES; i++) {
            urna.votar();
            System.out.println("\n\n");
        }

        System.out.println("\n\n");
        urna.imprimir();
    }
}
