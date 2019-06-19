package cz.cuni.mff.java.semestr4.cv07;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;

public class Tabulka {


    private static Component createComponents(){
        JTable table;
        String[] sloupce = new String[2];
        sloupce[0]="Element";
        sloupce[1]="Value";
        table = new JTable();
        return table;
    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Web Viewer");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Tabulka ap = new Tabulka();
       // Component panel = ap.createComponents();

       // frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Tabulka::createAndShowGUI);
    }

}
