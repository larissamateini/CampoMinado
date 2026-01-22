package mp2_2024101948_2024143078;

/**
 * Representa uma célula do tabuleiro do jogo "Campo Minado".
 * <p>
 * Esta classe encapsula os atributos e comportamentos de uma célula, 
 * demonstrando os princípios de encapsulamento e abstração. Os atributos 
 * são declarados como privados e só podem ser acessados ou alterados por meio
 * dos métodos getters e setters.
 * </p>
 * 
 */
public class Celula {
    private int linha;
    private int coluna;
    private boolean temMina;
    private boolean temBandeira;
    private boolean foiAberta;
    private int minasAdjacentes;
    private Energizador energizador;
    private boolean bandeiraPermanente;

    /**
     * Construtor completo que inicializa uma célula com os parâmetros especificados.
     *
     * @param linha o índice da linha da célula
     * @param coluna o índice da coluna da célula
     * @param temMina indica se a célula contém uma mina
     * @param temBandeira indica se a célula possui uma bandeira
     * @param foiAberta indica se a célula já foi aberta
     * @param minasAdjacentes quantidade de minas nas células adjacentes
     * @param energizador o energizador presente na célula
     */
    public Celula(int linha, int coluna, boolean temMina, boolean temBandeira, 
            boolean foiAberta, int minasAdjacentes, Energizador energizador) {
        this.linha = linha;
        this.coluna = coluna;
        this.temMina = temMina;
        this.temBandeira = temBandeira;
        this.foiAberta = foiAberta;
        this.minasAdjacentes = minasAdjacentes;
        this.energizador = energizador;
        this.bandeiraPermanente = false;
    }

    /**
     * Construtor nulo que inicializa uma célula sem mina, 
     * sem bandeira, não aberta e sem energizador.
     *
     * @param linha  o índice da linha da célula
     * @param coluna o índice da coluna da célula
     */
    public Celula(int linha, int coluna){
        this(linha, coluna, false, false, false, 0, null);
    }

    // Métodos getters e setters

    public int getLinha() {
        return linha;
    }
    
    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    public int getColuna() {
        return coluna;
    }
    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
    public boolean isTemMina() {
        return temMina;
    }
    
    public void setTemMina(boolean temMina) {
        this.temMina = temMina;
    }
    
    public boolean isTemBandeira() {
        return temBandeira;
    }
    
    public void setTemBandeira(boolean temBandeira) {
        this.temBandeira = temBandeira;
    }
    
    public boolean isFoiAberta() {
        return foiAberta;
    }
    
    public void setFoiAberta(boolean foiAberta) {
        this.foiAberta = foiAberta;
    }
    
    public int getMinasAdjacentes() {
        return minasAdjacentes;
    }
    
    public void setMinasAdjacentes(int minasAdjacentes) {
        this.minasAdjacentes = minasAdjacentes;
    }
    
    public Energizador getEnergizador() {
        return energizador;
    }
    
    public void setEnergizador(Energizador energizador) {
        this.energizador = energizador;
    }

    public boolean isBandeiraPermanente() {
        return bandeiraPermanente;
    }
    
    public void setBandeiraPermanente(boolean bandeiraPermanente) {
        this.bandeiraPermanente = bandeiraPermanente;
    }
    
    /**
     * Retorna uma representação textual detalhada da célula.
     * @return uma string contendo os atributos da célula
     */
    @Override
    public String toString() {
        return "Celula:" + "linha=" + linha 
                + " coluna=" + coluna
                + " temMina=" + temMina 
                + " temBandeira=" + temBandeira 
                + " foiAberta=" + foiAberta 
                + " minasAdjacentes=" + minasAdjacentes 
                + " energizador=" + energizador 
                + " bandeiraPermanente=" + bandeiraPermanente + '}';
    }
}
