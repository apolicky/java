package cz.cuni.mff.java.semestr4.cv06;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class WebBrowser implements ActionListener,ItemListener {



    private JTextField textField;
    private JComboBox<String> comboBox;

    private JTextArea textArea;

    private JScrollPane scrollPane;

    private URL url;

    private Component createComponents() {



        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        /*
        textField = new JTextField();
        textField.addActionListener(this);
        panel.add(textField,BorderLayout.PAGE_START);
        */
        comboBox = new JComboBox<>();

        comboBox.setEditable(true);
        comboBox.addActionListener(this);

        panel.add(comboBox,BorderLayout.PAGE_START);


        JPanel innerPanel = new JPanel(new CardLayout());

        textArea = new JTextArea(32,80);

        scrollPane = new JScrollPane(textArea);


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane,BorderLayout.CENTER);

        return panel;



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textArea.setText("");
        textArea.append("Loading...");
        try {
            //url = new URL(textField.getText());

            url = new URL(comboBox.getSelectedItem().toString());

            comboBox.addItem(comboBox.getSelectedItem().toString());


        } catch (java.net.MalformedURLException err) {
          //  textField.setText("wrong page" + err);
        }


        //textField.setEnabled(false);


        textArea.setEnabled(false);

        SwingWorker<JTextArea, Object> worker = new SwingWorker<>() {

            JTextArea swapTextArea;


            @Override
            public JTextArea doInBackground() {

                swapTextArea = new JTextArea(32,80);


                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ine) {
                    //textField.setText("interrupted while sleeping. " + ine);
                }

                try (InputStream IS = url.openStream();
                     BufferedReader BR = new BufferedReader(new InputStreamReader(IS));) {

                    String line;

                    while ((line = BR.readLine()) != null) {
                        swapTextArea.append(line + "\n");
                    }

                } catch (IOException err) {
                    textField.setText("error while downloading");
                }

                return swapTextArea;
            }


            @Override
            public void done() {

                textArea.setText(swapTextArea.getText());
                textArea.setLineWrap(true);

                textField.setText(url + " downloaded.");
                textField.setEnabled(true);
                textArea.setEnabled(true);
            }

        };

        worker.execute();

    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        //CardLayout cl = (CardLayout) (cards.getLayout());
        //cl.show(cards, (String) evt.getItem());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Web Viewer");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WebBrowser ap = new WebBrowser();
        Component panel = ap.createComponents();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(WebBrowser::createAndShowGUI);
    }
}