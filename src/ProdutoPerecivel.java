import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProdutoPerecivel extends Produto {

    private static final double DESCONTO = 0.25;
    private static final int PRAZO_DESCONTO = 7;
    private LocalDate dataDeValidade;

    public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate dataDeValidade) {
        super(desc, precoCusto, margemLucro);

        if (dataDeValidade == null || dataDeValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de validade não pode ser anterior à data atual."
            );
        }

        this.dataDeValidade = dataDeValidade;
    }

    @Override
    public double valorDeVenda() {
        LocalDate hoje = LocalDate.now();

        if (dataDeValidade.isBefore(hoje)) {
            throw new IllegalStateException(
                "Não é possível vender um produto fora da validade."
            );
        }

        long diasDeDiferenca = ChronoUnit.DAYS.between(hoje, dataDeValidade);
        double precoFinal = precoCusto * (1.0 + margemLucro);

        if (diasDeDiferenca <= PRAZO_DESCONTO) {
            precoFinal *= (1.0 - DESCONTO);
        }

        return precoFinal;
    }

    @Override
    public String toString() {
        return super.toString() + ", DATA DE VALIDADE: " + dataDeValidade;
    }
}