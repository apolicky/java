package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

public class Iterejtr {
    private int i;
    Iterejtr(){
        i=0;
    }

    public void set(int kolik){
        i=kolik;
    }

    public int get(){
        return i;
    }

    public void plus(){
        i++;
    }

    public void minus(){
        i--;
    }
}
