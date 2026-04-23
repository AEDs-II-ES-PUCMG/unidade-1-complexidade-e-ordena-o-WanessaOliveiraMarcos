import java.util.Comparator;

/**
 * Critério C - Índice de Economia (decrescente).
 * O índice de economia é a diferença entre o valor de catálogo atual e o valor efetivamente pago.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */

// FIZ SEGUINDO AS INSTRUÇÕES DO COMENTARIO ACIMA
public class ComparadorCriterioC implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        int comparador = Double.compare(o2.indiceEconomia(), o1.indiceEconomia());
        if(comparador == 0){
            int desempate1 =  Double.compare(o1.valorMedio(), o2.valorMedio());
            if(desempate1 == 0){
                 return Double.compare(o1.getIdPedido(), o2.getIdPedido());
            } else {
                return 1;
            }
        }else{
            return comparador;
        }
    }
}
