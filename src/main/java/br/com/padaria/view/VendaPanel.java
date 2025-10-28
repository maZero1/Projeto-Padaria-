package br.com.padaria.view;

import br.com.padaria.controller.AppController;
import br.com.padaria.model.Cliente;
import br.com.padaria.model.Produto;
import br.com.padaria.model.Venda;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VendaPanel extends JPanel {

    private final AppController app;
    private final JComboBox<Cliente> cbCliente = new JComboBox<>();
    private final JComboBox<Produto> cbProduto = new JComboBox<>();
    private final JSpinner spQtd = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private Venda vendaAtual;
    private final ProdutoPanel produtoPanel;

    private final DefaultTableModel itensModel = new DefaultTableModel(
            new Object[]{"Produto", "Qtd", "Preço", "Subtotal"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable itensTable = new JTable(itensModel);
    private final JLabel lblTotal = new JLabel("Total: R$ 0,00");

    public VendaPanel(AppController app, ProdutoPanel produtoPanel) {
        this.app = app;
        this.produtoPanel = produtoPanel;
        setLayout(new BorderLayout());

        JPanel topo = new JPanel();
        topo.add(new JLabel("Cliente:"));
        topo.add(cbCliente);
        cbCliente.addActionListener(e -> {
            Cliente c = (Cliente) cbCliente.getSelectedItem();
            if (c != null) {
                System.out.println("Cliente selecionado no combo: " + c.getNome() + " (ID: " + c.getId() + ")");
            }
        });
        JButton bNova = new JButton("Nova Venda");
        bNova.addActionListener(e -> novaVenda());
        topo.add(bNova);

        JPanel addItem = new JPanel();
        addItem.add(new JLabel("Produto:"));
        addItem.add(cbProduto);
        addItem.add(new JLabel("Qtd:"));
        addItem.add(spQtd);
        JButton bAdd = new JButton("Adicionar Item");
        bAdd.addActionListener(e -> adicionarItem());
        addItem.add(bAdd);

        JToolBar bar = new JToolBar();
        JButton bFinalizar = new JButton("Finalizar (Paga)");
        bFinalizar.addActionListener(e -> finalizar());
        bar.add(bFinalizar);

        itensTable.setPreferredScrollableViewportSize(new Dimension(700, 300));

        add(topo, BorderLayout.NORTH);
        add(addItem, BorderLayout.WEST);
        add(new JScrollPane(itensTable), BorderLayout.CENTER);
        add(bar, BorderLayout.SOUTH);
        add(lblTotal, BorderLayout.EAST);

        carregarCombos();
    }

    public void carregarCombos() {
        cbCliente.removeAllItems();
        for (Cliente c : app.clientes().findAll()) {
            cbCliente.addItem(c);
        }

        cbProduto.removeAllItems();
        for (Produto p : app.produtos().findAll()) {
            cbProduto.addItem(p);
        }
    }

    private void novaVenda() {
        Cliente c = (Cliente) cbCliente.getSelectedItem();
        System.out.println("=== NOVA VENDA ===");
        System.out.println("Cliente do combo: " + (c != null ? c.getNome() + " (ID: " + c.getId() + ")" : "NULL"));
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }
        vendaAtual = app.vendas().criarVenda(c);
        itensModel.setRowCount(0);
        lblTotal.setText("Total: R$ 0,00");
    }

    private void adicionarItem() {
        if (vendaAtual == null) {
            JOptionPane.showMessageDialog(this, "Clique em 'Nova Venda' primeiro.");
            return;
        }
        Produto p = (Produto) cbProduto.getSelectedItem();
        int qtd = (Integer) spQtd.getValue();
        if (p == null) {
            return;
        }
        try {
            app.vendas().adicionarItem(vendaAtual, p, qtd);
            itensModel.addRow(new Object[]{p.getNome(), qtd, p.getPreco(), qtd * p.getPreco()});
            atualizarTotal();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void finalizar() {
        if (vendaAtual == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma venda atual.");
            return;
        }
        try {
            Cliente clienteAtualizado = app.clientes().findById(vendaAtual.getCliente().getId());
            vendaAtual.setCliente(clienteAtualizado);
            app.vendas().finalizarPagamento(vendaAtual);
            produtoPanel.reload();
            carregarCombos();
            JOptionPane.showMessageDialog(this, "Venda paga! Pontos acumulados: "
                    + (int) Math.floor(vendaAtual.getTotal() / app.vendas().PONTOS_A_CADA_REAIS));
            vendaAtual = null;
            atualizarTotal();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void atualizarTotal() {
        double total = 0.0;
        for (int i = 0; i < itensModel.getRowCount(); i++) {
            total += (double) itensModel.getValueAt(i, 3);
        }
        lblTotal.setText(String.format("Total: R$ %.2f", total));
    }

    @Override
    public String toString() {
        return "VendaPanel";
    }
}
