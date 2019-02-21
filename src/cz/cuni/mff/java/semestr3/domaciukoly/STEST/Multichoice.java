package cz.cuni.mff.java.semestr3.domaciukoly.STEST;

import java.util.HashSet;

public class Multichoice extends Otazka {
    private int cisloOtazky_;
    private HashSet<Character> spravneOdpovedi_=new HashSet<Character>();;

    @Override
    public void nastavCislo(int cislo) {
        cisloOtazky_=cislo;
    }

    @Override
    public void nastavSpravneOdpovedi(Character... chars) {
        for(char c : chars){
            spravneOdpovedi_.add(c);

        }
    }

    @Override
    public int cisloOtazky() {
        return cisloOtazky_;
    }

    @Override
    public HashSet<Character> spravneOdpovedi() {
        return spravneOdpovedi_;
    }

}
