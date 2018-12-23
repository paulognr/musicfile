package com.paulognr.gui;

import com.paulognr.RandomUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PrincipalGUI extends JFrame {

    private JPanel panel;
    private JLabel label;
    private JButton selectFolderButtton;
    private JButton renameButton;
    private JFileChooser fileChooser;
    private JProgressBar progressBar;

    private int progressValue = 0;

    public PrincipalGUI () {
        super("Renomear arquivos");

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
        progressBar.setString("0%");
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
                BorderFactory.createEtchedBorder(), " ^-^ "));
        add(panel);

        setSize(320, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
                progressInfor.execute();
                selectFolderButtton.setEnabled(false);
                renameButton.setEnabled(false);
                renameByFolder(label.getText());

                progressBar.setValue(progressValue);
                progressBar.setString(progressValue + "%");
                label.setText("Finalizado!");
            }
        });
    }

    final SwingWorker progressInfor = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            boolean repeat = true;
            while (repeat) {
                if(progressValue == 100) {
                    repeat = false;
                }

                System.out.println("Progress value: " + progressValue);
                progressBar.setValue(progressValue);
                progressBar.setString(progressValue + "%");

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e1) {
                    //
                }
            }
            return 0;
        }
    };

    private void renameByFolder(String folderPath){
        File folderFile = new File(folderPath);

        if (folderFile.exists()) {

            List<String> files = new ArrayList<String>();
            for (File file : folderFile.listFiles()) {
                files.add(file.getName());
            }

            List<Integer> order = RandomUtils.getRandomArray(1, files.size()).stream().collect(Collectors.toList());

            for(int i = 0; i < files.size(); i++) {
                String originFileName = folderPath + File.separator + files.get(i);
                String newFileName = folderPath + File.separator + order.get(i) + "-" + files.get(i);
                File originFile = new File(originFileName);
                originFile.renameTo(new File(newFileName));

                progressValue = (int)Math.floor((i + 1) * 100 / files.size());
            }
            progressValue = 100;
        }
    }
}
