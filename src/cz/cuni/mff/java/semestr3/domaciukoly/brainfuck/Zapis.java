package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.io.IOException;

public class Zapis extends Prikaz {
    @Override
    public void provedSe(Pole pole) {
        try {
            pole.p[pole.ukazatel] = (char) System.in.read();
        }
        catch(IOException e){
            System.out.println("Chyba pri cteni znaku ze std vstupu.");
        }
    }

}
