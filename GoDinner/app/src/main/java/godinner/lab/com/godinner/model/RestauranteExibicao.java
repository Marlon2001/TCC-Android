package godinner.lab.com.godinner.model;

import java.io.Serializable;

public class RestauranteExibicao extends Restaurante implements Serializable {

    private String distancia;
    private String tempoEntrega;
    private Double precoEntrega;
    private String nota;

    public Double getPrecoEntrega() {
        return precoEntrega;
    }

    public void setPrecoEntrega(Double precoEntrega) {
        this.precoEntrega = precoEntrega;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTempoEntrega() {
        return tempoEntrega;
    }

    public void setTempoEntrega(String tempoEntrega) {
        this.tempoEntrega = tempoEntrega;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
