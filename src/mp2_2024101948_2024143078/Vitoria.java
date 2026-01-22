package mp2_2024101948_2024143078;

/**
 * Representa um registo de vitória no jogo.
 * <p>
 * A classe encapsula o nickname do jogador, o tempo da partida em segundos e o 
 * tempo formatado do jogo. Assim, garante a organização dos registos de jogo..
 * </p>
 */
public class Vitoria{
    private String nickname;
    private long tempoEmSegundos;
    private String tempoFormatado;

    /**
     * Constrói um registo de vitória com os dados fornecidos.
     * 
     * @param nickname        o nickname do jogador que venceu
     * @param tempoEmSegundos o tempo da partida em segundos
     * @param tempoFormatado  o tempo formatado (hh:mm:ss) da partida
     */
    public Vitoria(String nickname, long tempoEmSegundos, String tempoFormatado) {
        this.nickname = nickname;
        this.tempoEmSegundos = tempoEmSegundos;
        this.tempoFormatado = tempoFormatado;
    }

    /**
     * Retorna o nickname do jogador.
     * @return o nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Retorna o tempo da partida em segundos.
     * @return o tempo em segundos
     */
    public long getTempoEmSegundos() {
        return tempoEmSegundos;
    }

    /**
     * Retorna o tempo formatado da partida (hh:mm:ss).
     * @return o tempo formatado
     */
    public String getTempoFormatado() {
        return tempoFormatado;
    }
}
