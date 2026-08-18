public class ProdutoNaoPerecivel extends Produto {

    public ProdutoNaoPerecivel(String desc, double precoCusto) {
        super(desc, precoCusto);
    }

    public ProdutoNaoPerecivel(String desc, double precoCusto, double margemLucro) {
        super(desc, precoCusto, margemLucro);
    }

    @Override
    public double valorDeVenda() {
        return precoCusto * (1.0 + margemLucro);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}