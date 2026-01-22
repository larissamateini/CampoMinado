package mp2_2024101948_2024143078;

/**
 * Classe abstrata que representa um Energizador.
 * <p>
 * Esta classe demonstra o conceito de abstração e herança.
 * Define os comportamentos e atributos comuns a todos os energizadores do jogo.
 * </p>
 */
public abstract class Energizador {
    private String tipo;

    /**
     * Cria um energizador com o tipo especificado.
     * @param tipo o tipo do energizador
     */
    public Energizador(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Cria um energizador com um tipo padrão (sem tipo definido).
     */
    public Energizador() {
        this("Sem tipo");
    }

    /**
     * Retorna o tipo do energizador.
     * @return uma string representando o tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do energizador.
     * @param tipo a nova string que representa o tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna uma representação textual do energizador, ou seja, seu tipo.
     * @return o tipo do energizador
     */
    @Override
    public String toString() {
        return tipo;
    }

    /**
     * Método de fábrica que retorna um energizador aleatório dentre os tipos
     * disponíveis.
     * <p>
     * Este método aplica o conceito de polimorfismo, pois retorna uma instância
     * de uma subclasse de Energizador.
     * </p>
     *
     * @return um objeto do tipo Energizador
     */
    public static Energizador getEnergizador() {
        int r = (int)(Math.random() * 5);
        switch (r) {
            case 0:
                return new Escudo();
            case 1:
                return new Gelo();
            case 2:
                return new Linha();
            case 3:
                return new Coluna();
            case 4:
                return new Dica();
            default:
                return new Escudo();
        }
    }
}