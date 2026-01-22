package mp2_2024101948_2024143078;

/**
 * Representa o cronómetro utilizado para medir o tempo de uma partida.
 * <p>
 * A classe utiliza encapsulamento para proteger o atributo inicio,
 * que é utilizado para calcular o tempo decorrido no formato "hh:mm:ss".
 * </p>
 */
public class Cronometro {
    private long inicio;

    /**
     * Cria um cronómetro iniciando no instante atual.
     */
    public Cronometro() {
        this.inicio = System.currentTimeMillis();
    }

    /**
     * Cria um cronómetro com um instante inicial especificado.
     * @param inicio o instante inicial em milissegundos
     */
    public Cronometro(long inicio) {
        this.inicio = inicio;
    }

    /**
     * Retorna o instante inicial do cronómetro.
     * @return o valor de inicio em milissegundos
     */
    public long getInicio() {
        return inicio;
    }

    /**
     * Define o instante inicial do cronômetro.
     * @param inicio o novo instante em milissegundos
     */
    public void setInicio(long inicio) {
        this.inicio = inicio;
    }
    
    /**
     * Calcula e retorna o tempo decorrido desde o instante inicial, 
     * formatado como "hh:mm:ss".
     * @return uma string formatada representando o tempo decorrido
     */
    public String getDuracaoFormatada(){
        long totalSegundos = (System.currentTimeMillis() - inicio) / 1000;
        long segundos = totalSegundos % 60;
        long minutos = (totalSegundos / 60) % 60;
        long horas = totalSegundos / 3600;   
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    /**
     * Retorna uma representação textual da duração do jogo.
     * @return uma string que contém o tempo formatado com sua legenda
     */
    @Override
    public String toString() {
        return "Duração do Jogo: " + getDuracaoFormatada();
    }        
}
