package mp2_2024101948_2024143078;

/**
 * Representa o energizador do tipo "Escudo".
 * <p>
 * Esta classe herda de Energizador aplicando o conceito de herança e polimorfismo.
 * Ao usar o Escudo, o jogo ativa uma proteção que permite neutralizar uma mina.
 * </p>
 */
public class Escudo extends Energizador {

    /**
     * Construtor padrão para o energizador "escudo".
     */
    public Escudo(){
        super("escudo");
    }
}
