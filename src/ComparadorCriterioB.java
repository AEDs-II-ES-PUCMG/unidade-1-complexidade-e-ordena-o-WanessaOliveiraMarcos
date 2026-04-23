import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */

// FIZ SEGUINDO AS INSTRUÇÕES DO COMENTARIO ACIMA
public class ComparadorCriterioB implements Comparator<Pedido> {

    static int comparadorPorData(Pedido o1, Pedido o2){
        if (o1.getDataPedido().equals(o2.getDataPedido())) {
    		return (o1.getIdPedido() - o2.getIdPedido());
    	} else if (o1.getDataPedido().isBefore(o2.getDataPedido())) {
        	return -1;
        } else {
        	return 1;
        }
    }
    @Override
    public int compare(Pedido o1, Pedido o2) {
        int comparador = Double.compare(o1.getQuantosProdutos(), o2.getQuantosProdutos());
        if(comparador == 0){
            int desempate1 = comparadorPorData(o1, o2);
            if(desempate1 == 0){
                return Double.compare(o1.getIdPedido(), o2.getIdPedido());
            } else {
                return desempate1;
            }
        } else {
            Double.compare(o1.getQuantosProdutos(), o2.getQuantosProdutos());
        }
        return comparador;
    }
}
