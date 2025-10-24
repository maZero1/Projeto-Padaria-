package br.com.padaria.model;

public class Cliente {
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private int pontos;

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String cpf, String telefone, int pontos) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.pontos = pontos;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return String.format("%d - %s (pontos: %d)", id, nome, pontos);
    }
}


