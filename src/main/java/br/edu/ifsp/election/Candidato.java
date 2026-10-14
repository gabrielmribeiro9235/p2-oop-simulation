package br.edu.ifsp.election;

import java.util.Objects;

public class Candidato {
    private final String nomeUrna;
    private final String nomeCompleto;
    private final int numero;
    private long votos;
    private final Cargo cargo;
    private final Partido partido;

    public Candidato(String nomeUrna, String nomeCompleto, int numero, Cargo cargo, Partido partido) {
        this.nomeUrna = nomeUrna;
        this.nomeCompleto = nomeCompleto;
        this.numero = numero;
        this.cargo = cargo;
        this.partido = partido;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public int getNumero() {
        return numero;
    }

    public long getVotos() {
        return votos;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Partido getPartido() {
        return partido;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return numero == candidato.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "nomeUrna='" + nomeUrna + '\'' +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", numero=" + numero +
                ", votos=" + votos +
                ", partido=" + partido +
                '}';
    }

    public void receberVoto() {
        votos++;
    }
}
