package test;

import br.com.padaria.dao.ClienteDAO;
import br.com.padaria.model.Cliente;

public class Testconnection {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();

        Cliente novo = new Cliente(011, "Roberto Heinz", "12345678901", "48999998888", 18);
        dao.insert(novo);

        System.out.println("\n📋 Clientes cadastrados no banco:");
        dao.getAll().forEach(c ->
            System.out.println(c.getId() + " | " + c.getNome() + " | " + c.getCpf() + " | " + c.getTelefone())
        );
    }
}
