import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {

	public static void main(String[] args) {
		menu();
	}

	private static void menu() {
		int opcao;
		do {
			IO.println("\n===== MENU =====");
			IO.println("1 - Simular compra");
			IO.println("0 - Sair");

			opcao = Integer.parseInt(IO.readln("Escolha uma opção: "));
			switch (opcao) {
				case 1:
					simularCompra();
					break;
				case 0:
					IO.println("Programa encerrado.");
					break;
				default:
					IO.println("Opção inválida.");
			}
		} while (opcao != 0);
	}

	private static void simularCompra() {
		String tipo = IO.readln("\nTipo do produto (1 - Não perecível / 2 - Perecível): ");
		switch (tipo) {
			case "1":
				simularCompraNaoPerecivel();
				break;
			case "2":
				simularCompraPerecivel();
				break;
			default:
				IO.println("Tipo de produto inválido.");
		}
	}

	private static void simularCompraNaoPerecivel() {
		try {
			String descricao = IO.readln("Descrição: ");
			double precoCusto = Double.parseDouble(IO.readln("Preço de custo: "));
			double margemLucro = Double.parseDouble(IO.readln("Margem de lucro: "));

			Produto produto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);

			IO.println("\nProduto selecionado:");
			IO.println(produto.toString());

			String confirmar = IO.readln("Deseja realizar a compra? (S/N): ");

			if (confirmar.equalsIgnoreCase("S")) {
				double valor = produto.valorDeVenda();
				IO.println("\nCompra realizada com sucesso!");
				IO.println("Valor da compra: R$ " + valor);
			} else {
				IO.println("Compra cancelada.");
			}

		} catch (IllegalArgumentException | IllegalStateException e) {
			IO.println("Erro: " + e.getMessage());
		}
	}

	private static void simularCompraPerecivel() {
		try {
			String descricao = IO.readln("Descrição: ");
			double precoCusto = Double.parseDouble(IO.readln("Preço de custo: "));
			double margemLucro = Double.parseDouble(IO.readln("Margem de lucro: "));
			String data = IO.readln("Data de validade (dd/MM/yyyy): ");

			LocalDate dataDeValidade = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			Produto produto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataDeValidade);

			IO.println("\nProduto selecionado:");
			IO.println(produto.toString());
			
			String confirmar = IO.readln( "Deseja realizar a compra? (S/N): ");

			if (confirmar.equalsIgnoreCase("S")) {
				double valor = produto.valorDeVenda();
				IO.println("\nCompra realizada com sucesso!");
				IO.println("Valor da compra: R$ " + valor);
			} else {
				IO.println("Compra cancelada.");
			}

		} catch (IllegalArgumentException | IllegalStateException e) {
			IO.println("Erro: " + e.getMessage());
		}
	}
}