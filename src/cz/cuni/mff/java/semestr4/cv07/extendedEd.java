package cz.cuni.mff.java.semestr4.cv07;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;

public class extendedEd {


    private static String nameoffile;
    private static JFrame frame;
    private static JTextArea textArea;

    private static void createAndShowGUI() {

        nameoffile=new String();

        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(32, 80);
        JScrollPane scrollPane = new JScrollPane(textArea);

        Container pane = frame.getContentPane();

        JPanel editingSpace = new JPanel();

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        editingSpace.add(scrollPane);

        addTrayIcon();


        JPanel controls = new JPanel();

        controls = addControlButtons(controls);

        frame.setJMenuBar(createMenu());

        pane.add(editingSpace, BorderLayout.CENTER);
        pane.add(controls, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private static void addTrayIcon(){
        if (SystemTray.isSupported()) {
            SystemTray st = SystemTray.getSystemTray();

            JPopupMenu pm = new JPopupMenu();
            JMenuItem mi = new JMenuItem("Exit");
            mi.addActionListener(e -> System.exit(0));
            pm.add(mi);
            pm.add(mi);
            pm.add(mi);
            pm.add(mi);

            ImageIcon im = new ImageIcon(extendedEd.class.getResource("/home/adam/Desktop/funky_tray_icon.jpg"));

            TrayIcon ti = new TrayIcon(im.getImage(), "Test app", null);
            ti.addActionListener(e -> frame.setVisible(true));

            ti.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        pm.setInvoker(pm);
                        pm.setVisible(true);
                        pm.setLocation(e.getX(), e.getY() - pm.getHeight());
                    }
                }
            });

            try {
                st.add(ti);
            } catch (AWTException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        else{
            System.out.println("System tray not supported.");
        }

    }



    private static JPanel addControlButtons(JPanel controls){
        JButton saveButton = new JButton("Save");
        saveButton.getSize(new Dimension(5, 2));
        saveButton.addActionListener((ActionEvent e) -> {
            saveFile();
        });
        JButton saveAsButton = new JButton("Save as");
        saveAsButton.setSize(new Dimension(5,2));
        saveAsButton.addActionListener((ActionEvent e)->{
            saveFileAs();
        });

        controls.add(saveButton, BorderLayout.EAST);
        controls.add(saveAsButton,BorderLayout.EAST);

        return controls;
    }

    private static JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        //menu.add(item);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener((ActionEvent e) -> {
            saveFile();
        });
        //menu.add(save);

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener((ActionEvent l) -> {
            openFile();
        });
        menu.add(load);
        menu.add(save);
        menu.add(quit);

        mb.add(menu);


        return mb;
    }

    private static void openFile(){

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int r = fileChooser.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION)

        {
            nameoffile=(fileChooser.getSelectedFile().getAbsolutePath());
        }
        else
            textArea.setText("the user cancelled the operation");


        try (BufferedReader BR = new BufferedReader(new FileReader(nameoffile))) {
            String line = "";
            while ((line = BR.readLine()) != null) {
                textArea.append(line + "\n");
            }

        } catch (IOException e) {
            System.out.println("file not found.");
        }


    }

    private static void saveFileAs(){
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        String newnameoffile = new String();

        int r = fileChooser.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION)

        {
            newnameoffile=(fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(newnameoffile);
        }



        try (BufferedWriter BW = new BufferedWriter(new FileWriter(newnameoffile))) {

            BW.write(textArea.getText());

        } catch (IOException e) {

            System.out.println("fail during saving.");

        }

    }

    private static void saveFile() {

        try (BufferedWriter BW = new BufferedWriter(new FileWriter(nameoffile + ".chng"))) {

            BW.write(textArea.getText());
        } catch (IOException e) {
            System.out.println("fail during saving.");
        }
    }

    public static void main(String[] args) {


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}


