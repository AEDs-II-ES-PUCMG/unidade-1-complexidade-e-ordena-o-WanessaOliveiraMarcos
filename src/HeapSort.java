import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class HeapSort<T extends Comparable<T>> implements IOrdenador<T>{
    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private T[] dadosOrdenados;
    private Comparator<T> comparador;

    public HeapSort() {
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
        int tamanho = dadosOrdenados.length;
        inicio = LocalDateTime.now();
        heapSort(dadosOrdenados);
        termino = LocalDateTime.now();
        return dadosOrdenados;
    }

    private void heapSort(T[] array){
        // Criando outro vetor, com todos os elementos do vetor anterior reposicionados (uma posição a frente)
        // de forma a ignorar a posição 0, que é a raiz da árvore
        T[] tmp = Arrays.copyOf(array, array.length+1);
        for (int i = 0; i < array.length; i++) {
            tmp[i+1] = array[i];
        }

        // Construção do heap
        for(int tamHeap = (tmp.length-1)/2; tamHeap >= 1; tamHeap--) {
            restaura(tmp, tamHeap, tmp.length - 1);
        }

        //Ordenação propriamente dita
        int tamHeap = tmp.length - 1;
        swap(tmp, 1, tamHeap--);
        while(tamHeap > 1) {
            restaura(tmp, 1, tamHeap);
            swap(tmp, 1, tamHeap--);
        }

        //Alterar o vetor para voltar à posição zero
        for(int i = 0; i < array.length; i++) {
            array[i] = tmp[i+1];
        }
    }
    private void restaura(T[] array, int i, int tamHeap){
	    int maior = i;
            int filho = getMaiorFilho(array, i, tamHeap);

	    if(this.comparador.compare(array[i], array[filho]) < 0)
		    maior = filho;
	    if (maior != i) {
	            swap(array, i, maior);
	            if (maior <= tamHeap/2)
	            	restaura(array, maior, tamHeap);
	    }
    }

    private int getMaiorFilho(T[] array, int i, int tamHeap) {
        int filho;
        if (2*i == tamHeap || this.comparador.compare(array[2*i], array[2*i+1]) > 0){
            filho = 2*i;
      	} else {
        	filho = 2*i + 1;
      	}
      	return filho;
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
        return Duration.between(inicio, termino).toMillis();
    }

}
