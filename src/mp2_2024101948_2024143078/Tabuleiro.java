package mp2_2024101948_2024143078;

/**
 * Representa o tabuleiro do jogo.
 * <p>
 * Esta classe encapsula a matriz bidimensional de objetos Celula, que compõem o
 * tabuleiro, e gerencia o número de bandeiras disponíveis para marcação. 
 * A classe esconde os detalhes internos de manipulação do tabuleiro e fornece 
 * métodos para inicializar, exibir e interagir com as células.
 * </p>
 * <p>
 * A configuração do tabuleiro é determinada a partir de um parâmetro NivelJogo, 
 * que especifica as dimensões e o número de minas de acordo com o nível de 
 * dificuldade escolhido pelo jogador.
 * </p>
 * 
 */
public class Tabuleiro {
    private Celula[][] tabuleiro;
    private int bandeirasDisponiveis;
    
    /**
     * Constrói um tabuleiro configurado de acordo com o nível de dificuldade 
     * especificado.
     * <p>
     * O construtor utiliza o enum NivelJogo para determinar a dimensão do 
     * tabuleiro e o número de minas distribuídas por ele. Em seguida, chama o 
     * método inicializarTabuleiro para configurar as células, posicionar minas 
     * e distribuir energizadores.
     * </p>
     *
     * @param nivel o nível de dificuldade do jogo (INICIAL, MEDIO ou PROFISSIONAL)
     */
    public Tabuleiro(NivelJogo nivel) {
        switch (nivel) {
            case INICIAL:
                inicializarTabuleiro(9, 10);
                break;
            case MEDIO:
                inicializarTabuleiro(10, 16);
                break;
            case PROFISSIONAL:
                inicializarTabuleiro(12, 24);
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Cria um tabuleiro com o nível de dificuldade padrão (INICIAL).
     */
    public Tabuleiro() {
        this(NivelJogo.INICIAL);
    }

    /**
     * Inicializa o tabuleiro:
     * <ul>
     *   <li>Cria uma matriz de Celula com a dimensão especificada.</li>
     *   <li>Posiciona aleatoriamente as minas até atingir o número desejado.</li>
     *   <li>Define a quantidade de bandeiras disponíveis igual ao número de minas.</li>
     *   <li>Calcula o número de minas adjacentes para cada célula.</li>
     *   <li>Distribui os energizadores em células vazias.</li>
     * </ul>
     *
     * @param dimensao o tamanho (número de linhas e colunas) do tabuleiro
     * @param numeroDeMinas o número total de minas a serem posicionadas
     */
    public void inicializarTabuleiro(int dimensao, int numeroDeMinas) {
        this.tabuleiro = new Celula[dimensao][dimensao];
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                this.tabuleiro[i][j] = new Celula(i, j);
            }
        }
        
        int totalMinas = 0;
        while (totalMinas < numeroDeMinas) {
            int linha = (int)(Math.random() * dimensao);
            int coluna = (int)(Math.random() * dimensao);
            if (!this.tabuleiro[linha][coluna].isTemMina()){
                this.tabuleiro[linha][coluna].setTemMina(true);
                totalMinas++;
            }
        }
        
        bandeirasDisponiveis = numeroDeMinas;
        calcularMinasAdjacentes();

        Energizador[] energizadoresArray = new Energizador[]{
            new Escudo(),
            new Gelo(),
            new Linha(),
            new Coluna(),
            new Dica()
        };

        for (Energizador e : energizadoresArray) {
            boolean placed = false;
            while (!placed) {
                int i = (int)(Math.random() * dimensao);
                int j = (int)(Math.random() * dimensao);
                Celula c = tabuleiro[i][j];
                if (!c.isTemMina() && c.getEnergizador() == null) {
                    c.setEnergizador(e);
                    placed = true;
                }
            }
        }
    }

    /**
     * Calcula e atualiza o número de minas adjacentes para cada célula que não
     * contém mina.
     * <p>
     * Este método percorre todas as células do tabuleiro. Para cada célula sem 
     * mina, ele conta quantas das células vizinhas contêm minas e atualiza o 
     * atributo minasAdjacentes da célula. 
     * </p>
     */
    public void calcularMinasAdjacentes() {
        int dimensao = tabuleiro.length;
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                if (!tabuleiro[i][j].isTemMina()) {
                    int count = 0;
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            if (di == 0 && dj == 0)
                                continue;
                            int ni = i + di;
                            int nj = j + dj;
                            if (ni >= 0 && nj >= 0 && ni < dimensao && nj < dimensao
                                    && tabuleiro[ni][nj].isTemMina()) {
                                count++;
                            }
                        }
                    }
                    tabuleiro[i][j].setMinasAdjacentes(count);
                }
            }
        }
    }

    /**
     * Exibe o tabuleiro na consola.
     * <p>
     * As colunas são numeradas de 1 até o tamanho do tabuleiro, facilitando a 
     * interação com o jogador. 
     * Se o parâmetro revelar for true, todas as células são exibidas com seus 
     * valores, independentemente de terem sido abertas.
     * Caso contrário, células não abertas são representadas pelo símbolo "X" e 
     * células com bandeira por "B".
     * </p>
     *
     * @param revelar {@code true} para exibir todas as células,
     * {@code false} para exibir em modo normal
     */
    public void exibirTabuleiro(boolean revelar) {
        int dimensao = tabuleiro.length;
        System.out.print("   ");
        for (int j = 1; j <= dimensao; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        
        for (int i = 0; i < dimensao; i++) {
            char rowLabel = (char) ('A' + i);
            System.out.print(rowLabel + "  ");
            for (int j = 0; j < dimensao; j++) {
                Celula c = tabuleiro[i][j];
                String mostrarCel = "";
                if (c.isFoiAberta()) {
                    if (c.isTemMina()) {
                        mostrarCel = "*";
                    } else if (c.getMinasAdjacentes() > 0) {
                        mostrarCel = Integer.toString(c.getMinasAdjacentes());
                    } else {
                        mostrarCel = " ";
                    }
                } else {
                    if (revelar) {
                        if (c.isTemMina()) {
                            mostrarCel = "*";
                        } else {
                            mostrarCel = (c.getMinasAdjacentes() > 0) 
                                    ? Integer.toString(c.getMinasAdjacentes()) : " ";
                        }
                    } else {
                        mostrarCel = c.isTemBandeira() ? "B" : "X";
                    }
                }
                System.out.print(mostrarCel + " ");
            }
            System.out.println();
        }
        System.out.println("\nBandeiras disponiveis: " + bandeirasDisponiveis);
    }

    /**
     * Tenta abrir a célula na posição especificada.
     * <p>
     * Se a célula já foi aberta ou possui uma bandeira, nenhuma ação é realizada.
     * Se a célula contém uma mina, o método retorna false imediatamente.
     * Caso a célula não contenha mina e não tenha minas adjacentes, o método
     * aciona a abertura em cascata das células vizinhas.
     * </p>
     *
     * @param linha o índice da linha da célula a ser aberta
     * @param coluna o índice da coluna da célula a ser aberta
     * @return {@code true} se a célula foi aberta sem acionar uma mina, 
     * ou {@code false} se a mina foi acionada
     */
    public boolean abrirCelula(int linha, int coluna) {
        int dimensao = tabuleiro.length;
        if (linha < 0 || linha >= dimensao || coluna < 0 || coluna >= dimensao){
            return true;
        }
        Celula cel = tabuleiro[linha][coluna];
        if (cel.isFoiAberta() || cel.isTemBandeira()) {
            return true;
        }
        cel.setFoiAberta(true);
        if (cel.isTemMina()) {
            return false;
        } else {
            if (cel.getMinasAdjacentes() == 0) {
                abrirCascata(linha, coluna);
            }
        }
        return true;
    }

    /**
     * Abre recursivamente as células vizinhas a partir da célula na posição 
     * especificada.
     * <p>
     * Este método é chamado quando uma célula aberta não possui minas adjacentes, 
     * permitindo a abertura em massa de células seguras. Ou seja, faz uso
     * de recursão para simplificar a lógica de abertura em cascata.
     * </p>
     *
     * @param linha o índice da linha da célula inicial
     * @param coluna o índice da coluna da célula inicial
     */
    private void abrirCascata(int linha, int coluna) {
        int dimensao = tabuleiro.length;
        
        for (int di = -1; di <= 1; di++) {
            
            for (int dj = -1; dj <= 1; dj++) {
                int ni = linha + di, nj = coluna + dj;
                
                if (ni >= 0 && ni < dimensao && nj >= 0 && nj < dimensao) {
                    Celula c = tabuleiro[ni][nj];
                    
                    if (!c.isFoiAberta() && !c.isTemBandeira() && !c.isTemMina()) {
                        c.setFoiAberta(true);
                        
                        if (c.getMinasAdjacentes() == 0) {
                            abrirCascata(ni, nj);
                        }
                    }
                }
            }
        }
    }

    /**
     * Marca ou desmarca uma célula com bandeira.
     * <p>
     * Se a célula não estiver aberta e houver bandeiras disponíveis, o método 
     * marca a célula com uma bandeira, decrementando o contador de bandeiras. 
     * Se a célula já estiver marcada, a marcação é removida e o contador é 
     * incrementado.
     * </p>
     *
     * @param linha o índice da linha da célula
     * @param coluna o índice da coluna da célula
     */
    public void marcarBandeira(int linha, int coluna) {
        int dimensao = tabuleiro.length;
        if (linha < 0 || linha >= dimensao || coluna < 0 || coluna >= dimensao){
            return;
        }
        
        Celula cel = tabuleiro[linha][coluna];
        if (cel.isFoiAberta()){
            return;
        }
        
        if (!cel.isTemBandeira() && bandeirasDisponiveis > 0) {
            cel.setTemBandeira(true);
            bandeirasDisponiveis--;
            
        } else if (!cel.isBandeiraPermanente() && cel.isTemBandeira()) {
            cel.setTemBandeira(false);
            bandeirasDisponiveis++;
            
        } else {
            System.out.println("Nao é possível remover uma bandeira permanente "
                    + "ou nao ha bandeiras disponiveis.");
        }
    }

    /**
     * Verifica se o jogador venceu o jogo.
     * <p>
     * O jogo é considerado vencido se todas as células que não contêm minas 
     * estiverem abertas.
     * </p>
     *
     * @return {@code true} se o jogo foi vencido, ou {@code false} caso contrário
     */
    public boolean verificarVitoria() {
        int dimensao = tabuleiro.length;
        
        for (int i = 0; i < dimensao; i++) {
            
            for (int j = 0; j < dimensao; j++) {
                Celula cel = tabuleiro[i][j];
                
                if (!cel.isTemMina() && !cel.isFoiAberta()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Revela todas as minas do tabuleiro.
     * <p>
     * Este método é chamado quando o jogador aciona uma mina, 
     * fazendo com que todas as minas sejam exibidas para o jogador.
     * </p>
     */
    public void revelarMinas() {
        int dimensao = tabuleiro.length;
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                Celula cel = tabuleiro[i][j];
                if (cel.isTemMina()) {
                    cel.setFoiAberta(true);
                }
            }
        }
    }
    
    /**
     * Retorna a matriz de células que compõe o tabuleiro.
     *
     * @return a matriz de obejtos Celula
     */
    public Celula[][] getTabuleiro() {
        return tabuleiro;
    }
    
    /**
     * Retorna o número de bandeiras disponíveis para marcação.
     * @return o número de bandeiras disponíveis
     */
    public int getBandeirasDisponiveis() {
        return bandeirasDisponiveis;
    }
    
    /**
     * Define o número de bandeiras disponíveis para marcação.
     * @param bandeirasDisponiveis o novo número de bandeiras disponíveis
     */
    public void setBandeirasDisponiveis(int bandeirasDisponiveis) {
        this.bandeirasDisponiveis = bandeirasDisponiveis;
    }
    
    /**
     * Retorna uma representação textual do tabuleiro.
     * @return uma string representando o estado interno do tabuleiro
     */
    @Override
    public String toString() {
        return "Tabuleiro{" + "tabuleiro=" + tabuleiro + "}";
    }
}
