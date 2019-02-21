package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.io.IOException;

public class Vypis extends Prikaz {
    @Override
    public void provedSe(Pole pole) throws IOException{
        System.out.print((char)pole.p[pole.ukazatel]);
    }

}
