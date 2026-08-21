import java.text.DecimalFormat;

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

     /**
      * Gera uma linha de texto a partir dos dados do produto.
      * Preço e margem de lucro são formatados com 2 casas decimais.
      * @return Uma string no formato "1;descrição;preçoDeCusto;margemDeLucro"
      */
     @Override
     public String gerarDadosTexto() {
          DecimalFormat df = new DecimalFormat("0.00");
          String texto = "1" + ";" + descricao + ";" + df.format(precoCusto) + ";" + margemLucro;
          return texto;
     }
}