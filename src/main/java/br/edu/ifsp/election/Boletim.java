package br.edu.ifsp.election;

import br.edu.ifsp.Utilitario;

import java.util.List;

public final class Boletim {
    private Boletim() {}

    public static String emitir(String nomeCargo, List<Candidato> candidatos, int brancos, int nulos) {
        StringBuilder dadosCandidatos =
                new StringBuilder(String.format("%s%s%s\n",
                        Utilitario.textoFixo("Nome", 50),
                        Utilitario.textoFixo("Número", 10),
                        Utilitario.textoFixo("Votos", 10)
                ));

        candidatos.forEach(c ->
                dadosCandidatos.append(String.format("%s%s%s\n",
                        Utilitario.textoFixo(c.getNomeUrna(), 50),
                        Utilitario.textoFixo(String.valueOf(c.getNumero()), 10),
                        Utilitario.textoFixo(String.valueOf(c.getVotos()), 10))
                )
        );

        long votosNominais = candidatos.stream().mapToLong(Candidato::getVotos).sum();
        long totalApurado = votosNominais + brancos + nulos;

        return String.format("""
                ============== JUSTIÇA ELEITORAL ==============
                 Boletim de Urna
                ------------------------------%s------------------------------
                %s
                ----------------------------------------------------------------------------
                Total de votos Nominais: %d
                Brancos: %d
                Nulos: %d
                Total apurado: %d
                ---------------------------------------------------------------------------
                """, nomeCargo, dadosCandidatos, votosNominais, brancos, nulos, totalApurado);
    }
}
