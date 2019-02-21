package cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani;

import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Vyraz;

public class Sinus extends UnOperator {
    private Vyraz vyraz;

    public Sinus(Vyraz v){
        vyraz=v;
    }

    @Override
    public Vyraz provedSe(){
        return new Konstanta(Math.sin(vyraz.hodnota()));
    }

    @Override
    public void vypisSe(){
        System.out.println();
    }

    @Override
    public int priorita() {
        return 3;
    }
}
