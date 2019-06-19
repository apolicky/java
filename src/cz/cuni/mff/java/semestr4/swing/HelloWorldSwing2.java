package cz.cuni.mff.java.semestr4.swing;

import javax.swing.*;

public class HelloWorldSwing2 {

    private static void createAndShowGUI() {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HelloWorldSwing2::createAndShowGUI);
    }
}
