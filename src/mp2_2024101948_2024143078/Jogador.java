package mp2_2024101948_2024143078;

import java.util.ArrayList;

/**
 * Representa um jogador no jogo "Campo Minado".
 * <p>
 * Esta classe demonstra a interação entre objetos (o Jogador possui um 
 * Inventário). Além disso, utiliza uma coleção estática para manter o histórico
 * de jogadores.
 * </p>
 */
public class Jogador {
    private String nickname;
    private Inventario inventario;
    static ArrayList<Jogador> jogadores = new ArrayList<>();

    /**
     * Cria um jogador com o nickname especificado e inicializa seu inventário.
     * @param nickname o nome do jogador
     */
    public Jogador(String nickname) {
        this.nickname = nickname;
        this.inventario = new Inventario();
    }

    /**
     * Construtor padrão que cria um jogador com nickname "Sem nome".
     */
    public Jogador() {
        this("Sem nome");
    }

    /**
     * Retorna o nickname do jogador.
     * @return o nickname
     */
    public String getNome() {
        return nickname;
    }

    /**
     * Define o nickname do jogador.
     * @param nickname o novo nickname
     */
    public void setNome(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Retorna o inventário do jogador.
     * @return o inventário
     */
    public Inventario getInventario() {
        return inventario;
    }

    /**
     * Define o inventário do jogador.
     * @param inventario o novo inventário
     */
    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    
    /**
     * Adiciona um novo jogador à lista estática de jogadores.
     * @param jogador o jogador a ser adicionado
     */
    public static void addNovoJogador(Jogador jogador){
        jogadores.add(jogador);
    }

    /**
     * Retorna a lista de jogadores.
     * @return a lista estática de jogadores
     */
    public static ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    /**
     * Retorna uma representação textual do jogador, incluindo seu nickname e 
     * inventário.
     * @return uma string representando o jogador
     */
    @Override
    public String toString() {
        return "\n Nickname " + nickname 
                + "\nInventario:" + inventario;
    }
}
