package br.com.padaria.view;

import br.com.padaria.controller.AppController;
import br.com.padaria.model.Cliente;
import br.com.padaria.model.Produto;
import java.awt.BorderLayout;
import javax.swing.*;

public class ResgatePanel extends JPanel {

    private final AppController app; 
    private final JComboBox<Cliente> cbCliente = new JComboBox<>(); 
    private final JComboBox<Produto> cbProduto = new JComboBox<>();  
    
    public ResgatePanel(AppController app) { 
        this.app = app; 
        setLayout(new BorderLayout()); 
        
        JPanel top = new JPanel(); 
        top.add(new JLabel("Cliente:")); 
        top.add(cbCliente); 
        JButton bLoad = new JButton("Atualizar"); 
        bLoad.addActionListener(e -> carregarCombos()); 
        top.add(bLoad);     
        
        JPanel center = new JPanel(); 
        center.add(new JLabel("Produto (resgatável):")); 
        center.add(cbProduto); 
        JButton bResg = new JButton("Resgatar"); 
        bResg.addActionListener(e -> resgatar()); 
        center.add(bResg); 
        
        add(top, BorderLayout.NORTH); 
        add(center, BorderLayout.CENTER); 
        carregarCombos(); 
    } 
    public void carregarCombos() { 
        cbCliente.removeAllItems(); 
        for (Cliente c : app.clientes().findAll()) 
            cbCliente.addItem(c); 
        
        Cliente sel = (Cliente) cbCliente.getSelectedItem();  
        
        cbProduto.removeAllItems(); 
        for (Produto p : app.produtos().findAll()) { 
            if (p.isResgatavel()) cbProduto.addItem(p); 
        } 
    } 
    private void resgatar() { 
        Cliente c = (Cliente) cbCliente.getSelectedItem(); 
        Produto p = (Produto) cbProduto.getSelectedItem(); 
        if (c == null || p == null) { 
            JOptionPane.showMessageDialog(this, "Selecione cliente e produto."); 
            return; 
        } 
        try { 
            Cliente clienteAtualizado = app.clientes().findById(c.getId());
            app.vendas().resgatarProduto(clienteAtualizado, p); 
            JOptionPane.showMessageDialog(this, "Resgate efetuado: " + p.getNome()); 
            carregarCombos(); 
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, ex.getMessage()); 
        } 
    } 
}

