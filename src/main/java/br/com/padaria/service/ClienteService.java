package br.com.padaria.service;

import br.com.padaria.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteService implements CrudService<Cliente, Integer> {

    private final List<Cliente> listaClientes = new ArrayList<>();
    private int proximoIdSequencial = 1;

    public ClienteService() {
        save(new Cliente(null, "Ana Clara", "111.222.333-44", "(48) 98888-0001", 0));
        save(new Cliente(null, "Bruno Lima", "555.666.777-88", "(48) 98888-0002", 120));
        save(new Cliente(null, "Carlos Souza", "999.888.777-66", "(48) 98888-0003", 30));
    }

    @Override
    public List<Cliente> findAll() {
        return listaClientes;
    }

    @Override
    public Cliente findById(Integer idBuscado) {
        for (Cliente clienteAtual : listaClientes) {
            if (clienteAtual.getId() != null && clienteAtual.getId().equals(idBuscado)) {
                return clienteAtual;
            }
        }
        return null;
    }

    @Override
    public Cliente save(Cliente clienteParaSalvar) {
        if (clienteParaSalvar.getId() == null) {
            clienteParaSalvar.setId(proximoIdSequencial++);
            listaClientes.add(clienteParaSalvar);
            return clienteParaSalvar;
        }

        for (Cliente clienteExistente : listaClientes) {
            if (clienteExistente.getId().equals(clienteParaSalvar.getId())) {
                clienteExistente.setNome(clienteParaSalvar.getNome());
                clienteExistente.setCpf(clienteParaSalvar.getCpf());
                clienteExistente.setTelefone(clienteParaSalvar.getTelefone());
                clienteExistente.setPontos(clienteParaSalvar.getPontos());
                return clienteExistente;
            }
        }

        listaClientes.add(clienteParaSalvar);
        if (clienteParaSalvar.getId() >= proximoIdSequencial) {
            proximoIdSequencial = clienteParaSalvar.getId() + 1; //garantia para não ter erro
        }
        return clienteParaSalvar;
    }

    @Override
    public boolean delete(Integer idParaExcluir) {
        Cliente clienteParaRemover = null;
        for (Cliente clienteAtual : listaClientes) {
            if (clienteAtual.getId().equals(idParaExcluir)) {
                clienteParaRemover = clienteAtual;
                break;
            }
        }
        if (clienteParaRemover != null) {
            listaClientes.remove(clienteParaRemover);
            return true;
        }
        return false;
    }
}
