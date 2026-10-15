package br.edu.ifsp.election;

import java.util.*;
import java.util.stream.Stream;

public class Partido {
    private final int numero;
    private final String nome;
    private final Map<Integer, Candidato> candidatosSenador;
    private final Map<Integer, Candidato> candidatosDeputadoFederal;
    private final Map<Integer, Candidato> candidatosDeputadoEstadual;
    private Candidato governador;
    private Candidato presidente;

    public Partido(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
        this.candidatosSenador = new HashMap<>();
        this.candidatosDeputadoFederal = new HashMap<>();
        this.candidatosDeputadoEstadual = new HashMap<>();
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return numero == partido.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    @Override
    public String toString() {
        return "Partido{" +
                "numero=" + numero +
                ", nome='" + nome + '\'' +
                '}';
    }

    public void addCandidato(Candidato candidato) {
        Objects.requireNonNull(candidato, "Candidato não pode ser nulo");

        switch (candidato.getCargo()) {
            case SENADOR -> candidatosSenador.put(candidato.getNumero(), candidato);
            case GOVERNADOR -> governador = candidato;
            case DEPUTADO_ESTADUAL -> candidatosDeputadoEstadual.put(candidato.getNumero(), candidato);
            case DEPUTADO_FEDERAL -> candidatosDeputadoFederal.put(candidato.getNumero(), candidato);
            case PRESIDENTE -> presidente = candidato;
        }
    }

    public Candidato getCandidato(Cargo cargo, int numCandidato) {
        return switch (cargo) {
            case GOVERNADOR -> governador != null && governador.getNumero() == numCandidato ? governador : null;
            case SENADOR -> candidatosSenador.get(numCandidato);
            case PRESIDENTE -> presidente != null && presidente.getNumero() == numCandidato ? presidente : null;
            case DEPUTADO_FEDERAL -> candidatosDeputadoFederal.get(numCandidato);
            case DEPUTADO_ESTADUAL -> candidatosDeputadoEstadual.get(numCandidato);
        };
    }

    public Stream<Candidato> senado() {
        return candidatosSenador.values().stream();
    }

    public Stream<Candidato> governo() {
        List<Candidato> list = new ArrayList<>();
        if (governador != null) list.add(governador);

        return list.stream();
    }

    public Stream<Candidato> presidencia() {
        List<Candidato> list = new ArrayList<>();
        if (presidente != null) list.add(presidente);

        return list.stream();
    }

    public Stream<Candidato> deputadosEstaduais() {
        return candidatosDeputadoEstadual.values().stream();
    }

    public Stream<Candidato> deputadosFederais() {
        return candidatosDeputadoFederal.values().stream();
    }
}
