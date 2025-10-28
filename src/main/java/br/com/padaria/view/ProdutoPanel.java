package br.com.padaria.view;

import br.com.padaria.controller.AppController;
import br.com.padaria.model.Produto;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProdutoPanel extends JPanel {

    private final AppController app;
    private final ResgatePanel resgatePanel;
    
    private final DefaultTableModel model = new DefaultTableModel(
        new Object[] {"ID", "Nome", "Preço", "Estoque", "Resgatável", "Custo (pts)"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);

    public ProdutoPanel(AppController app, ResgatePanel resgatePanel) {
        this.app = app;
        setLayout(new BorderLayout());

        JToolBar bar = new JToolBar();
        JButton bAdd = new JButton("Adicionar");
        JButton bEdt = new JButton("Editar");
        JButton bDel = new JButton("Excluir");
        bAdd.addActionListener(e -> addProduto());
        bEdt.addActionListener(e -> editProduto());
        bDel.addActionListener(e -> delProduto());
        bar.add(bAdd); bar.add(bEdt); bar.add(bDel);

        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(700, 400));

        add(bar, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        reload();
        this.resgatePanel = resgatePanel;
    }

    public void reload() {
        model.setRowCount(0);
        List<Produto> list = app.produtos().findAll();
        for (Produto p : list) {
            model.addRow(new Object[]{p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), p.isResgatavel(), p.getCustoPontos()});
        }
    }

    private void addProduto() {
        JTextField tfNome = new JTextField();
        JTextField tfPreco = new JTextField();
        JTextField tfEstq  = new JTextField();
        JCheckBox cbResg = new JCheckBox("Resgatável");
        JTextField tfPts = new JTextField("0");

        Object[] fields = {"Nome:", tfNome, "Preço:", tfPreco, "Estoque:", tfEstq, cbResg, "Custo em pontos:", tfPts};
        int op = JOptionPane.showConfirmDialog(this, fields, "Novo Produto", JOptionPane.OK_CANCEL_OPTION);
        if (op == JOptionPane.OK_OPTION) {
            try {
                Produto p = new Produto(null, tfNome.getText().trim(),
                    Double.parseDouble(tfPreco.getText().trim()),
                    Integer.parseInt(tfEstq.getText().trim()),
                    cbResg.isSelected(),
                    Integer.parseInt(tfPts.getText().trim()));
                app.produtos().save(p);
                reload();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos: " + ex.getMessage());
            }
        }
    }

    private void editProduto() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um produto"); return; }
        Integer id = (Integer) model.getValueAt(row, 0);
        Produto p = app.produtos().findById(id);
        if (p == null) { JOptionPane.showMessageDialog(this, "Produto não encontrado"); return; }

        JTextField tfNome = new JTextField(p.getNome());
        JTextField tfPreco = new JTextField(String.valueOf(p.getPreco()));
        JTextField tfEstq  = new JTextField(String.valueOf(p.getEstoque()));
        JCheckBox cbResg = new JCheckBox("Resgatável", p.isResgatavel());
        JTextField tfPts = new JTextField(String.valueOf(p.getCustoPontos()));

        Object[] fields = {"Nome:", tfNome, "Preço:", tfPreco, "Estoque:", tfEstq, cbResg, "Custo em pontos:", tfPts};
        int op = JOptionPane.showConfirmDialog(this, fields, "Editar Produto", JOptionPane.OK_CANCEL_OPTION);
        if (op == JOptionPane.OK_OPTION) {
            try {
                p.setNome(tfNome.getText().trim());
                p.setPreco(Double.parseDouble(tfPreco.getText().trim()));
                p.setEstoque(Integer.parseInt(tfEstq.getText().trim()));
                p.setResgatavel(cbResg.isSelected());
                p.setCustoPontos(Integer.parseInt(tfPts.getText().trim()));
                app.produtos().save(p);
                reload();                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos: " + ex.getMessage());
            }
        }
    }

    private void delProduto() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um produto"); return; }
        Integer id = (Integer) model.getValueAt(row, 0);
        int op = JOptionPane.showConfirmDialog(this, "Excluir produto " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            app.produtos().delete(id);
            reload();
        }
    }
}
