package cz.cuni.mff.java.semestr4.cv05.ex02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class ex02 {

    private static String nameoffile;
    private static JFrame frame;
    private static JTextArea textArea;

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Text Editor");


        JSplitPane splitPane = new JSplitPane();




        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JLabel label = new JLabel("Hello World");
        textArea = new JTextArea(32,80);
        JScrollPane scrollPane = new JScrollPane(textArea);

        Container pane = frame.getContentPane();



        JPanel upperPortion = new JPanel();



        try(BufferedReader BR = new BufferedReader(new FileReader(nameoffile))){
            String line="";
            while((line=BR.readLine())!=null){
                textArea.append(line+"\n");
            }

        }
        catch(IOException e){
            System.out.println("file not found.");
        }



        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        upperPortion.add(scrollPane);
        //pane.add(scrollPane,BorderLayout.CENTER);

        JPanel lowerPortion = new JPanel();

        JButton saveButton = new JButton("Save");
        saveButton.getSize(new Dimension(5,2));
        saveButton.addActionListener((ActionEvent e) -> {
            saveFile();
        });

        lowerPortion.add(saveButton,BorderLayout.EAST);

        //pane.add(saveButton);

        frame.setJMenuBar(createMenu());

        //plitPane.setTopComponent(upperPortion);
        //splitPane.setBottomComponent(lowerPortion);

        pane.add(upperPortion,BorderLayout.CENTER);
        pane.add(lowerPortion,BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private static JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem item = new JMenuItem("Quit");
        item.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        menu.add(item);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener((ActionEvent e) -> {
            saveFile();
        });
        menu.add(save);

        mb.add(menu);

        /*
        menu = new JMenu("Help");
        item = new JMenuItem("Content");
        menu.add(item);
        menu.add(new JSeparator());
        item = new JMenuItem("About");
        menu.add(item);
        mb.add(menu);
        */


        return mb;
    }

    public static void saveFile(){
        //textArea.getText()
        //System.out.println(textArea.getText()))
        try(BufferedWriter BW = new BufferedWriter(new FileWriter(nameoffile+".chng"))){

            BW.write(textArea.getText());
        }
        catch(IOException e){
            System.out.println("fail during saving.");
        }
    }

    public static void main(String[] args) {

        if(args.length==1){
            nameoffile=new String();

            nameoffile=args[0];
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
