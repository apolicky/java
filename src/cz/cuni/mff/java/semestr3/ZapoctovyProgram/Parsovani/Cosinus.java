package cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani;

import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Vyraz;

public class Cosinus extends UnOperator {
    private Vyraz vyraz;

    private char mujZnak='+';

    public Cosinus(Vyraz v){
        vyraz=v;
    }

    @Override

    public Vyraz provedSe(){
        return new Konstanta(Math.cos(vyraz.hodnota()));
    }

    @Override
    public void vypisSe(){
        System.out.println("cos("+vyraz.hodnota()+")");
    }

    @Override
    public int priorita() {
        return 3;
    }
}
