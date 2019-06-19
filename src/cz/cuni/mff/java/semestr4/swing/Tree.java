package cz.cuni.mff.java.semestr4.swing;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class Tree {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Tree");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();

        JTree tree = new JTree();
        tree.addTreeSelectionListener((TreeSelectionEvent e) -> System.out.println(e.getPath()));
        pane.add(tree);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Tree::createAndShowGUI);
    }

}
