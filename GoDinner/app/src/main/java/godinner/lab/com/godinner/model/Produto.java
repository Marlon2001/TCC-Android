package godinner.lab.com.godinner.model;

public class Produto {

    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
    private Double desconto;
    private Integer vendidos;
    private String status;


    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Integer getVendidos() {
        return vendidos;
    }

    public void setVendidos(Integer vendidos) {
        this.vendidos = vendidos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
