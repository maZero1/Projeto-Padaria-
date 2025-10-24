package br.com.padaria.controller;

import br.com.padaria.service.ClienteService;
import br.com.padaria.service.ProdutoService;
import br.com.padaria.service.VendaService;

public class AppController {
    private final ProdutoService produtoService = new ProdutoService();
    private final ClienteService clienteService = new ClienteService();
    private final VendaService vendaService = new VendaService(produtoService, clienteService);

    public ProdutoService produtos() { return produtoService; }
    public ClienteService clientes() { return clienteService; }
    public VendaService vendas() { return vendaService; }
}
