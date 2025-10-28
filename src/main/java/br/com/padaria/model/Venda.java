package br.com.padaria.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {

    private Integer id;
    private Cliente cliente;
    private final List<ItemVenda> itens = new ArrayList<>();
    private boolean paga;
    private LocalDateTime dataHora = LocalDateTime.now();

    public Venda(Integer id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void addItem(ItemVenda item) {
        itens.add(item);
    }

    public double getTotal() {
    double total = 0.0;
    for (ItemVenda item : itens) {
        total += item.getSubtotal();
    }
    return total;
    }

}
