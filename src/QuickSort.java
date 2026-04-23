import java.util.Arrays;
import java.util.Comparator;
import java.time.Duration;
import java.time.LocalDateTime;
public class QuickSort<T extends Comparable<T>> implements IOrdenador<T>{
    private long comparacoes;
	private long movimentacoes;
	private LocalDateTime inicio;
	private LocalDateTime termino;	
    private T[] dadosOrdenados;
    private Comparator<T> comparador;

    public QuickSort() {
        comparacoes = 0;
        movimentacoes = 0;
    }

    @Override
    public T[] ordenar(T[] dados) {
        return ordenar(dados, T::compareTo);
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {
        this.comparador = comparador;
        dadosOrdenados = Arrays.copyOf(dados, dados.length);
        inicio = LocalDateTime.now();
        quickSort(dadosOrdenados, 0, dadosOrdenados.length - 1);
        termino = LocalDateTime.now();
        return dadosOrdenados;
    }


    private void quickSort(T[] array, int esq, int dir){
        int part;
        if (esq < dir){
            part = particao(array, esq, dir);
            quickSort(array, esq, part - 1);
            quickSort(array, part + 1, dir);
        }
    }

    private int particao(T[] array, int inicio, int fim) {
        T pivot = array[fim];
        int part = inicio - 1;
        for (int i = inicio; i < fim; i++) {
            comparacoes++;
            if (this.comparador.compare(array[i], pivot) < 0) {
                part++;
                swap(array, part, i);
            }
        }
        part++;
        swap(array, part, fim);
        return (part);
    }
	
    private void swap(T[] vet,int i, int j) {
		movimentacoes++;
		
		T temp = vet[i];
	    vet[i] = vet[j];
	    vet[j] = temp;
	}

    public long getComparacoes() {
		return comparacoes;
	}

	public long getMovimentacoes() {
		return movimentacoes;
	}
	
	public double getTempoOrdenacao() {
	    return  Duration.between(inicio, termino).toMillis();	    
	}
    
}
