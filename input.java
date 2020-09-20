package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class input {
    private JTextField filename;
    private JTextField precentageToRead;
    private JTextField k;
    private JButton clusteringButton;
    private JPanel panel;


    public input() {
        clusteringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int per =Integer.parseInt(getPrecentageToRead().getText());
                int k =Integer.parseInt(getK().getText());
                Clustering c = new Clustering(k,per,getFileName().getText());
                ArrayList<Cluster> arr = new ArrayList<>();
                try {
                    arr =c.iterateDataSet();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String toprint = "";
                for(int i=0;i<arr.size();i++){
                    toprint+="Cluster"+ i+1 +"has users : "+arr.get(i).users +"\n";
                    System.out.println(arr.get(i).users);
                }
                JOptionPane.showMessageDialog(null,toprint);
                try {
                    JOptionPane.showMessageDialog(null,c.detectOutlier(arr));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
    }


    public JPanel getPanel() {
        return panel;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    public JTextField getFileName() {
        return filename;
    }

    public JTextField getPrecentageToRead() {
        return precentageToRead;
    }
    public JTextField getK() {
        return k;
    }
    public JButton getSubmitButton() {
        return clusteringButton;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clustering");
        frame.setContentPane(new input().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
