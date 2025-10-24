/*package br.com.padaria.dao;

import java.sql.*;
import java.util.*;
import Connection.ConnectionFactory;
import br.com.padaria.model.Venda;
import br.com.padaria.model.Cliente;

public class VendaDAO {

    public void insert(Venda venda) {
        String sql = "INSERT INTO venda (id_cliente, data_venda, valor_total, forma_pagamento, observacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, venda.getCliente().getIdCliente());
            stmt.setTimestamp(2, new Timestamp(venda.getDataVenda().getTime()));
            stmt.setDouble(3, venda.getValorTotal());
            stmt.setString(4, venda.getFormaPagamento());
            stmt.setString(5, venda.getObservacao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir venda: " + e.getMessage());
        }
    }

    public List<Venda> getAll() {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda ORDER BY data_venda DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Venda v = new Venda();
                v.setIdVenda(rs.getLong("id_venda"));
                v.setDataVenda(rs.getTimestamp("data_venda"));
                v.setValorTotal(rs.getDouble("valor_total"));
                v.setFormaPagamento(rs.getString("forma_pagamento"));
                v.setObservacao(rs.getString("observacao"));

                Cliente c = new Cliente();
                c.setIdCliente(rs.getLong("id_cliente"));
                v.setCliente(c);

                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        }
        return lista;
    }

    public Venda getById(long id) {
        String sql = "SELECT * FROM venda WHERE id_venda=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Venda v = new Venda();
                v.setIdVenda(rs.getLong("id_venda"));
                v.setDataVenda(rs.getTimestamp("data_venda"));
                v.setValorTotal(rs.getDouble("valor_total"));
                v.setFormaPagamento(rs.getString("forma_pagamento"));
                v.setObservacao(rs.getString("observacao"));

                Cliente c = new Cliente();
                c.setIdCliente(rs.getLong("id_cliente"));
                v.setCliente(c);

                return v;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda: " + e.getMessage());
        }
        return null;
    }

    public void update(Venda venda) {
        String sql = "UPDATE venda SET id_cliente=?, data_venda=?, valor_total=?, forma_pagamento=?, observacao=? WHERE id_venda=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, venda.getCliente().getIdCliente());
            stmt.setTimestamp(2, new Timestamp(venda.getDataVenda().getTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar venda: " + e.getMessage());
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM venda WHERE id_venda=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir venda: " + e.getMessage());
        }
    }
}*/