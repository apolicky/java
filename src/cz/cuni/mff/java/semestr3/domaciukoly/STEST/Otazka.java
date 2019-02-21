package cz.cuni.mff.java.semestr3.domaciukoly.STEST;

import java.io.IOException;
import java.util.HashSet;
import java.util.SortedSet;

public abstract class Otazka {
    private SortedSet<Character> spravneOdpovedi;
    private int cisloOtazky_;
    abstract public int cisloOtazky(); //vraci cislo otazky podle parsoovace
    abstract public HashSet<Character> spravneOdpovedi();
    abstract public void nastavCislo(int cislo);
    abstract public void nastavSpravneOdpovedi(Character... chars) throws IOException;
}
