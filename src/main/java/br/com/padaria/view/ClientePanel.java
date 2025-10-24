package br.com.padaria.view;

import br.com.padaria.controller.AppController;
import br.com.padaria.model.Cliente;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClientePanel extends JPanel {

    private final AppController app;
    private final VendaPanel vendaPanel; // 🔗 referência para o painel de vendas

    private final DefaultTableModel model = new DefaultTableModel(
        new Object[] {"ID", "Nome", "CPF", "Telefone", "Pontos"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);

    public ClientePanel(AppController app, VendaPanel vendaPanel) {
        this.app = app;
        this.vendaPanel = vendaPanel;

        setLayout(new BorderLayout());

        JToolBar bar = new JToolBar();
        JButton bAdd = new JButton("Adicionar");
        JButton bEdt = new JButton("Editar");
        JButton bDel = new JButton("Excluir");
        bAdd.addActionListener(e -> addCliente());
        bEdt.addActionListener(e -> editCliente());
        bDel.addActionListener(e -> delCliente());
        bar.add(bAdd); bar.add(bEdt); bar.add(bDel);

        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(700, 400));

        add(bar, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        reload();
    }

    private void reload() {
        model.setRowCount(0);
        List<Cliente> list = app.clientes().findAll();
        for (Cliente c : list) {
            model.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getPontos()});
        }
    }

    private void addCliente() {
        JTextField tfNome = new JTextField();
        JTextField tfCpf = new JTextField();
        JTextField tfTel  = new JTextField();
        Object[] fields = {"Nome:", tfNome, "CPF:", tfCpf, "Telefone:", tfTel};
        int op = JOptionPane.showConfirmDialog(this, fields, "Novo Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (op == JOptionPane.OK_OPTION) {
            try {
                Cliente c = new Cliente(null, tfNome.getText().trim(), tfCpf.getText().trim(), tfTel.getText().trim(), 0);
                app.clientes().save(c);
                reload();

                if (vendaPanel != null) {
                    vendaPanel.carregarCombos();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos: " + ex.getMessage());
            }
        }
    }

    private void editCliente() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um cliente"); return; }
        Integer id = (Integer) model.getValueAt(row, 0);
        Cliente c = app.clientes().findById(id);
        if (c == null) { JOptionPane.showMessageDialog(this, "Cliente não encontrado"); return; }

        JTextField tfNome = new JTextField(c.getNome());
        JTextField tfCpf = new JTextField(c.getCpf());
        JTextField tfTel  = new JTextField(c.getTelefone());
        Object[] fields = {"Nome:", tfNome, "CPF:", tfCpf, "Telefone:", tfTel};
        int op = JOptionPane.showConfirmDialog(this, fields, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (op == JOptionPane.OK_OPTION) {
            try {
                c.setNome(tfNome.getText().trim());
                c.setCpf(tfCpf.getText().trim());
                c.setTelefone(tfTel.getText().trim());
                app.clientes().save(c);
                reload();

                if (vendaPanel != null) {
                    vendaPanel.carregarCombos();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos: " + ex.getMessage());
            }
        }
    }

    private void delCliente() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um cliente"); return; }
        Integer id = (Integer) model.getValueAt(row, 0);
        int op = JOptionPane.showConfirmDialog(this, "Excluir cliente " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            app.clientes().delete(id);
            reload();

            if (vendaPanel != null) {
                vendaPanel.carregarCombos();
            }
        }
    }
}
