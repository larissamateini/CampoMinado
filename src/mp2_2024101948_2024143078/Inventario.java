package mp2_2024101948_2024143078;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa o inventário de energizadores de um jogador.
 * <p>
 * Esta classe utiliza uma estrutura de dados (Map) para armazenar a quantidade 
 * de cada tipo de energizador.
 * Demonstra encapsulamento e a ideia de composição, onde o inventário é um 
 * componente do objeto Jogador.
 * </p>
 */
public class Inventario {
    private Map<String, Integer> energizadores;

    /**
     * Cria um inventário utilizando o mapa fornecido.
     * @param energizadores o mapa com os energizadores e suas quantidades
     */
    public Inventario(Map<String, Integer> energizadores) {
        this.energizadores = energizadores;
    }

    /**
     * Cria um inventário vazio.
     */
    public Inventario() {
        energizadores = new HashMap<>();
    }
    
    /**
     * Adiciona um energizador do tipo especificado ao inventário.
     * @param tipo o tipo do energizador a ser adicionado
     */
    public void adicionar(String tipo) {
        energizadores.put(tipo, energizadores.getOrDefault(tipo, 0) + 1);
    }
    
    /**
     * Tenta usar (consumir) um energizador do tipo especificado.
     * @param tipo o tipo do energizador
     * @return {@code true} se o emprego foi efetuado com sucesso, 
     * ou {@code false} se não houver quantidade suficiente
     */
    public boolean usar(String tipo) {
        if (energizadores.containsKey(tipo) && energizadores.get(tipo) > 0) {
            energizadores.put(tipo, energizadores.get(tipo) - 1);
            return true;
        }
        return false;
    }
    
    /**
     * Retorna o mapa de energizadores.
     * @return o mapa contendo os energizadores e suas quantidades
     */
    public Map<String, Integer> getEnergizadores() {
        return energizadores;
    }
    
    /**
     * Limpa o inventário removendo todos os energizadores.
     */
    public void clear() {
        energizadores.clear();
    }

    /**
     * Retorna uma representação textual do inventário.
     * @return uma string representando o inventário
     */
    @Override
    public String toString() {
        return energizadores.toString();
    }
}
