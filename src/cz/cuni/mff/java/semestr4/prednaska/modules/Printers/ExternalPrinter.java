package cz.cuni.mff.java.semestr4.prednaska.modules.Printers;

import cz.cuni.mff.java.semestr4.prednaska.modules.Printers.InternalPrinter;

public class ExternalPrinter {
    public static void print(String msg) {
        System.out.println("Printer: " + msg);
    }

    public static void printViaInternal(String msg) {
        InternalPrinter.print(msg);
    }
}
