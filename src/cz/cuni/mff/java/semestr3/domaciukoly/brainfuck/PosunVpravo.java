package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.io.IOException;

public class PosunVpravo extends Prikaz {

    @Override
    public void provedSe(Pole pole) throws IOException {
        if(pole.ukazatel==(pole.p.length-1)){
            throw new IOException("Memory overrun");
        }
        else{
            pole.ukazatel++;
        }
    }


}
