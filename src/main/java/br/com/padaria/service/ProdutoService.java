package br.com.padaria.service;

import br.com.padaria.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService implements CrudService<Produto, Integer> {

    private final List<Produto> listaProdutos = new ArrayList<>();
    private int proximoIdSequencial = 1;

    public ProdutoService() {
        save(new Produto(null, "Pão Francês", 1.00, 200, true, 10));
        save(new Produto(null, "Café 500g", 25.90, 30, false, 0));
        save(new Produto(null, "Sonho", 6.50, 50, true, 45));
        save(new Produto(null, "Leite 1L", 6.10, 40, false, 0));
    }

    @Override
    public List<Produto> findAll() {
        return listaProdutos;
    }

    @Override
    public Produto findById(Integer idBuscado) {
        for (Produto produtoAtual : listaProdutos) {
            if (produtoAtual.getId() != null && produtoAtual.getId().equals(idBuscado)) {
                return produtoAtual;
            }
        }
        return null;
    }

    @Override
    public Produto save(Produto produtoParaSalvar) {
        if (produtoParaSalvar.getId() == null) {
            produtoParaSalvar.setId(proximoIdSequencial++);
            listaProdutos.add(produtoParaSalvar);
            return produtoParaSalvar;
        }

        for (Produto produtoExistente : listaProdutos) {
            if (produtoExistente.getId().equals(produtoParaSalvar.getId())) {
                produtoExistente.setNome(produtoParaSalvar.getNome());
                produtoExistente.setPreco(produtoParaSalvar.getPreco());
                produtoExistente.setEstoque(produtoParaSalvar.getEstoque());
                produtoExistente.setResgatavel(produtoParaSalvar.isResgatavel());
                produtoExistente.setCustoPontos(produtoParaSalvar.getCustoPontos());
                return produtoExistente;
            }
        }

        listaProdutos.add(produtoParaSalvar);
        if (produtoParaSalvar.getId() >= proximoIdSequencial) {
            proximoIdSequencial = produtoParaSalvar.getId() + 1; // garantia para não ter erro
        }
        return produtoParaSalvar;
    }

    @Override
    public boolean delete(Integer idParaExcluir) {
        Produto produtoParaRemover = null;
        for (Produto produtoAtual : listaProdutos) {
            if (produtoAtual.getId().equals(idParaExcluir)) {
                produtoParaRemover = produtoAtual;
                break;
            }
        }
        if (produtoParaRemover != null) {
            listaProdutos.remove(produtoParaRemover);
            return true;
        }
        return false;
    }
}
