import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */

// FIZ SEGUINDO AS INSTRUÇÕES DO COMENTARIO ACIMA
public class ComparadorCriterioA implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        int comparar = Double.compare(o1.valorFinal(), o2.valorFinal());
        if (comparar == 0) {
            int desempate1 = Double.compare(o1.getQuantosProdutos(), o2.getQuantosProdutos());
    		if(desempate1 == 0){
                return Double.compare(o1.getIdPedido(), o2.getIdPedido());
            }else {
                return desempate1;
            }
    	}else{
           return Double.compare(o1.valorFinal(), o2.valorFinal());
        }
    }
}
