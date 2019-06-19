package cz.cuni.mff.java.semestr4.prednaska.modules;

import cz.cuni.mff.java.semestr4.prednaska.modules.Printers.ExternalPrinter;
//import cz.cuni.mff.java.mods.printers.internal.InternalPrinter;

public class Main {
    public static void main(String[] args) {
        ExternalPrinter.print("Hello modular world");

        //InternalPrinter.print("Hello modular world");

        ExternalPrinter.printViaInternal("Hello modular world");

    }
}
