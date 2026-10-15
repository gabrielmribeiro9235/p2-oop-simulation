package br.edu.ifsp.election;

import br.edu.ifsp.Utilitario;

import java.util.*;

public class UrnaImpl implements Urna {
    private final int[] nulos;
    private final int[] brancos;
    private Map<Integer, Partido> partidos;

    public UrnaImpl() {
        nulos = new int[5];
        brancos = new int[5];
        carregar();
    }

    private void imprimirPartidos() {
        System.out.println("--------------------------PARTIDOS--------------------------");
        System.out.println(Utilitario.textoFixo("Número", 20) + "Nome");

        partidos.values()
                .forEach(p ->
                    System.out.println(Utilitario.textoFixo(String.valueOf(p.getNumero()), 20) + p.getNome())
                );
    }

    private void imprimirCandidatos() {
        System.out.println("---------------------- CANDIDATOS A DEPUTADO ESTADUAL ---------------------");
        System.out.println(
            Utilitario.textoFixo("Nome", 50) +
            Utilitario.textoFixo("Cargo", 25) +
            Utilitario.textoFixo("Número", 10) +
            "Partido"
        );

        partidos.values()
            .forEach(p ->
                p.deputadosEstaduais()
                    .forEach(de ->
                        System.out.println(
                                Utilitario.textoFixo(de.getNomeUrna(), 50) +
                                Utilitario.textoFixo(de.getCargo().toString(), 25) +
                                Utilitario.textoFixo(String.valueOf(de.getNumero()), 10) +
                                de.getPartido().getNome()
                        )
                    )
            );

        System.out.println("---------------------- CANDIDATOS A DEPUTADO FEDERAL ---------------------");
        System.out.println(
            Utilitario.textoFixo("Nome", 50) +
            Utilitario.textoFixo("Cargo", 25) +
            Utilitario.textoFixo("Número", 10) +
            "Partido"
        );

        partidos.values()
            .forEach(p ->
                p.deputadosFederais()
                    .forEach(df ->
                        System.out.println(
                                Utilitario.textoFixo(df.getNomeUrna(), 50) +
                                Utilitario.textoFixo(df.getCargo().toString(), 25) +
                                Utilitario.textoFixo(String.valueOf(df.getNumero()), 10) +
                                df.getPartido().getNome()
                        )
                    )
            );

        System.out.println("---------------------- CANDIDATOS A SENADOR ---------------------");
        System.out.println(
            Utilitario.textoFixo("Nome", 50) +
            Utilitario.textoFixo("Cargo", 25) +
            Utilitario.textoFixo("Número", 10) +
            "Partido"
        );

        partidos.values()
            .forEach(p ->
                p.senado()
                    .forEach(s ->
                        System.out.println(
                                Utilitario.textoFixo(s.getNomeUrna(), 50) +
                                Utilitario.textoFixo(s.getCargo().toString(), 25) +
                                Utilitario.textoFixo(String.valueOf(s.getNumero()), 10) +
                                s.getPartido().getNome()
                        )
                    )
            );

        System.out.println("---------------------- CANDIDATOS A GOVERNADOR ---------------------");
        System.out.println(
            Utilitario.textoFixo("Nome", 50) +
            Utilitario.textoFixo("Cargo", 25) +
            Utilitario.textoFixo("Número", 10) +
            "Partido"
        );

        partidos.values()
            .forEach(p ->
                p.governo()
                    .forEach(g ->
                        System.out.println(
                                Utilitario.textoFixo(g.getNomeUrna(), 50) +
                                Utilitario.textoFixo(g.getCargo().toString(), 25) +
                                Utilitario.textoFixo(String.valueOf(g.getNumero()), 10) +
                                g.getPartido().getNome()
                        )
                    )
            );

        System.out.println("---------------------- CANDIDATOS A PRESIDENTE ---------------------");
        System.out.println(
            Utilitario.textoFixo("Nome", 50) +
            Utilitario.textoFixo("Cargo", 25) +
            Utilitario.textoFixo("Número", 10) +
            "Partido"
        );

        partidos.values()
            .forEach(p ->
                p.presidencia()
                    .forEach(pr ->
                        System.out.println(
                                Utilitario.textoFixo(pr.getNomeUrna(), 50) +
                                Utilitario.textoFixo(pr.getCargo().toString(), 25) +
                                Utilitario.textoFixo(String.valueOf(pr.getNumero()), 10) +
                                pr.getPartido().getNome()
                        )
                    )
            );
    }

    @Override
    public void carregar() {
        partidos = new HashMap<>();
        List<String> candidatos = Utilitario.carregarCandidatos();

        candidatos = candidatos.subList(1, candidatos.size());

        for (String candidato : candidatos) {
            String[] informacoes = candidato.split(";");
            String nomeCompleto = informacoes[0];
            String nomeUrna = informacoes[1];
            Cargo cargo = switch (informacoes[2]) {
                case "Presidente" -> Cargo.PRESIDENTE;
                case "Governador" -> Cargo.GOVERNADOR;
                case "Senador" -> Cargo.SENADOR;
                case "Deputado Federal" -> Cargo.DEPUTADO_FEDERAL;
                case "Deputado Estadual" -> Cargo.DEPUTADO_ESTADUAL;
                default -> throw new IllegalStateException("Unexpected value: " + informacoes[2]);
            };
            int numero = Integer.parseInt(informacoes[3]);
            int numeroPartido = Integer.parseInt(informacoes[3].substring(0, 2));
            String nomePartido = informacoes[4];

            Partido partido = partidos.get(numeroPartido);

            if (partido == null) {
                partidos.put(numeroPartido, new Partido(numeroPartido, nomePartido));

                partido = partidos.get(numeroPartido);
            }

            partido.addCandidato(new Candidato(nomeUrna, nomeCompleto, numero, cargo, partido));
        }

        imprimirPartidos();
        System.out.println("--------------------------------------------------------------------");
        imprimirCandidatos();
    }

    @Override
    public void votar() {
        votarCargo(Cargo.DEPUTADO_FEDERAL);
        votarCargo(Cargo.DEPUTADO_ESTADUAL);
        votarCargo(Cargo.SENADOR);
        votarCargo(Cargo.GOVERNADOR);
        votarCargo(Cargo.PRESIDENTE);
    }

    private void votarCargo(Cargo cargo) {
        Scanner scanner = new Scanner(System.in);

        boolean corrige;
        do {
            Candidato candidato = null;
            boolean branco = false;
            boolean nulo = false;

            String opcao;
            boolean isNumber;
            do {
                isNumber = true;

                switch (cargo) {
                    case DEPUTADO_FEDERAL -> System.out.print("Deputado Federal [4 números | BRANCO]: ");
                    case DEPUTADO_ESTADUAL -> System.out.print("Deputado Estadual [5 números | BRANCO]: ");
                    case SENADOR -> System.out.print("Senador [3 números | BRANCO]: ");
                    case GOVERNADOR -> System.out.print("Governador [2 números | BRANCO]: ");
                    case PRESIDENTE -> System.out.print("Presidente [2 números | BRANCO]: ");
                }

                opcao = scanner.nextLine();

                for (char c : opcao.toCharArray()) {
                    if (!Character.isDigit(c)) isNumber = false;
                }
            } while (opcao.isBlank() || (!"BRANCO".equals(opcao) && !isNumber));

            if ("BRANCO".equals(opcao)) {
                System.out.println("VOTO EM BRANCO");
                branco = true;
            } else {
                int numeroPartido = Integer.parseInt(opcao.length() > 2 ? opcao.substring(0, 2) : opcao);
                Partido partido = partidos.get(numeroPartido);

                if (partido == null) {
                    System.out.println("VOTO NULO");
                    nulo = true;
                } else {
                    candidato = partido.getCandidato(cargo, Integer.parseInt(opcao));

                    if (candidato == null) {
                        System.out.println("VOTO NULO");
                        nulo = true;
                    } else {
                        System.out.printf("Nome: %s\t\tPartido: %s (%d)\n", candidato.getNomeUrna(), candidato.getPartido().getNome(), candidato.getPartido().getNumero());
                    }
                }
            }

            String confirma;
            do {
                System.out.print("[CORRIGE | CONFIRMA]: ");

                confirma = scanner.nextLine();
            } while (!"CORRIGE".equals(confirma) && !"CONFIRMA".equals(confirma));

            if ("CORRIGE".equals(confirma)) {
                corrige = true;
            } else {
                corrige = false;

                if (branco) {
                    brancos[cargo.ordinal()]++;
                } else if (nulo) {
                    nulos[cargo.ordinal()]++;
                } else {
                    candidato.receberVoto();
                }
            }
        } while (corrige);
    }

    @Override
    public void imprimir() {
        List<Candidato> deputadosFederais = partidos.values().stream().flatMap(Partido::deputadosFederais).toList();
        System.out.println(Boletim.emitir("DEPUTADO FEDERAL", deputadosFederais, brancos[0], nulos[0]));

        List<Candidato> deputadosEstaduais = partidos.values().stream().flatMap(Partido::deputadosEstaduais).toList();
        System.out.println(Boletim.emitir("DEPUTADO ESTADUAL", deputadosEstaduais, brancos[1], nulos[1]));

        List<Candidato> senadores = partidos.values().stream().flatMap(Partido::senado).toList();
        System.out.println(Boletim.emitir("SENADOR", senadores, brancos[2], nulos[2]));

        List<Candidato> governadores = partidos.values().stream().flatMap(Partido::governo).toList();
        System.out.println(Boletim.emitir("GOVERNADOR", governadores, brancos[3], nulos[3]));

        List<Candidato> presidentes = partidos.values().stream().flatMap(Partido::presidencia).toList();
        System.out.println(Boletim.emitir("PRESIDENTE", presidentes, brancos[4], nulos[4]));
    }
}
