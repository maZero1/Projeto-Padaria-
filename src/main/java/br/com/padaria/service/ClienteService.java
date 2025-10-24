package br.com.padaria.service;

import br.com.padaria.model.Cliente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteService implements CrudService<Cliente, Integer> {

    private final List<Cliente> storage = new ArrayList<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    public ClienteService() {
        save(new Cliente(null, "Ana Clara", "111.222.333-44", "(48) 98888-0001", 0));
        save(new Cliente(null, "Bruno Lima", "555.666.777-88", "(48) 98888-0002", 120));
        save(new Cliente(null, "Carlos Souza", "999.888.777-66", "(48) 98888-0003", 30));
    }

    @Override
    public List<Cliente> findAll() { return Collections.unmodifiableList(storage); }

    @Override
    public Cliente findById(Integer id) {
        Optional<Cliente> opt = storage.stream().filter(c -> c.getId().equals(id)).findFirst();
        return opt.orElse(null);
    }

    @Override
    public Cliente save(Cliente c) {
        if (c.getId() == null) {
            c.setId(seq.getAndIncrement());
            storage.add(c);
        } else {
            for (int i=0;i<storage.size();i++) {
                if (storage.get(i).getId().equals(c.getId())) {
                    Cliente existente = storage.get(i);
                    existente.setNome(c.getNome());
                    existente.setCpf(c.getCpf());
                    existente.setTelefone(c.getTelefone());
                    existente.setPontos(c.getPontos());
                return existente;
                }
            }
            storage.add(c);
        }
        return c;
    }

    @Override
    public boolean delete(Integer id) {
        return storage.removeIf(c -> c.getId().equals(id));
    }
}
