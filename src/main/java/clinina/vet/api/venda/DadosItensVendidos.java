package clinina.vet.api.venda;

import clinina.vet.api.fornecedor.Fornecedor;

import java.util.Date;

public interface DadosItensVendidos {
    //aqui não é um record, pra fazer o sql com campos especificos e join
    //só deu pra fazer usando interface e colocando metodos com os atributos da tablea
    //a tabela tinha id, imagemP, produto, quantidade, preco_unitario, preco_total, data
    Long getId();
    String getImagemP();
    String getProduto();
    Integer getQuantidade();
    Double getPrecoUnitario();
    Double getPrecoTotal();
    String getPeso();
    Date getData();
    String getPagamento();
    Double getDesconto();
}
