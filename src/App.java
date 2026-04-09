import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static Scanner teclado = new Scanner(System.in);

    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    static String exibirVetor(Integer[] vetor) {
        return Arrays.toString(vetor);
    }

    public static void main(String[] args) {
        int tam = 20;
        Integer[] vetorOriginal = gerarVetorObjetos(tam);
        IOrdenador<Integer> ordenador = null;

        System.out.println("--- SISTEMA DE ORDENAÇÃO ---");
        System.out.println("Vetor original: " + exibirVetor(vetorOriginal));
        System.out.println("\nEscolha o método de ordenação que deseja:");
        System.out.println("1. BubbleSort");
        System.out.println("2. InsertionSort");
        System.out.println("3. SelectionSort");
        System.out.println("4. MergeSort");
        System.out.print("Opção: ");
        
        int opcao = teclado.nextInt();

        switch (opcao) {
            case 1:
                ordenador = new BubbleSort<>();
                break;
            case 2:
                ordenador = new InsertionSort<>();
                break;
            case 3:
                ordenador = new SelectionSort<>();
                break;
            case 4:
                ordenador = new MergeSort<>();
                break;
            default:
                System.out.println("Opção inválida! Encerrando.");
                return;
        }

        // Execução polimórfica
        Integer[] vetorOrdenado = ordenador.ordenar(vetorOriginal);

        // Exibição dos resultados
        System.out.println("\n--- RESULTADO: ---");
        System.out.println("Vetor ordenado: " + exibirVetor(vetorOrdenado));
        
        teclado.close();
    }
}