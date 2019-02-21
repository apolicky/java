package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

public class ZvysHodnotu extends Prikaz {

    @Override
    public void provedSe(Pole pole) {
        pole.p[pole.ukazatel]+=1;
    }

}
