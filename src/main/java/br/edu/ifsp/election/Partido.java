package br.edu.ifsp.election;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Partido {
    private final int numero;
    private final String nome;
    private final Map<Integer, Candidato> candidatosSenador;
    private final Map<Integer, Candidato> candidatosDeputadoFederal;
    private final Map<Integer, Candidato> candidatosDeputadoEstadual;
    private final Map<Integer, Candidato> governador;
    private final Map<Integer, Candidato> presidente;

    public Partido(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
        this.candidatosSenador = new HashMap<>();
        this.candidatosDeputadoFederal = new HashMap<>();
        this.candidatosDeputadoEstadual = new HashMap<>();
        this.governador = new HashMap<>();
        this.presidente = new HashMap<>();
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
            case GOVERNADOR -> governador.put(candidato.getNumero(), candidato);
            case DEPUTADO_ESTADUAL -> candidatosDeputadoEstadual.put(candidato.getNumero(), candidato);
            case DEPUTADO_FEDERAL -> candidatosDeputadoFederal.put(candidato.getNumero(), candidato);
            case PRESIDENTE -> presidente.put(candidato.getNumero(), candidato);
        }
    }

    public Candidato getCandidato(Cargo cargo, int numCandidato) {
        return switch (cargo) {
            case GOVERNADOR -> governador.get(numCandidato);
            case SENADOR -> candidatosSenador.get(numCandidato);
            case PRESIDENTE -> presidente.get(numCandidato);
            case DEPUTADO_FEDERAL -> candidatosDeputadoFederal.get(numCandidato);
            case DEPUTADO_ESTADUAL -> candidatosDeputadoEstadual.get(numCandidato);
        };
    }

    public Stream<Candidato> senado() {
        return candidatosSenador.values().stream();
    }

    public Stream<Candidato> governo() {
        return governador.values().stream();
    }

    public Stream<Candidato> presidencia() {
        return presidente.values().stream();
    }

    public Stream<Candidato> deputadosEstaduais() {
        return candidatosDeputadoEstadual.values().stream();
    }

    public Stream<Candidato> deputadosFederais() {
        return candidatosDeputadoFederal.values().stream();
    }
}
