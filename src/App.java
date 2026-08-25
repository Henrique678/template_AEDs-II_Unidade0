import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    /** Quantidade máxima de produtos que podem ser armazenados no vetor */
    static final int MAX_NOVOS_PRODUTOS = 10;

    /** Nome do arquivo de dados. O arquivo deve estar localizado na raiz do projeto */
    static String nomeArquivoDados;
    
    /** Scanner para leitura de dados do teclado */
    static Scanner teclado;

    /** Vetor de produtos cadastrados */
    static Produto[] produtosCadastrados;

    /** Quantidade de produtos cadastrados atualmente no vetor */
    static int quantosProdutos = 0;

    /** Gera um efeito de pausa na CLI. Espera por um enter para continuar */
    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    /** Cabeçalho principal da CLI do sistema */
    static void cabecalho() {
        System.out.println("AEDs II COMÉRCIO DE COISINHAS");
        System.out.println("=============================");
    }
    
    /** Imprime o menu principal, lê a opção do usuário e a retorna (int).
     * @return Um inteiro com a opção do usuário.
    */
    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar e imprimir os dados de um produto");
        System.out.println("3 - Cadastrar novo produto");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }
    
    /**
     * Lê os dados de um arquivo-texto e retorna um vetor de produtos. Arquivo-texto no formato
     * N (quantidade de produtos) <br/>
     * tipo;descrição;preçoDeCusto;margemDeLucro;[dataDeValidade] <br/>
     * Deve haver uma linha para cada um dos produtos. Retorna um vetor vazio em caso de problemas com o arquivo.
     * @param nomeArquivoDados Nome do arquivo de dados a ser aberto.
     * @return Um vetor com os produtos carregados, ou vazio em caso de problemas de leitura.
     */
	static Produto[] lerProdutos(String nomeArquivoDados) {
        try {
            File arquivo = new File(nomeArquivoDados);
            Scanner leitor = new Scanner(arquivo, "UTF-8");

            int quantidade = Integer.parseInt(leitor.nextLine().trim());
            Produto[] produtos = new Produto[quantidade];

            for (int i = 0; i < quantidade; i++) {
                String linha = leitor.nextLine();
                linha = linha.replace(",", ".");
                produtos[i] = Produto.criarDoTexto(linha);
            }

            leitor.close();
            quantosProdutos = quantidade;

            Produto[] vetorExpandido = new Produto[quantidade + MAX_NOVOS_PRODUTOS];

            for (int i = 0; i < quantidade; i++) {
                vetorExpandido[i] = produtos[i];
            }

            return vetorExpandido;

        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo de produtos.");
            quantosProdutos = 0;
            return new Produto[MAX_NOVOS_PRODUTOS];
        }
    }
    
    /** Localiza um produto no vetor de produtos cadastrados, a partir do nome de produto informado pelo usuário, e imprime seus dados. 
     *  A busca não é sensível ao caso. Em caso de não encontrar o produto, imprime uma mensagem padrão */
	static void localizarProdutos() {
        System.out.print("Digite o nome do produto: ");
        String nome = teclado.nextLine();

        boolean encontrado = false;

        for (int i = 0; i < quantosProdutos; i++) {
            if (produtosCadastrados[i] != null) {
                String[] dados = produtosCadastrados[i].gerarDadosTexto().split(";");
                String descricao = dados[1];

                if (descricao.equalsIgnoreCase(nome)) {
                    System.out.println(produtosCadastrados[i]);
                    encontrado = true;
                    break;
                }
            }
        }

        if (!encontrado) {
            System.out.println("Produto não encontrado.");
        }
    }
    
    /**
     * Salva os dados dos produtos cadastrados no arquivo csv informado. Sobrescreve todo o conteúdo do arquivo.
     * @param nomeArquivo Nome do arquivo a ser gravado.
     */
    public static void salvarProdutos(String nomeArquivo) {
        try {
            PrintWriter escritor = new PrintWriter(nomeArquivo, "UTF-8");

            escritor.println(quantosProdutos);

            for (int i = 0; i < quantosProdutos; i++) {
                if (produtosCadastrados[i] != null) {
                    String linha = produtosCadastrados[i].gerarDadosTexto();

                    linha = linha.replace(",", ".");

                    escritor.println(linha);
                }
            }

            escritor.close();

            System.out.println("Produtos salvos com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao salvar os produtos.");
        }
    }
    
    /** Lista todos os produtos cadastrados, numerados, um por linha */
    static void listarTodosOsProdutos() {
        if (quantosProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < quantosProdutos; i++) {
            if (produtosCadastrados[i] != null) {
                System.out.println((i + 1) + " - " + produtosCadastrados[i]
                );
            }
        }
    }
    
    /**
     * Rotina para cadastro de um novo produto: pergunta ao usuário o tipo do produto, lê os dados correspondentes,
     * cria o objeto adequado de acordo com o tipo, inclui o produto no vetor.
     */
    static void cadastrarProduto() {
        if (quantosProdutos >= produtosCadastrados.length) {
            System.out.println("Não há espaço para cadastrar novos produtos.");
            return;
        }

        System.out.println("1 - Produto não perecível");
        System.out.println("2 - Produto perecível");
        System.out.print("Digite o tipo do produto: ");

        int tipo;

        try {
            tipo = Integer.parseInt(teclado.nextLine());
        } catch (Exception e) {
            System.out.println("Tipo inválido.");
            return;
        }

        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo de produto inválido.");
            return;
        }

        System.out.print("Digite a descrição: ");
        String descricao = teclado.nextLine();

        double precoCusto;
        double margemLucro;

        try {
            System.out.print("Digite o preço de custo: ");
            precoCusto = Double.parseDouble(teclado.nextLine().replace(",", "."));

            System.out.print("Digite a margem de lucro: ");
            margemLucro = Double.parseDouble(teclado.nextLine().replace(",", "."));

        } catch (Exception e) {
            System.out.println("Preço ou margem de lucro inválidos.");
            return;
        }

        try {
            if (tipo == 1) {
                produtosCadastrados[quantosProdutos] =
                        new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
            } else {
                System.out.print("Digite a data de validade (dd/MM/yyyy): ");

                String textoData = teclado.nextLine();
                DateTimeFormatter formato =DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate data =LocalDate.parse(textoData, formato);

                produtosCadastrados[quantosProdutos] = new ProdutoPerecivel(descricao, precoCusto, margemLucro, data);
            }
            quantosProdutos++;

            System.out.println("Produto cadastrado com sucesso!");

        } catch (Exception e) {
			System.out.println("Não foi possível cadastrar o produto: " + e.getMessage());
        }
    } 
    
	public static void main(String[] args) {
		teclado = new Scanner(System.in, Charset.forName("UTF-8"));
        nomeArquivoDados = "dadosProdutos.csv";
        produtosCadastrados = lerProdutos(nomeArquivoDados);
        
        int opcao = -1;
      
        do{
            opcao = menu();
            switch (opcao) {
                case 1 -> listarTodosOsProdutos();
                case 2 -> localizarProdutos();
                case 3 -> cadastrarProduto();
            }
            pausa();
        }while(opcao != 0);       

        salvarProdutos(nomeArquivoDados);
        teclado.close();    
    }
}
