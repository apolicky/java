package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.io.IOException;

public class PosunVlevo extends Prikaz{

    @Override
    public void provedSe(Pole pole) throws IOException {
        if(pole.ukazatel==0){
            throw new IOException("Memory underrun");
        }
        else{
            pole.ukazatel--;
        }
    }

}
