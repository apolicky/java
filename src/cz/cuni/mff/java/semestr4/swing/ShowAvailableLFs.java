package cz.cuni.mff.java.semestr4.swing;

import javax.swing.UIManager.*;
import javax.swing.*;

public class ShowAvailableLFs {


    public static void main(String[] argv) {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            System.out.println(info);
        }
    }
}
