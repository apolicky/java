package cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani;

import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Vyraz;

public class Exp extends BinOperator {
    private Vyraz co_, naKolikatou_;

    private char mujZnak='^';

    public Exp(Vyraz co, Vyraz naKolikatou){
        co_=co;
        naKolikatou_=naKolikatou;
    }

    @Override
    public Vyraz provedSe(){
        return new Konstanta(Math.pow(co_.hodnota(),naKolikatou_.hodnota()));
    }

    @Override
    public void vypisSe(){
        System.out.println(co_.hodnota() + "^(" + ")");
    }

    @Override
    public int priorita() {
        return 2;
    }
}
