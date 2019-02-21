package cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani;

import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Vyraz;

public class Konstanta extends Vyraz {
    private double hodnota;
    //private char jmeno;

    public Konstanta(double hodn){
        this.hodnota=hodn;
    }

    @Override
    public void vypisSe() {
        System.out.println(hodnota);
    }

    @Override
    public double hodnota() {
        return hodnota;
    }

    @Override
    public Vyraz provedSe(){
        return this;
    }
}
