
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;
    static Produto[] copiaProdutosOrdenadosPorCodigo;
    static Produto[] copiaProdutosOrdenadosPorDescricao;
    static Produto[] copiaProdutosOrdenadosPorPorcentagemDesconto;
    // #region utilidades
    static Scanner teclado;
    

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("5 - Quicksort");
        System.out.println("6 - Heapsort");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão");
        System.out.println("2 - Por código");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }

    public static String lerTexto(String mensagem) {
        Scanner teclado = new Scanner(System.in);
        System.out.print(mensagem + ": ");
        return teclado.nextLine();
    }

    static Produto buscaBinaria(Produto[] array, Comparator<Produto> comparador, Produto procurado) {
        int min = 0;
        int max = quantProdutos - 1;

        while (min <= max) {
            int meio = (min + max) / 2;

            int cmp = comparador.compare(array[meio], procurado);

            if (cmp == 0) {
                return array[meio];
            } else if (cmp < 0) {
                min = meio + 1;
            } else {
                max = meio - 1;
            }
        }
        return null;
    }
    static Produto localizarProduto() {
        cabecalho();
        System.out.println("Deseja localizar o produto por:");
        System.out.println("1 - Código");
        System.out.println("2 - Descrição");
        
        int opcao = lerNumero("Opção", Integer.class);

        if (opcao == 1) {
            int codigo = lerNumero("Digite o código do produto", Integer.class);
            Produto procurado = new ProdutoNaoPerecivel("tmp", 1.0);
            procurado.setIdProduto(codigo);
        
            return buscaBinaria(
                copiaProdutosOrdenadosPorCodigo,
                new ComparadorPorCodigo(),
                procurado
            );

        } else if (opcao == 2) {
            String descricao = lerTexto("Digite a descrição do produto");
            Produto template = new ProdutoNaoPerecivel(descricao, 1.0);

            return buscaBinaria(
                copiaProdutosOrdenadosPorDescricao,
                new ComparadorPorDescricao(),
                template
            );
        }

        System.out.println("Opção inválida!");
        return null;
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){
        cabecalho();
        
        // Passo 1: escolhe o algoritmo
        int opcao = exibirMenuOrdenadores();
        switch (opcao) {
            case 1 -> ordenador = new Bubblesort<>();
            case 2 -> ordenador = new InsertSort<>();
            case 3 -> ordenador = new SelectionSort<>();
            case 4 -> ordenador = new Mergesort<>();
            case 5 -> ordenador = new QuickSort<>();
            case 6 -> ordenador = new HeapSort<>();
            default -> { return; }
        }

        // Passo 2: escolhe o critério
        int opcaoComp = exibirMenuComparadores();
        Comparator<Produto> comparador;
        switch (opcaoComp) {
            case 2 -> comparador = new ComparadorPorCodigo();
            default -> comparador = new ComparadorPorDescricao();
        }

        // Passo 3: ordena e exibe
        Produto[] produtosOrdenados = ordenador.ordenar(produtos, comparador);
        
        cabecalho();
        for (Produto p : produtosOrdenados) {
            System.out.println(p);
        }
       
        // Passo 4: pergunta se quer sobrescrever
        verificarSubstituicao(produtos, produtosOrdenados);
    }

    static void embaralharProdutos(){
        Collections.shuffle(Arrays.asList(produtos));
    }

    static Produto buscaBinariaPorCodigo(int codigo) {
        int inicio = 0;
        int fim = quantProdutos - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int codigoMeio = copiaProdutosOrdenadosPorCodigo[meio].hashCode();

            if (codigoMeio == codigo)
                return copiaProdutosOrdenadosPorCodigo[meio];
            else if (codigoMeio < codigo)
                inicio = meio + 1;
            else
                fim = meio - 1;
        }
        return null;
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        cabecalho();
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)?");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S"))
            dadosOriginais = Arrays.copyOf(copiaDados, copiaDados.length);
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    static void imprimirLista(String titulo, Produto[] lista) {
        System.out.println("\n=== " + titulo + " ===");
        for (Produto p : lista) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);

        // Cria as duas cópias ordenadas
        IOrdenador<Produto> ord = new Mergesort<>(); // melhor algoritmo pois sua ordem de complexidade é O(n log n) no pior caso, enquanto são O(n²)
        copiaProdutosOrdenadosPorCodigo = ord.ordenar(produtos, new ComparadorPorCodigo());
        copiaProdutosOrdenadosPorDescricao = ord.ordenar(produtos, new ComparadorPorDescricao());
        copiaProdutosOrdenadosPorPorcentagemDesconto = ord.ordenar(produtos, new ComparadorPorPorcentagemDesconto());

        // imprimirLista("Ordenados por Código", copiaProdutosOrdenadosPorCodigo);
        // imprimirLista("Ordenados por Descrição", copiaProdutosOrdenadosPorDescricao);
        // imprimirLista("Ordenados por Desconto", copiaProdutosOrdenadosPorPorcentagemDesconto);

        embaralharProdutos();

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);
        teclado.close();
    }                        
}
