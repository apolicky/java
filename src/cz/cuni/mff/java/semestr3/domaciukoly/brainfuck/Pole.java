
package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

public class Pole {
    public int ukazatel=0;
    public char[] p;

    public Pole(){
        p=new char[30000];
        for(char c:p){
            c=0;
        }
    }

    public Pole(int velikostPole){
        p=new char[velikostPole];
        for (char c: p
             ) {
            c=0;
        }
    }

}
