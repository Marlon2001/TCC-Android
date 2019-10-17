package godinner.lab.com.godinner.model;

public class SacolaPedido {

    private Integer idSacola;
    private Integer idRestaurante;
    private Double valorEntrega;
    private Double valorTotalPedido;

    public Integer getIdSacola() {
        return idSacola;
    }

    public void setIdSacola(Integer idSacola) {
        this.idSacola = idSacola;
    }

    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Integer getIdRestaurante() {
        return idRestaurante;
    }

    public Double getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(Double valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public Double getValorTotalPedido() {
        return valorTotalPedido;
    }

    public void setValorTotalPedido(Double valorTotalPedido) {
        this.valorTotalPedido = valorTotalPedido;
    }
}
