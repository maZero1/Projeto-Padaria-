package br.com.padaria.service;

import br.com.padaria.model.Produto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdutoService implements CrudService<Produto, Integer> {

    private final List<Produto> storage = new ArrayList<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    public ProdutoService() {
        // seeds
        save(new Produto(null, "Pão Francês", 1.00, 200, true, 10));
        save(new Produto(null, "Café 500g", 25.90, 30, false, 0));
        save(new Produto(null, "Sonho", 6.50, 50, true, 45));
        save(new Produto(null, "Leite 1L", 6.10, 40, false, 0));
    }

    @Override
    public List<Produto> findAll() { return Collections.unmodifiableList(storage); }

    @Override
    public Produto findById(Integer id) {
        Optional<Produto> opt = storage.stream().filter(p -> p.getId().equals(id)).findFirst();
        return opt.orElse(null);
    }

    @Override
    public Produto save(Produto p) {
        if (p.getId() == null) {
            p.setId(seq.getAndIncrement());
            storage.add(p);
        } else {
            for (int i=0;i<storage.size();i++) {
                if (storage.get(i).getId().equals(p.getId())) {
                    storage.set(i, p);
                    return p;
                }
            }
            storage.add(p);
        }
        return p;
    }

    @Override
    public boolean delete(Integer id) {
        return storage.removeIf(p -> p.getId().equals(id));
    }
}
