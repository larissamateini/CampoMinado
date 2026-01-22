package mp2_2024101948_2024143078;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Representa a lógica central do jogo "MineSweeper Game".
 * <p>
 * Esta classe centraliza os conceitos de encapsulamento e composição, 
 * coordenando a interação entre os objetos que compõem o jogo.
 * O método iniciarExecucao() inicia o loop principal de interação com o jogador,
 * onde comandos são lidos e processados, delegando a execução de operações 
 * específicas para métodos internos.
 * </p>
 * <p>
 * Além disso, a classe gerencia o histórico de vitórias e o inventário do jogador,
 * reforçando a reutilização de código e a organização modular.
 * </p>
 */
public class Jogo {

    private Jogador jogador;
    private Tabuleiro tabuleiro;
    private int contadorAnonimos;
    
    private long inicioDeJogo;
    private Cronometro tempoDeJogo;
    
    private int protecaoEscudo = 0;
    private int jogadasCongeladas = 0;
    private long momentoInicioCongelamento = 0;
    
    private ArrayList<String> dicasUsadas = new ArrayList<>();
    private static ArrayList<Vitoria> historicoVitorias = new ArrayList<>();

    /**
     * Construtor da classe Jogo.
     * <p>
     * Inicializa os componentes essenciais do jogo (jogador, tabuleiro, cronómetro).
     * Também define o contador de jogadores anónimos para 1.
     * </p>
     */
    public Jogo() {
        this.jogador = new Jogador();
        this.tabuleiro = new Tabuleiro();
        this.contadorAnonimos = 1;
        this.inicioDeJogo = System.currentTimeMillis();
        this.tempoDeJogo = new Cronometro(this.inicioDeJogo);
    }

    /**
     * Inicia a execução do jogo.
     * <p>
     * Este método cria um laço de repetição que apresenta o menu principal e
     * processa a escolha do jogador. Ele utiliza a classe Scanner para ler
     * os comandos e delega a execução para os métodos correspondentes, garantindo
     * a separação de responsabilidades.
     * </p>
     */
    public void iniciarExecucao() {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            exibirMenuPrincipal();
            System.out.print("Escolha: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Opção invalida. Por favor, tente novamente.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    iniciarJogo(scanner);
                    break;
                case 2:
                    exibirTop3();
                    break;
                case 3:
                    opcao = confirmarSaida(scanner, opcao);
                    break;
                default:
                    System.out.println("\nOpcao invalida. "
                            + "Por favor, tente novamente.");
            }

        } while (opcao != 3);

        System.out.println("Programa finalizado.");
    }

     /**
     * Exibe o menu principal do jogo.
     * <p>
     * Mostra as opções disponíveis: iniciar um novo jogo, exibir o top 3 e sair.
     * </p>
     */
    public void exibirMenuPrincipal() {
        System.out.println("\nMineSweeper Game"
                + "\n-----------------"
                + "\n1. Novo Jogo"
                + "\n2. Top 3"
                + "\n3. Sair da aplicacao");
    }

    /**
     * Inicia um novo jogo.
     * <p>
     * Este método lê o nickname do usuário, verifica se o jogador já existe e, 
     * adiciona energizadores ao inventário do jogador. Em seguida, permite a 
     * escolha do nível de dificuldade e cria um Tabuleiro configurado de acordo. 
     * Por fim, delega a execução da partida para o método processarComandos.
     * </p>
     *
     * @param scanner o objeto Scanner para leitura de comandos
     */
    public void iniciarJogo(Scanner scanner) {
        int opcao = 0;
        
        System.out.print("Digite o seu nickname: ");
        String novoNickname = scanner.nextLine().trim();

        if (novoNickname.isEmpty()) {
            novoNickname = "Anonimo " + contadorAnonimos++;
        }
        
        this.jogador = new Jogador(novoNickname);
        
        boolean jogadorEncontrado = false;
        for (Jogador jogadorDalista : Jogador.getJogadores()) {
            
            if (jogadorDalista.getNome().equals(this.jogador.getNome())) {
                this.jogador = jogadorDalista;
                
                System.out.println("\nBem-vindo de volta, " 
                        + this.jogador.getNome() + "!");
                jogadorEncontrado = true;
                break;
            }
        }
        
        if (!jogadorEncontrado) {
            System.out.println("\nBem-vindo, " + this.jogador.getNome() + "!");
            Jogador.addNovoJogador(this.jogador);
            
            for (int i = 0; i < 3; i++) {
                Energizador energizadorR = Energizador.getEnergizador();
                jogador.getInventario().adicionar(energizadorR.getTipo());
            }
        }

        jogador.setInventario(new Inventario());
        for (int i = 0; i < 3; i++) {
            Energizador energizadorR = Energizador.getEnergizador();
            jogador.getInventario().adicionar(energizadorR.getTipo());
        }

        do {
            exibirMenuDeNiveis();
            System.out.print("Escolha: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Opcao invalida. Por favor, tente novamente.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    this.tabuleiro = new Tabuleiro(NivelJogo.INICIAL);
                    break;
                case 2:
                    this.tabuleiro = new Tabuleiro(NivelJogo.MEDIO);
                    break;
                case 3:
                    this.tabuleiro = new Tabuleiro(NivelJogo.PROFISSIONAL);
                    break;
                default:
                    System.out.println("\nOpção invalida. "
                            + "Por favor, tente novamente.");
            }

        } while (opcao != 1 && opcao != 2 && opcao != 3);

        processarComandos(scanner);
    }
    
     /**
     * Processa os comandos do jogo enquanto a partida está em andamento.
     * <p>
     * Este método implementa o loop principal do jogo, onde comandos são lidos 
     * da consola e processados, permitindo a interação do jogador com o tabuleiro. 
     * São tratados comandos como:
     * </p>
     * <ul>
     *   <li>/ajuda: exibe as instruções de uso;</li>
     *   <li>/abrir: tenta abrir uma célula;</li>
     *   <li>/bandeira: marca ou desmarca uma célula com bandeira;</li>
     *   <li>/inventário: exibe o inventário do jogador;</li>
     *   <li>/usar: utiliza um energizador, aplicando seu efeito;</li>
     *   <li>/historial: mostra o histórico de comandos inseridos;</li>
     *   <li>/vitorias: exibe o ranking de tempo das vitórias;</li>
     *   <li>/batota: ativa o modo batota (exibe o tabuleiro completo);</li>
     *   <li>/sair: encerra a partida.</li>
     * </ul>
     * 
     *
     * @param scanner o objeto Scanner para leitura dos comandos do usuário
     */
    public void processarComandos(Scanner scanner) {
        ArrayList<String> historicoComandos = new ArrayList<>();
        boolean jogoAtivo = true;
        while (jogoAtivo) {
            
            if (jogadasCongeladas > 0) {
                long tempoCongelado = momentoInicioCongelamento - inicioDeJogo;
                long secs = tempoCongelado / 1000;
                long s = secs % 60;
                long m = (secs / 60) % 60;
                long h = secs / 3600;
                System.out.printf("\nTempo: %02d:%02d:%02d\n", h, m, s);
                
            } else {
                
                System.out.println("\nTempo: " + tempoDeJogo.getDuracaoFormatada());
            }
            
            tabuleiro.exibirTabuleiro(false);
            
            System.out.println("Inventario: " + jogador.getInventario());
            System.out.print("\n[Digite </ajuda> para assistencia]\nComando: ");
            
            String comando = scanner.nextLine().trim();
            historicoComandos.add(comando);
            String[] partes = comando.split(" ");
            
            if (partes.length == 0) continue;
            String cmd = partes[0].toLowerCase();
            
            switch (cmd) {
                case "/ajuda":
                    System.out.println("\nComandos disponiveis:\n"
                        + "/ajuda - Lista comandos\n"
                        + "/abrir <linha> <coluna> - Abre a celula\n"
                        + "/bandeira <linha> <coluna> - Marca/desmarca a celula\n"
                        + "/inventario - Mostra inventario\n"
                        + "/usar <energizador> [<argumento>] - Usa um energizador\n"
                        + "/historial - Mostra historico de comandos\n"
                        + "/vitorias - Mostra historico de vitorias\n"
                        + "/batota - Ativa modo batota\n"
                        + "/sair - Encerra o jogo");
                    break;
                    
                case "/abrir":
                    if (partes.length >= 3) {
                        int linha = partes[1].toUpperCase().charAt(0) - 'A';
                        int coluna;
                        try {
                            coluna = Integer.parseInt(partes[2]) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Coluna invalida.");
                            break;
                        }
                        boolean resultado = tabuleiro.abrirCelula(linha, coluna);
                        if (!resultado) {
                            
                            if (protecaoEscudo > 0) {
                                protecaoEscudo--;
                                Celula cel = tabuleiro.getTabuleiro()[linha][coluna];
                                cel.setTemBandeira(true);
                                cel.setBandeiraPermanente(true);
                                cel.setFoiAberta(true);
                                System.out.println("Escudo ativado! Mina neutralizada.");
                                resultado = true;
                            } else {
                                System.out.println("Você acionou uma mina! Fim de jogo.");
                                tabuleiro.revelarMinas();
                                tabuleiro.exibirTabuleiro(true);
                                
                                jogador.getInventario().clear();
                                jogoAtivo = false;
                                break;
                            }
                        }
                        
                        if (tabuleiro.verificarVitoria()) {
                            System.out.println("Parabéns! Você venceu!");
                            tabuleiro.exibirTabuleiro(true);
                            
                            long tempoEmSegundos = (System.currentTimeMillis() - tempoDeJogo.getInicio()) / 1000;
                            String tempoFormatado = tempoDeJogo.getDuracaoFormatada();
                            Vitoria vit = new Vitoria(jogador.getNome(), tempoEmSegundos, tempoFormatado);
                            historicoVitorias.add(vit);
                            jogoAtivo = false;
                        }
                    } else {
                        
                        System.out.println("Uso: /abrir <linha> <coluna>");
                    }
                    break;
                    
                case "/bandeira":
                    if (partes.length >= 3) {
                        int linha = partes[1].toUpperCase().charAt(0) - 'A';
                        int coluna;
                        
                        try {
                            coluna = Integer.parseInt(partes[2]) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Coluna invalida.");
                            break;
                        }
                        
                        tabuleiro.marcarBandeira(linha, coluna);
                    } else {
                        
                        System.out.println("Uso: /bandeira <linha> <coluna>");
                    }
                    break;
                    
                case "/inventario":
                    System.out.println("Inventario: \n" + jogador.getInventario());
                    break;
                    
                case "/usar":
                    if (partes.length >= 2) {
                        String energizador = partes[1].toLowerCase();
                        
                        if (jogador.getInventario().usar(energizador)) {
                            switch (energizador) {
                                case "escudo":
                                    protecaoEscudo++;
                                    System.out.println("Escudo ativado. Proteção adicionada.");
                                    break;
                                    
                                case "gelo":
                                    jogadasCongeladas += 3;
                                    
                                    if (momentoInicioCongelamento == 0) {
                                        momentoInicioCongelamento = System.currentTimeMillis();
                                    }
                                    System.out.println("Gelo ativado: "
                                            + "tempo congelado por " 
                                            + jogadasCongeladas + " jogadas.");
                                    break;
                                    
                                case "linha":
                                    if (partes.length >= 3) {
                                        int linha = partes[2].toUpperCase().charAt(0) - 'A';
                                        Celula[][] matriz = tabuleiro.getTabuleiro();
                                        int dimensao = matriz.length;
                                        
                                        for (int j = 0; j < dimensao; j++) {
                                            Celula cel = matriz[linha][j];
                                            
                                            if (cel.isTemMina() && !cel.isTemBandeira()) {
                                                cel.setTemBandeira(true);
                                                cel.setBandeiraPermanente(true);
                                                cel.setFoiAberta(true);
                                                int bandeiras = tabuleiro.getBandeirasDisponiveis();
                                                
                                                if (bandeiras > 0) {
                                                    tabuleiro.setBandeirasDisponiveis(bandeiras - 1);
                                                }
                                                
                                            } else if (!cel.isFoiAberta()) {
                                                tabuleiro.abrirCelula(linha, j);
                                            }
                                        }
                                        System.out.println("Linha " 
                                                + partes[2].toUpperCase() 
                                                + " processada.");
                                    } else {
                                        
                                        System.out.println("Uso: "
                                                + "/usar linha <letra>");
                                    }
                                    break;
                                    
                                case "coluna":
                                    if (partes.length >= 3) {
                                        int colIndex;
                                        
                                        try {
                                            colIndex = Integer.parseInt(partes[2]) - 1;
                                        } catch (NumberFormatException e) {
                                            System.out.println("Coluna inválida.");
                                            break;
                                        }
                                        
                                        Celula[][] matriz = tabuleiro.getTabuleiro();
                                        int dimensao = matriz.length;
                                        
                                        for (int i = 0; i < dimensao; i++) {
                                            Celula cel = matriz[i][colIndex];
                                            
                                            if (cel.isTemMina() && !cel.isTemBandeira()) {
                                                cel.setTemBandeira(true);
                                                cel.setBandeiraPermanente(true);
                                                cel.setFoiAberta(true);
                                                int bandeiras = tabuleiro.getBandeirasDisponiveis();
                                                
                                                if (bandeiras > 0) {
                                                    tabuleiro.setBandeirasDisponiveis(bandeiras - 1);
                                                }
                                            } else if (!cel.isFoiAberta()) {
                                                tabuleiro.abrirCelula(i, colIndex);
                                            }
                                        }
                                        System.out.println("Coluna " 
                                                + partes[2] + " processada.");
                                        
                                    } else {
                                        System.out.println("Uso: /usar coluna <número>");
                                    }
                                    break;
                                    
                                case "dica":
                                    // Obtenha a matriz de células
                                    Celula[][] matriz = tabuleiro.getTabuleiro();
                                    // Cria uma lista para armazenar coordenadas seguras
                                    ArrayList<String> celulasSeguras = new ArrayList<>();
                                    int dimensao = matriz.length;

                                    for (int i = 0; i < dimensao; i++) {
                                        for (int j = 0; j < dimensao; j++) {
                                            // Considera célula segura se NÃO tiver mina e não foi aberta
                                            if (!matriz[i][j].isTemMina() && !matriz[i][j].isFoiAberta()) {
                                                // Formata as coordenadas para exibição
                                                char letra = (char) ('A' + i);
                                                String coord = letra + " " + (j + 1); // j+1 para exibir colunas começando em 1
                                                // Apenas adiciona se essa dica ainda não foi sugerida
                                                if (!dicasUsadas.contains(coord)) {
                                                    celulasSeguras.add(coord);
                                                }
                                            }
                                        }
                                    }

                                    if (!celulasSeguras.isEmpty()) {
                                        Random rand = new Random();
                                        int indiceAleatorio = rand.nextInt(celulasSeguras.size());
                                        String dicaSugerida = celulasSeguras.get(indiceAleatorio);
                                        dicasUsadas.add(dicaSugerida);
                                        System.out.println("Dica: tente abrir a célula " + dicaSugerida);
                                    } else {
                                        System.out.println("Nenhuma dica disponivel.");
                                    }
                                    break;

                    
                                default:
                                    System.out.println("Energizador '" 
                                            + energizador 
                                            + "' nao reconhecido.");
                            }
                            
                        } else {
                            System.out.println("Energizador " + energizador 
                                    + " nao disponivel no seu inventario.");
                        }
                        
                    } else {
                        
                        System.out.println("Uso <energizador> [<argumento>]");
                    }
                    break;
                    
                case "/historial":
                    System.out.println("Historico de Comandos:");
                    for (String hist : historicoComandos) {
                        System.out.println(hist);
                    }
                    break;
                    
                case "/vitorias":
                    exibirTop3();
                    break;
                case "/batota":
                    System.out.println("Modo batota ativado:");
                    tabuleiro.exibirTabuleiro(true);
                    break;
                    
                case "/sair":
                    jogador.getInventario().clear();
                    jogoAtivo = false;
                    break;
                    
                default:
                    System.out.println("Comando desconhecido.");
                    break;
            }
            
            if (jogadasCongeladas > 0) {
                jogadasCongeladas--;
                
                if (jogadasCongeladas == 0) {
                    long extra = System.currentTimeMillis() - momentoInicioCongelamento;
                    tempoDeJogo.setInicio(tempoDeJogo.getInicio() + extra);
                    momentoInicioCongelamento = 0;
                }
            }
        }
    }

    /**
     * Exibe o ranking Top 3 com os melhores tempos de vitória no jogo.
     * <p>
     * Este método percorre o histórico de vitórias (armazenado em uma coleção)
     * e identifica os três registos com os menores tempos. 
     * A exibição utiliza formatação para alinhar as colunas e apresenta o 
     * nickname e o tempo formatado.
     * </p>
     */
    public void exibirTop3() {
        System.out.println("\nTop 3 Vitorias:");
        System.out.printf("%-15s %s%n", "Nickname", "Tempo");

        Vitoria top1 = null;
        Vitoria top2 = null;
        Vitoria top3 = null;

        for (Vitoria v : historicoVitorias) {
            if (top1 == null || v.getTempoEmSegundos() < top1.getTempoEmSegundos()) {
                top3 = top2;
                top2 = top1;
                top1 = v;
            } else if (top2 == null || v.getTempoEmSegundos() < top2.getTempoEmSegundos()) {
                top3 = top2;
                top2 = v;
            } else if (top3 == null || v.getTempoEmSegundos() < top3.getTempoEmSegundos()) {
                top3 = v;
            }
        }

        if (top1 != null) {
            System.out.printf("%-15s %s%n", top1.getNickname(), top1.getTempoFormatado());
        }
        if (top2 != null) {
            System.out.printf("%-15s %s%n", top2.getNickname(), top2.getTempoFormatado());
        }
        if (top3 != null) {
            System.out.printf("%-15s %s%n", top3.getNickname(), top3.getTempoFormatado());
        }
        if (top1 == null) {
            System.out.println("Nenhuma vitoria registada.");
        }
    }
    
    /**
     * Solicita e processa a confirmação do usuário para sair do jogo.
     * <p>
     * Este método exibe uma mensagem de aviso informando que abandonar o jogo
     * causará a perda de todas as informações recolhidas e aguarda a resposta
     * do jogador. 
     * Caso o jogador confirme, o método limpa o inventário, exibe uma mensagem 
     * de finalização e encerra o loop principal. 
     * Se o jogador desistir de sair da aplicação, o jogo continua. 
     * </p>
     *
     * @param scanner o objeto Scanner utilizado para ler as respostas do
     * usuário.
     * @param opcao um valor inteiro representando a opção atual.
     * @return retorna 3 se o usuário confirmar a saída, ou 4 se o usuário optar
     * por continuar.
     */
    public int confirmarSaida(Scanner scanner, int opcao) {
        int confirmacao;
        
        do {
            System.out.println("Se abandonares o jogo perdera todas as "
                    + "informacoes recolhidas. Tens certeza? "
                    + "\n1- Sim \n2- Nao"
                    + "\nResposta:");
            confirmacao = scanner.nextInt();
            scanner.nextLine();
            
            switch (confirmacao) {
                case 1:
                    System.out.println("Programa finalizado.");
                    
                    jogador.getInventario().clear();
                    return 3;
                case 2:
                    return 4;
                default:
                    System.out.println("\nOpcao invalida. Por favor, tente novamente.");
            }
        } while (confirmacao != 1 && confirmacao != 2);
        
        return 0;
    }

    /**
     * Exibe o menu de escolha de nível de dificuldade.
     * <p>
     * Este método apresenta ao usuário as opções de níveis de dificuldade
     * disponíveis no jogo: Inicial, Medio e Profissional. Com autilização da
     * enumeração classe enum NivelJogo, garante-se a consistência dos
     * valores e a integridade dos dados.
     * </p>
     */
    public void exibirMenuDeNiveis() {
        System.out.println("Nivel de Dificuldade: "
                + "\n-----------------"
                + "\n1. Inicial"
                + "\n2. Medio"
                + "\n3. Profissional");
    }
}
