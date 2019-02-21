package cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani;

import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Vyraz;

public class Minus extends BinOperator {
    private Vyraz levy,pravy;

    private char mujZnak='-';

    public Minus(Vyraz l, Vyraz p){
        levy=l;
        pravy=p;
    }

    @Override
    public Vyraz provedSe(){
        return new Konstanta(levy.hodnota()-pravy.hodnota());
    }

    @Override
    public void vypisSe(){
        System.out.println();
    }

    @Override
    public int priorita() {
        return 0;
    }
}
