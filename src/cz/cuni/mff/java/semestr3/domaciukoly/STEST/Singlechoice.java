package cz.cuni.mff.java.semestr3.domaciukoly.STEST;

import java.io.IOException;
import java.util.HashSet;

public class Singlechoice extends Otazka {
    private int cisloOtazky;
    //private char odpoved;
    private HashSet<Character> sprOdpovedi=new HashSet<>();

    @Override
    public void nastavSpravneOdpovedi(Character... chars) throws IOException {
        if(chars.length>1){
            throw new IOException("Moc odpovedi pro singlechoice.");
        }
        else{
            sprOdpovedi.add(chars[0]);
        }
    }

    @Override
    public void nastavCislo(int cislo) {
        cisloOtazky=cislo;
    }

    @Override
    public HashSet<Character> spravneOdpovedi() {
        //HashSet<Character> h = new HashSet<>();
        //h.add(odpoved);
        return sprOdpovedi;
    }

    @Override
    public int cisloOtazky() {
        return cisloOtazky;
    }
}
