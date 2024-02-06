package clinina.vet.api.produto;


import clinina.vet.api.Idade.Idade;
import clinina.vet.api.categoria.Categoria;
import clinina.vet.api.fornecedor.Fornecedor;
import clinina.vet.api.imagens.Imagem;
import clinina.vet.api.informacoes.Informacao;
import clinina.vet.api.sabor.Sabor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table(name = "produtos")
@Entity(name = "Produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoDeBarras;
    @ManyToOne
    private Categoria categoria;
    private String produto; //mudar para nome
    //private String imagens;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true) //xxx
    private List<Imagem> imagens = new ArrayList<>();                                   //xxx
    @ManyToOne
    private Sabor sabor;
    @ManyToOne
    private Idade idade;
    private double preco;
    private String peso;
    private double desconto;
    private String animal;
    private int castrado; //talvez voltar a colocar int
    private String porte;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Informacao> informacao = new ArrayList<>();
    @ManyToOne
    private Fornecedor fornecedor;
    private int estoque;
    private String imagemP;

    public Produto(DadosCadastroProduto dados){
        this.codigoDeBarras = dados.codigoDeBarras();

        //Categoria categoria = new Categoria();
        //categoria.setNome(dados.categoria());
        //this.categoria = categoria;
        this.categoria = dados.categoria();

        this.produto = dados.produto();
        //this.imagens = dados.imagens();
        this.sabor = dados.sabor();
        this.idade = dados.idade();
        this.preco = dados.preco();
        this.peso = dados.peso();
        this.desconto = dados.desconto();
        this.animal = dados.animal();
        this.castrado = dados.castrado();
        //this.porte = dados.porte().toString();
        //this.informacao = dados.informacao();
        this.fornecedor = dados.fornecedor();
        this.estoque = dados.estoque();
        this.imagemP = dados.imagemP();
    }

}
