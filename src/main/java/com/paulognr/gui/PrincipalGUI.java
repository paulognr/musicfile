package com.paulognr.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class PrincipalGUI extends JFrame {

    private JPanel panel;
    private JLabel label;
    private JButton selectFolderButtton;
    private JButton renameButton;
    private JFileChooser fileChooser;
    private JProgressBar progressBar;

    public PrincipalGUI () {
        super("Renomear músicas");

        panel = new JPanel(new GridBagLayout(), false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        label = new JLabel("Aguardando...");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(label, constraints);

        selectFolderButtton = new JButton("Selecioar pasta");
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        panel.add(selectFolderButtton, constraints);

        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setSize(new Dimension(100, 23));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(progressBar, constraints);

        renameButton = new JButton("Renomear");
        renameButton.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 0;
        panel.add(renameButton, constraints);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Pasta"));
        add(panel);
        pack();
        setLocationRelativeTo(null);

        loadListener();
    }

    private void loadListener() {
        selectFolderButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(panel);
                renameButton.setEnabled(true);
                label.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folder = label.getText();
                progressInfor.execute();
            }
        });
    }

    final SwingWorker progressInfor = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            for (int i = 1; i <= 100; i++) {
                try {
                    progressBar.setValue(i);
                    progressBar.setString(i + "%");
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            return 0;
        }
    };

    private void renameByFolder(String folderPath){
        File folderFile = new File(folderPath);

        if (folderFile.exists()) {

            ArrayList<String> files = new ArrayList<String>();
            for (File file : folderFile.listFiles()) {
                files.add(file.getName());
            }
        }
    }
}
