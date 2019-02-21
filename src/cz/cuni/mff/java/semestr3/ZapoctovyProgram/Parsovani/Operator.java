package cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani;

import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Vyraz;

abstract public class Operator extends Vyraz {
    abstract public int priorita();
    public double hodnota(){
        return Double.NaN;
    }
}
