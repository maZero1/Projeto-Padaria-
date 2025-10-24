package br.com.padaria.service;

import br.com.padaria.model.Cliente;
import br.com.padaria.model.ItemVenda;
import br.com.padaria.model.Produto;
import br.com.padaria.model.Venda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VendaService {

    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final List<Venda> vendas = new ArrayList<>();
    private final AtomicInteger seq = new AtomicInteger(1);
    public static final int PONTOS_A_CADA_REAIS = 10;

    public VendaService(ProdutoService ps, ClienteService cs) {
        this.produtoService = ps;
        this.clienteService = cs;
    }

    public Venda criarVenda(Cliente cliente) {
        Venda v = new Venda(seq.getAndIncrement(), cliente);
        vendas.add(v);
        return v;
    }

    public void adicionarItem(Venda v, Produto p, int qtd) {
        if (p.getEstoque() < qtd) {
            throw new IllegalArgumentException("Estoque insuficiente para " + p.getNome());
        }
        ItemVenda iv = new ItemVenda(null, p, qtd, p.getPreco());
        v.addItem(iv);
    }

    public void finalizarPagamento(Venda v) {
        if (v.getItens().isEmpty()) throw new IllegalStateException("Venda sem itens.");
        for (ItemVenda iv : v.getItens()) {
            Produto p = iv.getProduto();
            p.setEstoque(p.getEstoque() - iv.getQuantidade());
            produtoService.save(p);
        }
        v.setPaga(true);

        int pontos = (int)Math.floor(v.getTotal() / PONTOS_A_CADA_REAIS);
        if (pontos > 0) {
            Cliente c = v.getCliente();
            c.setPontos(c.getPontos() + pontos);
            clienteService.save(c);
        }
    }

    public List<Venda> listarVendas() {
        return Collections.unmodifiableList(vendas);
    }

    public void resgatarProduto(Cliente c, Produto p) {
        if (!p.isResgatavel()) throw new IllegalArgumentException("Produto não é resgatável.");
        if (p.getEstoque() <= 0) throw new IllegalArgumentException("Sem estoque para resgate.");
        if (c.getPontos() < p.getCustoPontos()) throw new IllegalArgumentException("Pontos insuficientes.");

        c.setPontos(c.getPontos() - p.getCustoPontos());
        clienteService.save(c);

        p.setEstoque(p.getEstoque() - 1);
        produtoService.save(p);
    }
}
