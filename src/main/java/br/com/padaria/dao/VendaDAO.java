package br.com.padaria.dao;

import java.sql.*;
import java.util.*;
import Connection.ConnectionFactory;
import br.com.padaria.model.Cliente;
import br.com.padaria.model.Venda;
import java.time.LocalDateTime;

public class VendaDAO {
    
    public void insert(Venda venda) {
        String sql = "INSERT INTO venda (cliente_id, total, paga, data_hora) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, venda.getCliente().getId());
            stmt.setDouble(2, venda.getTotal());
            stmt.setInt(3, venda.isPaga() ? 1 : 0);
            stmt.setString(4, venda.getDataHora().toString());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir venda: " + e.getMessage());
        }
    }
    
    public List<Venda> getAll() {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda ORDER BY id DESC";
        
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            ClienteDAO clienteDAO = new ClienteDAO();
            
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteDAO.getById(clienteId);
                
                Venda v = new Venda(id, cliente);
                v.setPaga(rs.getInt("paga") == 1);
                v.setDataHora(LocalDateTime.parse(rs.getString("data_hora")));
                lista.add(v);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        }
        
        return lista;
    }
    
    public Venda getById(int id) {
        String sql = "SELECT * FROM venda WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Integer clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteDAO.getById(clienteId);
                
                Venda v = new Venda(rs.getInt("id"), cliente);
                v.setPaga(rs.getInt("paga") == 1);
                v.setDataHora(LocalDateTime.parse(rs.getString("data_hora")));
                return v;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda: " + e.getMessage());
        }
        
        return null;
    }
    
    public void update(Venda venda) {
        String sql = "UPDATE venda SET cliente_id = ?, total = ?, paga = ?, data_hora = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, venda.getCliente().getId());
            stmt.setDouble(2, venda.getTotal());
            stmt.setInt(3, venda.isPaga() ? 1 : 0);
            stmt.setString(4, venda.getDataHora().toString());
            stmt.setInt(5, venda.getId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar venda: " + e.getMessage());
        }
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM venda WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir venda: " + e.getMessage());
        }
    }
}