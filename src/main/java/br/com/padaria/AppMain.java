package br.com.padaria;

import br.com.padaria.view.MainFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AppMain {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("Não foi possível aplicar o Nimbus LAF: " + ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
