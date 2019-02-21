package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.io.IOException;

public abstract class Prikaz {
    public Prikaz(){}
    public abstract void provedSe(Pole pole) throws IOException;
}


