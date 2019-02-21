package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.io.IOException;

public class SnizHodnotu extends Prikaz {

    @Override
    public void provedSe(Pole pole) throws IOException {
        pole.p[pole.ukazatel]-=1;
    }

}
