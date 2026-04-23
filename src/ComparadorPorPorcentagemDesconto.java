import java.util.Comparator;
public class ComparadorPorPorcentagemDesconto implements Comparator<Produto>{

    @Override
    public int compare(Produto o1, Produto o2) {
        return Double.compare(
            o1.getPorcentagemDesconto(),
            o2.getPorcentagemDesconto()
        );
    }
    
}
