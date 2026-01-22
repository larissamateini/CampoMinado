package mp2_2024101948_2024143078;

/**
 * A classe principal do projeto "Campo Minado".
 * <p>
 * Esta classe instancia um objeto da classe Jogo – que encapsula a lógica 
 * do jogo – e delega a ele a responsabilidade de iniciar e gerenciar a execução
 * do jogo.
 * </p>
 * 
 */
public class MP2_2024101948_2024143078 {

    /**
     * Método principal que inicia a execução do jogo.
     * <p>
     * Este método cria uma instância Jogo e chama seu método 
     * iniciarExecucao() para iniciar a interação com o jogador.
     * </p>
     *
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciarExecucao();
    }
    
}
