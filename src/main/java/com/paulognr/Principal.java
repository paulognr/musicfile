package com.paulognr;

import com.paulognr.gui.PrincipalGUI;

import javax.swing.*;

public class Principal {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrincipalGUI().setVisible(true);
            }
        });
    }
}
