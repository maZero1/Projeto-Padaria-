package br.com.padaria.model;

public class Produto {
    private Integer id;
    private String nome;
    private double preco;
    private int estoque;
    private boolean resgatavel;
    private int custoPontos;

    public Produto(Integer id, String nome, double preco, int estoque, boolean resgatavel, int custoPontos) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.resgatavel = resgatavel;
        this.custoPontos = custoPontos;
    }
    public Produto(){
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public boolean isResgatavel() {
        return resgatavel;
    }

    public void setResgatavel(boolean resgatavel) {
        this.resgatavel = resgatavel;
    }

    public int getCustoPontos() {
        return custoPontos;
    }

    public void setCustoPontos(int custoPontos) {
        this.custoPontos = custoPontos;
    }

    @Override
    public String toString() {
        return String.format(
            "%d - %s (R$ %.2f | estq: %d | %s | %d pts)",
            id, nome, preco, estoque,
            (resgatavel ? "resgatável" : "normal"), custoPontos
        );
    }
}


