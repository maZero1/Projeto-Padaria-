package br.com.padaria.view;

import br.com.padaria.controller.AppController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.*;

public class MainFrame extends JFrame {

    private final JPanel content = new JPanel(new CardLayout());
    private final JLabel status = new JLabel("Pronto");
    private final AppController app = new AppController();

    private final ProdutoPanel produtoPanel;
    private final ClientePanel clientePanel;
    private final VendaPanel vendaPanel;
    private final ResgatePanel resgatePanel;

    public static final String CARD_PRODUTOS = "CARD_PRODUTOS";
    public static final String CARD_CLIENTES = "CARD_CLIENTES";
    public static final String CARD_VENDAS = "CARD_VENDAS";
    public static final String CARD_RESGATE = "CARD_RESGATE";

    public MainFrame() {
        super("Padaria + Fidelidade (Swing MVC)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 400));
        setLocationRelativeTo(null);
        
        resgatePanel = new ResgatePanel(app);
        produtoPanel = new ProdutoPanel(app, resgatePanel);
        vendaPanel   = new VendaPanel(app);
        clientePanel = new ClientePanel(app, vendaPanel);
        
        JToolBar tb = new JToolBar();
        JButton bProd = new JButton("Produtos");
        JButton bCli  = new JButton("Clientes");
        JButton bVen  = new JButton("Vendas");
        JButton bRes  = new JButton("Troca de Pontos");
        bProd.addActionListener(e -> showCard(CARD_PRODUTOS));
        bCli.addActionListener(e -> showCard(CARD_CLIENTES));
        bVen.addActionListener(e -> showCard(CARD_VENDAS));
        bRes.addActionListener(e -> showCard(CARD_RESGATE));
        tb.add(bProd); tb.add(bCli); tb.add(bVen); tb.add(bRes);

        content.add(produtoPanel, CARD_PRODUTOS);
        content.add(clientePanel, CARD_CLIENTES);
        content.add(vendaPanel, CARD_VENDAS);
        content.add(resgatePanel, CARD_RESGATE);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.add(status, BorderLayout.WEST);

        add(tb, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        showCard(CARD_PRODUTOS);
    }

    private void showCard(String name) {
        CardLayout cl = (CardLayout) content.getLayout();
        cl.show(content, name);
        
        if (CARD_PRODUTOS.equals(name)) {
        produtoPanel.repaint();
        } else if (CARD_CLIENTES.equals(name)) {
        clientePanel.repaint();
        } else if (CARD_VENDAS.equals(name)) {
        vendaPanel.repaint();
        } else if (CARD_RESGATE.equals(name)) {
        resgatePanel.carregarCombos();
        }

        status.setText("Exibindo: " + name);
    }
}

